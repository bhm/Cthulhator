package com.bustiblelemons.cthulhator.character.viewer;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.system.brp.skills.BRPSkill;
import com.bustiblelemons.cthulhator.system.brp.statistics.BRPStatistic;
import com.bustiblelemons.cthulhator.system.brp.statistics.HitPoints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by hiv on 17.11.14.
 */
public enum CharacterViewerCard {

    SEE_THROUGH {
        @Override
        public int getLayoutId() {
            return R.layout.card_see_through;
        }

        @Override
        public Collection<String> getPropertyNames() {
            return Collections.emptyList();
        }
    },

    TITLE {
        @Override
        public int getLayoutId() {
            return R.layout.card_title;
        }

        @Override
        public Collection<String> getPropertyNames() {
            return Collections.emptyList();
        }
    },

    COMBAT {
        private List<String> mNames;

        @Override
        public int getLayoutId() {
            return R.layout.card_brp_combat;
        }

        @Override
        public Collection<String> getPropertyNames() {
            if (mNames == null) {
                mNames = new ArrayList<String>(4);
                mNames.add(BRPStatistic.CON.name());
                mNames.add(BRPStatistic.SIZ.name());
                mNames.add(BRPStatistic.STR.name());
                mNames.add(BRPStatistic.DEX.name());
                mNames.add(HitPoints.class.getSimpleName());
            }
            return mNames;
        }
    }, MAGIC {
        private List<String> mNames;

        @Override
        public int getLayoutId() {
            return R.layout.card_brp_magic;
        }

        @Override
        public Collection<String> getPropertyNames() {
            if (mNames == null) {
                mNames = new ArrayList<String>(2);
                mNames.add(BRPStatistic.POW.name());
                mNames.add(BRPStatistic.LUCK.name());
            }
            return mNames;
        }
    }, MIND {
        private List<String> mNames;

        @Override
        public int getLayoutId() {
            return R.layout.card_brp_mind;
        }

        @Override
        public Collection<String> getPropertyNames() {
            if (mNames == null) {
                mNames = new ArrayList<String>(1);
                mNames.add(BRPStatistic.INT.name());
                mNames.add(BRPStatistic.EDU.name());
                mNames.add(BRPStatistic.APP.name());
                mNames.add(BRPStatistic.APP.name());
            }
            return mNames;
        }
    }, MYTHOS {
        private List<String> mNames;

        @Override
        public int getLayoutId() {
            return R.layout.card_brp_mythos;
        }

        @Override
        public Collection<String> getPropertyNames() {
            if (mNames == null) {
                mNames = new ArrayList<String>(1);
                mNames.add(BRPSkill.CthulhuMythos.name());
                mNames.add(BRPStatistic.SAN.name());
            }
            return mNames;
        }
    }, SOCIAL {
        private List<String> mNames;

        @Override
        public int getLayoutId() {
            return R.layout.card_brp_social;
        }

        @Override
        public Collection<String> getPropertyNames() {
            if (mNames == null) {
                mNames = new ArrayList<String>(1);
                mNames.add(BRPStatistic.APP.name());
            }
            return mNames;
        }
    }, SKILLS {
        @Override
        public int getLayoutId() {
            return R.layout.card_brp_skills;
        }

        @Override
        public Collection<String> getPropertyNames() {
            return null;
        }
    },;

    public abstract int getLayoutId();

    public abstract Collection<String> getPropertyNames();
}
