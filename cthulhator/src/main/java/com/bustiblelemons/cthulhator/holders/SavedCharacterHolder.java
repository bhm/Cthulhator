package com.bustiblelemons.cthulhator.holders;

import android.content.Context;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;
import com.bustiblelemons.holders.impl.AbsViewHolder;
import com.bustiblelemons.views.LoadingImage;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 13.08.14.
 */
public class SavedCharacterHolder extends AbsViewHolder<SavedCharacter> {

    @InjectView(android.R.id.icon)
    LoadingImage imageView;

    public SavedCharacterHolder(Context context) {
        super(context);
    }

    @Override
    public void create(View convertView) {
        super.create(convertView);
        ButterKnife.inject(this, convertView);
    }

    @Override
    public void bindValues(SavedCharacter item, int position) {
        setTitle(item.getName());
        if (imageView != null) {
            imageView.loadFrom(item.getPhotoUrl());
        }
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.single_saved_character;
    }
}
