package com.bustiblelemons.cthulhator.character.description.logic;

import android.content.Context;

import com.bustiblelemons.holders.impl.AbsViewHolder;

import io.github.scottmaclure.character.traits.model.RandomTraitsSet;

/**
 * Created by bhm on 02.08.14.
 */
public class RandomTraitsSetHolder extends AbsViewHolder<RandomTraitsSet> {
    public RandomTraitsSetHolder(Context context) {
        super(context);
    }

    @Override
    public void bindValues(RandomTraitsSet item, int position) {

    }

    @Override
    public int getLayoutId(int position) {
        return 0;
    }
}
