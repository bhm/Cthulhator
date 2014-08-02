package com.bustiblelemons.cthulhator.adapters;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.bustiblelemons.adapters.SequentialAdapter;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.holders.BRPSkillHolder;
import com.bustiblelemons.cthulhator.model.brp.skills.BRPSkill;
import com.bustiblelemons.holders.impl.ViewHolder;

/**
 * Created by bhm on 20.07.14.
 */
public class BRPSkillsAdapter extends SequentialAdapter<BRPSkill>
        implements AdapterView.OnItemClickListener {
    public BRPSkillsAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder<BRPSkill> getViewHolder(int position) {
        return new BRPSkillHolder(getContext());
    }

    @Override
    protected int getItemLayoutId(int position) {
        return R.layout.single_brp_skill_item;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
