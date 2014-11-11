package com.bustiblelemons.cthulhator.adapters;

import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

/**
 * Created by bhm on 31.08.14.
 */
public interface OnSkillChanged {
    boolean onSkillIncreased(CharacterProperty property);

    boolean onSkillDecreased(CharacterProperty property);
}
