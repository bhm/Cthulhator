package com.bustiblelemons.cthulhator.model;

/**
 * Created by bhm on 20.07.14.
 */
public interface ICharacter {
    int getStatisticValue(String name);

    int getSkillValue(Skill name);

    int getCurrentSanity();

    int getMaxSanity();
}
