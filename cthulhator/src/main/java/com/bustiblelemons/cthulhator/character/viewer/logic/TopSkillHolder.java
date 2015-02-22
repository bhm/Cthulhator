package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.recycler.AbsRecyclerHolder;
import com.bustiblelemons.views.SkillView;

/**
 * Created by hiv on 22.02.15.
 */
public class TopSkillHolder extends AbsRecyclerHolder<CharacterProperty> {

    private SkillView mSkillView;

    public TopSkillHolder(View view) {
        super(view);
        if (view instanceof SkillView) {
            mSkillView = (SkillView) view;
            mSkillView.setShowModifiers(false);
        }
    }

    @Override
    public void onBindData(CharacterProperty item) {
        if (mSkillView != null) {
            mSkillView.setTitle(item.getName());
            mSkillView.setIntValue(item.getValue());
        }
    }
}
