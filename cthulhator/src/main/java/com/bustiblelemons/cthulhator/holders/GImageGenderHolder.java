package com.bustiblelemons.cthulhator.holders;

import android.content.Context;

import com.bustiblelemons.cthulhator.model.brp.gimagesearch.GImageSearchGender;
import com.bustiblelemons.holders.impl.BaseViewHolderImpl;

/**
 * Created by bhm on 27.07.14.
 */
public class GImageGenderHolder extends BaseViewHolderImpl<GImageSearchGender> {
    public GImageGenderHolder(Context context) {
        super(context);
    }

    @Override
    public void bindValues(GImageSearchGender item, int position) {
        setTitle(item.getTitleId());
    }
}
