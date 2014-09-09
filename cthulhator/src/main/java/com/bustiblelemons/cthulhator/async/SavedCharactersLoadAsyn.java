package com.bustiblelemons.cthulhator.async;

import android.content.Context;

import com.bustiblelemons.cthulhator.cache.CharacterCache;
import com.bustiblelemons.cthulhator.model.Grouping;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;
import com.bustiblelemons.cthulhator.model.cache.SavedCharactersSet;

import java.util.List;

import io.github.scottmaclure.character.traits.network.api.asyn.AbsAsynTask;

/**
 * Created by bhm on 13.08.14.
 */
public class SavedCharactersLoadAsyn extends AbsAsynTask<Grouping, List<SavedCharacter>> {

    private final SavedCharactersCallBack callBack;

    public SavedCharactersLoadAsyn(Context context, SavedCharactersCallBack callBack) {
        super(context);
        this.callBack = callBack;
    }

    @Override
    protected List<SavedCharacter> call(Grouping... groupings) throws Exception {
        for (Grouping g : groupings) {
            SavedCharactersSet set = CharacterCache.getCharacterSet(context);
            if (set != null) {
                publishProgress(g, set.get(g.getOffset(), g.getLimit()));
            }
        }
        return null;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected boolean onSuccess(List<SavedCharacter> result) {
        return false;
    }

    @Override
    public void onProgressUpdate(Grouping param, List<SavedCharacter> result) {
        if (callBack != null) {
            callBack.onSavedCharactersLoaded(param, result);
        }
    }
}
