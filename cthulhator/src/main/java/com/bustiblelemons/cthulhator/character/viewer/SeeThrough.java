package com.bustiblelemons.cthulhator.character.viewer;

import android.graphics.Point;

/**
 * Created by hiv on 08.02.15.
 */
public class SeeThrough extends Point {
    private int mHeightPercentage = 100;
    private int mWidthPercentage = 100;

    public int getCalculatedHeight() {
        return getCalculated(y, mHeightPercentage);
    }

    public int getCalculatedWidth() {
        return getCalculated(x, mWidthPercentage);
    }

    private int getCalculated(int val, int percentage) {
        float fPercentage = (float) percentage / 100f;
        float fVal = (float) val;
        int r = (int) (fVal * fPercentage);
        return r;
    }

    public SeeThrough withHeightPercentage(int percentage) {
        setHeightPercentage(percentage);
        return this;
    }

    public SeeThrough withWidthtPercentage(int percentage) {
        setWidthPercentage(percentage);
        return this;
    }

    public int getHeightPercentage() {
        return mHeightPercentage;
    }

    public void setHeightPercentage(int heightPercentage) {
        mHeightPercentage = heightPercentage;
        if (mHeightPercentage == 0) {
            mHeightPercentage = 1;
        }
    }

    public int getWidthPercentage() {
        return mWidthPercentage;
    }

    public void setWidthPercentage(int widthPercentage) {
        mWidthPercentage = widthPercentage;
        if (mWidthPercentage == 0) {
            mWidthPercentage = 1;
        }
    }
}
