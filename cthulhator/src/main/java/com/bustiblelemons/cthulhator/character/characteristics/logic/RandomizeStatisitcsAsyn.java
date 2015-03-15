package com.bustiblelemons.cthulhator.character.characteristics.logic;

import android.content.Context;

import com.bustiblelemons.async.AbsSimpleAsync;
import com.bustiblelemons.cthulhator.character.persistance.CharacterWrapper;

/**
 * Created by hiv on 12.11.14.
 */
public class RandomizeStatisitcsAsyn extends AbsSimpleAsync<CharacterWrapper, CharacterWrapper> {

    private OnStatisitcsRandomized mOnStatisticsRandomized;

    public RandomizeStatisitcsAsyn(Context context) {
        super(context);
    }

    public RandomizeStatisitcsAsyn withOnStatisticsRandomzied(OnStatisitcsRandomized callback) {
        mOnStatisticsRandomized = callback;
        return this;
    }

    @Override
    protected CharacterWrapper call(CharacterWrapper... params) throws Exception {
        for (CharacterWrapper character : params) {
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
    protected void onProgressUpdate(CharacterWrapper param, CharacterWrapper result) {
        if (mOnStatisticsRandomized != null) {
            mOnStatisticsRandomized.onStatisitcsRandomzied(result);
        }
    }
}
