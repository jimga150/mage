
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CounterRemovedFromSourceWhileExiledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetNonBasicLandPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Detritivore extends CardImpl {

    public Detritivore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.LHURGOYF);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Detritivore's power and toughness are each equal to the number of nonbasic land cards in your opponents' graveyards.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new NonBasicLandsInOpponentsGraveyards())));

        // Suspend X-{X}{3}{R}. X can't be 0.
        this.addAbility(new SuspendAbility(Integer.MAX_VALUE, new ManaCostsImpl<>("{3}{R}"), this, true));

        // Whenever a time counter is removed from Detritivore while it's exiled, destroy target nonbasic land.
        Ability ability = new CounterRemovedFromSourceWhileExiledTriggeredAbility(CounterType.TIME, new DestroyTargetEffect());
        ability.addTarget(new TargetNonBasicLandPermanent());
        this.addAbility(ability);
    }

    private Detritivore(final Detritivore card) {
        super(card);
    }

    @Override
    public Detritivore copy() {
        return new Detritivore(this);
    }
}

class NonBasicLandsInOpponentsGraveyards implements DynamicValue {

    private static final FilterCard filter = new FilterCard("nonbasic land card");

    static {
        filter.add(CardType.LAND.getPredicate());
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            for (UUID playerUUID : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (controller.hasOpponent(playerUUID, game)) {
                    Player player = game.getPlayer(playerUUID);
                    if (player != null) {
                        amount += player.getGraveyard().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
                    }
                }
            }
        }
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String getMessage(ValuePhrasing phrasing) {
        return "the number of nonbasic land cards in your opponents' graveyards";
    }
}
