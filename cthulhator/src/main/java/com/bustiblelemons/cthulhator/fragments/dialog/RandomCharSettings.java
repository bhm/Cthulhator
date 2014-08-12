package com.bustiblelemons.cthulhator.fragments.dialog;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by bhm on 12.08.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomCharSettings {
    private static RandomCharSettings sDefaults;
    static {
        sDefaults = new RandomCharSettings();
        sDefaults.setGenderSpinnerPosition(0);
        sDefaults.setPeriodSpinnerPosition(1);
        sDefaults.setSeekbarPosition(2);
    }
    private        int                genderSpinnerPosition;
    private        int                periodSpinnerPosition;
    private        int                seekbarPosition;

    public static RandomCharSettings defaults() {
        return sDefaults;
    }

    public int getGenderSpinnerPosition() {
        return genderSpinnerPosition;
    }

    public void setGenderSpinnerPosition(int genderSpinnerPosition) {
        this.genderSpinnerPosition = genderSpinnerPosition;
    }

    public int getPeriodSpinnerPosition() {
        return periodSpinnerPosition;
    }

    public void setPeriodSpinnerPosition(int periodSpinnerPosition) {
        this.periodSpinnerPosition = periodSpinnerPosition;
    }

    public int getSeekbarPosition() {
        return seekbarPosition;
    }

    public void setSeekbarPosition(int seekbarPosition) {
        this.seekbarPosition = seekbarPosition;
    }
}
