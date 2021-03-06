package com.bustiblelemons.cthulhator.character.skills.logic;

import android.content.Context;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.holders.impl.AbsViewHolder;

/**
 * Created by bhm on 01.10.14.
 */
public class SkillsHeaderHolder extends AbsViewHolder<CharacterProperty> {
    public SkillsHeaderHolder(Context context) {
        super(context);
    }

    @Override
    public void bindValues(CharacterProperty item, int position) {
        if (item != null) {
            setTitle(item.getMainActionGroup().name());
        }
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.single_skills_header;
    }
}
