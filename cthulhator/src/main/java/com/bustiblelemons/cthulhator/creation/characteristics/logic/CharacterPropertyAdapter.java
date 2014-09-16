package com.bustiblelemons.cthulhator.creation.characteristics.logic;

import android.content.Context;

import com.bustiblelemons.adapters.AbsListAdapter;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.holders.impl.ViewHolder;

/**
 * Created by bhm on 16.09.14.
 */
public class CharacterPropertyAdapter
        extends AbsListAdapter<CharacterProperty, ViewHolder<CharacterProperty>> {
    public CharacterPropertyAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder<CharacterProperty> getViewHolder(int position) {
        return new CharacterPropertyHolder();
    }
}
