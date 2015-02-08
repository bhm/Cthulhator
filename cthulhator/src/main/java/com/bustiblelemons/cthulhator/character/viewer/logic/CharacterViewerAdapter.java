package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.content.Context;
import android.view.View;

import com.bustiblelemons.cthulhator.character.persistance.CharacterWrappper;
import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.cthulhator.system.properties.PropertyValueRetreiver;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfoProvider;
import com.bustiblelemons.recycler.AbsRecyclerAdapter;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

/**
 * Created by hiv on 10.12.14.
 */
public class CharacterViewerAdapter extends AbsRecyclerAdapter<CharacterViewerCard,
        AbsRecyclerHolder<CharacterViewerCard>> implements CharacterInfoProvider,
                                                           PropertyValueRetreiver {

    private CharacterWrappper mCharacterWrappper;
    private int mSeeThroughSize;

    public CharacterViewerAdapter(Context context) {
        super(context);
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
    public AbsRecyclerHolder<CharacterViewerCard> getViewHolder(View view, int viewType) {
        switch (getItem(viewType)) {
        case SEE_THROUGH:
            return new SeeThroughCardHolder(view, mSeeThroughSize);
        case TITLE:
            return new TitleCardHolder(view)
                    .withCharacterInfoProivder(this)
                    .withPropertyValueRetreiver(this);
        default:
            return new CharacterViewerCardHolder(view).withPropertyValueRetreiver(mCharacterWrappper);
        }
    }

    public CharacterViewerAdapter withCharacterWrapper(CharacterWrappper characterWrappper) {
        mCharacterWrappper = characterWrappper;
        return this;
    }

    @Override
    public CharacterInfo onRetreiveCharacterInfo(Context arg) {

        return null;
    }

    @Override
    public int onRetreivePropertValue(String propertyName) {
        return 0;
    }

    public CharacterViewerAdapter withSeeThroughSize(int seeThroughSize) {
        mSeeThroughSize = seeThroughSize;
        return this;
    }
}
