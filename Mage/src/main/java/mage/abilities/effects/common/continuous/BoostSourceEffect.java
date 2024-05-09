package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BoostSourceEffect extends ContinuousEffectImpl {
    private DynamicValue power;
    private DynamicValue toughness;

    public BoostSourceEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, "{this}");
    }

    public BoostSourceEffect(int power, int toughness, Duration duration, String description) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, description);
    }

    public BoostSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, "{this}");
    }

    public BoostSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration, String description) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.staticText = description + " gets " + CardUtil.getBoostText(power, toughness, duration);
    }

    protected BoostSourceEffect(final BoostSourceEffect effect) {
        super(effect);
        this.power = effect.power.copy();
        this.toughness = effect.toughness.copy();
    }

    @Override
    public BoostSourceEffect copy() {
        return new BoostSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getAffectedObjectsSet()) {
            try {
                affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(BoostSourceEffect.class).error("Could not get sourceId reference: " + source.getRule());
            }
            power = StaticValue.get(power.calculate(game, source, this));
            toughness = StaticValue.get(toughness.calculate(game, source, this));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target;
        if (getAffectedObjectsSet()) {
            target = affectedObjectList.get(0).getPermanent(game);
        } else {
            target = game.getPermanent(source.getSourceId());
        }
        if (target != null) {
            target.addPower(power.calculate(game, source, this));
            target.addToughness(toughness.calculate(game, source, this));
            return true;
        }
        return false;
    }
}
