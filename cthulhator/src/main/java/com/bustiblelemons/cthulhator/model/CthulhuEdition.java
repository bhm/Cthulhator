package com.bustiblelemons.cthulhator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 13.08.14.
 */
public enum CthulhuEdition {
    ToC, CoC5, CoC6, CoC7, CthulhuLite {
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

    public List<CharacterProperty> getCharacteristics() {
        List<CharacterProperty> r = new ArrayList<CharacterProperty>();
        return r;
    }

}
