package com.bustiblelemons.cthulhator.creation.history.logic;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.bustiblelemons.cthulhator.adapters.AbsStickyListAdapter;
import com.bustiblelemons.cthulhator.creation.history.model.HistoryEventHeader;
import com.bustiblelemons.cthulhator.creation.history.ui.HistoryEventHeaderHolder;
import com.bustiblelemons.cthulhator.model.HistoryEvent;
import com.bustiblelemons.holders.impl.AbsViewHolder;

/**
 * Created by bhm on 29.09.14.
 */
public class HistoryAdapter extends AbsStickyListAdapter<HistoryEvent, AbsViewHolder<HistoryEvent>,
        HistoryEventHeader, AbsViewHolder<HistoryEventHeader>>
        implements AdapterView.OnItemClickListener {
    private final OnOpenHistoryEventDetails openHistoryEventDetails;

    public HistoryAdapter(Context context, OnOpenHistoryEventDetails openHistoryEventDetails) {
        super(context);
        this.openHistoryEventDetails = openHistoryEventDetails;
    }

    @Override
    protected AbsViewHolder<HistoryEvent> getViewHolder(int position) {
        return new HistoryEventHolder(getContext());
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (openHistoryEventDetails != null) {
            openHistoryEventDetails.onOpenHistoryEventDetails(getItem(position));
        }
    }

    @Override
    protected AbsViewHolder<HistoryEventHeader> getHeaderViewHolder(int position) {
        return new HistoryEventHeaderHolder(getContext());
    }
}
