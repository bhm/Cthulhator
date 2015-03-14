package com.bustiblelemons.cthulhator.character.skills.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.character.skills.model.CreationWorkflowCard;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

/**
 * Created by hiv on 14.03.15.
 */
public class CreationWorkflowCardHolder extends AbsRecyclerHolder<CreationWorkflowCard> implements
                                                                                        View.OnClickListener {

    private CreationWorkflowCard mCard;

    private OnCreationWorkflowListener mOnCreationWorkflowListener;

    public CreationWorkflowCardHolder withCreationWorkflowCardListener(OnCreationWorkflowListener listener) {
        mOnCreationWorkflowListener =  listener;
        return this;
    }

    public CreationWorkflowCardHolder(View view, CreationWorkflowCard card) {
        super(view);
        view.setOnClickListener(this);
        mCard = card;
    }

    @Override
    public void onBindData(CreationWorkflowCard item) {
        //fallthrough
    }

    @Override
    public void onClick(View v) {
        if (mOnCreationWorkflowListener != null) {
            mOnCreationWorkflowListener.onCreationWorkflowClicked(mCard);
        }
    }
}
