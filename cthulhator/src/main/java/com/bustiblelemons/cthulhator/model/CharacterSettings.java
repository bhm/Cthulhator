package com.bustiblelemons.cthulhator.model;

import com.bustiblelemons.cthulhator.model.brp.gimagesearch.Gender;

import java.io.Serializable;

/**
 * Created by bhm on 04.08.14.
 */
public interface CharacterSettings extends Serializable {
    Gender getGender();

    int getYear();

    boolean isModern();

    String getQuery();
}