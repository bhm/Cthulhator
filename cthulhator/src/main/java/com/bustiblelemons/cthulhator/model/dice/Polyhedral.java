package com.bustiblelemons.cthulhator.model.dice;

import java.util.Random;

/**
 * Created by bhm on 19.07.14.
 */
public class Polyhedral extends DicePoolElement implements DiceRoll {
    private Random random = new Random();
    private int min;
    private int max;

    protected Polyhedral(int min, int max) {
        super(min, max);
    }

    public static Polyhedral from(String diceMaxValue) {
        int max = getValue(diceMaxValue);
        return from(max);
    }

    public static Polyhedral from(int max) {
        return from(1, max);
    }

    public static Polyhedral from(int min, int max) {
        return new Polyhedral(min, max);
    }

    private static int getValue(String diceNumber) {
        int r = 0;
        try {
            r  = Integer.parseInt(diceNumber);
        } catch (NumberFormatException e) {

        }
        return r;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public int roll() {
        if (getMax() <= 1) {
            return 1;
        }
        int r = getMin();
        r += random.nextInt(getMax() - getMin());
        return r;
    }

    @Override
    public int getValue() {
        return roll();
    }
}
