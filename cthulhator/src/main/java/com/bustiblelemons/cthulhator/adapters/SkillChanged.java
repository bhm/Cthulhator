package com.bustiblelemons.cthulhator.adapters;

import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

/**
 * Created by bhm on 31.08.14.
 */
public interface SkillChanged {
    boolean onSkillChanged(CharacterProperty property, int value, boolean up);
}
