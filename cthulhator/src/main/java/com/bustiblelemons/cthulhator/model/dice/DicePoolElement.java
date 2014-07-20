package com.bustiblelemons.cthulhator.model.dice;

import com.bustiblelemons.logging.Logger;

/**
 * Created by bhm on 20.07.14.
 */
public abstract class DicePoolElement implements DicePoolValue {
    protected static Logger log;
    private          int    min;
    private          int    max;

    protected DicePoolElement(int min, int max) {
        log = new Logger(getClass());
        this.min = min;
        this.max = max;
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
}
