package com.bustiblelemons.cthulhator.character.characterslist.logic;

import android.content.Context;

import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharactersSet;
import com.bustiblelemons.cthulhator.system.Grouping;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.storage.Storage;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 20.07.14.
 */
public class CharacterCache {

    private static String sCharactersCacheFile = "saved.characters.json";
    private static CharacterCache instance;

    protected CharacterCache() {
    }

    public static final ObjectMapper getMapper() {
        return LazyMapperHolder.INSTANCE;
    }

    public static CharacterCache getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void loadSavedCharactersAsync(Context context, Grouping... groupings) {
        if (context instanceof OnCharactersInfoLoaded) {
            SavedCharactersLoadAsyn load = new SavedCharactersLoadAsyn(context,
                    (OnCharactersInfoLoaded) context);
            load.executeCrossPlatform(groupings);
        }
    }

    public static File getSavedCharactersFile(Context context) {
        return Storage.getStorageFile(context, sCharactersCacheFile);
    }

    public static void saveCharacter(Context context, SavedCharacter character) {
        getInstance()._saveCharacter(context, character);
    }

    public static SavedCharactersSet getCharacterSet(Context context) {
        return getInstance()._getCharacterSet(context);
    }

    public synchronized SavedCharactersSet _getCharacterSet(Context context) {
        ObjectMapper m = getMapper();
        File savedCharacters = CharacterCache.getSavedCharactersFile(context);
        SavedCharactersSet r = null;
        try {
            r = m.readValue(savedCharacters, SavedCharactersSet.class);
        } catch (IOException e) {
            e.printStackTrace();
            r = new SavedCharactersSet();
            r.setCharacters(new ArrayList<SavedCharacter>());
        }
        return r;

    }

    private void _saveCharacter(Context context, SavedCharacter character) {
        SavedCharactersSet set = _getCharacterSet(context);
        if (set == null) {
            set = new SavedCharactersSet();
        }
        set.add(character);
        ObjectMapper mapper = getMapper();
        File f = CharacterCache.getSavedCharactersFile(context);
        try {
            mapper.writeValue(f, set);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnCharactersInfoLoaded {
        void onCharactersInfoLoaded(Grouping grouping, List<CharacterInfo> result);
    }

    private static final class LazyMapperHolder {
        public static final ObjectMapper INSTANCE = new ObjectMapper();
    }

    private static final class LazyHolder {
        private static final CharacterCache INSTANCE = new CharacterCache();
    }
}
