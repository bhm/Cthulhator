package com.bustiblelemons.cthulhator.system.properties;

import com.bustiblelemons.cthulhator.system.dice.model.ValueSpace;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by hiv on 01.12.14.
 */
public class ValueSpaceSet extends HashSet<ValueSpace> {

    public ValueSpaceSet() {

    }

    public ValueSpaceSet(int capacity) {
        super(capacity);
    }

    public ValueSpaceSet(int capacity, float loadFactor) {
        super(capacity, loadFactor);
    }

    public ValueSpaceSet(Collection<? extends ValueSpace> collection) {
        super(collection);
    }

    public ValueSpaceSet(int minValue, int maxValue) {
        ValueSpace valueSpace = new ValueSpace();
        valueSpace.setMax(maxValue);
        valueSpace.setMin(minValue);
        add(valueSpace);
    }

    public ValueSpace getPointPool(QualifierPair pair) {
        if (pair == null) {
            return ValueSpace.EMPTY;
        }
        for (ValueSpace valueSpace : this) {
            if (valueSpace != null && valueSpace.matches(pair)) {
                return valueSpace;
            }
        }
        return ValueSpace.EMPTY;
    }

    public ValueSpace getPointPoolByValue(int value) {
        for (ValueSpace valueSpace : this) {
            if (valueSpace != null && valueSpace.qualifiesFor(value)) {
                return valueSpace;
            }
        }
        return ValueSpace.EMPTY;
    }

}
