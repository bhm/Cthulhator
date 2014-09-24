package com.bustiblelemons.cthulhator.model;

import com.bustiblelemons.google.apis.GoogleSearchGender;

import java.io.Serializable;

/**
 * Created by bhm on 04.08.14.
 */
public interface CharacterSettings extends Serializable {
    GoogleSearchGender getGender();

    int getYear();

    boolean isModern();

    String getQuery();
}
