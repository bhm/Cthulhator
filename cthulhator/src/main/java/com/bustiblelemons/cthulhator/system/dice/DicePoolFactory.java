package com.bustiblelemons.cthulhator.system.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by bhm on 19.07.14.
 */
public class DicePoolFactory {

    public static List<DicePoolElement> from(String notation) {
        List<DicePoolElement> r = new ArrayList<DicePoolElement>();
        Scanner s = new Scanner(notation);
        s.useDelimiter("+");
        while (s.hasNext()) {
            String pool = s.next();
            if (pool.contains("d")) {
                Scanner poolScanner = new Scanner(pool).useDelimiter("d");
                String diceMaxValue = "6";
                Polyhedral polyhedral = Polyhedral.from(diceMaxValue);
            } else {
                DiceBonus bonus = DiceBonus.from(pool);
                r.add(bonus);
            }
        }
        return r;
    }
}
