package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.recycler.AbsRecyclerAdapter;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

/**
 * Created by hiv on 22.02.15.
 */
public class TopRkillsAdapter
        extends AbsRecyclerAdapter<CharacterProperty, AbsRecyclerHolder<CharacterProperty>> {


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.single_skill_item;
    }

    @Override
    public AbsRecyclerHolder<CharacterProperty> getViewHolder(View view, int viewType) {
        return new TopSkillHolder(view);
    }
}
