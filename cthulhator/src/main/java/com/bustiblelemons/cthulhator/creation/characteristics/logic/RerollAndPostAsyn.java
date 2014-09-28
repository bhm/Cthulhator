package com.bustiblelemons.cthulhator.creation.characteristics.logic;

import android.content.Context;

import com.bustiblelemons.cthulhator.model.CharacterProperty;

import io.github.scottmaclure.character.traits.asyn.AbsAsynTask;

/**
 * Created by bhm on 28.09.14.
 */
public class RerollAndPostAsyn extends AbsAsynTask<CharacterProperty, CharacterProperty> {
    public RerollAndPostAsyn(Context context) {
        super(context);
    }

    @Override
    protected CharacterProperty call(CharacterProperty... params) throws Exception {
        return null;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    public void onProgressUpdate(CharacterProperty param, CharacterProperty result) {

    }
}
