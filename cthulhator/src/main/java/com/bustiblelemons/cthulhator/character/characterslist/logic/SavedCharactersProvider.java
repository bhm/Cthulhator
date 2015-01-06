package com.bustiblelemons.cthulhator.character.characterslist.logic;

import android.content.Context;
import android.util.LruCache;

import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharactersSet;
import com.bustiblelemons.cthulhator.system.Grouping;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.logging.Logger;
import com.bustiblelemons.storage.Storage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 20.07.14.
 */
public class SavedCharactersProvider {

    private static final Logger                            log                  = new Logger(SavedCharactersProvider.class);
    private static final LruCache<Integer, SavedCharacter> sCharactersLRU       =
            new LruCache<Integer, SavedCharacter>(4);
    private static       String                            sCharactersCacheFile = "saved.characters.json";
    private static SavedCharactersProvider instance;
    private        SavedCharactersSet      mCharacterSet;

    protected SavedCharactersProvider() {
    }

    public static final ObjectMapper getMapper() {
        return LazyMapperHolder.INSTANCE;
    }

    public static SavedCharactersProvider getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void loadSavedCharactersAsync(Context context, Grouping... groupings) {
        if (context instanceof OnCharactersInfoLoaded) {
            SavedCharactersLoadAsyn load =
                    new SavedCharactersLoadAsyn(context, (OnCharactersInfoLoaded) context);
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

    public static SavedCharacter getSavedCharacterById(Context context, int characterId) {
        return getInstance()._getCharacterById(context, characterId);
    }

    public static void loadCharacterAsyn(Context context, int characterHashCode) {
        if (context instanceof OnRetreiveCharacter) {
            OnRetreiveCharacter onRetreiveCharacter = (OnRetreiveCharacter) context;
            RetreiveCharacterAsyn asyn = new RetreiveCharacterAsyn(context, onRetreiveCharacter);
            asyn.executeCrossPlatform(characterHashCode);
        }
    }

    private synchronized SavedCharactersSet _getCharacterSet(Context context) {
        if (mCharacterSet == null) {
            ObjectMapper m = getMapper();
            File savedCharacters = SavedCharactersProvider.getSavedCharactersFile(context);
            try {
                mCharacterSet = m.readValue(savedCharacters, SavedCharactersSet.class);
            } catch (IOException e) {
                e.printStackTrace();
                mCharacterSet = new SavedCharactersSet();
                mCharacterSet.setCharacters(new ArrayList<SavedCharacter>());
            }
        }
        return mCharacterSet;

    }

    private synchronized void _saveCharacter(Context context, SavedCharacter character) {
        if (mCharacterSet == null) {
            mCharacterSet = _getCharacterSet(context);
        }
        if (mCharacterSet == null) {
            mCharacterSet = new SavedCharactersSet();
        }
        mCharacterSet.add(character);
        ObjectMapper mapper = getMapper();
        File f = SavedCharactersProvider.getSavedCharactersFile(context);
        try {
            mapper.writeValue(f, mCharacterSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized SavedCharacter _getCharacterById(Context context, int id) {
        if (sCharactersLRU.get(id) == null) {
            if (mCharacterSet == null) {
                mCharacterSet = _getCharacterSet(context);
                if (mCharacterSet == null) {
                    return null;
                }
            }
            for (SavedCharacter character : mCharacterSet.getCharacters()) {
                if (character != null && character.getId() == id) {
                    return character;
                }
            }
        }
        return null;
    }

    public interface OnRetreiveCharacter {
        void onRetreiveCharacter(SavedCharacter savedCharacter, int hashCode);
    }

    public interface OnCharactersInfoLoaded {
        void onCharactersInfoLoaded(Grouping grouping, List<CharacterInfo> result);
    }

    private static final class LazyMapperHolder {
        public static final ObjectMapper INSTANCE = new ObjectMapper();
    }

    private static final class LazyHolder {
        private static final SavedCharactersProvider INSTANCE = new SavedCharactersProvider();
    }
}
