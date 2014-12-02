package com.bustiblelemons.cthulhator.system.properties;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hiv on 01.12.14.
 */
public class QualifierPair implements Parcelable {

    private final int min;
    private final int max;

    public QualifierPair(int min, int max) {
        this.min = min;
        this.max = max;
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

        if (max != that.max) return false;
        if (min != that.min) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = min;
        result = 31 * result + max;
        return result;
    }

    @JsonProperty("max")
    public int getMin() {
        return this.min;
    }

    @JsonProperty("max")
    public int getMax() {
        return this.max;
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
        dest.writeInt(this.min);
        dest.writeInt(this.max);
    }

    private QualifierPair(Parcel in) {
        this.min = in.readInt();
        this.max = in.readInt();
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
