package com.bustiblelemons.cthulhator.async;

import android.content.Context;

import com.bustiblelemons.cthulhator.cache.CharacterCache;
import com.bustiblelemons.cthulhator.model.Grouping;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacterTransformer;
import com.bustiblelemons.cthulhator.model.cache.SavedCharactersSet;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;

import java.util.ArrayList;
import java.util.List;

import io.github.scottmaclure.character.traits.asyn.AbsAsynTask;

/**
 * Created by bhm on 13.08.14.
 */
public class SavedCharactersLoadAsyn extends AbsAsynTask<Grouping, List<CharacterInfo>> {

    private final CharacterCache.OnCharactersInfoLoaded callBack;

    public SavedCharactersLoadAsyn(Context context, CharacterCache.OnCharactersInfoLoaded callBack) {
        super(context);
        this.callBack = callBack;
    }

    @Override
    protected List<CharacterInfo> call(Grouping... groupings) throws Exception {
        for (Grouping g : groupings) {
            SavedCharactersSet set = CharacterCache.getCharacterSet(context);
            if (set != null) {
                List<CharacterInfo> infos = new ArrayList<CharacterInfo>();
                for (SavedCharacter savedCharacter : set.getCharacters()) {
                    CharacterInfo characterInfo =
                            SavedCharacterTransformer.getInstance().transform(savedCharacter);
                    infos.add(characterInfo);
                }
                publishProgress(g, infos);
            }
        }
        return null;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected boolean onSuccess(List<CharacterInfo> result) {
        return false;
    }

    @Override
    public void onProgressUpdate(Grouping param, List<CharacterInfo> result) {
        if (callBack != null) {
            callBack.onCharactersInfoLoaded(param, result);
        }
    }
}
