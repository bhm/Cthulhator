package com.bustiblelemons.cthulhator.creation.history.logic;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;

import org.joda.time.DateTime;

/**
 * Created by bhm on 30.09.14.
 */
public interface OnShowDatePicker {
    void onShowDatePickerCallback(DateTime forDateTime,
                                  CalendarDatePickerDialog.OnDateSetListener callback);
}
