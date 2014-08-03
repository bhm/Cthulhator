package com.bustiblelemons.cthulhator.model.time;

import java.io.Serializable;

/**
 * Created by bhm on 03.08.14.
 */
public interface YearsPeriod extends Serializable {
    int getDefaultYear();

    int getMaxYear();

    int getMinYear();

    int getYearJump();

    String getName();
}
