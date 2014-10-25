package com.bustiblelemons.cthulhator.character.history.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by bhm on 03.08.14.
 */
@JsonIgnoreProperties({"Builder", "MODERN", "CURRENT_YEAR"})
public class YearsPeriodImpl implements Serializable, YearsPeriod {

    private int maxYear;
    private int minYear;
    private int yearJump;
    private int defaultYear;
    private String name;

    private YearsPeriodImpl(Builder b) {
        this.maxYear = b.max;
        this.minYear = b.min;
        this.yearJump = b.jump;
        this.defaultYear = b.defaultYear;
        this.name = b.name;
    }

    @Override
    public int getDefaultYear() {
        return defaultYear;
    }

    @Override
    public int getMaxYear() {
        return maxYear;
    }

    @Override
    public int getMinYear() {
        return minYear;
    }

    @Override
    public int getYearJump() {
        return yearJump;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDefaultYearJumpPosition() {
        int r = getDefaultYear() - getMinYear() / getYearJump();
        return r > 1 ? r - 1 : 0;
    }

    public static class Builder {

        protected int jump;
        protected int min;
        protected int max;
        protected int defaultYear;
        protected String name;

        public Builder setDefaultYear(int defaultYear) {
            this.defaultYear = defaultYear;
            return this;
        }

        public Builder setJump(int jump) {
            this.jump = jump;
            return this;
        }

        public Builder setMin(int min) {
            this.min = min;
            return this;
        }

        public Builder setMax(int max) {
            this.max = max;
            return this;
        }

        public YearsPeriodImpl build() {
            return new YearsPeriodImpl(this);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }
    }
}
