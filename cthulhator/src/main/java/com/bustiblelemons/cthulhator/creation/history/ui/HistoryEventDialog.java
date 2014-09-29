package com.bustiblelemons.cthulhator.creation.history.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.model.HistoryEvent;
import com.bustiblelemons.fragments.dialog.AbsArgDialogFragment;
import com.micromobs.android.floatlabel.FloatLabelEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by bhm on 29.09.14.
 */
public class HistoryEventDialog extends AbsArgDialogFragment<HistoryEvent> {

    public static final String TAG = HistoryEventDialog.class.getSimpleName();
    @InjectView(android.R.id.title)
    FloatLabelEditText titleView;
    @InjectView(R.id.short_info)
    FloatLabelEditText descriptionView;
    @InjectView(R.id.date)
    TextView           dateView;

    private HistoryEvent             mEvent;
    private OnHistoryEventPassedBack mCallback;

    public static HistoryEventDialog newInstance(HistoryEvent event) {
        HistoryEventDialog r = new HistoryEventDialog();
        r.setNewInstanceArgument(event);
        return r;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnHistoryEventPassedBack) {
            mCallback = (OnHistoryEventPassedBack) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dailog_history_event, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEvent = getInstanceArgument();
        setupView();
    }

    private void setupView() {
        if (mEvent != null) {
            if (titleView != null) {
                titleView.setText(mEvent.getName());
            }
            if (descriptionView != null) {
                descriptionView.setText(mEvent.getDescription());
            }
            if (dateView != null) {
                dateView.setText(mEvent.getFormatedDate());
            }
        }
    }

    @OnClick(R.id.date)
    public void onPickDate(View view) {

    }

    @Override
    protected void onInstanceArgumentRead(HistoryEvent instanceArgument) {
        mEvent = instanceArgument;
    }

    public interface OnHistoryEventPassedBack {
        void onHistoryEventEdited(HistoryEvent old, HistoryEvent newEvent);
    }
}
