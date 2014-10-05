package com.bustiblelemons.cthulhator.character.characterslist.logic;

import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.system.Grouping;

import java.util.List;

/**
 * Created by bhm on 13.08.14.
 */
public interface SavedCharactersCallBack {
    void onSavedCharactersLoaded(Grouping grouping, List<SavedCharacter> characters);
}
