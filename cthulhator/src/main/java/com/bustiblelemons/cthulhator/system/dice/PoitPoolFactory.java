package com.bustiblelemons.cthulhator.system.dice;

import com.bustiblelemons.cthulhator.system.dice.model.ValueSpace;
import com.bustiblelemons.cthulhator.system.edition.CthulhuEdition;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by bhm on 10.09.14.
 */
public class PoitPoolFactory {

    public static ValueSpace characteristicPoolFromEdition(CthulhuEdition edition) {
        Collection<CharacterProperty> list = Collections.emptyList();
        if (edition != null) {
            list = edition.getCharacteristics();
        }
        return fromCharacterProperties(list);
    }

    public static ValueSpace fromCharacterProperties(Collection<CharacterProperty> characteristics) {
        int min = 0;
        int max = 0;
        for (CharacterProperty prop : characteristics) {
            if (prop != null) {
                min += prop.getMinValue();
                max += prop.getMaxValue();
            }
        }
        if (max == 0) {
            max = Integer.MAX_VALUE;
        }
        ValueSpace r = new ValueSpace.Builder().setMax(max).setMin(min).build();
        return r;
    }

    public static ValueSpace randomPoolFromEdition(CthulhuEdition edition) {
        Collection<CharacterProperty> list = Collections.emptyList();
        if (edition != null) {
            list = edition.getCharacteristics();
        }
        return randomPoolFromCharacterPropertyList(list);
    }

    public static ValueSpace randomPoolFromCharacterPropertyList(Collection<CharacterProperty> list) {
        int min = 0;
        int max = 0;
        for (CharacterProperty prop : list) {
            if (prop != null) {
                min += prop.getMinValue();
                max += prop.randomValue();
            }
        }
        if (max == 0) {
            max = Integer.MAX_VALUE;
        }
        ValueSpace r = new ValueSpace.Builder().setMax(max).setMin(min).build();
        return r;
    }
}
