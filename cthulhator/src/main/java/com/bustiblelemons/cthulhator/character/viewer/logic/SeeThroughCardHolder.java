package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

/**
 * Created by hiv on 08.02.15.
 */
public class SeeThroughCardHolder extends AbsRecyclerHolder<CharacterViewerCard> {


    public SeeThroughCardHolder(View view, int seeThroughSize) {
        super(view);
        view.setMinimumHeight(seeThroughSize);
    }

    @Override
    public void onBindData(CharacterViewerCard item) {

    }
}
