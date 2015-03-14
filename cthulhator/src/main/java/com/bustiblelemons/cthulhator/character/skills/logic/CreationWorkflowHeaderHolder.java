package com.bustiblelemons.cthulhator.character.skills.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.character.skills.model.CreationWorkflowCard;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterCardView;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfoProvider;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

/**
 * Created by hiv on 14.03.15.
 */
public class CreationWorkflowHeaderHolder extends AbsRecyclerHolder<CreationWorkflowCard>
        implements CharacterCardView.OnCharacterCardViewClick {

    private CharacterInfoProvider      mCharacterInfoProvider;
    private CharacterCardView          mCharacterCardView;
    private OnCreationWorkflowListener mOnCreationWorkflowListener;

    public CreationWorkflowHeaderHolder(View view) {
        super(view);
        if (view instanceof CharacterCardView) {
            mCharacterCardView = (CharacterCardView) view;
            mCharacterCardView.setOnCharacterCardViewClick(this);
        }
    }

    public CreationWorkflowHeaderHolder withCreationWorkflowCardListener(OnCreationWorkflowListener listener) {
        mOnCreationWorkflowListener = listener;
        return this;
    }

    @Override
    public void onBindData(CreationWorkflowCard item) {
        if (mCharacterCardView != null) {
            CharacterInfo characterInfo =
                    mCharacterInfoProvider.onRetreiveCharacterInfo(mCharacterCardView.getContext());
            mCharacterCardView.setCardInfo(characterInfo);
        }
    }

    public CreationWorkflowHeaderHolder withCharacterInfoProvider(CharacterInfoProvider
                                                                          characterInfoProvider) {
        mCharacterInfoProvider = characterInfoProvider;
        return this;
    }

    @Override
    public void onImageClick(CharacterCardView view) {
        if (mOnCreationWorkflowListener != null) {
            mOnCreationWorkflowListener.onCreationWorkflowClicked(CreationWorkflowCard.DETAILS);
        }
    }

    @Override
    public void onMainInfoClick(CharacterCardView view) {
        if (mOnCreationWorkflowListener != null) {
            mOnCreationWorkflowListener.onCreationWorkflowClicked(CreationWorkflowCard.DETAILS);
        }
    }

    @Override
    public void onShortInfoClick(CharacterCardView view) {
        if (mOnCreationWorkflowListener != null) {
            mOnCreationWorkflowListener.onCreationWorkflowClicked(CreationWorkflowCard.DETAILS);
        }
    }

    @Override
    public void onExtraInfoClick(CharacterCardView view) {
        if (mOnCreationWorkflowListener != null) {
            mOnCreationWorkflowListener.onCreationWorkflowClicked(CreationWorkflowCard.STATS);
        }
    }
}
