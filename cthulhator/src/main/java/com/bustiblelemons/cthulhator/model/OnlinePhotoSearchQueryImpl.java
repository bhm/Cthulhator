package com.bustiblelemons.cthulhator.model;

import android.support.v4.util.LruCache;

import com.bustiblelemons.cthulhator.model.brp.gimagesearch.Gender;
import com.bustiblelemons.cthulhator.model.time.YearsPeriod;

/**
 * Created by bhm on 04.08.14.
 */
public class OnlinePhotoSearchQueryImpl implements OnlinePhotoSearchQuery {
    private static OnlinePhotoSearchQuery sDefaults;
    private        boolean                modern;
    private        int                    year;
    private        Gender                 gender;

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

    private OnlinePhotoSearchQueryImpl(int year, Gender gender) {
        this.year = year;
        this.gender = gender;
    }

    private static final LruCache<Integer, OnlinePhotoSearchQueryImpl> cache =
            new LruCache<Integer, OnlinePhotoSearchQueryImpl>(3);

    public static OnlinePhotoSearchQueryImpl create(int year, Gender gender) {
        int code = year + gender.hashCode();
        if (cache.get(code) == null) {
            OnlinePhotoSearchQueryImpl search = new OnlinePhotoSearchQueryImpl(year, gender);
            cache.put(code, search);
        }
        return cache.get(code);
    }

    @Override
    public boolean isModern() {
        return year != YearsPeriod.CURRENT_YEAR;
    }

    @Override
    public String getQuery() {
        return year + "".concat(gender.name());
    }

    public static OnlinePhotoSearchQuery defaults() {
        return sDefaults == null ? sDefaults = create(1920, Gender.ANY) : sDefaults;
    }
}
