package com.bustiblelemons.cthulhator.model.brp.gimagesearch;

import com.bustiblelemons.cthulhator.R;

/**
 * Created by bhm on 27.07.14.
 */
public enum Gender {
    ANY {
        @Override
        public int getTitleId() {
            return R.string.any;
        }
    },
    FEMALE {
        @Override
        public int getTitleId() {
            return R.string.female;
        }
    }, MALE {
        @Override
        public int getTitleId() {
            return R.string.male;
        }
    }, BOY {
        @Override
        public int getTitleId() {
            return R.string.boy;
        }
    }, GIRL {
        @Override
        public int getTitleId() {
            return R.string.girl;
        }
    }, MEN {
        @Override
        public int getTitleId() {
            return R.string.men;
        }
    }, WOMAN {
        @Override
        public int getTitleId() {
            return R.string.woman;
        }
    };

    public abstract int getTitleId();

    public boolean isMale() {
        switch(this) {
            case MALE:
            case BOY:
            case MEN:
                return true;
            default:
                return false;
        }
    }
}
