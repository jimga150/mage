
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.HomunculusToken;

/**
 *
 * @author Plopman
 */
public final class PuppetConjurer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.HOMUNCULUS, "Homunculus");

    public PuppetConjurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {U}, {tap}: Create a 0/1 blue Homunculus artifact creature token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new HomunculusToken()), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // At the beginning of your upkeep, sacrifice a Homunculus.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeEffect(filter, 1, "")));
    }

    private PuppetConjurer(final PuppetConjurer card) {
        super(card);
    }

    @Override
    public PuppetConjurer copy() {
        return new PuppetConjurer(this);
    }
}
