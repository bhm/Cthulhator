package com.bustiblelemons.cthulhator.system.brp.skills;

import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.PropertyFormat;
import com.bustiblelemons.cthulhator.system.properties.PropertyType;

import java.util.Locale;

/**
 * Created by bhm on 20.09.14.
 */
public enum BRPSkillPointPools {
    CAREER, HOBBY;

    public CharacterProperty asProperty() {
        CharacterProperty r = new CharacterProperty();
        r.setName(name().toLowerCase(Locale.ENGLISH));
        r.setFormat(PropertyFormat.NUMBER);
        r.setType(PropertyType.POINT_POOL);
        r.setMinValue(0);
        return r;
    }
}
