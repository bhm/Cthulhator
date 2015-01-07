package com.bustiblelemons.cthulhator.system.properties;

import android.content.Context;

import com.bustiblelemons.async.AbsSimpleAsync;
import com.bustiblelemons.cthulhator.character.persistance.CharacterWrappper;

import java.util.Set;

/**
 * Created by hiv on 31.10.14.
 */
public class NotifyCorelativesAsyn extends AbsSimpleAsync<CharacterProperty, CharacterProperty> {

    private CharacterWrappper savedCharacter;

    public NotifyCorelativesAsyn(Context context, CharacterWrappper savedCharacter) {
        super(context);
        this.savedCharacter = savedCharacter;
    }

    @Override
    protected CharacterProperty call(CharacterProperty... params) throws Exception {
        for (CharacterProperty property : params) {
            if (property != null) {
                Set<CharacterProperty> corelatives = savedCharacter.getCorelatives(property);
            }
        }
        return null;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected void onProgressUpdate(CharacterProperty param, CharacterProperty result) {

    }
}
