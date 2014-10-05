package com.bustiblelemons.cthulhator.system.dice;

/**
 * Created by bhm on 20.07.14.
 */
public class DiceBonus extends DicePoolElement {

    public DiceBonus(int val) {
        super(val, val);
    }

    public static DiceBonus from(String value) {
        int val = 0;
        try {
            val = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.d("Could not parse %s", value);
        }
        return new DiceBonus(val);
    }

    @Override
    public int getValue() {
        return getMax();
    }
}
