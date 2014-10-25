package com.bustiblelemons.cthulhator.system.time;

import com.bustiblelemons.cthulhator.character.history.model.YearsPeriod;

import org.apache.commons.lang.WordUtils;

import java.util.Locale;

/**
 * Created by bhm on 03.08.14.
 */
public enum CthulhuPeriod implements YearsPeriod {
    GASLIGHT {
        @Override
        public int getDefaultYear() {
            return 1890;
        }

        @Override
        public int getMaxYear() {
            return 1910;
        }

        @Override
        public int getMinYear() {
            return 1860;
        }

    }, JAZZAGE {
        @Override
        public int getDefaultYear() {
            return 1920;
        }

        @Override
        public int getMaxYear() {
            return 1940;
        }

        @Override
        public int getMinYear() {
            return 1910;
        }

    }, MODERN {
        @Override
        public int getDefaultYear() {
            return CURRENT_YEAR;
        }

        @Override
        public int getMaxYear() {
            return CURRENT_YEAR;
        }

        @Override
        public int getMinYear() {
            return CURRENT_YEAR;
        }

        @Override
        public int getYearJump() {
            return 0;
        }

    };

    public static CthulhuPeriod fromYear(int year) {
        for (CthulhuPeriod p : values()) {
            if (year <= p.getMaxYear() && year >= p.getMinYear()) {
                return p;
            }
        }
        return JAZZAGE;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "Period %s : [Min=%s; Jump=%s; Max=%s", name(),
                getMinYear(), getYearJump(), getMaxYear());
    }

    @Override
    public int getYearJump() {
        return 5;
    }

    @Override
    public int getDefaultYearJumpPosition() {
        int r = getDefaultYear() - getMinYear() / getYearJump();
        return r > 1 ? r - 1 : 0;
    }

    @Override
    public String getName() {
        return WordUtils.capitalizeFully(name());
    }
}
