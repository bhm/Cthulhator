package com.bustiblelemons.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import com.bustiblelemons.bustiblelibs.R;
import com.bustiblelemons.holders.impl.ViewHolder;

import java.util.List;

/**
 * Created by bhm on 27.07.14.
 */
public abstract class AbsSpinnerAdapter<T> extends AbsListAdapter<T, ViewHolder<T>> implements SpinnerAdapter {
    protected AbsSpinnerAdapter(Context context) {
        super(context);
    }

    public AbsSpinnerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    public abstract ViewHolder<T> getDropDownHolder(int position);

    public abstract int getDropDownLayoutId(int position);

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder<T> holder;
        if (convertView == null) {
            holder = getDropDownHolder(position);
            convertView = getInflater().inflate(getDropDownLayoutId(position), parent, false);
            holder.create(convertView);
            convertView.setTag(R.id.tag_holder, holder);
        } else {
            holder = (ViewHolder<T>) convertView.getTag(R.id.tag_holder);
        }
        bindViewHolder(position, holder);
        return convertView;
    }
}
