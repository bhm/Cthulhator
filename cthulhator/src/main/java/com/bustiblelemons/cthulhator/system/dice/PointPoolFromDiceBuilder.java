package com.bustiblelemons.cthulhator.system.dice;

import com.bustiblelemons.cthulhator.system.dice.model.PolyhedralDice;
import com.bustiblelemons.cthulhator.system.dice.model.ValueSpace;

/**
 * Created by bhm on 09.09.14.
 */
public class PointPoolFromDiceBuilder {

    private int min = 0;

    private int currentMax = 0;

    public PointPoolFromDiceBuilder addDicePool(int count, PolyhedralDice dice) {
        for (int i = 0; i < count; i++) {
            min++;
            currentMax += dice.getMax();
        }
        return this;
    }

    public PointPoolFromDiceBuilder addModifiers(int... modifiers) {
        for (int mod : modifiers) {
            min += mod;
            currentMax += mod;
        }
        return this;
    }

    public ValueSpace build() {
        return new ValueSpace.Builder().setMax(currentMax).setMin(min).build();
    }
}
