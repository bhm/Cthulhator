package com.bustiblelemons.cthulhator.system.edition;

import com.bustiblelemons.cthulhator.system.dice.model.PointPool;

/**
 * Created by hiv on 26.10.14.
 */
public enum DamageBonus {
    Scanty(2, 12),
    Meagre(13, 16),
    Average(17, 24),
    Exceptional(25, 32),
    Abnormal(33, 36),
    Preternatural0(36, 40),
    Preternatural1(41, 56),
    Preternatural2(57, 72),
    Preternatural3(73, 88),
    Preternatural4(89, 104),
    Preternatural5(105, 120);
    private int       mMin;
    private int       mMax;
    private PointPool pointPool;

    DamageBonus(int mMin, int mMax) {
        this.mMin = mMin;
        this.mMax = mMax;
    }

    public PointPool getPointPool() {
        return pointPool;
    }

    public void setPointPool(PointPool pointPool) {
        this.pointPool = pointPool;
    }

}
