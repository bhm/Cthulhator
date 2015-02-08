package com.bustiblelemons.cthulhator.character.viewer;

import android.graphics.Point;

/**
 * Created by hiv on 08.02.15.
 */
public class SeeThrough extends Point {

    private int mHeightToSubtract = 0;
    private int mWidthToSubstract = 0;

    public int getCalculatedHeight() {
        return getCalculated(y, mHeightToSubtract);
    }

    public int getCalculatedWidth() {
        return getCalculated(x, mWidthToSubstract);
    }

    private int getCalculated(int val, int substract) {
        return val - substract;
    }

    public SeeThrough withHeightSubstract(int val) {
        mHeightToSubtract = val;
        return this;
    }

    public SeeThrough withWidthToSubstract(int val) {
        mWidthToSubstract = val;
        return this;
    }

}
