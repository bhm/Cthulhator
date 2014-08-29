package com.bustiblelemons.holders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bustiblelemons.bustiblelibs.R;
import com.bustiblelemons.holders.impl.ViewHolder;

/**
 * Created by bhm on 21.07.14.
 */
public class ValuePagerHolder implements ViewHolder<String> {
    private final Context  context;
    private       TextView title;

    public ValuePagerHolder(Context context) {
        this.context = context;
    }

    @Override
    public void create(View convertView) {
        title = (TextView) convertView;
    }

    @Override
    public void bindValues(String item, int position) {
        title.setText(item);
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.single_pager_item;
    }
}
