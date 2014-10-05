package com.bustiblelemons.cthulhator.settings.character;

import com.bustiblelemons.cthulhator.system.time.CthulhuPeriod;
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

    CthulhuPeriod getCthulhuPeriod();
}
