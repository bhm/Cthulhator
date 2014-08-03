package com.bustiblelemons.cthulhator.holders;

import android.content.Context;

import com.bustiblelemons.cthulhator.model.brp.gimagesearch.Gender;
import com.bustiblelemons.holders.impl.AbsViewHolder;

/**
 * Created by bhm on 27.07.14.
 */
public class GImageGenderHolder extends AbsViewHolder<Gender> {
    public GImageGenderHolder(Context context) {
        super(context);
    }

    @Override
    public void bindValues(Gender item, int position) {
        setTitle(item.getTitleId());
    }
}
