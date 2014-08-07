package com.bustiblelemons.cthulhator.model.time;

import org.apache.commons.lang.WordUtils;

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
            return 1920;
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


    @Override
    public int getYearJump() {
        return 5;
    }

    @Override
    public String getName() {
        return WordUtils.capitalizeFully(name());
    }
}
