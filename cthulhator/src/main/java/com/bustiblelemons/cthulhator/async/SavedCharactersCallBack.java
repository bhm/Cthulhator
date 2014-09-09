package com.bustiblelemons.cthulhator.async;

import com.bustiblelemons.cthulhator.model.Grouping;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

import java.util.List;

/**
 * Created by bhm on 13.08.14.
 */
public interface SavedCharactersCallBack {
    void onSavedCharactersLoaded(Grouping grouping, List<SavedCharacter> characters);
}
