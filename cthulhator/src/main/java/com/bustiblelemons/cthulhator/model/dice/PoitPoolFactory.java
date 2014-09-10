package com.bustiblelemons.cthulhator.model.dice;

import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.CthulhuEdition;

import java.util.Collections;
import java.util.List;

/**
 * Created by bhm on 10.09.14.
 */
public class PoitPoolFactory {

    public static PointPool characteristicPoolFromEdition(CthulhuEdition edition) {
        List<CharacterProperty> list = Collections.emptyList();
        if (edition != null) {
            list = edition.getCharacteristics();
        }
        return fromCharacterProperties(list);
    }

    public static PointPool fromCharacterProperties(List<CharacterProperty> characteristics) {
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
        PointPool r = new PointPool.Builder().setMax(max).setMin(min).build();
        return r;
    }

    public static PointPool randomPoolFromEdition(CthulhuEdition edition) {
        List<CharacterProperty> list = Collections.emptyList();
        if (edition != null) {
            list = edition.getCharacteristics();
        }
        return randomPoolFromCharacterPropertyList(list);
    }

    public static PointPool randomPoolFromCharacterPropertyList(List<CharacterProperty> list) {
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
        PointPool r = new PointPool.Builder().setMax(max).setMin(min).build();
        return r;
    }
}
