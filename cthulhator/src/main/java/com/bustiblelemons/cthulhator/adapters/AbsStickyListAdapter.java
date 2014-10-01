package com.bustiblelemons.cthulhator.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.adapters.AbsListAdapter;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.holders.impl.ViewHolder;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by bhm on 29.09.14.
 */
public abstract class AbsStickyListAdapter<T, H extends ViewHolder<T>, SH extends ViewHolder<T>>
        extends AbsListAdapter<T, H>
        implements StickyListHeadersAdapter {

    public AbsStickyListAdapter(Context context) {
        super(context);
    }

    public AbsStickyListAdapter(Context context, List<T> data) {
        super(context, data);
    }

    protected abstract SH getHeaderViewHolder(int position);

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder<T> holder;
        if (convertView == null) {
            holder = getHeaderViewHolder(position);
            convertView = getInflater().inflate(holder.getLayoutId(position), viewGroup, false);
            holder.create(convertView);
            convertView.setTag(R.id.tag_holder, holder);
        } else {
            holder = (ViewHolder<T>) convertView.getTag(R.id.tag_holder);
        }
        holder.bindValues(getItem(position), position);
        return convertView;
    }
}
