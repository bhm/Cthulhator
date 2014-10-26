package com.bustiblelemons.cthulhator.system.dice;

import com.bustiblelemons.cthulhator.system.dice.model.PointPool;
import com.bustiblelemons.cthulhator.system.dice.model.PolyhedralDice;

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

    public PointPool build() {
        return new PointPool.Builder().setMax(currentMax).setMin(min).build();
    }
}
