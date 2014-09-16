package com.bustiblelemons.cthulhator.creation.characteristics.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.holders.impl.ViewHolder;
import com.bustiblelemons.views.SkillView;

/**
 * Created by bhm on 16.09.14.
 */
public class CharacterPropertyHolder implements ViewHolder<CharacterProperty> {

    SkillView titleView;

    @Override
    public void create(View convertView) {
        titleView = (SkillView) convertView;
    }

    @Override
    public void bindValues(CharacterProperty item, int position) {
        if (item != null) {
            titleView.setIntValue(item.getValue());
        }
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.single_character_property;
    }
}
