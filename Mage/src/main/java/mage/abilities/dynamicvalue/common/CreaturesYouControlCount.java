package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.ValueHint;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum CreaturesYouControlCount implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURES, sourceAbility.getControllerId(), sourceAbility, game);
    }

    @Override
    public CreaturesYouControlCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage(EffectPhrasing phrasing) {
        switch (phrasing) {
            case FOR_EACH:
                return "for each creature you control";
            default:
                return "the number of creatures you control";
        }
    }

    @Override
    public ValueHint getHint() {
        return new ValueHint("creatures you control", this);
    }
}
