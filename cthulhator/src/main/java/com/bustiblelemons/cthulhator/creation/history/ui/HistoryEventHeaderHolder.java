package com.bustiblelemons.cthulhator.creation.history.ui;

import android.content.Context;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.creation.history.model.HistoryEventHeader;
import com.bustiblelemons.holders.impl.AbsViewHolder;

/**
 * Created by bhm on 29.09.14.
 */
public class HistoryEventHeaderHolder extends AbsViewHolder<HistoryEventHeader> {
    public HistoryEventHeaderHolder(Context context) {
        super(context);
    }

    @Override
    public void bindValues(HistoryEventHeader item, int position) {
        if (item != null) {
            setTitle(item.getTitle());
        }
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.single_history_event_header;
    }
}
