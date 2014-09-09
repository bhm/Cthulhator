package com.bustiblelemons.cthulhator.dice;

/**
 * Created by bhm on 09.09.14.
 */
public class PoolPointFromDice {

    private int min = 0;

    private int currentMax = min + 1;

    public int getCurrentMax() {
        return currentMax = min + 1;
    }

    public int addDicePool(int count, PolyHedralDice dice) {
        for (int i = 0; i < count; i++) {
            addDice(dice);
        }
        return currentMax;
    }

    public int addDice(PolyHedralDice... dice) {
        for (PolyHedralDice d : dice) {
            addModifier(d.getMin());
            currentMax += d.getMax();
        }
        return currentMax;
    }

    public int addModifiers(int... modifiers) {
        for (int mod : modifiers) {
            addModifier(mod);
        }
        return min;
    }

    private void addModifier(int mod) {
        min += mod;
    }

    public PoolPoint build() {
        return new PoolPoint.Builder().setMax(currentMax).setMin(min).build();
    }
}
