package com.bustiblelemons.cthulhator.model.dice;

/**
 * Created by bhm on 09.09.14.
 */
public enum PolyHedralDice {
    D2 {
        @Override
        public int getMax() {
            return 2;
        }
    }, D3 {
        @Override
        public int getMax() {
            return 3;
        }
    }, D6 {
        @Override
        public int getMax() {
            return 6;
        }
    }, D8 {
        @Override
        public int getMax() {
            return 8;
        }
    }, D10 {
        @Override
        public int getMax() {
            return 10;
        }
    }, D12 {
        @Override
        public int getMax() {
            return 12;
        }
    }, D20 {
        @Override
        public int getMax() {
            return 20;
        }
    }, D32 {
        @Override
        public int getMax() {
            return 32;
        }
    };

    public int getMin() {
        return 1;
    }

    public abstract int getMax();
}
