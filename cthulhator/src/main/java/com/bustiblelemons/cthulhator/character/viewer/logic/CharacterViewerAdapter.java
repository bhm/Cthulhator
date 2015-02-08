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
                                                           PropertyValueRetreiver,
                                                           HeightSizeListener {

    private CharacterWrappper mCharacterWrappper;
    private HeightSizeRelay   mHeightSizeRelay;

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
            SeeThroughCardHolder holder = new SeeThroughCardHolder(view);
            mHeightSizeRelay = new HeightSizeRelay(holder);
            return holder;
        case TITLE:
            return new TitleCardHolder(view)
                    .withHeightSizeListener(mHeightSizeRelay)
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
        if (mCharacterWrappper != null) {
            return mCharacterWrappper.onRetreiveCharacterInfo(arg);
        }
        return null;
    }

    @Override
    public int onRetreivePropertValue(String propertyName) {
        if (mCharacterWrappper != null) {
            return mCharacterWrappper.onRetreivePropertValue(propertyName);
        }
        return 0;
    }

    @Override
    public void onHeightSizeReported(Object reporter, int height) {

    }
}
