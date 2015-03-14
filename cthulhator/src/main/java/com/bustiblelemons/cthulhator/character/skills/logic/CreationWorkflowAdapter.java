package com.bustiblelemons.cthulhator.character.skills.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.character.skills.model.CreationWorkflowCard;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfoProvider;
import com.bustiblelemons.recycler.AbsRecyclerAdapter;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

/**
 * Created by hiv on 14.03.15.
 */
public class CreationWorkflowAdapter extends AbsRecyclerAdapter<CreationWorkflowCard, AbsRecyclerHolder<CreationWorkflowCard>> {

    private OnCreationWorkflowListener mOnclicklistener;
    private CharacterInfoProvider      mCharacterInfoProvider;

    public CreationWorkflowAdapter withCreationWorkflowCardListener(OnCreationWorkflowListener listener) {
        mOnclicklistener = listener;
        return this;
    }

    public CreationWorkflowAdapter withCharacterInfoProvider(CharacterInfoProvider
                                                                     characterInfoProvider) {
        mCharacterInfoProvider = characterInfoProvider;
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getLayoutId(int viewType) {
        return getItem(viewType).getLayoutId();
    }

    @Override
    public AbsRecyclerHolder<CreationWorkflowCard> getViewHolder(View view, int viewType) {
        CreationWorkflowCard card = getItem(viewType);
        switch (card) {
        case HEADER:
            return new CreationWorkflowHeaderHolder(view)
                    .withCreationWorkflowCardListener(mOnclicklistener)
                    .withCharacterInfoProvider(mCharacterInfoProvider);
        default:
            return new CreationWorkflowCardHolder(view, card)
                    .withCreationWorkflowCardListener(mOnclicklistener);

        }
    }
}
