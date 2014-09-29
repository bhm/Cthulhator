package com.bustiblelemons.cthulhator.creation.history.model;

/**
 * Created by bhm on 29.09.14.
 */
public class HistoryEventHeader {

    public static final String DATE_FORMAT      = "MM dd";
    public static final String DATE_FORMAT_LONG = "MM dd, yyyy";

    private String title;
    private long   date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
