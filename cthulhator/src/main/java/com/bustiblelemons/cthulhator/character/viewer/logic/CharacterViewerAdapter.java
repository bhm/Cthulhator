package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.character.persistance.CharacterWrapper;
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

    private CharacterWrapper          mCharacterWrapper;
    private HeightSizeRelay           mHeightSizeRelay;
    private FabListener<CircleButton> mFabListener;

    private OnShowSkills mOnShowSkillsCallback;

    public CharacterViewerAdapter withOnShowSkillsCallback(OnShowSkills arg) {
        mOnShowSkillsCallback = arg;
        return this;
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
                    .withCharacterInfoProivder(mCharacterWrapper)
                    .withPropertyValueRetreiver(mCharacterWrapper)
                    .withOnShowSkillsCallback(mOnShowSkillsCallback);
//        case MIND:
//            return new MindViewerCardHolder(view).withPropertyValueRetreiver(mCharacterWrappper);
        default:
            return new CharacterViewerCardHolder(view).withPropertyValueRetreiver(mCharacterWrapper);
        }
    }

    public CharacterViewerAdapter withCharacterWrapper(CharacterWrapper characterWrapper) {
        mCharacterWrapper = characterWrapper;
        return this;
    }
}
