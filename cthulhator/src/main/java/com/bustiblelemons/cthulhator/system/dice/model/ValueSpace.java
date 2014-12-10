package com.bustiblelemons.cthulhator.system.dice.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bustiblelemons.cthulhator.system.properties.QualifierPair;
import com.bustiblelemons.patterns.ObservedObjectImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

/**
 * Created by bhm on 09.09.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValueSpace extends ObservedObjectImpl<Integer> implements Parcelable {
    public static final ValueSpace EMPTY     = new ValueSpace(Integer.MAX_VALUE);
    public static final ValueSpace ZERO      = new ValueSpace(0, 0);
    public static final ValueSpace STATISTIC = new ValueSpace(0, 100);

    @JsonProperty("qualifier")
    public  QualifierPair qualifierPair;
    @JsonIgnore
    private Random        mRandom;
    @JsonProperty("max")
    private int mMax   = Integer.MAX_VALUE;
    @JsonProperty("points")
    private int points = mMax;
    @JsonProperty("min")
    private int mMin   = 0;
    @JsonProperty("display")
    private String displayValue;
    @JsonProperty("value")
    private int    value;

    private ValueSpace(int max) {
        this(0, max);
    }

    private ValueSpace(int min, int max) {
        this(min, max, System.currentTimeMillis());
    }

    private ValueSpace(int min, int max, long mSeed) {
        this.mMin = min;
        this.mMax = max;
        this.points = max;
        mRandom = new Random(mSeed);
    }

    public ValueSpace() {

    }

    public static ValueSpace random(int max) {
        ValueSpace r = new ValueSpace(max);
        return r;
    }

    @JsonProperty("qualifier")
    public QualifierPair getQualifierPair() {
        if (qualifierPair == null) {
            qualifierPair = QualifierPair.create(getMin(), getMax());
        }
        return qualifierPair;
    }

    @JsonProperty("qualifier")
    public void setQualifierPair(QualifierPair qualifierPair) {
        this.qualifierPair = qualifierPair;
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

    @JsonProperty("max")
    public int getMax() {
        return mMax;
    }

    @JsonProperty("max")
    public void setMax(int mMax) {
        this.mMax = mMax;
    }

    @JsonProperty("min")
    public int getMin() {
        return mMin;
    }

    @JsonProperty("min")
    public void setMin(int mMin) {
        this.mMin = mMin;
    }

    @JsonProperty("points")
    public int getPoints() {
        return points;
    }

    @JsonProperty("points")
    public void setPoints(int points) {
        this.points = points;
        notifyObservers(this.points);
    }

    @JsonIgnore
    public synchronized boolean increase() {
        if (points + 1 <= mMax) {
            points++;
            return true;
        }
        return false;
    }

    @JsonIgnore
    public synchronized boolean decrease() {
        if (points - 1 >= mMin) {
            points--;
            return false;
        }
        return false;
    }

    @JsonIgnore
    public synchronized int decreaseByRandom() {
        return decreaseByRandom(getPoints());
    }

    @JsonIgnore
    public synchronized int decreaseByRandom(int max) {
        int r = decreaseByRandom(1, max);
        notifyObservers(r);
        return r;
    }

    @JsonIgnore
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

    @JsonIgnore
    public synchronized int increaseBy(int value) {
        if (points + value <= mMax) {
            points += value;
        } else {
            points = mMax;
        }
        notifyObservers(points);
        return points;
    }

    @JsonIgnore
    public synchronized int decreaseBy(int value) {
        if (points - value >= mMin) {
            points -= value;
        } else {
            points = mMin;
        }
        notifyObservers(points);
        return points;
    }

    @JsonIgnore
    public synchronized boolean canIncrease() {
        return canIncrease(1);
    }

    @JsonIgnore
    public synchronized boolean canIncrease(int by) {
        return getPoints() + by <= getMax();
    }

    @JsonIgnore
    public synchronized boolean canDecrease() {
        return canDecrease(1);
    }

    @JsonIgnore
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

    @JsonIgnore
    public boolean matches(QualifierPair qualifier) {
        if (this.qualifierPair == null) {
            this.qualifierPair = QualifierPair.create(getMin(), getMax());
        }
        return qualifier.getMin() >= this.qualifierPair.getMin()
                && qualifier.getMax() <= this.qualifierPair.getMax();
    }

    @JsonIgnore
    public boolean qualifiesFor(int value) {
        return getQualifierPair().qualifiesFor(value);
    }

    @JsonProperty("display")
    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    @JsonProperty("display")
    public String getDisplayValue() {
        return displayValue;
    }

    @JsonProperty("value")
    public int getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(int value) {
        this.value = value;
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

        public ValueSpace build() {
            return new ValueSpace(this.mMin, this.mMax, this.mSeed);
        }

        public Builder setMin(int min) {
            this.mMin = min;
            return this;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.qualifierPair, flags);
        dest.writeSerializable(this.mRandom);
        dest.writeInt(this.mMax);
        dest.writeInt(this.points);
        dest.writeInt(this.mMin);
        dest.writeString(this.displayValue);
        dest.writeInt(this.value);
    }

    private ValueSpace(Parcel in) {
        this.qualifierPair = in.readParcelable(QualifierPair.class.getClassLoader());
        this.mRandom = (Random) in.readSerializable();
        this.mMax = in.readInt();
        this.points = in.readInt();
        this.mMin = in.readInt();
        this.displayValue = in.readString();
        this.value = in.readInt();
    }

    public static final Parcelable.Creator<ValueSpace> CREATOR = new Parcelable.Creator<ValueSpace>() {
        public ValueSpace createFromParcel(Parcel source) {
            return new ValueSpace(source);
        }

        public ValueSpace[] newArray(int size) {
            return new ValueSpace[size];
        }
    };
}
