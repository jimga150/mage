
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class Pallimud extends CardImpl {

    public Pallimud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // As Pallimud enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.Detriment)));
        
        // Pallimud's power is equal to the number of tapped lands the chosen player controls.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(new PallimudCount())));
    }

    private Pallimud(final Pallimud card) {
        super(card);
    }

    @Override
    public Pallimud copy() {
        return new Pallimud(this);
    }
}

class PallimudCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            UUID playerId = (UUID) game.getState().getValue(sourceAbility.getSourceId() + ChooseOpponentEffect.VALUE_KEY);
            Player chosenPlayer = game.getPlayer(playerId);
            if (chosenPlayer != null) {
                FilterLandPermanent filter = new FilterLandPermanent("tapped lands the chosen player controls");
                filter.add(TappedPredicate.TAPPED);
                filter.add(new ControllerIdPredicate(playerId));
                return game.getBattlefield().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
            }
        }
        return 0;
    }

    @Override
    public PallimudCount copy() {
        return new PallimudCount();
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
        return "the number of tapped lands the chosen player controls";
    }
}
