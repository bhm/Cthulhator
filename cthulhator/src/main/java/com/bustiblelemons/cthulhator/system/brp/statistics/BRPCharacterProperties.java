package com.bustiblelemons.cthulhator.system.brp.statistics;

import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

/**
 * Created by bhm on 10.09.14.
 */
public class BRPCharacterProperties {

    public static final CharacterProperty STR =
            BRPStatCharacterProperty.fromStatistic(BRPStatistic.STR);
    public static final CharacterProperty CON =
            BRPStatCharacterProperty.fromStatistic(BRPStatistic.CON);
    public static final CharacterProperty POW =
            BRPStatCharacterProperty.fromStatistic(BRPStatistic.POW);
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
