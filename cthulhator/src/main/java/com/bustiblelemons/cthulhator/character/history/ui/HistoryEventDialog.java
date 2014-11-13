package com.bustiblelemons.cthulhator.character.history.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.history.logic.OnShowDatePicker;
import com.bustiblelemons.cthulhator.character.history.model.HistoryEvent;
import com.bustiblelemons.fragments.dialog.AbsArgDialogFragment;
import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.micromobs.android.floatlabel.FloatLabelEditText;

import org.joda.time.DateTime;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by bhm on 29.09.14.
 */
public class HistoryEventDialog extends AbsArgDialogFragment<HistoryEvent>
        implements CalendarDatePickerDialog.OnDateSetListener {

    public static final String TAG = HistoryEventDialog.class.getSimpleName();
    @InjectView(android.R.id.title)
    FloatLabelEditText titleView;
    @InjectView(R.id.short_info)
    FloatLabelEditText descriptionView;
    @InjectView(R.id.date)
    TextView           dateView;

    private HistoryEvent             mEvent;
    private OnHistoryEventPassedBack mCallback;
    private OnShowDatePicker         mOnShowDatePicker;
    private DateTime mDate;

    public static HistoryEventDialog newInstance(HistoryEvent event) {
        HistoryEventDialog r = new HistoryEventDialog();
        r.setNoTitle(true);
        r.setNewInstanceArgument(event);
        return r;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnHistoryEventPassedBack) {
            mCallback = (OnHistoryEventPassedBack) activity;
        }
        if (activity instanceof OnShowDatePicker) {
            mOnShowDatePicker = (OnShowDatePicker) activity;
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
    }

    private void setupView() {
        if (mEvent != null) {
            if (titleView != null) {
                titleView.setText(mEvent.getName());
            }
            if (descriptionView != null) {
                descriptionView.setText(mEvent.getDescription());
            }
            setDateView();
        }
    }

    private void setDateView() {
        if (dateView != null) {
            dateView.setText(mEvent.getFormatedDate());
        }
    }

    @OnClick(R.id.add)
    public void onSave(View view) {
        try {
            if (mCallback != null) {
                HistoryEvent newEvent = new HistoryEvent();
                newEvent.setName(titleView.getText());
                newEvent.setDescription(descriptionView.getText());
                newEvent.setDate(mDate.getMillis());
                mCallback.onHistoryEventEdited(mEvent, newEvent);
            }
        } finally {
            dismiss();
        }
    }

    @OnClick(android.R.id.closeButton)
    public void onCancel(View view) {
        dismiss();
    }


    @OnClick(R.id.date)
    public void onPickDate(View view) {
        if (mOnShowDatePicker != null) {
            DateTime dateTime = new DateTime(mEvent.getDate());
            mOnShowDatePicker.onShowDatePickerCallback(dateTime, this);
        }
    }

    @Override
    protected void onInstanceArgumentRead(HistoryEvent instanceArgument) {
        mEvent = instanceArgument;
        if (mEvent == null) {
            mEvent = new HistoryEvent();
        }
        mDate = new DateTime(mEvent.getDate());
        setupView();
    }

    @Override
    public void onDateSet(CalendarDatePickerDialog calendarDatePickerDialog,
                          int year, int monthOfYear, int dayOfMonth) {
        int hour = mDate.getHourOfDay();
        int minute = mDate.getMinuteOfHour();
        mDate = new DateTime(year, monthOfYear, dayOfMonth, hour, minute);
        long epochDate = mDate.getMillis();
        mEvent.setDate(epochDate);
        setDateView();
    }

    public interface OnHistoryEventPassedBack {
        void onHistoryEventEdited(HistoryEvent old, HistoryEvent newEvent);
    }
}
