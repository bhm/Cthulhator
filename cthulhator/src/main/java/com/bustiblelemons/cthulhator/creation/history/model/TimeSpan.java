package com.bustiblelemons.cthulhator.creation.history.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by bhm on 29.09.14.
 */
@JsonIgnoreProperties
public class TimeSpan {

    @JsonIgnore
    public static final TimeSpan EMPTY = new TimeSpan(Long.MIN_VALUE, Long.MAX_VALUE);

    private long beginEpoch;
    private long endEpoch;


    public TimeSpan(long beginEpoch, long endEpoch) {
        this.beginEpoch = beginEpoch;
        this.endEpoch = endEpoch;
    }

    public TimeSpan() {
    }

    public long getBeginEpoch() {
        return beginEpoch;
    }

    public void setBeginEpoch(long beginEpoch) {
        this.beginEpoch = beginEpoch;
    }

    public long getEndEpoch() {
        return endEpoch;
    }

    public void setEndEpoch(long endEpoch) {
        this.endEpoch = endEpoch;
    }
}
