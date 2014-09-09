package com.bustiblelemons.cthulhator.dice;

import java.util.Observable;
import java.util.Random;

/**
 * Created by bhm on 09.09.14.
 */
public class PoolPoint {
    private final Random mRandom;
    private final int    mMax;

    private int        points       = 0;
    private int        mMin         = 0;
    private Observable mObservalble = new Observable();

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public synchronized boolean increase() {
        if (points + 1 <= mMax) {
            points++;
            return true;
        }
        return false;
    }

    public synchronized boolean decrease() {
        if (points - 1 >= mMin) {
            points--;
            return false;
        }
        return false;
    }

    private PoolPoint(int max) {
        this(0, max);
    }

    private PoolPoint(int min, int max) {
        this(min, max, System.currentTimeMillis());
    }

    private PoolPoint(int min, int max, long mSeed) {
        this.mMin = min;
        this.mMax = max;
        mRandom = new Random(mSeed);
        this.points = mRandom.nextInt(max);
    }

    public static PoolPoint random(int max) {
        PoolPoint r = new PoolPoint(max);
        return r;
    }

    public static class Builder {
        private long mSeed = System.currentTimeMillis();
        private int  mMax  = 100;
        private int  min   = 0;

        public Builder setSeed(long mSeed) {
            this.mSeed = mSeed;
            return this;
        }

        public Builder setMax(int mMax) {
            this.mMax = mMax;
            return this;
        }

        public PoolPoint build() {
            return new PoolPoint(this.min, this.mMax, this.mSeed);
        }

        public Builder setMin(int min) {
            this.min = min;
            return this;
        }
    }

}
