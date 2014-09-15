package com.bustiblelemons.cthulhator.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bhm on 13.08.14.
 */
public enum CthulhuEdition {
    ToC {
        @Override
        public Set<CharacterProperty> getCharacteristics() {
            return Collections.emptySet();
        }
    }, CoC5, CoC6, CoC7, CthulhuLite {
        @Override
        public int getHobbySkillPointMultiplier() {
            return 5;
        }

        @Override
        public int getCarrierSkillPointMultiplier() {
            return 10;
        }
    };

    public int getHobbySkillPointMultiplier() {
        return 10;
    }

    public int getCarrierSkillPointMultiplier() {
        return 20;
    }

    public Set<CharacterProperty> getCharacteristics() {
        Set<CharacterProperty> r = new HashSet<CharacterProperty>();
        r.add(CharacterProperties.CON);
        r.add(CharacterProperties.STR);
        r.add(CharacterProperties.DEX);
        r.add(CharacterProperties.APP);

        r.add(CharacterProperties.INT);
        r.add(CharacterProperties.SIZ);

        r.add(CharacterProperties.EDU);
        return r;
    }
}
