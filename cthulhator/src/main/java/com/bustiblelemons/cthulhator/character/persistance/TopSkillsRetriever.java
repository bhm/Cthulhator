package com.bustiblelemons.cthulhator.character.persistance;

import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.List;

/**
 * Created by hiv on 22.02.15.
 */
public interface TopSkillsRetriever {
    List<CharacterProperty> getTopSkills();
    List<CharacterProperty> getTopSkills(int max);
}
