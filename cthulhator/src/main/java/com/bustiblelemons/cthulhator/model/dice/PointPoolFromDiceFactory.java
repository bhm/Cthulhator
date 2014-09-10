package com.bustiblelemons.cthulhator.model.dice;

/**
 * Created by bhm on 09.09.14.
 */
public class PointPoolFromDiceFactory {

    private int min = 0;

    private int currentMax = min + 1;

    public int getCurrentMax() {
        return currentMax = min + 1;
    }

    public PointPoolFromDiceFactory addDicePool(int count, PolyHedralDice dice) {
        for (int i = 0; i < count; i++) {
            addDice(dice);
        }
        return this;
    }

    public PointPoolFromDiceFactory addDice(PolyHedralDice... dice) {
        for (PolyHedralDice d : dice) {
            addModifier(d.getMin());
            currentMax += d.getMax();
        }
        return this;
    }

    public PointPoolFromDiceFactory addModifiers(int... modifiers) {
        for (int mod : modifiers) {
            addModifier(mod);
        }
        return this;
    }

    private void addModifier(int mod) {
        min += mod;
    }

    public PointPool build() {
        return new PointPool.Builder().setMax(currentMax).setMin(min).build();
    }
}
