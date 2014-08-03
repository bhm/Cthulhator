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

        @Override
        public int getYearJump() {
            return 10;
        }

    }, JAZZAGE {
        @Override
        public int getDefaultYear() {
            return 0;
        }

        @Override
        public int getMaxYear() {
            return 0;
        }

        @Override
        public int getMinYear() {
            return 0;
        }

        @Override
        public int getYearJump() {
            return 0;
        }
    }, MODERN {
        @Override
        public int getDefaultYear() {
            return 0;
        }

        @Override
        public int getMaxYear() {
            return 0;
        }

        @Override
        public int getMinYear() {
            return 0;
        }

        @Override
        public int getYearJump() {
            return 0;
        }

    };

    @Override
    public String getName() {
        return WordUtils.capitalizeFully(name());
    }
}
