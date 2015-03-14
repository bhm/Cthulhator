package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.content.Context;
import android.view.View;

import com.bustiblelemons.cthulhator.character.persistance.CharacterWrappper;
import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.cthulhator.view.FabListener;
import com.bustiblelemons.recycler.AbsRecyclerAdapter;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

import at.markushi.ui.CircleButton;

/**
 * Created by hiv on 10.12.14.
 */
public class CharacterViewerAdapter extends AbsRecyclerAdapter<CharacterViewerCard,
        AbsRecyclerHolder<CharacterViewerCard>> {

    private CharacterWrappper         mCharacterWrappper;
    private HeightSizeRelay           mHeightSizeRelay;
    private FabListener<CircleButton> mFabListener;

    public CharacterViewerAdapter(Context context) {
        super();
    }

    @Override
    public int getItemViewType(int position) {
        CharacterViewerCard card = getItem(position);
        return card.ordinal();
    }

    public CharacterViewerAdapter withFabListener(FabListener<CircleButton> listener) {
        mFabListener = listener;
        return this;
    }

    @Override
    public int getLayoutId(int viewType) {
        return CharacterViewerCard.values()[viewType].getLayoutId();
    }

    @Override
    public AbsRecyclerHolder<CharacterViewerCard> getViewHolder(View view, int viewType) {
        switch (getItem(viewType)) {
        case SEE_THROUGH:
            SeeThroughCardHolder holder = new SeeThroughCardHolder(view);
            mHeightSizeRelay = new HeightSizeRelay(holder);
            return holder;
        case TITLE:
            return new TitleCardHolder(view)
                    .withFabListener(mFabListener)
                    .withHeightSizeListener(mHeightSizeRelay)
                    .withCharacterInfoProivder(mCharacterWrappper)
                    .withPropertyValueRetreiver(mCharacterWrappper);
//        case MIND:
//            return new MindViewerCardHolder(view).withPropertyValueRetreiver(mCharacterWrappper);
        default:
            return new CharacterViewerCardHolder(view).withPropertyValueRetreiver(mCharacterWrappper);
        }
    }

    public CharacterViewerAdapter withCharacterWrapper(CharacterWrappper characterWrappper) {
        mCharacterWrappper = characterWrappper;
        return this;
    }
}
