package com.bustiblelemons.cthulhator.adapters;

import android.content.Context;

import com.bustiblelemons.adapters.AbsListAdapter;
import com.bustiblelemons.cthulhator.holders.SkillViewHolder;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.holders.impl.ViewHolder;

/**
 * Created by bhm on 31.08.14.
 */
public class SkillsAdapter
        extends AbsListAdapter<CharacterProperty, ViewHolder<CharacterProperty>> {
    private final SkillChanged skillChanged;

    public SkillsAdapter(Context context, SkillChanged skillChanged) {
        super(context);
        this.skillChanged = skillChanged;
    }

    @Override
    protected ViewHolder<CharacterProperty> getViewHolder(int position) {
        return new SkillViewHolder(getContext(), skillChanged);
    }
}
