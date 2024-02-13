package mage.cards.d;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.CardUtil;

/**
 *
 * @author jimga150
 */
public final class DinoDNA extends CardImpl {

    public DinoDNA(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        

        // Imprint -- {1}, {T}: Exile target creature card from a graveyard. Activate only as a sorcery.
        // Based on Dimir Doppleganger
        Ability imprintAbility = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DinoDNAImprintEffect(), new ManaCostsImpl<>("{1}"));
        imprintAbility.addCost(new TapSourceCost());
        imprintAbility.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard")));

        // {6}: Create a token that's a copy of target creature card exiled with Dino DNA, except it's a 6/6 green Dinosaur creature with trample. Activate only as a sorcery.
        // Based on Croaking Counterpart and Bronzebeak Foragers
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, null, false, 1, false, false,
                null, 6, 6, false
        );
        effect.setOnlyColor(ObjectColor.GREEN);
        effect.setOnlySubType(SubType.DINOSAUR);
        effect.addAdditionalAbilities(TrampleAbility.getInstance());
        effect.setText("Create a token that's a copy of target creature card exiled with Dino DNA, except it's a 6/6 green Dinosaur creature with trample.");
        Ability copyAbility = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{6}"));
        copyAbility.setTargetAdjuster(DinoDNACopyAdjuster.instance);

        this.addAbility(imprintAbility);
        this.addAbility(copyAbility);
    }

    private DinoDNA(final DinoDNA card) {
        super(card);
    }

    @Override
    public DinoDNA copy() {
        return new DinoDNA(this);
    }
}
class DinoDNAImprintEffect extends OneShotEffect {

    DinoDNAImprintEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target creature card from a graveyard.";
    }

    private DinoDNAImprintEffect(final DinoDNAImprintEffect effect) {
        super(effect);
    }

    @Override
    public DinoDNAImprintEffect copy() {
        return new DinoDNAImprintEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Card toExile = game.getCard(source.getFirstTarget());
        if (toExile == null) {
            return false;
        }

        UUID exileId = CardUtil.getExileZoneId(game, source);
        player.moveCardsToExile(
                toExile, source, game, true,
                exileId, CardUtil.getSourceName(game, source)
        );
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        return exileZone != null && !exileZone.isEmpty();
    }
}

enum DinoDNACopyAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        FilterCard filter = new FilterCreatureCard();
        ability.addTarget(new TargetCardInExile(filter, CardUtil.getExileZoneId(game, ability)));
    }
}
