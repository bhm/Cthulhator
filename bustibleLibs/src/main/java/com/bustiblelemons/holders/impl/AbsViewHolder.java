package com.bustiblelemons.holders.impl;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;


public abstract class AbsViewHolder<T> implements ViewHolder<T> {

    public String TAG;

    {
        TAG = this.getClass().getSimpleName();
    }

    protected Context  context;
    @Optional
    @InjectView(android.R.id.title)
    protected TextView titleView;
    private int position = 0;

    public AbsViewHolder(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void create(View convertView) {
        ButterKnife.inject(this, convertView);
    }

    public boolean hasTitle() {
        return this.titleView != null;
    }

    public int bindPosition(int position) {
        return this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public boolean setTitle(int resId) {
        titleView.setText(resId);
        return hasTitleView();
    }

    public boolean setTitle(String title) {
        titleView.setText(title);
        return hasTitleView();
    }

    protected boolean hasTitleView() {
        return this.titleView != null;
    }

    protected String getString(int resId, Object... args) {
        return getContext().getString(resId, args);
    }

    protected View getTitleView() {
        return this.titleView;
    }
}