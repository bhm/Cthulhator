package com.bustiblelemons.cthulhator.character.skills.logic;

import android.content.Context;

import com.bustiblelemons.cthulhator.adapters.AbsStickyListAdapter;
import com.bustiblelemons.cthulhator.adapters.OnSkillChanged;
import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.holders.impl.ViewHolder;

/**
 * Created by bhm on 01.10.14.
 */
public class SkillsAdapterSticky extends AbsStickyListAdapter<
        CharacterProperty, ViewHolder<CharacterProperty>, ViewHolder<CharacterProperty>> {

    private OnSkillChanged     mOnSkillChanged;
    private CanModifyPointPool mCanModifyPointPool;

    public SkillsAdapterSticky(Context context, OnSkillChanged onSkillChanged,
                               CanModifyPointPool canModifyPointPool) {
        super(context);
        this.mOnSkillChanged = onSkillChanged;
        this.mCanModifyPointPool = canModifyPointPool;
    }

    @Override
    protected ViewHolder<CharacterProperty> getViewHolder(int position) {
        return new SkillViewHolder(mOnSkillChanged, mCanModifyPointPool);
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
