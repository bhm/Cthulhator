package com.bustiblelemons.cthulhator.character.portrait.logic;

import android.content.Context;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.google.apis.GoogleSearchGender;
import com.bustiblelemons.holders.impl.AbsViewHolder;

/**
 * Created by bhm on 27.07.14.
 */
public class GImageGenderHolder extends AbsViewHolder<GoogleSearchGender> {
    public GImageGenderHolder(Context context) {
        super(context);
    }

    @Override
    public void bindValues(GoogleSearchGender item, int position) {
        setTitle(item.getTitleId());
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.single_drop_down_gender;
    }
}
