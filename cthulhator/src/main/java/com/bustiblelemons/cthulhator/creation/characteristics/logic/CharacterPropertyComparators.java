package com.bustiblelemons.cthulhator.creation.characteristics.logic;

import com.bustiblelemons.cthulhator.model.CharacterProperty;

import java.util.Comparator;

/**
 * Created by bhm on 27.09.14.
 */
public enum CharacterPropertyComparators implements Comparator<CharacterProperty> {
    ALPHABETICAL {
        @Override
        public int compare(CharacterProperty lhs, CharacterProperty rhs) {
            if (lhs != null && rhs == null) {
                return 1;
            } else if (lhs == null && rhs != null) {
                return -1;
            } else if (lhs == null && rhs == null) {
                return 0;
            } else {
                String lName = lhs.getName();
                String rName = rhs.getName();
                return lName.compareTo(rName);
            }
        }
    },
    VALUE {
        @Override
        public int compare(CharacterProperty lhs, CharacterProperty rhs) {
            if (lhs != null && rhs == null) {
                return 1;
            } else if (lhs == null && rhs != null) {
                return -1;
            } else if (lhs == null && rhs == null) {
                return 0;
            } else {
                int lVal = lhs.getValue();
                int rVal = rhs.getValue();
                if (lVal > rVal) {
                    return 1;
                } else if (lVal < rVal) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    };
}
