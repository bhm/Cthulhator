package com.bustiblelemons.cthulhator.view.characteristiccard;

import android.content.Context;
import android.view.View;

import com.bustiblelemons.adapters.AbsListAdapter;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.PropertyType;
import com.bustiblelemons.holders.impl.ViewHolder;
import com.bustiblelemons.views.SkillView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hiv on 02.11.14.
 */
public class CharacterCardAdapter extends
                                  AbsListAdapter<CharacterProperty, ViewHolder<CharacterProperty>> {

    public CharacterCardAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder<CharacterProperty> getViewHolder(int position) {
        return new CharacterCardPropertyHolder(getContext());
    }

    protected class CharacterCardPropertyHolder implements ViewHolder<CharacterProperty> {

        private final Context context;
        @InjectView(android.R.id.title)
        SkillView titleView;

        public CharacterCardPropertyHolder(Context context) {
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
                if (PropertyType.DAMAGE_BONUS.equals(item.getType())) {
                    titleView.setValue(item.getDisplayName());
                }
            }
        }

        @Override
        public int getLayoutId(int position) {
            return R.layout.single_character_property;
        }
    }
}
