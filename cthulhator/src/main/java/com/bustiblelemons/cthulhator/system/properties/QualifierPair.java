package com.bustiblelemons.cthulhator.system.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hiv on 01.12.14.
 */
public class QualifierPair {

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

    public boolean matches(int value) {
        return getMin() >= value && value <= getMax();
    }

    @JsonIgnore
    public boolean qualifiesFor(int value) {
        return getMin() >= value && value <= getMax();
    }
}
