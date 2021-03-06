package com.bustiblelemons.cthulhator.character.characteristics.logic;

import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.Comparator;

/**
 * Created by bhm on 27.09.14.
 */
public enum CharacterPropertyComparators implements Comparator<CharacterProperty> {
    ALPHABETICAL {
        @Override
        public int compare(CharacterProperty lhs, CharacterProperty rhs) {
            if (lhs != null && rhs != null) {
                String lName = lhs.getName();
                String rName = rhs.getName();
                return lName.compareTo(rName);
            } else {
                return super.compare(lhs, rhs);
            }
        }
    }, VALUE_DESC {
        @Override
        public int compare(CharacterProperty lhs, CharacterProperty rhs) {
            return 0 - VALUE.compare(lhs, rhs);
        }

    }, VALUE {
        @Override
        public int compare(CharacterProperty lhs, CharacterProperty rhs) {
            if (lhs != null && rhs != null) {
                int lVal = lhs.getValue();
                int rVal = rhs.getValue();
                if (lVal > rVal) {
                    return 1;
                } else if (lVal < rVal) {
                    return -1;
                } else {
                    return ALPHABETICAL.compare(lhs, rhs);
                }
            } else {
                return super.compare(lhs, rhs);
            }
        }
    }, ACTION_GROUP {
        @Override
        public int compare(CharacterProperty lhs, CharacterProperty rhs) {
            if (lhs != null && rhs != null) {
                ActionGroup lGroup = lhs.getMainActionGroup();
                ActionGroup rGroup = rhs.getMainActionGroup();
                if (lGroup != null && rGroup == null) {
                    return 1;
                } else if (lGroup == null && rGroup != null) {
                    return -1;
                } else if (lGroup == null && rGroup == null) {
                    return 0;
                } else {
                    int byGroup = lGroup.compareTo(rGroup);
                    return byGroup == 0 ? ALPHABETICAL.compare(lhs, rhs) : byGroup;
                }
            } else {
                return super.compare(lhs, rhs);
            }
        }
    }, ACTION_BY_VALUE {
        @Override
        public int compare(CharacterProperty lhs, CharacterProperty rhs) {
            if (lhs != null && rhs != null) {
                ActionGroup lGroup = lhs.getMainActionGroup();
                ActionGroup rGroup = rhs.getMainActionGroup();
                if (lGroup != null && rGroup == null) {
                    return 1;
                } else if (lGroup == null && rGroup != null) {
                    return -1;
                } else if (lGroup == null && rGroup == null) {
                    return 0;
                } else {
                    int byGroup = ACTION_GROUP.compare(lhs, rhs);
                    return byGroup == 0 ? VALUE.compare(lhs, rhs) : byGroup;
                }
            } else {
                return super.compare(lhs, rhs);
            }
        }

    };

    @Override
    public int compare(CharacterProperty lhs, CharacterProperty rhs) {
        if (lhs != null && rhs == null) {
            return 1;
        } else if (lhs == null && rhs != null) {
            return -1;
        } else {
            return 0;
        }
    }

}
