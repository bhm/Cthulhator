package com.bustiblelemons.cthulhator.system.edition;

import com.bustiblelemons.cthulhator.system.dice.PointPoolFromDiceBuilder;

/**
 * Created by hiv on 26.10.14.
 */
public class DamageBonusFactory {

    public static DamageBonus forEdition(CthulhuEdition edition, int constitution, int size) {
        DamageBonus r = new DamageBonus();
        int cumulative = constitution + size;
        switch (edition) {
        default:
            PointPoolFromDiceBuilder builder = getPointPoolFromDiceBuilder(cumulative);
            r.setPointPool(builder.build());
            return r;
        }

    }

    private static PointPoolFromDiceBuilder getPointPoolFromDiceBuilder(int cumulative) {
        PointPoolFromDiceBuilder r = new PointPoolFromDiceBuilder();
        if (cumulative < 12) {

        } else if (cumulative > 13 && cumulative < 16) {

        }
        return r;
    }
}
