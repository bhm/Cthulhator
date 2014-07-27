package com.bustiblelemons.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import com.bustiblelemons.bustiblelibs.R;
import com.bustiblelemons.holders.impl.BaseViewHolder;

import java.util.List;

/**
 * Created by bhm on 27.07.14.
 */
public abstract class BaseSpinnerAdapter<T> extends BaseListAdapter<T> implements SpinnerAdapter {
    protected BaseSpinnerAdapter(Context context) {
        super(context);
    }

    public BaseSpinnerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    public abstract BaseViewHolder<T> getDropDownHolder(int position);

    public abstract int getDropDownLayoutId(int position);

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder<T> holder;
        if (convertView == null) {
            holder = getDropDownHolder(position);
            convertView = getInflater().inflate(getDropDownLayoutId(position), parent, false);
            holder.create(convertView);
            convertView.setTag(R.id.tag_holder, holder);
        } else {
            holder = (BaseViewHolder<T>) convertView.getTag(R.id.tag_holder);
        }
        bindViewHolder(position, holder);
        return convertView;
    }
}
