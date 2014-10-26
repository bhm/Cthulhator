package com.bustiblelemons.cthulhator.system.damage;

import com.bustiblelemons.cthulhator.system.dice.model.PointPool;

/**
 * Created by hiv on 26.10.14.
 */
public interface DamageBonus {

    PointPool getPointPool();

    int random();

}
