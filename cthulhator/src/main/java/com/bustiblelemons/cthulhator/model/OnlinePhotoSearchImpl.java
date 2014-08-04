package com.bustiblelemons.cthulhator.model;

import android.support.v4.util.LruCache;

import com.bustiblelemons.cthulhator.model.brp.gimagesearch.Gender;

import java.util.Calendar;

/**
 * Created by bhm on 04.08.14.
 */
public class OnlinePhotoSearchImpl implements OnlinePhotoSearch {
    private boolean modern;
    private int     year;
    private Gender  gender;

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public int getYear() {
        return year;
    }

    private static final int sCurrentYear;

    static {
        sCurrentYear = Calendar.getInstance().get(Calendar.YEAR);
    }

    public void setModern(boolean modern) {
        this.modern = modern;
    }

    private OnlinePhotoSearchImpl(int year, Gender gender) {
        this.year = year;
        this.gender = gender;
    }

    private static final LruCache<Integer, OnlinePhotoSearchImpl> cache =
            new LruCache<Integer, OnlinePhotoSearchImpl>(3);

    public static OnlinePhotoSearchImpl create(int year, Gender gender) {
        int code = year + gender.hashCode();
        if (cache.get(code) == null) {
            OnlinePhotoSearchImpl search = new OnlinePhotoSearchImpl(year, gender);
            cache.put(code, search);
        }
        return cache.get(code);
    }

    @Override
    public boolean isModern() {
        return year != sCurrentYear;
    }

    @Override
    public String getQuery() {
        return year + "".concat(gender.name());
    }
}
