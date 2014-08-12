package com.bustiblelemons.cthulhator.cache;

import com.bustiblelemons.cthulhator.model.ToCCharacter;
import com.bustiblelemons.cthulhator.model.brp.BRPCharacter;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 20.07.14.
 */
public class CharacterCache {

    protected CharacterCache() {
    }

    private static CharacterCache instance;

    public static CharacterCache getInstance() {
        return instance == null ? instance = new CharacterCache() : instance;
    }

    public static List<BRPCharacter> getBRPCharacters() {
        return new ArrayList<BRPCharacter>();
    }

    public static List<ToCCharacter> getToCCharacters() {
        return new ArrayList<ToCCharacter>();
    }

    private static List<SavedCharacter> getSavedCharacters() {
        return new ArrayList<SavedCharacter>();
    }
}
