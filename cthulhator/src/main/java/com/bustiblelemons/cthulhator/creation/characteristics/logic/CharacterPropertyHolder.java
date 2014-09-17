package com.bustiblelemons.cthulhator.creation.characteristics.logic;

import android.content.Context;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.holders.impl.ViewHolder;
import com.bustiblelemons.utils.ResourceHelper;
import com.bustiblelemons.views.SkillView;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 16.09.14.
 */
public class CharacterPropertyHolder implements ViewHolder<CharacterProperty> {

    @InjectView(android.R.id.title)
    SkillView titleView;
    private Context context;

    public CharacterPropertyHolder(Context context) {
        this.context = context;
    }

    @Override
    public void create(View convertView) {
        ButterKnife.inject(this, convertView);
    }

    @Override
    public void bindValues(CharacterProperty item, int position) {
        if (item != null) {
            titleView.setTitle(item.getName());
            titleView.setIsPercentile(item.isPercentile());
            titleView.setIntValue(item.getValue());
        }
    }

    private void retreivePropertyName(CharacterProperty item) {
        String name = item.getName();
        String type = item.getType().name().toLowerCase(Locale.ENGLISH);
        String resName = String.format(Locale.ENGLISH, "%s_%s", type, name);
        int resId = ResourceHelper.from(context).getIdentifierForString(resName);
        item.setNameResId(resId);
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.single_character_property;
    }
}
