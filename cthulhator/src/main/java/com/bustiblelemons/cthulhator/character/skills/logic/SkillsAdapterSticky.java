package com.bustiblelemons.cthulhator.character.skills.logic;

import android.content.Context;

import com.bustiblelemons.cthulhator.adapters.AbsStickyListAdapter;
import com.bustiblelemons.cthulhator.adapters.SkillChanged;
import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.holders.impl.ViewHolder;

/**
 * Created by bhm on 01.10.14.
 */
public class SkillsAdapterSticky extends AbsStickyListAdapter<
        CharacterProperty, ViewHolder<CharacterProperty>, ViewHolder<CharacterProperty>> {
    private final SkillChanged skillChanged;

    public SkillsAdapterSticky(Context context, SkillChanged skillChanged) {
        super(context);
        this.skillChanged = skillChanged;
    }

    @Override
    protected ViewHolder<CharacterProperty> getViewHolder(int position) {
        return new SkillViewHolder(getContext(), skillChanged);
    }

    @Override
    protected ViewHolder<CharacterProperty> getHeaderViewHolder(int position) {
        return new SkillsHeaderHolder(getContext());
    }


    @Override
    public long getHeaderId(int position) {
        CharacterProperty item = getItem(position);
        if (item == null) {
            return position;
        } else {
            ActionGroup g = item.getMainActionGroup();
            return g.hashCode();
        }
    }
}
