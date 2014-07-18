package com.bustiblelemons.holders.impl;

import android.view.View;

/**
 * @author jacek on 3/27/14 15:36
 * @origin pl.foxcode.blumea.manager.statistics.holders
 */
public interface BaseViewHolder<T> {
    public void create(View convertView);

    public void bindValues(T item, int position);
}
