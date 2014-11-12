package com.bustiblelemons.cthulhator.character.characteristics.logic;

import android.content.Context;

import com.bustiblelemons.async.AbsSimpleAsync;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;

/**
 * Created by hiv on 12.11.14.
 */
public class RandomizeStatisitcsAsyn extends AbsSimpleAsync<SavedCharacter, SavedCharacter> {

    private OnStatisitcsRandomized mOnStatisticsRandomized;

    public RandomizeStatisitcsAsyn(Context context) {
        super(context);
    }

    public RandomizeStatisitcsAsyn withOnStatisticsRandomzied(OnStatisitcsRandomized callback) {
        mOnStatisticsRandomized = callback;
        return this;
    }

    @Override
    protected SavedCharacter call(SavedCharacter... params) throws Exception {
        for (SavedCharacter character : params) {
            if (character != null) {
                character.randomizeStatistics();
                publishProgress(character, character);
            }
        }
        return null;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected void onProgressUpdate(SavedCharacter param, SavedCharacter result) {
        if (mOnStatisticsRandomized != null) {
            mOnStatisticsRandomized.onStatisitcsRandomzied(result);
        }
    }
}
