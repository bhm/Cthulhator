package com.bustiblelemons.cthulhator.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.adapters.AbsListAdapter;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.holders.impl.ViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by bhm on 29.09.14.
 */
public abstract class AbsStickyListAdapter<T, H extends ViewHolder<T>,
        S, SH extends ViewHolder<S>>
        extends AbsListAdapter<T, H>
        implements StickyListHeadersAdapter {
    private List<S> mHeaders;

    public AbsStickyListAdapter(Context context) {
        super(context);
    }

    public AbsStickyListAdapter(Context context, List<T> data) {
        super(context, data);
    }

    protected abstract SH getHeaderViewHolder(int position);

    @Override
    public View getHeaderView(int position, View view, ViewGroup viewGroup) {
        ViewHolder<S> holder;
        if (view == null) {
            holder = getHeaderViewHolder(position);
            view = getInflater().inflate(holder.getLayoutId(position), viewGroup, false);

        } else {
            holder = (ViewHolder<S>) view.getTag(R.id.tag_holder);
        }
        holder.bindValues(getHeaderItem(position), position);
        return view;
    }

    private S getHeaderItem(int location) {
        if (mHeaders != null) {
            mHeaders.get(location);
        }
        return null;
    }

    @Override
    public long getHeaderId(int i) {
        return i;
    }

    public void addData(S header, Collection<T> data) {
        addHeader(header);
        addItems(data);
    }

    private void addHeader(S header) {
        if (mHeaders == null) {
            mHeaders = new ArrayList<S>();
        }
        mHeaders.add(header);
    }

    @Override
    public void removeAll() {
        mHeaders.clear();
        super.removeAll();
    }
}
