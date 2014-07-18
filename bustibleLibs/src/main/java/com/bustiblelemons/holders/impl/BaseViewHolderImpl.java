package com.bustiblelemons.holders.impl;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bustiblelemons.holders.ViewNotFountException;


public abstract class BaseViewHolderImpl<T> implements BaseViewHolder<T> {

    public String TAG;

    {
        TAG = this.getClass().getSimpleName();
    }

    protected Context  context;
    protected TextView titleView;
    private int position = 0;

    public BaseViewHolderImpl(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void create(View convertView) {
        this.titleView = (TextView) convertView.findViewById(android.R.id.title);
        if (titleView == null) {
            throw new ViewNotFountException();
        }
    }

    public int bindPosition(int position) {
        return this.position = position;
    }

    public int getPosition() {
        return position;
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