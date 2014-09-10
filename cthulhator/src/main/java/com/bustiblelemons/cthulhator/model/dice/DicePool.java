package com.bustiblelemons.cthulhator.model.dice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 19.07.14.
 */

public class DicePool implements DiceRoll {
    private String notation;

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    private List<DicePoolElement> dicePool = new ArrayList<DicePoolElement>();

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
