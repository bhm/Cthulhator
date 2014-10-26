package com.bustiblelemons.cthulhator.system.damage;

import com.bustiblelemons.cthulhator.system.edition.CthulhuEdition;

/**
 * Created by hiv on 26.10.14.
 */
public class DamageBonusFactory {
    public static DamageBonus forEdition(CthulhuEdition edition, int con, int siz) {
        switch (edition) {
        default:
        case CoC5:
            return DamageBonusCoC5.fromProperties(con, siz);
        case CoC7:
            return DamageBonusCoC7.fromProperties(con, siz);
        }
    }
}
