package com.bustiblelemons.cthulhator.system.dice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 19.07.14.
 */

public class DicePool implements DiceRoll {
    private String notation;
    private List<DicePoolElement> dicePool = new ArrayList<DicePoolElement>();

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    @Override
    public int roll() {
        if (dicePool.size() == 0) {
            dicePool = DicePoolFactory.from(notation);
        }
        int r = 0;
        for (DicePoolElement poly : dicePool) {
            r += poly.getValue();
        }
        return r;
    }

}
