package com.bustiblelemons.cthulhator.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by bhm on 13.08.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Grouping {
    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_LIMIT  = 20;
    private             int limit          = DEFAULT_LIMIT;
    private             int offset         = DEFAULT_OFFSET;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Grouping next() {
        this.incrementLimit();
        return this;
    }

    private void incrementLimit() {
        setOffset(getOffset() + getLimit());
    }
}
