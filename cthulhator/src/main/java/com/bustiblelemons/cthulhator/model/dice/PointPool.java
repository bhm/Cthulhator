package com.bustiblelemons.cthulhator.model.dice;

import com.bustiblelemons.patterns.ObservedObjectImpl;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Random;

/**
 * Created by bhm on 09.09.14.
 */
public class PointPool extends ObservedObjectImpl<Integer> {
    private Random mRandom;
    private int mMax   = Integer.MAX_VALUE;
    private int points = mMax;
    private int mMin   = 0;

    private PointPool(int max) {
        this(0, max);
    }

    private PointPool(int min, int max) {
        this(min, max, System.currentTimeMillis());
    }

    private PointPool(int min, int max, long mSeed) {
        this.mMin = min;
        this.mMax = max;
        this.points = max;
        mRandom = new Random(mSeed);
    }

    public static PointPool random(int max) {
        PointPool r = new PointPool(max);
        return r;
    }

    public int getMax() {
        return mMax;
    }

    public int getMin() {
        return mMin;
    }

    public void setMin(int mMin) {
        this.mMin = mMin;
    }

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

    public synchronized int decreaseByRandom() {
        return decreaseByRandom(getPoints());
    }

    public synchronized int decreaseByRandom(int max) {
        int r = decreaseByRandom(1, max);
        notifyObservers(r);
        return r;
    }

    public int decreaseByRandom(int min, int max) {
        int mod = min;
        if (points > 0) {
            mod = mRandom.nextInt(max) + 1;
            points -= mod;
        }
        return mod;
    }

    @Override
    public String toString() {
        return "PointPool{" +
                "mRandom=" + mRandom +
                ", mMax=" + mMax +
                ", points=" + points +
                ", mMin=" + mMin +
                '}';
    }

    @JsonIgnore
    public boolean hasPoints() {
        return getPoints() > getMin();
    }

    public static class Builder {
        private long mSeed = System.currentTimeMillis();
        private int  mMax  = 100;
        private int  mMin  = 0;

        public Builder setSeed(long mSeed) {
            this.mSeed = mSeed;
            return this;
        }

        public Builder setMax(int mMax) {
            this.mMax = mMax;
            return this;
        }

        public PointPool build() {
            return new PointPool(this.mMin, this.mMax, this.mSeed);
        }

        public Builder setMin(int min) {
            this.mMin = min;
            return this;
        }
    }
}
