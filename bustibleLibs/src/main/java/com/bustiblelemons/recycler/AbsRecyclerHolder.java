package com.bustiblelemons.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by hiv on 02.11.14.
 */
public abstract class AbsRecyclerHolder<I> extends RecyclerView.ViewHolder {

    public AbsRecyclerHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

    public abstract void onBindData(I item);

}
