
package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class RazorvergeThicket extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("other lands");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final YouControlPermanentCondition condition =
            new YouControlPermanentCondition(filter, ComparisonType.OR_LESS, 2);

    public RazorvergeThicket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Razorverge Thicket enters the battlefield tapped unless you control two or fewer other lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private RazorvergeThicket(final RazorvergeThicket card) {
        super(card);
    }

    @Override
    public RazorvergeThicket copy() {
        return new RazorvergeThicket(this);
    }

}
