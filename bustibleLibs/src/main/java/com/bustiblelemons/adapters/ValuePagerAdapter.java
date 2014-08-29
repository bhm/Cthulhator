package com.bustiblelemons.adapters;

import android.content.Context;

import com.bustiblelemons.holders.ValuePagerHolder;
import com.bustiblelemons.holders.impl.ViewHolder;

/**
 * Created by bhm on 21.07.14.
 */
public class ValuePagerAdapter extends BaseViewPagerAdapter<String> {

    public ValuePagerAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder<String> getViewHolder(int position) {
        return new ValuePagerHolder(getContext());
    }
}
