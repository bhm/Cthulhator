package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.content.Context;
import android.view.View;

import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.cthulhator.system.properties.PropertyValueRetreiver;
import com.bustiblelemons.recycler.AbsRecyclerAdapter;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

/**
 * Created by hiv on 10.12.14.
 */
public class CharacterViewerAdapter extends AbsRecyclerAdapter<CharacterViewerCard,
        AbsRecyclerHolder<CharacterViewerCard>> {

    private PropertyValueRetreiver    mRetreiver;

    public CharacterViewerAdapter(Context context) {
        super(context);
    }

    public CharacterViewerAdapter withPropertyValueRetreiver(PropertyValueRetreiver retreiver) {
        mRetreiver = retreiver;
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // View type actually is the position
    @Override
    public int getLayoutId(int viewType) {
        if (viewType >= 0) {
            CharacterViewerCard card = getItem(viewType);
            if (card != null) {
                return card.getLayoutId();
            }
        }
        return 0;
    }

    @Override
    public AbsRecyclerHolder<CharacterViewerCard> getViewHolder(View view) {
        CharacterViewerCardHolder holder = new CharacterViewerCardHolder(view);
        holder.withPropertyValueRetreiver(mRetreiver);
        return holder;
    }
}
