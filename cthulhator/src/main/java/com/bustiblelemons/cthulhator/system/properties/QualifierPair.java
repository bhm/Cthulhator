package com.bustiblelemons.cthulhator.system.properties;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hiv on 01.12.14.
 */
public class QualifierPair implements Parcelable {

    @JsonProperty("min")
    private int mMin;
    @JsonProperty("max")
    private int mMax;

    @JsonProperty("min")
    public void setMin(int min) {
        this.mMin = min;
    }

    @JsonProperty("max")
    public void setMax(int max) {
        this.mMax = max;
    }

    public QualifierPair() {

    }

    public QualifierPair(int min, int max) {
        this.mMin = min;
        this.mMax = max;
    }

    private static boolean objectsEqual(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static QualifierPair create(int a, int b) {
        return new QualifierPair(a, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QualifierPair that = (QualifierPair) o;

        if (mMax != that.mMax) return false;
        if (mMin != that.mMin) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mMin;
        result = 31 * result + mMax;
        return result;
    }

    @JsonProperty("min")
    public int getMin() {
        return this.mMin;
    }

    @JsonProperty("max")
    public int getMax() {
        return this.mMax;
    }

    @JsonIgnore
    public boolean qualifiesFor(int value) {
        return value >= getMin() && value <= getMax();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mMin);
        dest.writeInt(this.mMax);
    }

    private QualifierPair(Parcel in) {
        this.mMin = in.readInt();
        this.mMax = in.readInt();
    }

    public static final Parcelable.Creator<QualifierPair> CREATOR = new Parcelable.Creator<QualifierPair>() {
        public QualifierPair createFromParcel(Parcel source) {
            return new QualifierPair(source);
        }

        public QualifierPair[] newArray(int size) {
            return new QualifierPair[size];
        }
    };
}
