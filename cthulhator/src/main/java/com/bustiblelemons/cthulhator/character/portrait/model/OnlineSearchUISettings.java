package com.bustiblelemons.cthulhator.character.portrait.model;

import com.bustiblelemons.cthulhator.settings.character.CharacterSettings;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Created by bhm on 12.08.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnlineSearchUISettings {
    private static OnlineSearchUISettings sDefaults;

    static {
        sDefaults = new OnlineSearchUISettings();
        sDefaults.setGenderSpinnerPosition(0);
        sDefaults.setPeriodSpinnerPosition(1);
        sDefaults.setSeekbarPosition(2);
    }

    private int genderSpinnerPosition;
    private int periodSpinnerPosition;
    private int seekbarPosition;

    public static OnlineSearchUISettings defaults() {
        return sDefaults;
    }

    public static OnlineSearchUISettings from(CharacterSettings settings) {
        int genderPosition = settings.getGender().ordinal();
        int periodSpinnerPosition = settings.getCthulhuPeriod().ordinal();
        OnlineSearchUISettings r = new OnlineSearchUISettings();
        r.setGenderSpinnerPosition(genderPosition);
        r.setPeriodSpinnerPosition(periodSpinnerPosition);
        return r;
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
