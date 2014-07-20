package com.bustiblelemons.cthulhator.model.brp;

/**
 * Created by bhm on 19.07.14.
 */
public enum CoCEdition implements EditionNumber {
    FIVE {
        @Override
        public float getEditionNumber() {
            return 5f;
        }
    }, FIVE_FIVE {
        @Override
        public float getEditionNumber() {
            return 5.5f;
        }
    }, SIX {
        @Override
        public float getEditionNumber() {
            return 6.0f;
        }
    };

}
