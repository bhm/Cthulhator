package com.bustiblelemons.cthulhator.system.edition;

import com.bustiblelemons.cthulhator.system.dice.model.PointPool;

/**
 * Created by hiv on 26.10.14.
 */
public enum DamageBonus {
    Scanty,
    Meagre,
    Average,
    Exceptional,
    Abnormal,
    Preternatural0,
    Preternatural1,
    Preternatural2,
    Preternatural3,
    Preternatural4,
    Preternatural5;
    private PointPool pointPool;

    public PointPool getPointPool() {
        return pointPool;
    }

    public void setPointPool(PointPool pointPool) {
        this.pointPool = pointPool;
    }
}
