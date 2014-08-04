package com.bustiblelemons.cthulhator.model;

import com.bustiblelemons.cthulhator.model.brp.gimagesearch.Gender;

/**
 * Created by bhm on 04.08.14.
 */
public interface OnlinePhotoSearch {
    Gender getGender();

    int getYear();

    boolean isModern();

    String getQuery();
}
