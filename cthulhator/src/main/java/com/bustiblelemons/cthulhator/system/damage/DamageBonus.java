package com.bustiblelemons.cthulhator.system.damage;

import com.bustiblelemons.cthulhator.system.dice.model.ValueSpace;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

/**
 * Created by hiv on 26.10.14.
 */
public interface DamageBonus {

    ValueSpace getPointPool();

    int random();

    CharacterProperty asCharacterProperty();

}
