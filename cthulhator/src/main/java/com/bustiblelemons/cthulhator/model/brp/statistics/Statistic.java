package com.bustiblelemons.cthulhator.model.brp.statistics;

import com.bustiblelemons.cthulhator.model.dice.DicePool;

/**
 * Created by bhm on 19.07.14.
 */
public class Statistic {
    private String   name;
    private DicePool dicePool;
    private int      value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DicePool getDicePool() {
        return dicePool;
    }

    public void setDicePool(DicePool dicePool) {
        this.dicePool = dicePool;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
