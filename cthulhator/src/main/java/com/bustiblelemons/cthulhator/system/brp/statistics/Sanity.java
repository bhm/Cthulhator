package com.bustiblelemons.cthulhator.system.brp.statistics;

import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.PropertyFormat;
import com.bustiblelemons.cthulhator.system.properties.PropertyType;

/**
 * Created by bhm on 20.07.14.
 */
public class Sanity {
    private int current;
    private int max;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public CharacterProperty asCharacterProperty() {
        CharacterProperty r = new CharacterProperty();
        r.setName(Sanity.class.getSimpleName());
        r.setFormat(PropertyFormat.NUMBER);
        r.setMaxValue(getMax());
        r.setMinValue(0);
        r.setType(PropertyType.SANITY);
        return r;
    }
}
