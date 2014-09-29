package com.bustiblelemons.cthulhator.creation.history.logic;

import android.content.Context;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.model.HistoryEvent;
import com.bustiblelemons.holders.impl.AbsViewHolder;

import butterknife.InjectView;

/**
 * Created by bhm on 29.09.14.
 */
public class HistoryEventHolder extends AbsViewHolder<HistoryEvent> {

    @InjectView(R.id.short_info)
    TextView descriptionView;
    @InjectView(R.id.date)
    TextView dateView;

    public HistoryEventHolder(Context context) {
        super(context);
    }

    @Override
    public void bindValues(HistoryEvent item, int position) {
        if (item != null) {
            setTitle(item.getName());
            if (dateView != null) {
                dateView.setText(item.getFormatedDate());
            }
            if (descriptionView != null) {
                descriptionView.setText(item.getDescription());
            }
        }
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.single_history_event;
    }
}
