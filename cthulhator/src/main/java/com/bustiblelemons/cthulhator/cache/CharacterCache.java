package com.bustiblelemons.cthulhator.cache;

import android.content.Context;

import com.bustiblelemons.cthulhator.async.SavedCharactersCallBack;
import com.bustiblelemons.cthulhator.async.SavedCharactersLoadAsyn;
import com.bustiblelemons.cthulhator.model.Grouping;
import com.bustiblelemons.cthulhator.model.cache.SavedCharactersSet;
import com.bustiblelemons.cthulhator.model.desc.CharacterDescription;
import com.bustiblelemons.storage.Storage;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by bhm on 20.07.14.
 */
public class CharacterCache {

    private static String sCharactersCacheFile = "saved.characters.json";
    private static SavedCharactersSet sSavedCharacterSet;
    private static CharacterCache instance;

    protected CharacterCache() {
    }

    public static CharacterCache getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void loadSavedCharactersAsync(Context context, Grouping... groupings) {
        if (context instanceof SavedCharactersCallBack) {
            SavedCharactersLoadAsyn load = new SavedCharactersLoadAsyn(context,
                    (SavedCharactersCallBack) context);
            load.executeCrossPlatform(groupings);
        } else {
            throw new IllegalArgumentException(
                    "Context should implement " + SavedCharactersCallBack.class.toString());
        }
    }

    public synchronized static void addSavedCharacter(CharacterDescription description) {

    }

    public static File getSavedCharactersFile(Context context) {
        return Storage.getStorageFile(context, sCharactersCacheFile);
    }

    public static void initSavedCharactersCacheFile(Context context) {
        File file = getSavedCharactersFile(context);
        if (!file.exists()) {
            ObjectMapper m = new ObjectMapper();
            try {
                m.writeValue(file, SavedCharactersSet.empty());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized static SavedCharactersSet getCharacterSet(Context context) {
        if (sSavedCharacterSet == null) {
            ObjectMapper m = new ObjectMapper();
            File savedCharacters = CharacterCache.getSavedCharactersFile(context);
            try {
                sSavedCharacterSet = m.readValue(savedCharacters, SavedCharactersSet.class);
            } catch (IOException e) {
                e.printStackTrace();
                sSavedCharacterSet = SavedCharactersSet.empty();
            }
        }
        return sSavedCharacterSet;
    }

    private static final class LazyHolder {
        private static final CharacterCache INSTANCE = new CharacterCache();
    }
}
