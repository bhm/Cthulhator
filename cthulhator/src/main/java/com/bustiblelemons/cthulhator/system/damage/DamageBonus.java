package com.bustiblelemons.cthulhator.system.damage;

import com.bustiblelemons.cthulhator.system.dice.model.PointPool;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

/**
 * Created by hiv on 26.10.14.
 */
public interface DamageBonus {

    PointPool getPointPool();

    int random();

    CharacterProperty asCharacterProperty();

}
