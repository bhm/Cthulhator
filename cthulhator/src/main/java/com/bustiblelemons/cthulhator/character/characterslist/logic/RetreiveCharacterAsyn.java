package com.bustiblelemons.cthulhator.character.characterslist.logic;

import android.content.Context;

import com.bustiblelemons.async.AbsAsynTask;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.logging.Logger;

/**
 * @author jacek on 10/5/14 19:12
 * @origin com.bustiblelemons.cthulhator.character.characterslist.logic
 */
public class RetreiveCharacterAsyn extends AbsAsynTask<Integer, SavedCharacter> {
    private static final Logger log = new Logger(RetreiveCharacterAsyn.class);
    private CharacterCache.OnRetreiveCharacter onRetreiveCharacter;

    public RetreiveCharacterAsyn(Context context, CharacterCache.OnRetreiveCharacter onRetreiveCharacter) {
        super(context);
        this.onRetreiveCharacter = onRetreiveCharacter;
    }

    @Override
    protected SavedCharacter call(Integer... params) throws Exception {
        for (Integer hashCode : params) {
            if (hashCode != null) {
                SavedCharacter s = CharacterCache.getSavedCharacterByHashCode(getContext(), hashCode.intValue());
                publishProgress(hashCode, s);
            }
        }
        return null;
    }


    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected boolean onSuccess(SavedCharacter result) {
        return false;
    }

    @Override
    public void onProgressUpdate(Integer param, SavedCharacter result) {
        if (onRetreiveCharacter != null && param != null) {
            onRetreiveCharacter.onRetreiveCharacter(result, param.intValue());
        }
    }
}
