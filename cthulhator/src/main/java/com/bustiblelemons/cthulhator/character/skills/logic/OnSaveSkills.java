package com.bustiblelemons.cthulhator.character.skills.logic;

import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.Collection;

/**
 * Created by hiv on 11.11.14.
 */
public interface OnSaveSkills {
    void onSaveSkills(Collection<CharacterProperty> skills);
}
