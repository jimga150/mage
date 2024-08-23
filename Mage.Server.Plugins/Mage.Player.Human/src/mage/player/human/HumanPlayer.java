package mage.player.human;

import mage.MageIdentifier;
import mage.MageObject;
import mage.abilities.*;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effects;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.hint.HintUtils;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.ManaAbility;
import mage.cards.*;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.common.FilterCreatureForCombat;
import mage.filter.common.FilterCreatureForCombatBlock;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.GameImpl;
import mage.game.combat.CombatGroup;
import mage.game.draft.Draft;
import mage.game.events.DeclareAttackerEvent;
import mage.game.match.Match;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.tournament.Tournament;
import mage.players.Player;
import mage.players.PlayerImpl;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetDefender;
import mage.target.targetpointer.TargetPointer;
import mage.util.*;
import mage.utils.SystemUtil;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static mage.constants.PlayerAction.REQUEST_AUTO_ANSWER_RESET_ALL;
import static mage.constants.PlayerAction.TRIGGER_AUTO_ORDER_RESET_ALL;

/**
 * Human: server side logic to exchange game data between server app and another player's app
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class HumanPlayer extends PlayerImpl {

    private static final boolean ALLOW_USERS_TO_PUT_NON_PLAYABLE_SPELLS_ON_STACK_WORKAROUND = false; // warning, see workaround's info on usage

    // TODO: all user feedback actions executed and waited in diff threads and can't catch exeptions, e.g. on wrong code usage
    //  must catch and log such errors

    // Network and threads logic:
    // * starting point: ThreadExecutorImpl.java
    // * server executing a game's code by single game thread (named: "GAME xxx", one thread per game)
    // * data transfering goes by inner jboss threads (named: "WorkerThread xxx", one thread per client connection)
    //   - from server to client: sync mode, a single game thread uses jboss networking to send data to each player and viewer/watcher one by one
    //   - from client to server: async mode, each income command executes by shared call thread
    //
    // Client commands logic:
    // * commands can do anything in server, user, table and game contexts
    // * it's income in async mode at any time and in any order by "CALL xxx" threads
    // * there are two types of client commands:
    //   - feedback commands - must be executed and synced by GAME thread (example: answer for choose dialog, priority, etc)
    //   - other commands - can be executed at any time and don't require sync with a game and a GAME thread (example: user/skip settings, concede/cheat, chat messages)
    //
    // Feedback commands logic:
    // * income by CALL thread
    // * must be synced and executed by GAME thread
    // * keep only latest income feedback (if user sends multiple clicks/choices)
    // * HumanPlayer contains "response" object for threads sync and data exchange
    // * so sync logic:
    // * - GAME thread: open response for income command and wait (go to sleep by response.wait)
    // * - CALL thread: on closed response - waiting open status of player's response object (if it's too long then cancel the answer)
    // * - CALL thread: on opened response - save answer to player's response object and notify GAME thread about it by response.notifyAll
    // * - GAME thread: on nofify from response - check new answer value and process it (if it bad then repeat and wait the next one);
    private transient Boolean responseOpenedForAnswer = false; // GAME thread waiting new answer
    private transient long responseLastWaitingThreadId = 0;
    private final transient PlayerResponse response = new PlayerResponse();
    private final int RESPONSE_WAITING_TIME_SECS = 30; // waiting time before cancel current response
    private final int RESPONSE_WAITING_CHECK_MS = 100; // timeout for open status check

    protected static FilterCreatureForCombatBlock filterCreatureForCombatBlock = new FilterCreatureForCombatBlock();
    protected static FilterCreatureForCombat filterCreatureForCombat = new FilterCreatureForCombat();
    protected static FilterAttackingCreature filterAttack = new FilterAttackingCreature();
    protected static FilterBlockingCreature filterBlock = new FilterBlockingCreature();
    protected final Choice replacementEffectChoice;
    private static final Logger logger = Logger.getLogger(HumanPlayer.class);

    protected HashSet<String> autoSelectReplacementEffects = new LinkedHashSet<>(); // must be sorted
    protected ManaCost currentlyUnpaidMana;

    protected Set<UUID> triggerAutoOrderAbilityFirst = new HashSet<>();
    protected Set<UUID> triggerAutoOrderAbilityLast = new HashSet<>();
    protected Set<String> triggerAutoOrderNameFirst = new HashSet<>();
    protected Set<String> triggerAutoOrderNameLast = new HashSet<>();

    // auto-answer
    protected Map<String, Boolean> requestAutoAnswerId = new HashMap<>();
    protected Map<String, Boolean> requestAutoAnswerText = new HashMap<>();

    protected boolean holdingPriority;

    protected ConcurrentLinkedQueue<PlayerResponse> actionQueue = new ConcurrentLinkedQueue<>();
    protected ConcurrentLinkedQueue<PlayerResponse> actionQueueSaved = new ConcurrentLinkedQueue<>();
    protected int actionIterations = 0;
    protected boolean recordingMacro = false;
    protected boolean macroTriggeredSelectionFlag;
    protected boolean activatingMacro = false;

    public HumanPlayer(String name, RangeOfInfluence range, int skill) {
        super(name, range);
        human = true;

        replacementEffectChoice = new ChoiceImpl(true);
        replacementEffectChoice.setMessage("Choose replacement effect to resolve first");
        replacementEffectChoice.setSpecial(
                true,
                false,
                "Remember answer",
                "Choose same answer next time (you can reset saved answers by battlefield popup menu)"
        );
    }

    public HumanPlayer(final HumanPlayer player) {
        super(player);
        this.replacementEffectChoice = player.replacementEffectChoice;
        this.autoSelectReplacementEffects.addAll(player.autoSelectReplacementEffects);
        this.currentlyUnpaidMana = player.currentlyUnpaidMana;

        this.triggerAutoOrderAbilityFirst.addAll(player.triggerAutoOrderAbilityFirst);
        this.triggerAutoOrderAbilityLast.addAll(player.triggerAutoOrderAbilityLast);
        this.triggerAutoOrderNameFirst.addAll(player.triggerAutoOrderNameFirst);
        this.triggerAutoOrderNameLast.addAll(player.triggerAutoOrderNameLast);

        this.requestAutoAnswerId.putAll(player.requestAutoAnswerId);
        this.requestAutoAnswerText.putAll(player.requestAutoAnswerText);

        this.holdingPriority = player.holdingPriority;

        this.actionQueue.addAll(player.actionQueue);
        this.actionQueueSaved.addAll(player.actionQueueSaved);
        this.actionIterations = player.actionIterations;
        this.recordingMacro = player.recordingMacro;
        this.macroTriggeredSelectionFlag = player.macroTriggeredSelectionFlag;
        this.activatingMacro = player.activatingMacro;
    }

    protected boolean isExecutingMacro() {
        return !recordingMacro
                && (!actionQueue.isEmpty()
                || (actionIterations > 0 && !actionQueueSaved.isEmpty()));
    }

    /**
     * Waiting for opened response and save new value in it
     * Use it in CALL threads only, e.g. for client commands
     *
     * @return on true result game can use response value, on false result - it's outdated response
     */
    protected boolean waitResponseOpen() {
        // client send commands in async mode and can come too early
        // so if next command come too fast then wait here until game ready
        //
        // example with too early response:
        // * game must send new update to 3 users and ask user 2 for feedback answer, but user 3 is too slow
        // +0 secs: start sending data to 3 players
        // +0 secs: user 1 getting data
        // +1 secs: user 1 done
        // +1 secs: user 2 getting data
        // +3 secs: user 2 done
        // +3 secs: user 3 getting data (it's slow)
        // +4 secs: user 2 sent answer 1 (but game closed for it, so waiting)
        // +5 secs: user 2 sent answer 2 (he can't see changes after answer 1, so trying again - server must keep only latest answer)
        // +8 secs: user 3 done
        // +8 secs: game start wating a new answer from user 2
        // +8 secs: game find answer

        int currentTimesWaiting = 0;
        int maxTimesWaiting = RESPONSE_WAITING_TIME_SECS * 1000 / RESPONSE_WAITING_CHECK_MS;
        long currentThreadId = Thread.currentThread().getId();
        // it's a latest response
        responseLastWaitingThreadId = currentThreadId;
        while (!responseOpenedForAnswer && canRespond()) {
            if (responseLastWaitingThreadId != currentThreadId) {
                // there is another latest response, so cancel current
                return false;
            }

            // keep waiting
            currentTimesWaiting++;
            if (currentTimesWaiting > maxTimesWaiting) {
                // game frozen, possible reasons:
                // * ANOTHER player lost connection and GAME thread trying to send data to him
                // * current player send answer, but lost connect after it
                // * game thread stops and lost
                String possibleReason;
                if (response.getActiveAction() == null) {
                    possibleReason = "maybe connection problem with another player/watcher";
                } else {
                    possibleReason = "something wrong with your priority on " + response.getActiveAction();
                }
                logger.warn(String.format("Game frozen in waitResponseOpen for %d secs. User: %s; reason: %s; game: %s",
                        RESPONSE_WAITING_CHECK_MS * currentTimesWaiting / 1000,
                        this.getName(),
                        possibleReason,
                        response.getActiveGameInfo()
                ));
                return false;
            }

            try {
                Thread.sleep(RESPONSE_WAITING_CHECK_MS);
            } catch (InterruptedException ignore) {
            }
        }

        return true; // can use new value
    }

    protected boolean pullResponseFromQueue(Game game) {
        if (actionQueue.isEmpty() && actionIterations > 0 && !actionQueueSaved.isEmpty()) {
            actionQueue = new ConcurrentLinkedQueue<>(actionQueueSaved);
            actionIterations--;
//            logger.info("MACRO iteration: " + actionIterations);
        }
        PlayerResponse action = actionQueue.poll();
        if (action != null) {
            if (action.getString() != null
                    && action.getString().equals("resolveStack")) {
                action = actionQueue.poll();
                if (action == null) {
                    return false;
                }
                sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_STACK_RESOLVED, game, null);
            }
            //waitResponseOpen(); // it's a macro action, no need it here?
            synchronized (response) {
                response.copyFrom(action);
                response.notifyAll();
                macroTriggeredSelectionFlag = false;
                return true;
            }
        }
        return false;
    }

    /**
     * Prepare priority player for new feedback, call it for every choose cycle before waitForResponse
     */
    protected void prepareForResponse(Game game) {
        ThreadUtils.ensureRunInGameThread();

        // prepare priority player
        // on null - it's a discard in cleanaup and other non-user code, so don't change it here at that moment
        // TODO: must research null use case
        if (game.getState().getPriorityPlayerId() != null) {
            if (getId() == null) {
                logger.fatal("Player with no ID: " + name);
                this.quit(game);
                return;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Setting game priority for " + getId() + " [" + DebugUtil.getMethodNameWithSource(2) + ']');
            }
            game.getState().setPriorityPlayerId(getId());
        }

        responseOpenedForAnswer = false;
    }

    /**
     * Waiting feedback from priority player
     *
     * @param game
     */
    protected void waitForResponse(Game game) {
        ThreadUtils.ensureRunInGameThread();
        ;

        if (isExecutingMacro()) {
            pullResponseFromQueue(game);
//            logger.info("MACRO pull from queue: " + response.toString());
//            try {
//                TimeUnit.MILLISECONDS.sleep(1000);
//            } catch (InterruptedException e) {
//            }
            return;
        }

        boolean loop = true;
        while (loop) {
            // start waiting for next answer
            response.clear();
            response.setActiveAction(game, DebugUtil.getMethodNameWithSource(2));
            game.resumeTimer(getTurnControlledBy());
            responseOpenedForAnswer = true;

            loop = false;
            synchronized (response) { // TODO: synchronized response smells bad here, possible deadlocks? Need research
                try {
                    response.wait(); // start waiting a response.notifyAll command from CALL thread (client answer)
                } catch (InterruptedException ignore) {
                } finally {
                    responseOpenedForAnswer = false;
                    game.pauseTimer(getTurnControlledBy());
                }
            }

            // async command: concede by any player
            // game recived immediately response on OTHER player concede -- need to process end game and continue to wait
            // TODO: is it possible to break choose dialog of current player (check it in multiplayer)?
            if (response.getAsyncWantConcede()) {
                ((GameImpl) game).checkConcede();
                if (game.hasEnded()) {
                    return;
                }
                // wait another answer
                if (canRespond()) {
                    loop = true;
                }
            }

            // async command: cheat by current player
            if (response.getAsyncWantCheat()) {
                // execute cheats and continue
                SystemUtil.executeCheatCommands(game, null, this);
                game.fireUpdatePlayersEvent(); // need force to game update for new possible data
                // wait another answer
                if (canRespond()) {
                    loop = true;
                }
            }
        }

        if (recordingMacro && !macroTriggeredSelectionFlag) {
            actionQueueSaved.add(new PlayerResponse(response));
        }
    }

    @Override
    public boolean chooseMulligan(Game game) {
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        while (canRespond()) {
            int nextHandSize = game.mulliganDownTo(playerId);
            String cardsCountInfo = nextHandSize + (nextHandSize == 1 ? " card" : " cards");
            String message;
            if (getHand().size() > nextHandSize) {
                // pay
                message = "Mulligan " + HintUtils.prepareText("down to " + cardsCountInfo, Color.YELLOW) + "?";
            } else {
                // free
                message = "Mulligan " + HintUtils.prepareText("for free", Color.GREEN) + ", draw another " + cardsCountInfo + "?";
            }
            Map<String, Serializable> options = new HashMap<>();
            options.put("UI.left.btn.text", "Mulligan");
            options.put("UI.right.btn.text", "Keep");

            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireAskPlayerEvent(playerId, new MessageToClient(message), null, options);
            }
            waitForResponse(game);

            if (response.getBoolean() != null) {
                return response.getBoolean();
            }
        }

        return false;
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Ability source, Game game) {
        return this.chooseUse(outcome, message, null, "Yes", "No", source, game);
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText, Ability source, Game game) {
        if (game.inCheckPlayableState()) {
            return true;
        }

        MessageToClient messageToClient = new MessageToClient(message, secondMessage);
        Map<String, Serializable> options = new HashMap<>(2);
        String autoAnswerMessage = message; // Removes self-referential text for the purposes of auto-answering
        if (trueText != null) {
            options.put("UI.left.btn.text", trueText);
        }
        if (falseText != null) {
            options.put("UI.right.btn.text", falseText);
        }
        if (source != null) {
            //options.put(Constants.Option.ORIGINAL_ID, "")
            String sourceName = getRelatedObjectName(source, game);
            if (sourceName != null) {
                autoAnswerMessage = autoAnswerMessage.replace(sourceName, "{this}");
            }
        }

        options.put(Constants.Option.AUTO_ANSWER_MESSAGE, autoAnswerMessage);

        // auto-answer
        Boolean answer = null;
        if (source != null) {
            // ability + text
            answer = requestAutoAnswerId
                    .get(source.getOriginalId() + "#" + autoAnswerMessage);
        }
        if (answer == null) {
            // text
            answer = requestAutoAnswerText.get(autoAnswerMessage);
        }
        if (answer != null) {
            return answer;
        }

        while (canRespond()) {
            if (messageToClient.getSecondMessage() == null) {
                messageToClient.setSecondMessage(getRelatedObjectName(source, game));
            }

            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireAskPlayerEvent(playerId, messageToClient, source, options);
            }
            waitForResponse(game);

            if (response.getBoolean() != null) {
                return response.getBoolean();
            }
        }

        return false;
    }

    private String getRelatedObjectName(Ability source, Game game) {
        if (source != null) {
            return getRelatedObjectName(source.getSourceId(), game);
        }
        return null;
    }

    private String getRelatedObjectName(UUID sourceId, Game game) {
        MageObject mageObject = game.getObject(sourceId);
        if (mageObject != null) {
            return mageObject.getLogName();
        }
        return null;
    }

    private String addSecondLineWithObjectName(String message, UUID sourceId, Game game) {
        if (sourceId != null) {
            MageObject mageObject = game.getPermanent(sourceId);
            if (mageObject == null) {
                mageObject = game.getCard(sourceId);
            }
            if (mageObject != null) {
                message += "<div style='font-size:11pt'>" + mageObject.getLogName() + "</div>";
            }
        }
        return message;
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> effectsMap, Map<String, MageObject> objectsMap, Game game) {
        if (gameInCheckPlayableState(game, true)) { // ignore warning logs until double call for TAPPED_FOR_MANA will be fix
            return 0;
        }

        if (effectsMap.size() <= 1) {
            return 0;
        }

        // use auto-choice:
        // - uses "always first" logic (choose in same order as user answer saves)
        // - search same effects by text (object name [id]: rules)
        // - autoSelectReplacementEffects is sorted set
        // - must get "same settings" option between cycle/response (user can change it by preferences)

        boolean useSameSettings = getControllingPlayersUserData(game).isUseSameSettingsForReplacementEffects();
        if (!autoSelectReplacementEffects.isEmpty()) {
            for (String autoText : autoSelectReplacementEffects) {
                int count = 0;
                // find effect with same saved text
                for (String effectKey : effectsMap.keySet()) {
                    String currentText = prepareReplacementText(effectsMap.get(effectKey), useSameSettings);
                    if (currentText.equals(autoText)) {
                        return count;
                    }
                    count++;
                }
            }
        }

        replacementEffectChoice.clearChoice();
        replacementEffectChoice.getChoices().clear();
        replacementEffectChoice.getKeyChoices().clear();
        effectsMap.forEach((key, value) -> {
            MageObject object = objectsMap.getOrDefault(key, null);
            replacementEffectChoice.withItem(
                    key,
                    value,
                    null,
                    object != null ? ChoiceHintType.GAME_OBJECT : null,
                    object != null ? object.getId().toString() : null
            );
        });

        // if same choices then select first
        int differentChoices = 0;
        String lastChoice = "";
        for (String value : replacementEffectChoice.getKeyChoices().values()) {
            if (!lastChoice.equalsIgnoreCase(value)) {
                lastChoice = value;
                differentChoices++;
            }
        }
        if (differentChoices <= 1) {
            return 0;
        }

        while (canRespond()) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                replacementEffectChoice.onChooseStart(game, playerId);
                game.fireChooseChoiceEvent(playerId, replacementEffectChoice);
            }
            waitForResponse(game);

            logger.debug("Choose effect: " + response.getString());
            if (response.getString() != null) {
                // save auto-choice (effect's text)
                if (response.getString().startsWith("#")) {
                    replacementEffectChoice.setChoiceByKey(response.getString().substring(1), true);
                    if (replacementEffectChoice.isChosen()) {
                        // put auto-choice to the end
                        useSameSettings = getControllingPlayersUserData(game).isUseSameSettingsForReplacementEffects();
                        String effectText = prepareReplacementText(replacementEffectChoice.getChoiceValue(), useSameSettings);
                        autoSelectReplacementEffects.remove(effectText);
                        autoSelectReplacementEffects.add(effectText);
                    }
                } else {
                    replacementEffectChoice.setChoiceByKey(response.getString(), false);
                }

                if (replacementEffectChoice.getChoiceKey() != null) {
                    int index = 0;
                    for (String key : effectsMap.keySet()) {
                        if (replacementEffectChoice.getChoiceKey().equals(key)) {
                            replacementEffectChoice.onChooseEnd(game, playerId, replacementEffectChoice.getChoiceKey());
                            return index;
                        }
                        index++;
                    }
                }
            }
        }

        return 0;
    }

    private String prepareReplacementText(String fullText, boolean useSameSettingsForDifferentObjects) {
        // remove object id from the rules text (example: object [abd]: rules -> object : rules)
        if (useSameSettingsForDifferentObjects) {
            fullText = fullText.replaceAll("\\[\\w+\\]", "");
        }
        return fullText;
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        if (choice.isKeyChoice() && choice.getKeyChoices().isEmpty()) {
            throw new IllegalArgumentException("Wrong code usage. Key choices must contains some values");
        }
        if (!choice.isKeyChoice() && choice.getChoices().isEmpty()) {
            throw new IllegalArgumentException("Wrong code usage. Choices must contains some values");
        }

        // Try to autopay for mana
        if (Outcome.PutManaInPool == outcome && choice.isManaColorChoice() && currentlyUnpaidMana != null) {
            // Check check if the spell being paid for cares about the color of mana being paid
            // See: https://github.com/magefree/mage/issues/9070
            boolean caresAboutManaColor = false;
            if (!game.getStack().isEmpty() && game.getStack().getFirst() instanceof Spell) {
                Spell spellBeingCast = (Spell) game.getStack().getFirst();
                if (!spellBeingCast.isResolving() && spellBeingCast.getControllerId().equals(this.getId())) {
                    CardImpl card = (CardImpl) game.getCard(spellBeingCast.getSourceId());
                    caresAboutManaColor = card.caresAboutManaColor(game);
                }
            }

            if (!caresAboutManaColor && ManaUtil.tryToAutoSelectAManaColor(choice, currentlyUnpaidMana)) {
                return true;
            }
        }

        while (canRespond()) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                choice.onChooseStart(game, playerId);
                game.fireChooseChoiceEvent(playerId, choice);
            }
            waitForResponse(game);

            String val = response.getString();
            if (val != null && !val.isEmpty()) {
                if (choice.isKeyChoice()) {
                    choice.setChoiceByKey(val);
                } else {
                    choice.setChoice(val);
                }
                choice.onChooseEnd(game, playerId, val);
                return true;
            } else if (!choice.isRequired()) {
                // cancel
                choice.onChooseEnd(game, playerId, null);
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game) {
        return choose(outcome, target, source, game, null);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game, Map<String, Serializable> options) {
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        // choose one or multiple permanents
        UUID abilityControllerId = playerId;
        if (target.getTargetController() != null
                && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }
        if (options == null) {
            options = new HashMap<>();
        }

        while (canRespond()) {
            Set<UUID> possibleTargetIds = target.possibleTargets(abilityControllerId, source, game);
            if (possibleTargetIds == null || possibleTargetIds.isEmpty()) {
                return target.getTargets().size() >= target.getNumberOfTargets();
            }

            boolean required = target.isRequired(source != null ? source.getSourceId() : null, game);
            if (target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }

            UUID responseId = required ? target.tryToAutoChoose(abilityControllerId, source, game) : null;

            // responseId is null if a choice couldn't be automatically made
            if (responseId == null) {
                List<UUID> chosenTargets = target.getTargets();
                options.put("chosenTargets", (Serializable) chosenTargets);

                prepareForResponse(game);
                if (!isExecutingMacro()) {
                    game.fireSelectTargetEvent(getId(), new MessageToClient(target.getMessage(game), getRelatedObjectName(source, game)), possibleTargetIds, required, getOptions(target, options));
                }
                waitForResponse(game);
                responseId = getFixedResponseUUID(game);
            }

            if (responseId != null) {
                // selected some target

                // remove selected
                if (target.getTargets().contains(responseId)) {
                    target.remove(responseId);
                    continue;
                }

                if (!possibleTargetIds.contains(responseId)) {
                    continue;
                }

                if (target instanceof TargetPermanent) {
                    if (((TargetPermanent) target).canTarget(abilityControllerId, responseId, source, game, false)) {
                        target.add(responseId, game);
                        if (target.doneChoosing(game)) {
                            return true;
                        }
                    }
                } else {
                    MageObject object = game.getObject(source);
                    if (object instanceof Ability) {
                        if (target.canTarget(responseId, (Ability) object, game)) {
                            if (target.getTargets().contains(responseId)) { // if already included remove it with
                                target.remove(responseId);
                            } else {
                                target.addTarget(responseId, (Ability) object, game);
                                if (target.doneChoosing(game)) {
                                    return true;
                                }
                            }
                        }
                    } else if (target.canTarget(responseId, game)) {
                        if (target.getTargets().contains(responseId)) { // if already included remove it with
                            target.remove(responseId);
                        } else {
                            target.addTarget(responseId, null, game);
                            if (target.doneChoosing(game)) {
                                return true;
                            }
                        }
                    }
                }
            } else {
                // send other command like cancel or done (??sends other commands like concede??)

                // auto-complete on all selected
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }

                // cancel/done button
                if (!required) {
                    return false;
                }
            }
        }

        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        // choose one or multiple targets
        UUID abilityControllerId = playerId;
        if (target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }

        Map<String, Serializable> options = new HashMap<>();

        while (canRespond()) {
            Set<UUID> possibleTargetIds = target.possibleTargets(abilityControllerId, source, game);
            boolean required = target.isRequired(source != null ? source.getSourceId() : null, game);
            if (possibleTargetIds.isEmpty()
                    || target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }

            UUID responseId = required ? target.tryToAutoChoose(abilityControllerId, source, game) : null;

            // responseId is null if a choice couldn't be automatically made
            if (responseId == null) {


                List<UUID> chosenTargets = target.getTargets();
                options.put("chosenTargets", (Serializable) chosenTargets);

                prepareForResponse(game);
                if (!isExecutingMacro()) {
                    game.fireSelectTargetEvent(getId(), new MessageToClient(target.getMessage(game), getRelatedObjectName(source, game)),
                            possibleTargetIds, required, getOptions(target, options));
                }
                waitForResponse(game);
                responseId = getFixedResponseUUID(game);
            }

            if (responseId != null) {
                // remove selected
                if (target.getTargets().contains(responseId)) {
                    target.remove(responseId);
                    continue;
                }

                if (possibleTargetIds.contains(responseId)) {
                    if (target.canTarget(abilityControllerId, responseId, source, game)) {
                        target.addTarget(responseId, source, game);
                        if (target.doneChoosing(game)) {
                            return true;
                        }
                    }
                }
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!required) {
                    return false;
                }
            }
        }

        return false;
    }

    private Map<String, Serializable> getOptions(Target target, Map<String, Serializable> options) {
        if (options == null) {
            options = new HashMap<>();
        }
        if (target.getTargets().size() >= target.getNumberOfTargets()
                && !options.containsKey("UI.right.btn.text")) {
            options.put("UI.right.btn.text", "Done");
        }
        options.put("targetZone", target.getZone());
        return options;
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        // choose one or multiple cards
        if (cards == null || cards.isEmpty()) {
            return false;
        }

        UUID abilityControllerId;
        if (target.getTargetController() != null
                && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        } else {
            abilityControllerId = playerId;
        }

        while (canRespond()) {
            boolean required = target.isRequired(source != null ? source.getSourceId() : null, game);
            int count = cards.count(target.getFilter(), abilityControllerId, source, game);
            if (count == 0
                    || target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }

            List<UUID> chosenTargets = target.getTargets();
            List<UUID> possibleTargets = new ArrayList<>();
            for (UUID cardId : cards) {
                if (target.canTarget(abilityControllerId, cardId, source, cards, game)) {
                    possibleTargets.add(cardId);
                }
            }
            // if nothing to choose then show dialog (user must see non selectable items and click on any of them)
            if (required && possibleTargets.isEmpty()) {
                required = false;
            }

            UUID responseId = required ? target.tryToAutoChoose(abilityControllerId, source, game, possibleTargets)
                    : null;

            if (responseId == null) {
                Map<String, Serializable> options = getOptions(target, null);
                options.put("chosenTargets", (Serializable) chosenTargets);
                if (!possibleTargets.isEmpty()) {
                    options.put("possibleTargets", (Serializable) possibleTargets);
                }

                prepareForResponse(game);
                if (!isExecutingMacro()) {
                    game.fireSelectTargetEvent(playerId, new MessageToClient(target.getMessage(game)), cards, required, options);
                }
                waitForResponse(game);

                responseId = getFixedResponseUUID(game);
            }

            if (responseId != null) {
                if (target.getTargets().contains(responseId)) { // if already included remove it with
                    target.remove(responseId);
                } else {
                    if (target.canTarget(abilityControllerId, responseId, source, cards, game)) {
                        target.add(responseId, game);
                        if (target.doneChoosing(game)) {
                            return true;
                        }
                    }
                }
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!required) {
                    return false;
                }
            }
        }

        return false;
    }

    // choose one or multiple target cards
    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        if (cards == null || cards.isEmpty()) {
            return false;
        }

        UUID abilityControllerId;
        if (target.getTargetController() != null
                && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        } else {
            abilityControllerId = playerId;
        }

        while (canRespond()) {
            boolean required = target.isRequiredExplicitlySet() ? target.isRequired() : target.isRequired(source);
            int count = cards.count(target.getFilter(), abilityControllerId, source, game);
            if (count == 0
                    || target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }

            List<UUID> possibleTargets = new ArrayList<>();
            for (UUID cardId : cards) {
                if (target.canTarget(abilityControllerId, cardId, source, cards, game)) {
                    possibleTargets.add(cardId);
                }
            }
            // if nothing to choose then show dialog (user must see non selectable items and click on any of them)
            if (required && possibleTargets.isEmpty()) {
                required = false;
            }

            UUID responseId = required ? target.tryToAutoChoose(abilityControllerId, source, game, possibleTargets)
                    : null;

            if (responseId == null) {
                List<UUID> chosenTargets = target.getTargets();
                Map<String, Serializable> options = getOptions(target, null);
                options.put("chosenTargets", (Serializable) chosenTargets);

                if (!possibleTargets.isEmpty()) {
                    options.put("possibleTargets", (Serializable) possibleTargets);
                }

                prepareForResponse(game);
                if (!isExecutingMacro()) {
                    game.fireSelectTargetEvent(playerId, new MessageToClient(target.getMessage(game), getRelatedObjectName(source, game)), cards, required, options);
                }
                waitForResponse(game);

                responseId = getFixedResponseUUID(game);
            }

            if (responseId != null) {
                if (target.getTargets().contains(responseId)) { // if already included remove it
                    target.remove(responseId);
                } else if (target.canTarget(abilityControllerId, responseId, source, cards, game)) {
                    target.addTarget(responseId, source, game);
                    if (target.doneChoosing(game)) {
                        return true;
                    }
                }
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!required) {
                    return false;
                }
            }
        }

        return false;
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
        // choose amount
        // human can choose or un-choose MULTIPLE targets at once
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        if (source == null) {
            return false;
        }

        UUID abilityControllerId = playerId;
        if (target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }

        int amountTotal = target.getAmountTotal(game, source);
        if (amountTotal == 0) {
            return false; // nothing to distribute
        }
        MultiAmountType multiAmountType = source.getRule().contains("damage") ? MultiAmountType.DAMAGE : MultiAmountType.P1P1;

        // 601.2d. If the spell requires the player to divide or distribute an effect (such as damage or counters)
        // among one or more targets, the player announces the division.
        // Each of these targets must receive at least one of whatever is being divided.

        // Two steps logic:
        // 1. Select targets
        // 2. Distribute amount between selected targets

        // 1. Select targets
        while (canRespond()) {
            Set<UUID> possibleTargetIds = target.possibleTargets(abilityControllerId, source, game);
            boolean required = target.isRequired(source.getSourceId(), game);
            if (possibleTargetIds.isEmpty()
                    || target.getSize() >= target.getNumberOfTargets()) {
                required = false;
            }

            UUID responseId = required ? target.tryToAutoChoose(abilityControllerId, source, game) : null;

            // responseId is null if a choice couldn't be automatically made
            if (responseId == null) {
                List<UUID> chosenTargets = target.getTargets();
                List<UUID> possibleTargets = new ArrayList<>();
                for (UUID targetId : possibleTargetIds) {
                    if (target.canTarget(abilityControllerId, targetId, source, game)) {
                        possibleTargets.add(targetId);
                    }
                }
                // if nothing to choose then show dialog (user must see non selectable items and click on any of them)
                if (required && possibleTargets.isEmpty()) {
                    required = false;
                }

                // selected
                Map<String, Serializable> options = getOptions(target, null);
                options.put("chosenTargets", (Serializable) chosenTargets);
                if (!possibleTargets.isEmpty()) {
                    options.put("possibleTargets", (Serializable) possibleTargets);
                }

                prepareForResponse(game);
                if (!isExecutingMacro()) {
                    String multiType = multiAmountType == MultiAmountType.DAMAGE ? " to divide %d damage" : " to distribute %d counters";
                    String message = target.getMessage(game) + String.format(multiType, amountTotal);
                    game.fireSelectTargetEvent(playerId, new MessageToClient(message, getRelatedObjectName(source, game)), possibleTargetIds, required, options);
                }
                waitForResponse(game);

                responseId = getFixedResponseUUID(game);
            }

            if (responseId != null) {
                if (target.contains(responseId)) {
                    // unselect
                    target.remove(responseId);
                } else if (possibleTargetIds.contains(responseId)
                        && target.canTarget(abilityControllerId, responseId, source, game)
                        && target.getSize() < amountTotal) {
                    // select
                    target.addTarget(responseId, source, game);
                }
            } else if (!required) {
                break;
            }
        }

        // no targets to choose or disconnected
        List<UUID> targets = target.getTargets();
        if (targets.isEmpty()) {
            return false;
        }

        // 2. Distribute amount between selected targets

        // if only one target, it gets full amount, no possible choice
        if (targets.size() == 1) {
            target.setTargetAmount(targets.get(0), amountTotal, source, game);
            return true;
        }

        // if number of targets equal to amount, each get 1, no possible choice
        if (targets.size() == amountTotal) {
            for (UUID targetId : targets) {
                target.setTargetAmount(targetId, 1, source, game);
            }
            return true;
        }

        // should not be able to have more targets than amount, but in such case it's illegal
        if (targets.size() > amountTotal) {
            target.clearChosen();
            return false;
        }

        // prepare targets list with p/t or life stats (cause that's dialog used for damage distribute)
        List<String> targetNames = new ArrayList<>();
        for (UUID targetId : targets) {
            MageObject targetObject = game.getObject(targetId);
            if (targetObject != null) {
                targetNames.add(String.format("%s, P/T: %d/%d",
                        targetObject.getIdName(),
                        targetObject.getPower().getValue(),
                        targetObject.getToughness().getValue()
                ));
            } else {
                Player player = game.getPlayer(targetId);
                if (player != null) {
                    targetNames.add(String.format("%s, life: %d", player.getName(), player.getLife()));
                } else {
                    targetNames.add("ERROR, unknown target " + targetId.toString());
                }
            }
        }

        // ask and assign new amount
        List<Integer> targetValues = getMultiAmount(outcome, targetNames, 1, amountTotal, multiAmountType, game);
        for (int i = 0; i < targetValues.size(); i++) {
            int newAmount = targetValues.get(i);
            UUID targetId = targets.get(i);
            if (newAmount <= 0) {
                // remove target
                target.remove(targetId);
            } else {
                // set amount
                target.setTargetAmount(targetId, newAmount, source, game);
            }
        }
        return true;
    }

    @Override
    public boolean priority(Game game) {
        passed = false;
        // TODO: fix problems with turn under out control:
        // TODO: change pass and other states like passedUntilStackResolved for controlling player, not for "this"
        // TODO: check and change all "this" to controling player calls, many bugs with hand, mana, skips - https://github.com/magefree/mage/issues/2088
        // TODO: use controlling player in all choose dialogs (and canRespond too, what's with take control of player AI?!)
        if (canRespond()) {
            HumanPlayer controllingPlayer = this;
            if (isGameUnderControl()) { // TODO: must be ! to get real controlling player
                Player player = game.getPlayer(getTurnControlledBy());
                if (player instanceof HumanPlayer) {
                    controllingPlayer = (HumanPlayer) player;
                }
            }

            // TODO: check that all skips and stops used from real controlling player
            //  like holdingPriority (is it a bug here?)
            if (getJustActivatedType() != null && !holdingPriority) {
                if (controllingPlayer.getUserData().isPassPriorityCast()
                        && getJustActivatedType() == AbilityType.SPELL) {
                    setJustActivatedType(null);
                    pass(game);
                    return false;
                }
                if (controllingPlayer.getUserData().isPassPriorityActivation()
                        && getJustActivatedType().isNonManaActivatedAbility()) {
                    setJustActivatedType(null);
                    pass(game);
                    return false;
                }
            }

            // STOP conditions (temporary stop without skip reset)
            boolean quickStop = false;
            if (isGameUnderControl()) { // TODO: remove to enable quick stop for controlling player
                // if was attacked - always stop BEFORE blocker step (to cast extra spells)
                if (game.getTurnStepType() == PhaseStep.DECLARE_ATTACKERS
                        && game.getCombat().getPlayerDefenders(game).contains(playerId)) {
                    FilterCreatureForCombatBlock filter = filterCreatureForCombatBlock.copy();
                    filter.add(new ControllerIdPredicate(playerId));
                    // stop skip on any/zero permanents available
                    int possibleBlockersCount = game.getBattlefield().count(filter, playerId, null, game);
                    boolean canStopOnAny = possibleBlockersCount != 0 && getControllingPlayersUserData(game).getUserSkipPrioritySteps().isStopOnDeclareBlockersWithAnyPermanents();
                    boolean canStopOnZero = possibleBlockersCount == 0 && getControllingPlayersUserData(game).getUserSkipPrioritySteps().isStopOnDeclareBlockersWithZeroPermanents();
                    quickStop = canStopOnAny || canStopOnZero;
                }
            }

            // SKIP - use the skip actions only if the player itself controls its turn
            if (!quickStop && isGameUnderControl()) { // TODO: remove to enable skips for controlling player

                if (passedAllTurns || passedTurnSkipStack) {
                    if (passWithManaPoolCheck(game)) {
                        return false;
                    }
                }

                if (passedUntilEndStepBeforeMyTurn) {
                    if (game.getTurnStepType() != PhaseStep.END_TURN) {
                        // other step
                        if (passWithManaPoolCheck(game)) {
                            return false;
                        }
                    } else {
                        // end step - search yourself
                        PlayerList playerList = game.getState().getPlayerList(playerId);
                        if (!playerList.getPrevious().equals(game.getActivePlayerId())) {
                            if (passWithManaPoolCheck(game)) {
                                return false;
                            }
                        } else {
                            // stop
                            passedUntilEndStepBeforeMyTurn = false;
                        }
                    }
                }

                if (game.getStack().isEmpty()) {
                    // empty stack

                    boolean dontCheckPassStep = false;
                    if (passedUntilStackResolved) { // Don't skip to next step with this action. It always only resolves a stack. If stack is empty it does nothing.
                        passedUntilStackResolved = false;
                        dontCheckPassStep = true;
                    }

                    if (passedTurn
                            || passedTurnSkipStack) {
                        if (passWithManaPoolCheck(game)) {
                            return false;
                        }
                    }

                    if (passedUntilNextMain) {
                        if (game.getTurnStepType() == PhaseStep.POSTCOMBAT_MAIN
                                || game.getTurnStepType() == PhaseStep.PRECOMBAT_MAIN) {
                            // it's main step
                            if (!skippedAtLeastOnce
                                    || (!playerId.equals(game.getActivePlayerId())
                                    && !controllingPlayer.getUserData().getUserSkipPrioritySteps().isStopOnAllMainPhases())) {
                                skippedAtLeastOnce = true;
                                if (passWithManaPoolCheck(game)) {
                                    return false;
                                }
                            } else {
                                dontCheckPassStep = true;
                                passedUntilNextMain = false; // reset skip action
                            }
                        } else {
                            skippedAtLeastOnce = true;
                            if (passWithManaPoolCheck(game)) {
                                return false;
                            }
                        }
                    }

                    if (passedUntilEndOfTurn) {
                        if (game.getTurnStepType() == PhaseStep.END_TURN) {
                            // it's end of turn step
                            if (!skippedAtLeastOnce
                                    || (playerId.equals(game.getActivePlayerId())
                                    && !controllingPlayer
                                    .getUserData()
                                    .getUserSkipPrioritySteps()
                                    .isStopOnAllEndPhases())) {
                                skippedAtLeastOnce = true;
                                if (passWithManaPoolCheck(game)) {
                                    return false;
                                }
                            } else {
                                dontCheckPassStep = true;
                                passedUntilEndOfTurn = false; // reset skip action
                            }
                        } else {
                            skippedAtLeastOnce = true;
                            if (passWithManaPoolCheck(game)) {
                                return false;
                            }
                        }
                    }

                    if (!dontCheckPassStep
                            && checkPassStep(game, controllingPlayer)) {
                        if (passWithManaPoolCheck(game)) {
                            return false;
                        }
                    }

                } else {
                    // non empty stack
                    boolean haveNewObjectsOnStack = !Objects.equals(dateLastAddedToStack, game.getStack().getDateLastAdded());
                    dateLastAddedToStack = game.getStack().getDateLastAdded();
                    if (passedUntilStackResolved) {
                        if (haveNewObjectsOnStack
                                && (playerId.equals(game.getActivePlayerId())
                                && controllingPlayer
                                .getUserData()
                                .getUserSkipPrioritySteps()
                                .isStopOnStackNewObjects())) {
                            // new objects on stack -- disable "pass until stack resolved"
                            passedUntilStackResolved = false;
                        } else {
                            // no new objects on stack -- go to next priority
                        }
                    }
                    if (passedUntilStackResolved) {
                        if (passWithManaPoolCheck(game)) {
                            return false;
                        }
                    }
                }
            }

            while (canRespond()) {
                holdingPriority = false;

                prepareForResponse(game);
                if (!isExecutingMacro()) {
                    game.firePriorityEvent(playerId);
                }
                waitForResponse(game);

                if (game.executingRollback()) {
                    return true;
                }

                if (response.getBoolean() != null
                        || response.getInteger() != null) {
                    if (!activatingMacro && passWithManaPoolCheck(game)) {
                        return false;
                    } else {
                        if (activatingMacro) {
                            synchronized (actionQueue) {
                                actionQueue.notifyAll();
                            }
                        }
                        continue;
                    }
                }
                break;
            }

            UUID responseId = getFixedResponseUUID(game);
            if (response.getString() != null
                    && response.getString().equals("special")) {
                activateSpecialAction(game, null);
            } else if (responseId != null) {
                boolean result = false;
                MageObject object = game.getObject(responseId);
                if (object != null) {
                    Zone zone = game.getState().getZone(object.getId());
                    if (zone != null) {
                        // look at card or try to cast/activate abilities
                        Map<UUID, ActivatedAbility> useableAbilities = new LinkedHashMap<>();

                        Player actingPlayer = null;
                        if (playerId.equals(game.getPriorityPlayerId())) {
                            actingPlayer = this;
                        } else if (getPlayersUnderYourControl().contains(game.getPriorityPlayerId())) {
                            actingPlayer = game.getPlayer(game.getPriorityPlayerId());
                        }
                        if (actingPlayer != null) {
                            useableAbilities = actingPlayer.getPlayableActivatedAbilities(object, zone, game);

                            // GUI: workaround to enable users to put spells on stack without real available mana
                            // (without highlighting, like it was in old versions before June 2020)
                            // Reason: some gain ability adds cost modification and other things to spells on stack only,
                            // e.g. xmage can't find playable ability before put that spell on stack (wtf example: Chief Engineer,
                            // see ConvokeTest)
                            // TODO: it's a BAD workaround  -- users can't see that card/ability is broken and will not report to us, AI can't play that ability too
                            // Enable it on massive broken cards/abilities only or for manual tests
                            if (ALLOW_USERS_TO_PUT_NON_PLAYABLE_SPELLS_ON_STACK_WORKAROUND) {
                                if (object instanceof Card) {
                                    for (Ability ability : ((Card) object).getAbilities(game)) {
                                        if (ability instanceof SpellAbility && ((SpellAbility) ability).canActivate(actingPlayer.getId(), game).canActivate()
                                                || ability instanceof PlayLandAbility) {
                                            useableAbilities.putIfAbsent(ability.getId(), (ActivatedAbility) ability);
                                        }
                                    }
                                }
                            }
                        }

                        if (object instanceof Card
                                && ((Card) object).isFaceDown(game)
                                && lookAtFaceDownCard((Card) object, game, useableAbilities.size())) {
                            result = true;
                        } else {
                            if (!useableAbilities.isEmpty()) {
                                activateAbility(useableAbilities, object, game);
                                result = true;
                            }
                        }
                    }
                }
                return result;
            } else {
                return response.getManaType() == null;
            }
            return true;
        }

        return false;
    }

    private UUID getFixedResponseUUID(Game game) {
        // user can clicks on any side of multi/double faces card, but game must process click to main card all the time
        MageObject object = game.getObject(response.getUUID());

        // mdf cards
        if (object instanceof ModalDoubleFacedCardHalf) {
            if (!Zone.BATTLEFIELD.equals(game.getState().getZone(object.getId()))
                    && !Zone.STACK.equals(game.getState().getZone(object.getId()))) {
                return ((ModalDoubleFacedCardHalf) object).getMainCard().getId();
            }
        }

        return response.getUUID();
    }

    private boolean checkPassStep(Game game, HumanPlayer controllingPlayer) {
        try {

            if (playerId.equals(game.getActivePlayerId())) {
                return !controllingPlayer.getUserData().getUserSkipPrioritySteps().getYourTurn().isPhaseStepSet(game.getTurnStepType());
            } else {
                return !controllingPlayer.getUserData().getUserSkipPrioritySteps().getOpponentTurn().isPhaseStepSet(game.getTurnStepType());
            }
        } catch (NullPointerException ex) {
            if (controllingPlayer.getUserData() != null) {
                if (controllingPlayer.getUserData().getUserSkipPrioritySteps() != null) {
                    if (game.getStep() != null) {
                        if (game.getTurnStepType() == null) {
                            logger.error("game.getTurnStepType() == null");
                        }
                    } else {
                        logger.error("game.getStep() == null");
                    }
                } else {
                    logger.error("UserData.getUserSkipPrioritySteps == null");
                }
            } else {
                logger.error("UserData == null");
            }
        }
        return false;
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(java.util.List<TriggeredAbility> abilities, Game game) {
        // choose triggered abilitity from list
        if (gameInCheckPlayableState(game)) {
            return null;
        }

        // automatically order triggers with same ability, rules text, and targets
        String autoOrderRuleText = null;
        Ability autoOrderAbility = null;
        boolean autoOrderUse = getControllingPlayersUserData(game).isAutoOrderTrigger();
        while (canRespond()) {
            // try to set trigger auto order
            List<TriggeredAbility> abilitiesWithNoOrderSet = new ArrayList<>();
            List<TriggeredAbility> abilitiesOrderLast = new ArrayList<>();
            for (TriggeredAbility ability : abilities) {
                if (triggerAutoOrderAbilityFirst.contains(ability.getOriginalId())) {
                    return ability;
                }
                MageObject object = game.getObject(ability.getSourceId());
                String rule = ability.getRule(object != null ? object.getName() : null);
                if (triggerAutoOrderNameFirst.contains(rule)) {
                    return ability;
                }
                if (triggerAutoOrderAbilityLast.contains(ability.getOriginalId())) {
                    // multiple instances of same trigger has same originalId, no need to select order for it
                    abilitiesOrderLast.add(ability);
                    continue;
                }
                if (triggerAutoOrderNameLast.contains(rule)) {
                    abilitiesOrderLast.add(ability);
                    continue;
                }
                if (autoOrderUse) {
                    // multiple triggers with same rule text will be auto-ordered if possible
                    // if different, must use choose dialog
                    if (autoOrderRuleText == null) {
                        // first trigger, store rule text and ability to compare subsequent triggers
                        autoOrderRuleText = rule;
                        autoOrderAbility = ability;
                    } else if (!rule.equals(autoOrderRuleText)) {
                        // disable auto order if rule text is different
                        autoOrderUse = false;
                    } else {
                        // disable auto order if targets are different
                        Effects effects1 = autoOrderAbility.getEffects();
                        Effects effects2 = ability.getEffects();
                        if (effects1.size() != effects2.size()) {
                            autoOrderUse = false;
                        } else {
                            for (int i = 0; i < effects1.size(); i++) {
                                TargetPointer targetPointer1 = effects1.get(i).getTargetPointer();
                                TargetPointer targetPointer2 = effects2.get(i).getTargetPointer();
                                List<UUID> targets1 = (targetPointer1 == null) ? new ArrayList<>() : targetPointer1.getTargets(game, autoOrderAbility);
                                List<UUID> targets2 = (targetPointer2 == null) ? new ArrayList<>() : targetPointer2.getTargets(game, ability);
                                if (!targets1.equals(targets2)) {
                                    autoOrderUse = false;
                                    break;
                                }
                            }
                        }
                    }
                }
                abilitiesWithNoOrderSet.add(ability);
            }

            if (abilitiesWithNoOrderSet.isEmpty()) {
                // user can send diff abilities to the last, will be selected by "first" like first ordered ability above
                return abilitiesOrderLast.stream().findFirst().orElse(null);
            }

            if (abilitiesWithNoOrderSet.size() == 1 || autoOrderUse) {
                return abilitiesWithNoOrderSet.iterator().next();
            }

            // runtime check: lost triggers for GUI
            List<Ability> processingAbilities = new ArrayList<>(abilitiesWithNoOrderSet);
            processingAbilities.addAll(abilitiesOrderLast);

            if (abilities.size() != processingAbilities.size()) {
                throw new IllegalStateException(String.format("Choose dialog lost some of the triggered abilities:\n"
                                + "Must %d:\n%s\n"
                                + "Has %d:\n%s",
                        abilities.size(),
                        abilities.stream().map(Ability::getRule).collect(Collectors.joining("\n")),
                        processingAbilities.size(),
                        processingAbilities.stream().map(Ability::getRule).collect(Collectors.joining("\n"))
                ));
            }

            macroTriggeredSelectionFlag = true;
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetTriggeredAbilityEvent(playerId, "Pick triggered ability (goes to the stack first)", abilitiesWithNoOrderSet);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                for (TriggeredAbility ability : abilitiesWithNoOrderSet) {
                    if (ability.getId().equals(responseId)
                            || (!macroTriggeredSelectionFlag
                            && ability.getSourceId().equals(responseId))) {
                        if (recordingMacro) {
                            PlayerResponse tResponse = new PlayerResponse();
                            tResponse.setUUID(ability.getSourceId());
                            actionQueueSaved.add(tResponse);
                            logger.debug("Adding Triggered Ability Source: " + tResponse);
                        }
                        macroTriggeredSelectionFlag = false;
                        return ability;
                    }
                }
            }
        }

        macroTriggeredSelectionFlag = false;
        return null;
    }

    @Override
    public boolean playMana(Ability abilityToCast, ManaCost unpaid, String promptText, Game game) {
        payManaMode = true;
        try {
            return playManaHandling(abilityToCast, unpaid, promptText, game);
        } finally {
            payManaMode = false;
        }
    }

    protected boolean playManaHandling(Ability abilityToCast, ManaCost unpaid, String promptText, Game game) {
        // choose mana to pay (from permanents or from pool)
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        // TODO: make canRespond cycle?
        if (canRespond()) {
            Map<String, Serializable> options = new HashMap<>();
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.firePlayManaEvent(playerId, "Pay " + promptText, options);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (response.getBoolean() != null) {
                // cancel
                return false;
            } else if (responseId != null) {
                // pay from mana object
                playManaAbilities(responseId, abilityToCast, unpaid, game);
            } else if (response.getString() != null && response.getString().equals("special")) {
                // pay from special action
                if (unpaid instanceof ManaCostsImpl) {
                    activateSpecialAction(game, unpaid);
                }
            } else if (response.getManaType() != null) {
                // pay from own mana pool
                if (response.getManaPlayerId().equals(this.getId())) {
                    this.getManaPool().unlockManaType(response.getManaType());
                }
                // TODO: Handle if mana pool
            }

            return true;
        }

        return false; // must return false to stop mana pay cycle with non responding player
    }

    /**
     * Gets the number of times the user wants to repeat their macro
     *
     * @param game
     * @return
     */
    public int announceRepetitions(Game game) {
        if (gameInCheckPlayableState(game)) {
            return 0;
        }

        int xValue = 0;
        while (canRespond()) {
            prepareForResponse(game);
            game.fireGetAmountEvent(playerId, "How many times do you want to repeat your shortcut?", 0, 999);
            waitForResponse(game);

            if (response.getInteger() != null) {
                break;
            }
        }

        if (response.getInteger() != null) {
            xValue = response.getInteger();
        }
        return xValue;
    }

    /**
     * Gets the amount of mana the player want to spent for a x spell
     *
     * @param min
     * @param max
     * @param message
     * @param ability
     * @param game
     * @return
     */
    @Override
    public int announceXMana(int min, int max, String message, Game game, Ability ability) {
        if (gameInCheckPlayableState(game)) {
            return 0;
        }

        int xValue = 0;
        while (canRespond()) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetAmountEvent(playerId, message + CardUtil.getSourceLogName(game, ability), min, max);
            }
            waitForResponse(game);

            if (response.getInteger() != null) {
                break;
            }
        }

        if (response.getInteger() != null) {
            xValue = response.getInteger();
        }
        return xValue;
    }

    @Override
    public int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variableCost) {
        if (gameInCheckPlayableState(game)) {
            return 0;
        }

        int xValue = 0;
        while (canRespond()) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetAmountEvent(playerId, message, min, max);
            }
            waitForResponse(game);

            if (response.getInteger() != null) {
                break;
            }
        }

        if (response.getInteger() != null) {
            xValue = response.getInteger();
        }
        return xValue;
    }

    protected void playManaAbilities(UUID objectId, Ability abilityToCast, ManaCost unpaid, Game game) {
        MageObject object = game.getObject(objectId);
        if (object == null) {
            return;
        }

        // GUI: for user's information only - check if mana abilities allows to use here (getUseableManaAbilities already filter it)
        // Reason: when you use special mana ability then normal mana abilities will be restricted to pay. Users
        // can't see lands as playable and must know the reason (if they click on land then they get that message)
        if (abilityToCast.getAbilityType() == AbilityType.SPELL) {
            Spell spell = game.getStack().getSpell(abilityToCast.getSourceId());
            boolean haveManaAbilities = object.getAbilities().stream().anyMatch(ManaAbility.class::isInstance);
            if (spell != null && !spell.isResolving() && haveManaAbilities) {
                switch (spell.getCurrentActivatingManaAbilitiesStep()) {
                    // if you used special mana ability like convoke then normal mana abilities will be restricted to use, see Convoke for details
                    case BEFORE:
                    case NORMAL:
                        break;
                    case AFTER:
                        game.informPlayer(this, "You can no longer use activated mana abilities to pay for the current spell (special mana pay already used). Cancel and recast the spell to activate mana abilities first.");
                        return;
                }
            }
        }

        Zone zone = game.getState().getZone(object.getId());
        if (zone != null) {
            Map<UUID, ActivatedManaAbilityImpl> useableAbilities = getUseableManaAbilities(object, zone, game);
            if (!useableAbilities.isEmpty()) {
                // Added to ensure that mana is not being autopaid for spells that care about the color of mana being paid
                // See https://github.com/magefree/mage/issues/9070
                boolean caresAboutManaColor = false;
                if (abilityToCast.getAbilityType() == AbilityType.SPELL) {
                    CardImpl card = (CardImpl) game.getCard(abilityToCast.getSourceId());
                    caresAboutManaColor = card.caresAboutManaColor(game);
                }

                // Don't auto-pay if the spell cares about the color
                if (!caresAboutManaColor) {
                    useableAbilities = ManaUtil.tryToAutoPay(unpaid, useableAbilities); // eliminates other abilities if one fits perfectly
                }
                currentlyUnpaidMana = unpaid;
                activateAbility(useableAbilities, object, game);
                currentlyUnpaidMana = null;
            }
        }
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        if (gameInCheckPlayableState(game)) {
            return;
        }

        FilterCreatureForCombat filter = filterCreatureForCombat.copy();
        filter.add(new ControllerIdPredicate(attackingPlayerId));

        while (canRespond()) {
            java.util.List<UUID> possibleAttackers = new ArrayList<>();
            for (Permanent possibleAttacker : game.getBattlefield().getActivePermanents(filter, attackingPlayerId, game)) {
                if (possibleAttacker.canAttack(null, game)) {
                    possibleAttackers.add(possibleAttacker.getId());
                }
            }

            // skip declare attack step
            // old version:
            // - passedAllTurns, passedUntilEndStepBeforeMyTurn: always skipped
            // - other: on disabled option skipped
            if (passedAllTurns
                    || passedUntilEndStepBeforeMyTurn
                    || (!getControllingPlayersUserData(game)
                    .getUserSkipPrioritySteps()
                    .isStopOnDeclareAttackers()
                    && (passedTurn
                    || passedTurnSkipStack
                    || passedUntilEndOfTurn
                    || passedUntilNextMain))) {
                if (checkIfAttackersValid(game)) {
                    return;
                }
            }

            /*
            // new version:
            // - all: on disabled option skipped (if attackers selected)
            if (!getControllingPlayersUserData(game)
                    .getUserSkipPrioritySteps()
                    .isStopOnDeclareAttackers()
                    && (possibleAttackers.size() > 0)) {
                if (checkIfAttackersValid(game)) {
                    return;
                }
            }
             */
            Map<String, Serializable> options = new HashMap<>();
            options.put(Constants.Option.POSSIBLE_ATTACKERS, (Serializable) possibleAttackers);
            if (!possibleAttackers.isEmpty()) {
                options.put(Constants.Option.SPECIAL_BUTTON, "All attack");
            }

            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectEvent(playerId, "Select attackers", options);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (response.getString() != null
                    && response.getString().equals("special")) { // All attack
                setStoredBookmark(game.bookmarkState());
                UUID attackedDefender = null;
                if (game.getCombat().getDefenders().size() > 1) {
                    attackedDefender = selectDefenderForAllAttack(game.getCombat().getDefenders(), game);
                } else if (game.getCombat().getDefenders().size() == 1) {
                    attackedDefender = game.getCombat().getDefenders().iterator().next();
                }
                for (Permanent attacker : game.getBattlefield().getAllActivePermanents(filterCreatureForCombat, getId(), game)) {
                    if (game.getContinuousEffects().checkIfThereArePayCostToAttackBlockEffects(
                            new DeclareAttackerEvent(attackedDefender, attacker.getId(), attacker.getControllerId()), game)) {
                        continue;
                    }
                    // if attacker needs a specific defender to attack so select that one instead
                    if (game.getCombat().getCreaturesForcedToAttack().containsKey(attacker.getId())) {
                        Set<UUID> possibleDefenders = game.getCombat().getCreaturesForcedToAttack().get(attacker.getId());
                        if (!possibleDefenders.isEmpty() && !possibleDefenders.contains(attackedDefender)) {
                            declareAttacker(attacker.getId(), possibleDefenders.iterator().next(), game, false);
                            continue;
                        }
                    }
                    // attack selected default defender
                    declareAttacker(attacker.getId(), attackedDefender, game, false);
                }
            } else if (response.getInteger() != null) { // F-Key
                if (checkIfAttackersValid(game)) {
                    return;
                }
            } else if (response.getBoolean() != null) { // ok button
                if (checkIfAttackersValid(game)) {
                    return;
                }
            } else if (responseId != null) {
                Permanent attacker = game.getPermanent(responseId);
                if (attacker != null) {
                    if (filterCreatureForCombat.match(attacker, playerId, null, game)) {
                        selectDefender(game.getCombat().getDefenders(), attacker.getId(), game);
                    } else if (filterAttack.match(attacker, playerId, null, game) && game.getStack().isEmpty()) {
                        removeAttackerIfPossible(game, attacker);
                    }
                }
            }
        }
    }

    private boolean checkIfAttackersValid(Game game) {
        if (!game.getCombat().getCreaturesForcedToAttack().isEmpty()) {
            if (!game.getCombat().getAttackers().containsAll(game.getCombat().getCreaturesForcedToAttack().keySet())) {
                int forcedAttackers = 0;
                StringBuilder sb = new StringBuilder();
                for (UUID creatureId : game.getCombat().getCreaturesForcedToAttack().keySet()) {
                    boolean validForcedAttacker = false;
                    if (game.getCombat().getAttackers().contains(creatureId)) {
                        Set<UUID> possibleDefender = game.getCombat().getCreaturesForcedToAttack().get(creatureId);
                        if (possibleDefender.isEmpty()
                                || possibleDefender.contains(game.getCombat().getDefenderId(creatureId))) {
                            validForcedAttacker = true;
                        }
                    }
                    if (validForcedAttacker) {
                        forcedAttackers++;
                    } else {
                        Permanent creature = game.getPermanent(creatureId);
                        if (creature != null) {
                            sb.append(creature.getIdName()).append(' ');
                        }
                    }

                }
                if (game.getCombat().getMaxAttackers() > forcedAttackers) {
                    int requireToAttack = Math.min(game.getCombat().getMaxAttackers() - forcedAttackers, game.getCombat().getCreaturesForcedToAttack().size() - forcedAttackers);
                    String message = (requireToAttack == 1 ? " more attacker that is " : " more attackers that are ")
                            + "forced to attack.\nCreature"
                            + (requireToAttack == 1 ? "" : "s") + " forced to attack: ";
                    game.informPlayer(this, sb.insert(0, message)
                            .insert(0, requireToAttack)
                            .insert(0, "You have to attack with ").toString());
                    return false;
                }
            }
        }
        // check if enough attackers are declared
        // check if players have to be attacked
        Set<UUID> playersToAttackIfAble = new HashSet<>();
        for (Map.Entry<RequirementEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRequirementEffects(null, true, game).entrySet()) {
            RequirementEffect effect = entry.getKey();
            for (Ability ability : entry.getValue()) {
                UUID playerToAttack = effect.playerMustBeAttackedIfAble(ability, game);
                if (playerToAttack != null) {
                    playersToAttackIfAble.add(playerToAttack);
                }
            }
        }
        if (!playersToAttackIfAble.isEmpty()) {
            Set<UUID> checkPlayersToAttackIfAble = new HashSet<>(playersToAttackIfAble);
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                checkPlayersToAttackIfAble.remove(combatGroup.getDefendingPlayerId());
            }

            for (UUID forcedToAttackId : checkPlayersToAttackIfAble) {
                Player forcedToAttack = game.getPlayer(forcedToAttackId);

                for (Permanent attacker : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, getId(), game)) {

                    if (game.getContinuousEffects().checkIfThereArePayCostToAttackBlockEffects(
                            new DeclareAttackerEvent(forcedToAttackId, attacker.getId(), attacker.getControllerId()), game)) {
                        continue;
                    }
                    // if attacker needs a specific defender to attack so select that one instead
                    if (game.getCombat().getCreaturesForcedToAttack().containsKey(attacker.getId())) {
                        Set<UUID> possibleDefenders = game.getCombat().getCreaturesForcedToAttack().get(attacker.getId());
                        if (!possibleDefenders.isEmpty() && !possibleDefenders.contains(forcedToAttackId)) {
                            continue;
                        }
                    }
                    UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(attacker.getId(), game);
                    if (playersToAttackIfAble.contains(defendingPlayerId)) {
                        // already attacks other player taht has to be attacked
                        continue;
                    }
                    if (defendingPlayerId != null || attacker.canAttackInPrinciple(forcedToAttackId, game)) {
                        game.informPlayer(this, "You are forced to attack " + forcedToAttack.getName() + " or a controlled planeswalker e.g. with " + attacker.getIdName() + ".");
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void removeAttackerIfPossible(Game game, Permanent attacker) {
        for (Map.Entry entry : game.getContinuousEffects().getApplicableRequirementEffects(attacker, false, game).entrySet()) {
            RequirementEffect effect = (RequirementEffect) entry.getKey();
            if (effect.mustAttack(game)) {
                if (game.getCombat().getMaxAttackers() >= game.getCombat().getCreaturesForcedToAttack().size()
                        && game.getCombat().getDefenders().size() == 1) {
                    return; // we can't change creatures forced to attack if only one possible defender exists and all forced creatures can attack
                }
            }
        }
        game.getCombat().removeAttacker(attacker.getId(), game);
    }

    /**
     * Selects a defender for an attacker and adds the attacker to combat
     *
     * @param defenders  - list of possible defender
     * @param attackerId - UUID of attacker
     * @param game
     * @return
     */
    protected boolean selectDefender(Set<UUID> defenders, UUID attackerId, Game game) {
        boolean forcedToAttack = false;
        Set<UUID> possibleDefender = game.getCombat().getCreaturesForcedToAttack().get(attackerId);
        if (possibleDefender != null) {
            forcedToAttack = true;
        }
        if (possibleDefender == null
                || possibleDefender.isEmpty()) {
            possibleDefender = defenders;
        }
        if (possibleDefender.size() == 1) {
            declareAttacker(attackerId, possibleDefender.iterator().next(), game, true);
            return true;
        } else {
            TargetDefender target = new TargetDefender(possibleDefender);
            if (forcedToAttack) {
                StringBuilder sb = new StringBuilder(target.getTargetName());
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null) {
                    sb.append(" (").append(attacker.getName()).append(')');
                    target.withTargetName(sb.toString());
                }
            }
            if (chooseTarget(Outcome.Damage, target, null, game)) {
                UUID defenderId = getFixedResponseUUID(game);
                declareAttacker(attackerId, defenderId, game, true);
                return true;
            }
        }
        return false;
    }

    protected UUID selectDefenderForAllAttack(Set<UUID> defenders, Game game) {
        TargetDefender target = new TargetDefender(defenders);
        if (chooseTarget(Outcome.Damage, target, null, game)) {
            return getFixedResponseUUID(game);
        }
        return null;
    }

    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        if (gameInCheckPlayableState(game)) {
            return;
        }

        FilterCreatureForCombatBlock filter = filterCreatureForCombatBlock.copy();
        filter.add(new ControllerIdPredicate(defendingPlayerId));

        // stop skip on any/zero permanents available
        int possibleBlockersCount = game.getBattlefield().count(filter, playerId, source, game);
        boolean canStopOnAny = possibleBlockersCount != 0 && getControllingPlayersUserData(game).getUserSkipPrioritySteps().isStopOnDeclareBlockersWithAnyPermanents();
        boolean canStopOnZero = possibleBlockersCount == 0 && getControllingPlayersUserData(game).getUserSkipPrioritySteps().isStopOnDeclareBlockersWithZeroPermanents();

        // skip declare blocker step
        // as opposed to declare attacker - it can be skipped by ANY skip button TODO: make same for declare attackers and rework skip buttons (normal and forced)
        boolean skipButtonActivated = passedAllTurns
                || passedUntilEndStepBeforeMyTurn
                || passedTurn
                || passedUntilEndOfTurn
                || passedUntilNextMain;
        if (skipButtonActivated && !canStopOnAny && !canStopOnZero) {
            return;
        }

        while (canRespond()) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                Map<String, Serializable> options = new HashMap<>();
                java.util.List<UUID> possibleBlockers = game.getBattlefield().getActivePermanents(filter, playerId, game).stream()
                        .map(p -> p.getId())
                        .collect(Collectors.toList());
                options.put(Constants.Option.POSSIBLE_BLOCKERS, (Serializable) possibleBlockers);
                game.fireSelectEvent(playerId, "Select blockers", options);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (response.getBoolean() != null) {
                return;
            } else if (response.getInteger() != null) {
                return;
            } else if (responseId != null) {
                Permanent blocker = game.getPermanent(responseId);
                if (blocker != null) {
                    boolean removeBlocker = false;
                    // does not block yet and can block or can block more attackers
                    if (filter.match(blocker, playerId, source, game)) {
                        selectCombatGroup(defendingPlayerId, blocker.getId(), game);
                    } else if (filterBlock.match(blocker, playerId, source, game)
                            && game.getStack().isEmpty()) {
                        removeBlocker = true;
                    }

                    if (removeBlocker) {
                        game.getCombat().removeBlocker(blocker.getId(), game);
                    }
                }
            }
        }
    }

    @Override
    public UUID chooseAttackerOrder(java.util.List<Permanent> attackers, Game game) {
        if (gameInCheckPlayableState(game)) {
            return null;
        }

        while (canRespond()) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(playerId, "Pick attacker", attackers, true);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                for (Permanent perm : attackers) {
                    if (perm.getId().equals(responseId)) {
                        return perm.getId();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public UUID chooseBlockerOrder(java.util.List<Permanent> blockers, CombatGroup combatGroup, java.util.List<UUID> blockerOrder, Game game) {
        if (gameInCheckPlayableState(game)) {
            return null;
        }

        while (canRespond()) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(playerId, "Pick blocker", blockers, true);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                for (Permanent perm : blockers) {
                    if (perm.getId().equals(responseId)) {
                        return perm.getId();
                    }
                }
            }
        }
        return null;
    }

    protected void selectCombatGroup(UUID defenderId, UUID blockerId, Game game) {
        if (gameInCheckPlayableState(game)) {
            return;
        }
        TargetAttackingCreature target = new TargetAttackingCreature();

        // TODO: add canRespond cycle?
        if (!canRespond()) {
            return;
        }

        UUID responseId = null;

        prepareForResponse(game);
        if (!isExecutingMacro()) {
            // possible attackers to block
            Set<UUID> attackers = target.possibleTargets(playerId, null, game);
            Permanent blocker = game.getPermanent(blockerId);
            Set<UUID> possibleTargets = new HashSet<>();
            for (UUID attackerId : attackers) {
                CombatGroup group = game.getCombat().findGroup(attackerId);
                if (group != null && blocker != null && group.canBlock(blocker, game)) {
                    possibleTargets.add(attackerId);
                }
            }
            if (possibleTargets.size() == 1) {
                responseId = possibleTargets.stream().iterator().next();
            } else {
                game.fireSelectTargetEvent(playerId, new MessageToClient("Select attacker to block", getRelatedObjectName(blockerId, game)),
                        possibleTargets, false, getOptions(target, null));
                waitForResponse(game);
            }
        }

        if (responseId == null) {
            responseId = getFixedResponseUUID(game);
        }

        if (response.getBoolean() != null) {
            // do nothing
        } else if (responseId != null) {
            CombatGroup group = game.getCombat().findGroup(responseId);
            if (group != null) {
                // check if already blocked, if not add
                if (!group.getBlockers().contains(blockerId)) {
                    declareBlocker(defenderId, blockerId, responseId, game);
                } else { // else remove from block
                    game.getCombat().removeBlockerGromGroup(blockerId, group, game);
                }
            }
        }
    }

    @Override
    public void assignDamage(int damage, java.util.List<UUID> targets, String singleTargetName, UUID attackerId, Ability source, Game game) {
        int remainingDamage = damage;
        while (remainingDamage > 0 && canRespond()) {
            Target target = new TargetAnyTarget();
            target.withNotTarget(true);
            if (singleTargetName != null) {
                target.withTargetName(singleTargetName);
            }
            this.choose(Outcome.Damage, target, source, game);
            if (targets.isEmpty() || targets.contains(target.getFirstTarget())) {
                int damageAmount = this.getAmount(0, remainingDamage, "Select amount", game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.damage(damageAmount, attackerId, source, game, false, true);
                    remainingDamage -= damageAmount;
                } else {
                    Player player = game.getPlayer(target.getFirstTarget());
                    if (player != null) {
                        player.damage(damageAmount, attackerId, source, game);
                        remainingDamage -= damageAmount;
                    }
                }
            }
        }
    }

    @Override
    public int getAmount(int min, int max, String message, Game game) {
        if (gameInCheckPlayableState(game)) {
            return 0;
        }

        while (canRespond()) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetAmountEvent(playerId, message, min, max);
            }
            waitForResponse(game);

            if (response.getInteger() != null) {
                break;
            }
        }

        if (response.getInteger() != null) {
            return response.getInteger();
        } else {
            return 0;
        }
    }

    @Override
    public List<Integer> getMultiAmountWithIndividualConstraints(
            Outcome outcome,
            List<MultiAmountMessage> messages,
            int min,
            int max,
            MultiAmountType type,
            Game game
    ) {
        int needCount = messages.size();
        List<Integer> defaultList = MultiAmountType.prepareDefaltValues(messages, min, max);
        if (needCount == 0 || (needCount == 1 && min == max)
                || messages.stream().map(m -> m.min == m.max).reduce(true, Boolean::logicalAnd)) {
            return defaultList;
        }

        if (gameInCheckPlayableState(game)) {
            return defaultList;
        }

        List<Integer> answer = null;
        while (canRespond()) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                Map<String, Serializable> options = new HashMap<>(2);
                options.put("title", type.getTitle());
                options.put("header", type.getHeader());
                if (type.isCanCancel()) {
                    options.put("canCancel", true);
                }
                game.fireGetMultiAmountEvent(playerId, messages, min, max, options);
            }
            waitForResponse(game);

            // waiting correct values only
            if (response.getString() != null) {
                answer = MultiAmountType.parseAnswer(response.getString(), messages, min, max, false);
                if (MultiAmountType.isGoodValues(answer, messages, min, max)) {
                    break;
                } else {
                    // it's not normal: can be cheater or a wrong GUI checks
                    answer = null;
                    logger.error(String.format("GUI return wrong MultiAmountType values: %d %d %d - %s", needCount, min, max, response.getString()));
                    game.informPlayer(this, "Error, you must enter correct values.");
                }
            } else if (type.isCanCancel() && response.getBoolean() != null) {
                answer = null;
                break;
            }
        }

        if (answer != null) {
            return answer;
        } else if (type.isCanCancel()) {
            // cancel
            return null;
        } else {
            // something wrong, e.g. player disconnected
            return defaultList;
        }
    }

    @Override
    public void sideboard(Match match, Deck deck) {
        match.fireSideboardEvent(playerId, deck);
    }

    @Override
    public void construct(Tournament tournament, Deck deck) {
        tournament.fireConstructEvent(playerId);
    }

    @Override
    public void pickCard(java.util.List<Card> cards, Deck deck, Draft draft) {
        draft.firePickCardEvent(playerId);
    }

    /**
     * Activate special action (normal or mana)
     *
     * @param game
     * @param unpaidForManaAction - set unpaid for mana actions like convoke
     */
    protected void activateSpecialAction(Game game, ManaCost unpaidForManaAction) {
        if (gameInCheckPlayableState(game)) {
            return;
        }

        if (!canRespond()) {
            return;
        }

        Map<UUID, SpecialAction> specialActions = game.getState().getSpecialActions().getControlledBy(playerId, unpaidForManaAction != null);
        if (!specialActions.isEmpty()) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetChoiceEvent(playerId, name, null, new ArrayList<>(specialActions.values()));
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            SpecialAction specialAction = specialActions.getOrDefault(responseId, null);
            if (specialAction != null) {
                activateAbility(specialAction, game);
            }
        }
    }

    @Override
    public boolean activateAbility(ActivatedAbility ability, Game game) {
        getManaPool().setStock(); // needed for the "mana already in the pool has to be used manually" option
        return super.activateAbility(ability, game);
    }

    protected void activateAbility(Map<UUID, ? extends ActivatedAbility> abilities, MageObject object, Game game) {
        if (gameInCheckPlayableState(game)) {
            return;
        }

        if (!canRespond()) {
            return;
        }

        if (abilities.size() == 1
                && suppressAbilityPicker(abilities.values().iterator().next(), game)) {
            ActivatedAbility ability = abilities.values().iterator().next();
            if (!ability.getTargets().isEmpty()
                    || !(ability.getCosts().size() == 1
                    && ability.getCosts().get(0) instanceof SacrificeSourceCost)
                    || !(ability.getCosts().size() == 2
                    && ability.getCosts().get(0) instanceof TapSourceCost
                    && ability.getCosts().get(0) instanceof SacrificeSourceCost)) {
                activateAbility(ability, game);
                return;
            }
        }
        if (userData.isUseFirstManaAbility() && object instanceof Permanent && object.isLand(game)) {
            ActivatedAbility ability = abilities.values().iterator().next();
            if (ability.isActivatedAbility() && ability.isManaAbility()) {
                activateAbility(ability, game);
                return;
            }
        }

        String message = "Choose spell or ability to play";
        if (object != null) {
            message = message + "<br>" + object.getLogName();
        }

        // it's inner method, parent code already uses while and canRespond cycle,
        // so can request one time here
        prepareForResponse(game);
        if (!isExecutingMacro()) {
            game.fireGetChoiceEvent(playerId, message, object, new ArrayList<>(abilities.values()));
        }
        waitForResponse(game);

        UUID responseId = getFixedResponseUUID(game);
        if (responseId != null) {
            if (abilities.containsKey(responseId)) {
                activateAbility(abilities.get(responseId), game);
            }
        }
    }

    /**
     * Hide ability picker dialog on one available ability to activate
     */
    private boolean suppressAbilityPicker(ActivatedAbility ability, Game game) {
        // TODO: is it bugged on mana payment + under control?
        //  (if player under control then priority player must use own settings, not controlling)
        // user activated an ability picker in preferences

        // force to show ability picker for double faces cards in hand/commander/exile and other zones
        Card mainCard = game.getCard(CardUtil.getMainCardId(game, ability.getSourceId()));
        if (mainCard != null && !Zone.BATTLEFIELD.equals(game.getState().getZone(mainCard.getId()))) {
            if (mainCard instanceof SplitCard
                    || mainCard instanceof AdventureCard
                    || mainCard instanceof ModalDoubleFacedCard) {
                return false;
            }
        }

        // hide on land play
        if (ability instanceof PlayLandAbility) {
            return true;
        }

        // hide on alternative cost activated
        if (!getCastSourceIdWithAlternateMana().getOrDefault(ability.getSourceId(), Collections.emptySet()).contains(MageIdentifier.Default)
                && ability.getManaCostsToPay().manaValue() > 0) {
            return true;
        }

        // hide on mana activate and show all other
        return ability.isManaActivatedAbility();
    }

    @Override
    public SpellAbility chooseAbilityForCast(Card card, Game game, boolean noMana) {
        if (gameInCheckPlayableState(game)) {
            return null;
        }

        // TODO: add canRespond cycle?
        if (!canRespond()) {
            return null;
        }

        MageObject object = game.getObject(card.getId()); // must be object to find real abilities (example: commander)
        if (object != null) {
            String message = "Choose ability to cast" + (noMana ? " for FREE" : "") + "<br>" + object.getLogName();
            Map<UUID, SpellAbility> useableAbilities = PlayerImpl.getCastableSpellAbilities(game, playerId, object, game.getState().getZone(object.getId()), noMana);
            if (useableAbilities != null
                    && useableAbilities.size() == 1) {
                return useableAbilities.values().iterator().next();
            } else if (useableAbilities != null
                    && !useableAbilities.isEmpty()) {

                prepareForResponse(game);
                if (!isExecutingMacro()) {
                    game.fireGetChoiceEvent(playerId, message, object, new ArrayList<>(useableAbilities.values()));
                }
                waitForResponse(game);

                UUID responseId = getFixedResponseUUID(game);
                if (responseId != null) {
                    if (useableAbilities.containsKey(responseId)) {
                        return useableAbilities.get(responseId);
                    }
                }
            }
        }

        // default ability (example: on disconnect or cancel)
        return card.getSpellAbility();
    }

    @Override
    public ActivatedAbility chooseLandOrSpellAbility(Card card, Game game, boolean noMana) {
        if (gameInCheckPlayableState(game)) {
            return null;
        }

        // TODO: add canRespond cycle?
        if (!canRespond()) {
            return null;
        }

        MageObject object = game.getObject(card.getId()); // must be object to find real abilities (example: commander)
        if (object != null) {
            LinkedHashMap<UUID, ActivatedAbility> useableAbilities = new LinkedHashMap<>(PlayerImpl.getCastableSpellAbilities(game, playerId, object, game.getState().getZone(object.getId()), noMana));

            if (canPlayLand() && isActivePlayer(game)) {
                for (Ability ability : card.getAbilities(game)) {
                    if (ability instanceof PlayLandAbility) {
                        useableAbilities.put(ability.getId(), (PlayLandAbility) ability);
                    }
                }
            }

            switch (useableAbilities.size()) {
                case 0:
                    return card.getSpellAbility();
                case 1:
                    return useableAbilities.values().iterator().next();
                default:
                    prepareForResponse(game);
                    if (!isExecutingMacro()) {
                        String message = "Choose spell or ability to play" + (noMana ? " for FREE" : "") + "<br>" + object.getLogName();
                        game.fireGetChoiceEvent(playerId, message, object, new ArrayList<>(useableAbilities.values()));
                    }
                    waitForResponse(game);
                    ActivatedAbility response = useableAbilities.get(getFixedResponseUUID(game));
                    if (response != null) {
                        return response;
                    }
            }
        }

        // default ability (example: on disconnect or cancel)
        return card.getSpellAbility();
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        // choose mode to activate
        if (gameInCheckPlayableState(game)) {
            return null;
        }

        if (modes.size() == 0) {
            return null;
        }

        if (modes.size() == 1) {
            return modes.getMode();
        }

        boolean done = false;
        while (!done && canRespond()) {
            // prepare modes list
            MageObject obj = game.getObject(source);
            Map<UUID, String> modeMap = new LinkedHashMap<>();
            int modeIndex = 0;
            AvailableModes:
            for (Mode mode : modes.getAvailableModes(source, game)) {
                modeIndex++;
                int timesSelected = modes.getSelectedStats(mode.getId());
                for (UUID selectedModeId : modes.getSelectedModes()) {
                    Mode selectedMode = modes.get(selectedModeId);
                    if (mode.getId().equals(selectedMode.getId())) {
                        // mode selected
                        if (modes.isMayChooseSameModeMoreThanOnce()) {
                            // can select again
                        } else {
                            // hide mode from dialog
                            continue AvailableModes; // TODO: test 2x cheat here
                        }
                    }
                }

                if (mode.getTargets().canChoose(source.getControllerId(), source, game)) { // and needed targets have to be available
                    String modeText = mode.getEffects().getText(mode);
                    if (obj != null) {
                        modeText = modeText.replace("{this}", obj.getName());
                    }
                    if (modes.isMayChooseSameModeMoreThanOnce()) {
                        if (timesSelected > 0) {
                            modeText = "(selected " + timesSelected + "x) " + modeText;
                        }
                    }
                    if (!modeText.isEmpty()) {
                        modeText = Character.toUpperCase(modeText.charAt(0)) + modeText.substring(1);
                    }
                    StringBuilder sb = new StringBuilder();
                    if (mode.getPawPrintValue() > 0){
                        for (int i = 0; i < mode.getPawPrintValue(); ++i){
                            sb.append("{P}");
                        }
                        sb.append(": ");
                    } else {
                        sb.append(modeIndex).append(". ");
                    }
                    modeMap.put(mode.getId(), sb.append(modeText).toString());
                }
            }

            // done button for "for up" choices only
            boolean canEndChoice = (modes.getSelectedModes().size() >= modes.getMinModes() && modes.getMaxPawPrints() == 0) ||
                    (modes.getSelectedPawPrints() >= modes.getMaxPawPrints() && modes.getMaxPawPrints() > 0) ||
                    modes.isMayChooseNone();
            if (canEndChoice) {
                modeMap.put(Modes.CHOOSE_OPTION_DONE_ID, "Done");
            }
            modeMap.put(Modes.CHOOSE_OPTION_CANCEL_ID, "Cancel");

            // prepare dialog
            String message;
            if (modes.getMaxPawPrints() == 0){
                message = "Choose mode (selected " + modes.getSelectedModes().size() + " of " + modes.getMaxModes(game, source)
                        + ", min " + modes.getMinModes() + ")";
            } else {
                message = "Choose mode (selected " + modes.getSelectedPawPrints() + " of " + modes.getMaxPawPrints()
                        + " {P})";
            }

            if (obj != null) {
                message = message + "<br>" + obj.getLogName();
            }

            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetModeEvent(playerId, message, modeMap);
            }
            waitForResponse(game);

            // process choice
            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                for (Mode mode : modes.getAvailableModes(source, game)) {
                    if (mode.getId().equals(responseId)) {
                        // TODO: add checks on 2x selects (cheaters can rewrite client side code and select same mode multiple times)
                        // reason: wrong setup eachModeMoreThanOnce and eachModeOnlyOnce in many cards
                        return mode;
                    }
                }

                // end choice by done option in ability pickup dialog
                if (canEndChoice && Modes.CHOOSE_OPTION_DONE_ID.equals(responseId)) {
                    done = true;
                }

                // cancel choice (remove all selections)
                if (Modes.CHOOSE_OPTION_CANCEL_ID.equals(responseId)) {
                    modes.clearSelectedModes();
                }
            } else if (canEndChoice) {
                // end choice by done button in feedback panel
                // disable after done option implemented
                // done = true;
            }

            // triggered abilities can't be skipped by cancel or wrong answer
            if (!source.isTriggeredAbility()) {
                done = true;
            }
        }

        // user disconnected, press cancel, press done or something else
        return null;
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, java.util.List<? extends Card> pile1, java.util.List<? extends Card> pile2, Game game) {
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        while (canRespond()) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireChoosePileEvent(playerId, message, pile1, pile2);
            }
            waitForResponse(game);

            if (response.getBoolean() != null) {
                break;
            }
        }

        if (response.getBoolean() != null) {
            return response.getBoolean();
        } else {
            return false;
        }
    }

    @Override
    public void setResponseString(String responseString) {
        if (!waitResponseOpen()) {
            return;
        }
        synchronized (response) {
            response.setString(responseString);
            response.notifyAll();
            logger.debug("Got response string from player: " + getId());
        }
    }

    @Override
    public void setResponseManaType(UUID manaTypePlayerId, ManaType manaType) {
        if (!waitResponseOpen()) {
            return;
        }
        synchronized (response) {
            response.setManaType(manaType);
            response.setResponseManaPlayerId(manaTypePlayerId);
            response.notifyAll();
            logger.debug("Got response mana type from player: " + getId());
        }
    }

    @Override
    public void setResponseUUID(UUID responseUUID) {
        if (!waitResponseOpen()) {
            return;
        }
        synchronized (response) {
            response.setUUID(responseUUID);
            response.notifyAll();
            logger.debug("Got response UUID from player: " + getId());
        }
    }

    @Override
    public void setResponseBoolean(Boolean responseBoolean) {
        if (!waitResponseOpen()) {
            return;
        }
        synchronized (response) {
            response.setBoolean(responseBoolean);
            response.notifyAll();
            logger.debug("Got response boolean from player: " + getId());
        }
    }

    @Override
    public void setResponseInteger(Integer responseInteger) {
        if (!waitResponseOpen()) {
            return;
        }
        synchronized (response) {
            response.setInteger(responseInteger);
            response.notifyAll();
            logger.debug("Got response integer from player: " + getId());
        }
    }

    @Override
    public void abort() {
        // abort must cancel any response and stop waiting immediately
        abort = true;
        synchronized (response) {
            response.notifyAll();
            logger.debug("Got cancel action from player: " + getId());
        }
    }

    @Override
    public void signalPlayerConcede(boolean stopCurrentChooseDialog) {
        // waitResponseOpen(); // concede is async event, will be processed on first priority

        // may be executed in CALL, HEALTH, GAME and other threads
        // so make sure another player can't break/stop currently choosing player

        synchronized (response) {
            response.setAsyncWantConcede(); // tell game that it must check conceding players
            if (stopCurrentChooseDialog) {
                response.notifyAll(); // will force to stop a current waiting dialog (so game can continue)
            }
        }
    }

    @Override
    public void signalPlayerCheat() {
        // waitResponseOpen(); // cheat is async event, will be processed on first player's priority
        synchronized (response) {
            response.setAsyncWantCheat();
            response.notifyAll();
            logger.debug("Set cheat for waiting player: " + getId());
        }
    }

    @Override
    public void skip() {
        // waitResponseOpen(); //skip is direct event, no need to wait it
        // TODO: can be bugged and must be reworked, see wantConcede as example?!
        synchronized (response) {
            response.setInteger(0);
            response.notifyAll();
            logger.debug("Got skip action from player: " + getId());
        }
    }

    @Override
    public HumanPlayer copy() {
        return new HumanPlayer(this);
    }

    @Override
    public void sendPlayerAction(PlayerAction playerAction, Game game, Object data) {
        switch (playerAction) {
            case RESET_AUTO_SELECT_REPLACEMENT_EFFECTS:
                autoSelectReplacementEffects.clear();
                break;
            case TRIGGER_AUTO_ORDER_ABILITY_FIRST:
            case TRIGGER_AUTO_ORDER_ABILITY_LAST:
            case TRIGGER_AUTO_ORDER_NAME_FIRST:
            case TRIGGER_AUTO_ORDER_NAME_LAST:
            case TRIGGER_AUTO_ORDER_RESET_ALL:
                setTriggerAutoOrder(playerAction, game, data);
                break;
            case REQUEST_AUTO_ANSWER_ID_NO:
            case REQUEST_AUTO_ANSWER_ID_YES:
            case REQUEST_AUTO_ANSWER_TEXT_NO:
            case REQUEST_AUTO_ANSWER_TEXT_YES:
            case REQUEST_AUTO_ANSWER_RESET_ALL:
                setRequestAutoAnswer(playerAction, game, data);
                break;
            case HOLD_PRIORITY:
                holdingPriority = true;
                break;
            case UNHOLD_PRIORITY:
                holdingPriority = false;
                break;
            case TOGGLE_RECORD_MACRO:
                if (true) {
                    return; // TODO: macro unsupported in current version
                }
                if (recordingMacro) {
                    logger.debug("Finished Recording Macro");
                    activatingMacro = true;
                    recordingMacro = false;
                    actionIterations = announceRepetitions(game);
                    try {
                        synchronized (actionQueue) {
                            actionQueue.wait();
                        }
                    } catch (InterruptedException ex) {
                    } finally {
                        activatingMacro = false;
                    }
                } else {
                    logger.debug("Starting Recording Macro");
                    resetPlayerPassedActions();
                    recordingMacro = true;
                    actionIterations = 0;
                    actionQueueSaved.clear();
                    actionQueue.clear();
                }
                break;
            case PASS_PRIORITY_UNTIL_STACK_RESOLVED:
                // stop recording only, real stack processing in PlayerImpl
                if (recordingMacro) {
                    logger.debug("Adding a resolveStack");
                    PlayerResponse tResponse = new PlayerResponse();
                    tResponse.setString("resolveStack");
                    actionQueueSaved.add(tResponse);
                }
                super.sendPlayerAction(playerAction, game, data);
                break;
            default:
                super.sendPlayerAction(playerAction, game, data);
                break;
        }
    }

    private void setRequestAutoAnswer(PlayerAction playerAction, Game game, Object data) {
        if (playerAction == REQUEST_AUTO_ANSWER_RESET_ALL) {
            requestAutoAnswerId.clear();
            requestAutoAnswerText.clear();
            return;
        }
        if (data instanceof String) {
            String key = (String) data;
            switch (playerAction) {
                case REQUEST_AUTO_ANSWER_ID_NO:
                    requestAutoAnswerId.put(key, false);
                    break;
                case REQUEST_AUTO_ANSWER_TEXT_NO:
                    requestAutoAnswerText.put(key, false);
                    break;
                case REQUEST_AUTO_ANSWER_ID_YES:
                    requestAutoAnswerId.put(key, true);
                    break;
                case REQUEST_AUTO_ANSWER_TEXT_YES:
                    requestAutoAnswerText.put(key, true);
                    break;
            }
        }
    }

    /**
     * GUI related, remember choices for choose trigger dialog
     *
     * @param playerAction
     * @param game
     * @param data
     */
    private void setTriggerAutoOrder(PlayerAction playerAction, Game game, Object data) {
        if (playerAction == TRIGGER_AUTO_ORDER_RESET_ALL) {
            triggerAutoOrderAbilityFirst.clear();
            triggerAutoOrderAbilityLast.clear();
            triggerAutoOrderNameFirst.clear();
            triggerAutoOrderNameLast.clear();
            return;
        }

        if (data instanceof UUID) {
            // remember by id
            UUID abilityId = (UUID) data;
            UUID originalId = null;
            for (TriggeredAbility ability : game.getState().getTriggered(getId())) {
                if (ability.getId().equals(abilityId)) {
                    originalId = ability.getOriginalId();
                    break;
                }
            }
            if (originalId != null) {
                switch (playerAction) {
                    case TRIGGER_AUTO_ORDER_ABILITY_FIRST:
                        triggerAutoOrderAbilityFirst.add(originalId);
                        break;
                    case TRIGGER_AUTO_ORDER_ABILITY_LAST:
                        triggerAutoOrderAbilityLast.add(originalId);
                        break;
                }
            }
        } else if (data instanceof String) {
            // remember by name
            String abilityName = (String) data;
            if (abilityName.contains("{this}")) {
                throw new IllegalArgumentException("Wrong code usage. Remembering trigger must contains full rules name without {this}.");
            }

            switch (playerAction) {
                case TRIGGER_AUTO_ORDER_NAME_FIRST:
                    triggerAutoOrderNameFirst.add(abilityName);
                    break;
                case TRIGGER_AUTO_ORDER_NAME_LAST:
                    triggerAutoOrderNameLast.add(abilityName);
                    break;
            }
        }
    }

    protected boolean passWithManaPoolCheck(Game game) {
        if (userData.confirmEmptyManaPool()
                && game.getStack().isEmpty() && getManaPool().count() > 0) {
            String activePlayerText;
            if (game.isActivePlayer(playerId)) {
                activePlayerText = "Your turn";
            } else {
                activePlayerText = game.getPlayer(game.getActivePlayerId()).getName() + "'s turn";
            }
            String priorityPlayerText = "";
            if (!isGameUnderControl()) {
                priorityPlayerText = " / priority " + game.getPlayer(game.getPriorityPlayerId()).getName();
            }
            // TODO: chooseUse and other dialogs must be under controlling player
            if (!chooseUse(Outcome.Detriment, GameLog.getPlayerConfirmColoredText("You still have mana in your mana pool. Pass regardless?")
                    + GameLog.getSmallSecondLineText(activePlayerText + " / " + game.getTurnStepType().toString() + priorityPlayerText), null, game)) {
                sendPlayerAction(PlayerAction.PASS_PRIORITY_CANCEL_ALL_ACTIONS, game, null);
                return false;
            }
        }
        pass(game);
        return true;
    }

    @Override
    public String getHistory() {
        return "no available";
    }

    private boolean gameInCheckPlayableState(Game game) {
        return gameInCheckPlayableState(game, false);
    }

    private boolean gameInCheckPlayableState(Game game, boolean ignoreWarning) {
        if (game.inCheckPlayableState()) {
            if (!ignoreWarning) {
                logger.warn(String.format("Current stack: %d - %s",
                        game.getStack().size(),
                        game.getStack().stream().map(Object::toString).collect(Collectors.joining(", "))
                ));
                logger.warn("Player interaction in checkPlayableState", new Throwable());
            }
            return true;
        }
        return false;
    }
}
