package com.bustiblelemons.cthulhator.character.creation.logic;

import android.view.ViewGroup;

import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.recycler.AbsRecyclerAdapter;

/**
 * Created by hiv on 02.11.14.
 */
public class CreatorAdapter extends AbsRecyclerAdapter<CreatorCard, CreatorCardHolder> {

    @Override
    public CreatorCardHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new CreatorCardHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(CreatorCardHolder creatorCardHolder, int position) {
        creatorCardHolder.bindData(getItem(position));
    }
}
