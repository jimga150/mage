package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.LegendarySpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author JRHerlehy Created on 4/8/18.
 */
public final class KamahlsDruidicVow extends CardImpl {

    public KamahlsDruidicVow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);

        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        this.addAbility(new LegendarySpellAbility());

        // Look at the top X cards of your library.
        // You may put any number of land and/or legendary permanent cards with converted mana cost X or less from among them onto the battlefield.
        // Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new KamahlsDruidicVowEffect());
    }

    private KamahlsDruidicVow(final KamahlsDruidicVow card) {
        super(card);
    }

    @Override
    public KamahlsDruidicVow copy() {
        return new KamahlsDruidicVow(this);
    }

}

class KamahlsDruidicVowEffect extends OneShotEffect {

    KamahlsDruidicVowEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Look at the top X cards of your library. You may put any number of land and/or legendary permanent cards with mana value X or less from among them onto the battlefield. Put the rest into your graveyard";
    }

    private KamahlsDruidicVowEffect(final KamahlsDruidicVowEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int xValue = CardUtil.getSourceCostsTag(game, source, "X", 0);
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, xValue));
        controller.lookAtCards(source, null, cards, game);
        if (!cards.isEmpty()) {
            FilterCard filter = new FilterPermanentCard("land and/or legendary permanent cards with mana value " + xValue + " or less to put onto the battlefield");
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
            filter.add(
                    Predicates.or(
                            CardType.LAND.getPredicate(),
                            SuperType.LEGENDARY.getPredicate()
                    ));
            TargetCard target1 = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, filter);
            target1.withNotTarget(true);
            controller.choose(Outcome.PutCardInPlay, cards, target1, source, game);
            Cards toBattlefield = new CardsImpl(target1.getTargets());
            cards.removeAll(toBattlefield);
            controller.moveCards(toBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, false, null);
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        }
        return true;
    }

    @Override
    public KamahlsDruidicVowEffect copy() {
        return new KamahlsDruidicVowEffect(this);
    }
}
