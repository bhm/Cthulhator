package com.bustiblelemons.cthulhator.character.history.logic;

import com.bustiblelemons.cthulhator.character.history.model.HistoryEvent;

import java.util.Comparator;

/**
 * Created by bhm on 04.10.14.
 */
public enum HistoryComparators implements Comparator<HistoryEvent> {
    DATE {
        @Override
        public int compare(HistoryEvent lhs, HistoryEvent rhs) {
            if (lhs == null && rhs == null) {
                return 0;
            } else if (lhs != null && rhs == null) {
                return 1;
            } else if (lhs == null && rhs != null) {
                return -1;
            } else {
                long lEpoch = lhs.getDate();
                long rEpoch = rhs.getDate();
                if (lEpoch > rEpoch) {
                    return 1;
                } else if (lEpoch == rEpoch) {
                    return 0;
                } else {
                    return -1;
                }
            }
        }
    },
    DATE_DES {
        @Override
        public int compare(HistoryEvent lhs, HistoryEvent rhs) {
            if (lhs != null && rhs == null) {
                return -1;
            } else if (lhs == null && rhs == null) {
                return 0;
            } else if (lhs == null && rhs != null) {
                return 1;
            } else {
                long lEpoch = lhs.getDate();
                long rEpoch = rhs.getDate();
                if (lEpoch > rEpoch) {
                    return -1;
                } else if (lEpoch == rEpoch) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }
    };
}
