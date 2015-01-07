package com.bustiblelemons.cthulhator.settings.character;

import android.support.v4.util.LruCache;

import com.bustiblelemons.cthulhator.character.history.model.YearsPeriod;
import com.bustiblelemons.cthulhator.system.time.YearsPeriodImpl;
import com.bustiblelemons.google.apis.GoogleSearchGender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.lang.WordUtils;

import java.util.Locale;

/**
 * Created by bhm on 04.08.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterSettingsImpl implements CharacterSettings {
    private static final LruCache<Integer, CharacterSettingsImpl> cache =
            new LruCache<Integer, CharacterSettingsImpl>(3);
    private static CharacterSettings sDefaults;
    private YearsPeriodImpl mYearsPeriodImpl = YearsPeriodImpl.JAZZAGE;
    private boolean            modern;
    private int                year;
    private GoogleSearchGender googleSearchGender;

    public CharacterSettingsImpl() {
        this.year = YearsPeriodImpl.JAZZAGE.getDefaultYear();
        this.googleSearchGender = GoogleSearchGender.ANY;
    }

    private CharacterSettingsImpl(int year, GoogleSearchGender googleSearchGender) {
        this.year = year;
        setCthulhuPeriod(YearsPeriodImpl.fromYear(this.year));
        this.googleSearchGender = googleSearchGender;
    }

    public static CharacterSettingsImpl create(int year, GoogleSearchGender googleSearchGender) {
        CharacterSettingsImpl search = new CharacterSettingsImpl(year, googleSearchGender);
        return search;
    }

    public static CharacterSettings defaults() {
        return sDefaults == null ? sDefaults = create(1920, GoogleSearchGender.ANY) : sDefaults;
    }

    @Override
    public GoogleSearchGender getGender() {
        return googleSearchGender;
    }

    public void setGender(GoogleSearchGender googleSearchGender) {
        this.googleSearchGender = googleSearchGender;
    }

    @Override
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean isModern() {
        return year == YearsPeriod.CURRENT_YEAR;
    }

    public void setModern(boolean modern) {
        this.modern = modern;
    }

    @Override
    public String getQuery() {
        return year + "+".concat(googleSearchGender.getSearchString());
    }

    @Override
    public YearsPeriodImpl getCthulhuPeriod() {
        return mYearsPeriodImpl;
    }

    public void setCthulhuPeriod(YearsPeriodImpl yearsPeriodImpl) {
        if (yearsPeriodImpl == null) {
            this.mYearsPeriodImpl = YearsPeriodImpl.JAZZAGE;
        }
        this.mYearsPeriodImpl = yearsPeriodImpl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CharacterSettingsImpl that = (CharacterSettingsImpl) o;

        if (year != that.year) {
            return false;
        }
        if (googleSearchGender != that.googleSearchGender) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        String period = WordUtils.capitalizeFully(mYearsPeriodImpl.getName());
        String gender = WordUtils.capitalizeFully(googleSearchGender.getName());
        return String.format(Locale.ENGLISH, "%s %s", period, gender);
    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + (googleSearchGender != null ? googleSearchGender.hashCode() : 0);
        return result;
    }
}
