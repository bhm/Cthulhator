package com.bustiblelemons.cthulhator.holders;

import android.content.Context;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.model.brp.skills.BRPSkill;
import com.bustiblelemons.holders.impl.AbsViewHolder;

/**
 * Created by bhm on 20.07.14.
 */
public class BRPSkillHolder extends AbsViewHolder<BRPSkill> {
    public BRPSkillHolder(Context context) {
        super(context);
    }

    @Override
    public void create(View convertView) {
        super.create(convertView);

    }

    @Override
    public void bindValues(BRPSkill item, int position) {

    }

    @Override
    public int getLayoutId(int position) {
        return  R.layout.single_brp_skill_item;
    }
}
