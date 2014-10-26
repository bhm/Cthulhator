package com.bustiblelemons.cthulhator.system.dice.model;

import com.bustiblelemons.patterns.ObservedObjectImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

import java.util.Random;

/**
 * Created by bhm on 09.09.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointPool extends ObservedObjectImpl<Integer> {
    public static final PointPool EMPTY = new PointPool(Integer.MAX_VALUE);
    public static final PointPool ZERO  = new PointPool(0, 0);
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

    public PointPool() {

    }

    public static PointPool random(int max) {
        PointPool r = new PointPool(max);
        return r;
    }

    public int randomValue() {
        Random r = new Random();
        int ret = 0;
        if (getMin() >= 0) {
            int n = getMax() - getMin();
            int roll = r.nextInt(n);
            ret = roll + getMin() + 1;
        } else {
            int absMin = Math.abs(getMin());
            int n = getMax() + absMin;
            int roll = r.nextInt(n);
            ret = getMax() - roll;
        }
        r = null;
        return ret;
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int mMax) {
        this.mMax = mMax;
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
        notifyObservers(this.points);
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

    public synchronized int decreaseByRandom(int min, int max) {
        int mod = min;
        int _max = max;
        if (points > 0) {
            if (_max > points) {
                _max = points;
            }
            mod = mRandom.nextInt(_max - min) + min;
            decreaseBy(mod);
        }
        return mod;
    }

    public synchronized int increaseBy(int value) {
        if (points + value <= mMax) {
            points += value;
        } else {
            points = mMax;
        }
        notifyObservers(points);
        return points;
    }

    public synchronized int decreaseBy(int value) {
        if (points - value >= mMin) {
            points -= value;
        } else {
            points = mMin;
        }
        notifyObservers(points);
        return points;
    }

    public synchronized boolean canIncrease() {
        return canIncrease(1);
    }

    public synchronized boolean canIncrease(int by) {
        return getPoints() + by <= getMax();
    }

    public synchronized boolean canDecrease() {
        return canDecrease(1);
    }

    public synchronized boolean canDecrease(int by) {
        return getPoints() - by >= getMax();
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

    @JsonIgnoreType
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
