package com.bustiblelemons.cthulhator.holders;

import android.content.Context;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.SkillChanged;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.holders.impl.ViewHolder;
import com.bustiblelemons.views.SkillView;

/**
 * Created by bhm on 31.08.14.
 */
public class SkillViewHolder implements ViewHolder<CharacterProperty>,SkillView.SkillViewListener {
    private final Context           context;
    private       SkillChanged      skillChanged;
    private       SkillView         skillView;
    private       CharacterProperty skill;

    public SkillViewHolder(Context context, SkillChanged skillChanged) {
        this.context = context;
        this.skillChanged = skillChanged;
    }

    @Override
    public void create(View convertView) {
        skillView = (SkillView) convertView;
    }

    @Override
    public void bindValues(CharacterProperty item, int position) {
        this.skill = item;
        if (skillView != null) {
            skillView.setTitle(item.getName());
            skillView.setValue(item.getValue() + "");
            skillView.setListener(this);
        }
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.single_skill_item;
    }

    @Override
    public void onSkillValueClick(SkillView view) {

    }

    @Override
    public void onSkillTitleClick(SkillView view) {

    }

    @Override
    public boolean onIncreaseClicked(SkillView view) {
        if (skillChanged != null) {
            skillChanged.onSkillChanged(this.skill, skill.getValue(), true);
        }
        return false;
    }

    @Override
    public boolean onDecreaseClicked(SkillView view) {
        if (skillChanged != null) {
            skillChanged.onSkillChanged(this.skill, skill.getValue(), false);
        }
        return false;
    }
}
