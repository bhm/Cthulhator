package com.bustiblelemons.cthulhator.model;

import com.bustiblelemons.cthulhator.model.brp.statistics.BRPStatistic;

/**
 * Created by bhm on 10.09.14.
 */
public class CharacterProperties {


    public static final CharacterProperty CON =
            BRPStatCharacterProperty.fromStatistic(BRPStatistic.EDU);
    public static final CharacterProperty STR =
            BRPStatCharacterProperty.fromStatistic(BRPStatistic.STR);
    public static final CharacterProperty DEX =
            BRPStatCharacterProperty.fromStatistic(BRPStatistic.DEX);
    public static final CharacterProperty APP =
            BRPStatCharacterProperty.fromStatistic(BRPStatistic.APP);

    public static final CharacterProperty INT =
            BRPStatCharacterProperty.fromStatistic(BRPStatistic.INT);
    public static final CharacterProperty SIZ =
            BRPStatCharacterProperty.fromStatistic(BRPStatistic.SIZ);

    public static final CharacterProperty EDU =
            BRPStatCharacterProperty.fromStatistic(BRPStatistic.EDU);

}
