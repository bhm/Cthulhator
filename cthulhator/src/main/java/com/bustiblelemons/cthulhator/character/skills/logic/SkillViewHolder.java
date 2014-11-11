package com.bustiblelemons.cthulhator.character.skills.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.OnSkillChanged;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.holders.impl.ViewHolder;
import com.bustiblelemons.views.SkillView;

/**
 * Created by bhm on 31.08.14.
 */
public class SkillViewHolder implements ViewHolder<CharacterProperty>,
                                        SkillView.OnValueButtonsClicked {
    private OnSkillChanged     onSkillChanged;
    private SkillView          skillView;
    private CharacterProperty  mProperty;
    private CanModifyPointPool mCanModifyPointPool;

    public SkillViewHolder(OnSkillChanged onSkillChanged, CanModifyPointPool canModifyPointPool) {
        this.onSkillChanged = onSkillChanged;
        this.mCanModifyPointPool = canModifyPointPool;
    }

    @Override
    public void create(View convertView) {
        skillView = (SkillView) convertView;
    }

    @Override
    public void bindValues(CharacterProperty item, int position) {
        this.mProperty = item;
        if (skillView != null) {
            skillView.setMinValue(item.getMinValue());
            skillView.setMaxValue(item.getMaxValue());
            skillView.setTitle(item.getName());
            skillView.setIntValue(item.getValue());
            skillView.setOnValueButtonsClicked(this);
        }
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.single_skill_item;
    }

    @Override
    public boolean onIncreaseClicked(SkillView view) {
        if (mCanModifyPointPool != null && mCanModifyPointPool.canDecreasePointPool()) {
            if (onSkillChanged != null && mProperty.increaseValue()) {
                skillView.setIntValue(mProperty.getValue());
                return onSkillChanged.onSkillIncreased(mProperty);
            }
        }
        return false;
    }

    @Override
    public boolean onDecreaseClicked(SkillView view) {
        if (mCanModifyPointPool != null && mCanModifyPointPool.canIncreasePointPool()) {
            if (onSkillChanged != null && mProperty.decreaseValue()) {
                skillView.setIntValue(mProperty.getValue());
                return onSkillChanged.onSkillDecreased(mProperty);
            }
        }
        return false;
    }
}
