package com.bustiblelemons.cthulhator.test;

import com.bustiblelemons.cthulhator.R;

/**
 * Created by bhm on 20.07.14.
 */
public enum BRPCard implements CharacterCard {
    MYTHOS {
        @Override
        public int getLayoutId() {
            return R.layout.card_brp_mythos;
        }

    }, MIND {
        @Override
        public int getLayoutId() {
            return R.layout.card_brp_mind;
        }
    }, COMBAT {
        @Override
        public int getLayoutId() {
            return R.layout.card_brp_combat;
        }
    }, SKILLS {
        @Override
        public int getLayoutId() {
            return R.layout.card_brp_skills;
        }
    }, MAGIC {
        @Override
        public int getLayoutId() {
            return R.layout.card_brp_magic;
        }
    }, SOCIAL {
        @Override
        public int getLayoutId() {
            return R.layout.card_brp_social;
        }
    };
}
