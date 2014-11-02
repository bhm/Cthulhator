package com.bustiblelemons.cthulhator.character.creation.logic;

import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.Collection;

public interface RelatedPropertesRetreiver {

    Collection<CharacterProperty> getRelatedPropertes(CharacterProperty property);
}