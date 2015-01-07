package com.bustiblelemons.cthulhator.character.persistance;

import android.content.Context;

import com.bustiblelemons.async.AbsSimpleAsync;
import com.bustiblelemons.cthulhator.character.characterslist.logic.SavedCharacterTransformer;
import com.bustiblelemons.cthulhator.system.Grouping;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bhm on 13.08.14.
 */
public class SavedCharactersLoadAsyn extends AbsSimpleAsync<Grouping, List<CharacterInfo>> {

    private final SavedCharactersProvider.OnCharactersInfoLoaded callBack;

    public SavedCharactersLoadAsyn(Context context, SavedCharactersProvider.OnCharactersInfoLoaded callBack) {
        super(context);
        this.callBack = callBack;
    }

    @Override
    protected List<CharacterInfo> call(Grouping... groupings) throws Exception {
        for (Grouping g : groupings) {
            SavedCharactersSet set = SavedCharactersProvider.getCharacterSet(context);
            if (set != null) {
                List<CharacterInfo> infos = new ArrayList<CharacterInfo>();
                for (SavedCharacter savedCharacter : set.getCharacters()) {
                    CharacterInfo characterInfo =
                            SavedCharacterTransformer.getInstance()
                                    .withContext(getContext())
                                    .transform(new CharacterWrappper(savedCharacter));
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
