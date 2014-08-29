package com.bustiblelemons.cthulhator.model;

import android.support.v4.util.LruCache;

import com.bustiblelemons.cthulhator.model.brp.gimagesearch.Gender;
import com.bustiblelemons.cthulhator.model.time.CthulhuPeriod;
import com.bustiblelemons.cthulhator.model.time.YearsPeriod;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by bhm on 04.08.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterSettingsImpl implements CharacterSettings {
    private static CharacterSettings sDefaults;
    private        boolean           modern;
    private        int               year;
    private        Gender            gender;

    public void setYear(int year) {
        this.year = year;
    }

    public CharacterSettingsImpl() {
        this.year = CthulhuPeriod.JAZZAGE.getDefaultYear();
        this.gender = Gender.ANY;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public int getYear() {
        return year;
    }


    public void setModern(boolean modern) {
        this.modern = modern;
    }

    private CharacterSettingsImpl(int year, Gender gender) {
        this.year = year;
        this.gender = gender;
    }

    private static final LruCache<Integer, CharacterSettingsImpl> cache =
            new LruCache<Integer, CharacterSettingsImpl>(3);

    public static CharacterSettingsImpl create(int year, Gender gender) {
        CharacterSettingsImpl search = new CharacterSettingsImpl(year, gender);
        return search;
    }

    @Override
    public boolean isModern() {
        return year == YearsPeriod.CURRENT_YEAR;
    }

    @Override
    public String getQuery() {
        return year + "+".concat(gender.getSearchString());
    }

    public static CharacterSettings defaults() {
        return sDefaults == null ? sDefaults = create(1920, Gender.ANY) : sDefaults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        CharacterSettingsImpl that = (CharacterSettingsImpl) o;

        if (year != that.year) { return false; }
        if (gender != that.gender) { return false; }

        return true;
    }

    @Override
    public String toString() {
        return "CharacterSettingsImpl{" +
                "modern=" + modern +
                ", year=" + year +
                ", gender=" + gender +
                '}';
    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }
}
