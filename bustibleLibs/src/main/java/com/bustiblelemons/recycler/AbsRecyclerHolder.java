package com.bustiblelemons.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by hiv on 02.11.14.
 */
public abstract class AbsRecyclerHolder<I> extends RecyclerView.ViewHolder {

    private final LayoutInflater mLayoutInflater;

    public AbsRecyclerHolder(View itemView) {
        super(itemView);
        mLayoutInflater = LayoutInflater.from(itemView.getContext());
    }

    public LayoutInflater getInflater() {
        return mLayoutInflater;
    }

    public abstract void bindData(I item);

}
