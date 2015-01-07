package com.bustiblelemons.cthulhator.character.portrait.model;

import com.bustiblelemons.cthulhator.settings.character.CharacterSettings;
import com.bustiblelemons.cthulhator.system.time.YearsPeriodImpl;
import com.bustiblelemons.google.apis.GoogleSearchGender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Created by bhm on 12.08.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnlineSearchUISettings {

    private int genderSpinnerPosition;
    private int periodSpinnerPosition;
    private int seekbarPosition;

    public static OnlineSearchUISettings defaults() {
        return LazyDefaults.sDefaults;
    }

    public static OnlineSearchUISettings from(CharacterSettings settings) {
        GoogleSearchGender gender = settings.getGender();
        YearsPeriodImpl period = settings.getCthulhuPeriod();
        OnlineSearchUISettings r = new OnlineSearchUISettings();
        r.setGenderSpinnerPosition(gender.ordinal());
        r.setPeriodSpinnerPosition(period.ordinal());
        r.setSeekbarPosition(period.getDefaultYearJumpPosition());
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

    private static final class LazyDefaults {
        private static OnlineSearchUISettings sDefaults;

        static {
            YearsPeriodImpl p = YearsPeriodImpl.JAZZAGE;
            sDefaults = new OnlineSearchUISettings();
            sDefaults.setGenderSpinnerPosition(GoogleSearchGender.ANY.ordinal());
            sDefaults.setPeriodSpinnerPosition(p.ordinal());
            sDefaults.setSeekbarPosition(p.getDefaultYearJumpPosition());
        }
    }
}
