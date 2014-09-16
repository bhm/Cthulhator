package com.bustiblelemons.cthulhator.model.dice;

/**
 * Created by bhm on 09.09.14.
 */
public class PointPoolFromDiceFactory {

    private int min = 0;

    private int currentMax = 0;

    public PointPoolFromDiceFactory addDicePool(int count, PolyHedralDice dice) {
        for (int i = 0; i < count; i++) {
            min++;
            currentMax += dice.getMax();
        }
        return this;
    }

    public PointPoolFromDiceFactory addModifiers(int... modifiers) {
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
