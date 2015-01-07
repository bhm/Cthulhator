package com.bustiblelemons.cthulhator.character.characteristics.logic;

import android.content.Context;

import com.bustiblelemons.async.AbsSimpleAsync;
import com.bustiblelemons.cthulhator.character.persistance.CharacterWrappper;

/**
 * Created by hiv on 12.11.14.
 */
public class RandomizeStatisitcsAsyn extends AbsSimpleAsync<CharacterWrappper, CharacterWrappper> {

    private OnStatisitcsRandomized mOnStatisticsRandomized;

    public RandomizeStatisitcsAsyn(Context context) {
        super(context);
    }

    public RandomizeStatisitcsAsyn withOnStatisticsRandomzied(OnStatisitcsRandomized callback) {
        mOnStatisticsRandomized = callback;
        return this;
    }

    @Override
    protected CharacterWrappper call(CharacterWrappper... params) throws Exception {
        for (CharacterWrappper character : params) {
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
    protected void onProgressUpdate(CharacterWrappper param, CharacterWrappper result) {
        if (mOnStatisticsRandomized != null) {
            mOnStatisticsRandomized.onStatisitcsRandomzied(result);
        }
    }
}
