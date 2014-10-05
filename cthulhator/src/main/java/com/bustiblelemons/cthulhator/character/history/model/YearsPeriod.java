package com.bustiblelemons.cthulhator.character.history.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by bhm on 03.08.14.
 */
public interface YearsPeriod extends Serializable {

    int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    int getDefaultYear();

    int getMaxYear();

    int getMinYear();

    int getYearJump();

    String getName();
}
