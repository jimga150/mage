package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.*;
import mage.util.CardUtil;

/**
 *
 * @author jimga150
 */
public final class ObNixilisCaptiveKingpin extends CardImpl {

    public ObNixilisCaptiveKingpin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever one or more opponents each lose exactly 1 life, put a +1/+1 counter on Ob Nixilis, Captive Kingpin. Exile the top card of your library. Until your next end step, you may play that card.
        this.addAbility(new ObNixilisCaptiveKingpinAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.UntilYourNextEndStep)
                .withTextOptions("that card", false)
        ));
    }

    private ObNixilisCaptiveKingpin(final ObNixilisCaptiveKingpin card) {
        super(card);
    }

    @Override
    public ObNixilisCaptiveKingpin copy() {
        return new ObNixilisCaptiveKingpin(this);
    }
}

class ObNixilisCaptiveKingpinAbility extends TriggeredAbilityImpl {
    
    public ObNixilisCaptiveKingpinAbility(Effect effect) {
        this(Zone.BATTLEFIELD, effect, false);
    }
    
    public ObNixilisCaptiveKingpinAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        setTriggerPhrase("Whenever one or more opponents each lose exactly 1 life, ");
    }

    private ObNixilisCaptiveKingpinAbility(final ObNixilisCaptiveKingpinAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {

        LifeLostBatchEvent lifeLostBatchEvent = (LifeLostBatchEvent) event;

        boolean opponentLostLife = false;
        boolean allis1 = true;

        for (UUID targetPlayer : CardUtil.getEventTargets(lifeLostBatchEvent)){
            // skip controller
            if (targetPlayer == getControllerId()){
                continue;
            }
            opponentLostLife = true;

            int lifelost = lifeLostBatchEvent.getAmount(targetPlayer);
            if (lifelost != 1){
                allis1 = false;
                break;
            }
        }
        return opponentLostLife && allis1;
    }

    @Override
    public ObNixilisCaptiveKingpinAbility copy() {
        return new ObNixilisCaptiveKingpinAbility(this);
    }
}
