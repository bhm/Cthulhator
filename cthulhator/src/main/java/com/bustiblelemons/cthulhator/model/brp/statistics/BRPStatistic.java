package com.bustiblelemons.cthulhator.model.brp.statistics;

import com.bustiblelemons.cthulhator.R;

/**
 * Created by bhm on 20.07.14.
 */
public enum BRPStatistic {
    STR {
        @Override
        public int getNameResId() {
            return R.string.strength;
        }

        @Override
        public int getShortResId() {
            return R.string.str;
        }
    }, DEX {
        @Override
        public int getNameResId() {
            return R.string.dexterity;
        }

        @Override
        public int getShortResId() {
            return R.string.dex;
        }
    }, INT {
        @Override
        public int getNameResId() {
            return R.string._int;
        }

        @Override
        public int getShortResId() {
            return R.string.intelligence;
        }
    }, CON {
        @Override
        public int getNameResId() {
            return R.string.constitution;
        }

        @Override
        public int getShortResId() {
            return R.string.con;
        }
    }, APP {
        @Override
        public int getNameResId() {
            return R.string.appearance;
        }

        @Override
        public int getShortResId() {
            return R.string.app;
        }
    }, POW {
        @Override
        public int getNameResId() {
            return R.string.power;
        }

        @Override
        public int getShortResId() {
            return R.string.pow;
        }
    }, SIZ {
        @Override
        public int getNameResId() {
            return R.string.size;
        }

        @Override
        public int getShortResId() {
            return R.string.siz;
        }
    }, SAN {
        @Override
        public int getNameResId() {
            return R.string.sanity;
        }

        @Override
        public int getShortResId() {
            return R.string.san;
        }
    }, EDU {
        @Override
        public int getNameResId() {
            return R.string.education;
        }

        @Override
        public int getShortResId() {
            return R.string.edu;
        }
    }, IDEA {
        @Override
        public int getNameResId() {
            return R.string.idea;
        }

        @Override
        public int getShortResId() {
            return R.string.idea;
        }
    }, KNOW {
        @Override
        public int getNameResId() {
            return R.string.knowledge;
        }

        @Override
        public int getShortResId() {
            return R.string.know;
        }
    };

    public abstract int getNameResId();

    public abstract int getShortResId();
}
