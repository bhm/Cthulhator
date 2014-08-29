package com.bustiblelemons.cthulhator;

import com.bustiblelemons.BaseApplication;
import com.bustiblelemons.cthulhator.cache.CharacterCache;
import com.bustiblelemons.cthulhator.model.cache.SavedCharactersSet;

/**
 * Created 9 Dec 2013
 */
public class AppClass extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CharacterCache.initSavedCharactersCacheFile(this);
    }
}
