package com.bustiblelemons.cthulhator.system.damage;

import com.bustiblelemons.cthulhator.system.dice.PointPoolFromDiceBuilder;
import com.bustiblelemons.cthulhator.system.dice.model.PointPool;
import com.bustiblelemons.cthulhator.system.dice.model.PolyhedralDice;
import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.PropertyType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiv on 26.10.14.
 */
public enum DamageBonusCoC7 implements DamageBonus {
    Scanty(2, 64) {
        private PointPool mPointPool;

        @Override
        int getDiceCount() {
            return 1;
        }

        @Override
        public PointPool getPointPool() {
            if (mPointPool == null) {
                mPointPool = new PointPool.Builder()
                        .setMax(-2).setMax(0)
                        .build();
            }
            return mPointPool;
        }

        @Override
        public PolyhedralDice getDice() {
            return PolyhedralDice.D2;
        }
    },
    Meagre(65, 64) {
        private PointPool mPointPool;

        @Override
        int getDiceCount() {
            return 1;
        }

        @Override
        public PointPool getPointPool() {
            if (mPointPool == null) {
                mPointPool = new PointPool.Builder()
                        .setMax(-1).setMax(0)
                        .build();
            }
            return mPointPool;
        }

        @Override
        public PolyhedralDice getDice() {
            return PolyhedralDice.D1;
        }
    },
    Average(85, 124) {
        @Override
        int getDiceCount() {
            return 0;
        }

        @Override
        public PointPool getPointPool() {
            return PointPool.ZERO;
        }

        @Override
        public PolyhedralDice getDice() {
            return PolyhedralDice.D0;
        }
    },
    Exceptional(125, 164) {
        @Override
        int getDiceCount() {
            return 1;
        }

        @Override
        public PolyhedralDice getDice() {
            return PolyhedralDice.D4;
        }
    },
    Abnormal(165, 204) {
        @Override
        int getDiceCount() {
            return 1;
        }
    };
    private int       mMax;
    private int       mMin;
    private PointPool mPointPool;

    DamageBonusCoC7(int mMax, int mMin) {
        this.mMax = mMax;
        this.mMin = mMin;
    }

    public static DamageBonus fromProperties(int con, int siz) {
        return fromPropertySum(con + siz);
    }

    private static DamageBonus fromPropertySum(int i) {
        for (DamageBonusCoC7 bonusCoC5 : values()) {
            if (i < bonusCoC5.getMax() && i > bonusCoC5.getMin()) {
                return bonusCoC5;
            }
        }
        return Scanty;
    }

    public PolyhedralDice getDice() {
        return PolyhedralDice.D6;
    }

    abstract int getDiceCount();

    public int getMax() {
        return mMax;
    }

    public int getMin() {
        return mMin;
    }

    @Override
    public PointPool getPointPool() {
        if (mPointPool == null) {
            mPointPool = new PointPool();
            PointPoolFromDiceBuilder b = new PointPoolFromDiceBuilder();
            int count = getDiceCount();
            PolyhedralDice dice = getDice();
            if (getMin() == getMax()) {
                DamageBonusCoC7 lastEnum = getLastEnum();
                int mod = (getMax() - lastEnum.getMax()) / getFraction();
                if (mod <= 0) {
                    mod = 1;
                }
                count = lastEnum.getDiceCount() + mod;
            }
            b.addDicePool(count, dice);
            mPointPool = b.build();
        }
        return mPointPool;
    }

    private int getFraction() {
        return 40;
    }

    private DamageBonusCoC7 getLastEnum() {
        int lastPos = values().length - 1;
        return values()[lastPos];
    }

    @Override
    public int random() {
        return getPointPool().randomValue();
    }

    @Override
    public CharacterProperty asCharacterProperty() {
        CharacterProperty r = new CharacterProperty();
        r.setDisplayName(toString());
        r.setType(PropertyType.DAMAGE_BONUS);
        PointPool pointPool = getPointPool();
        r.setMaxValue(pointPool.getMax());
        r.setMinValue(pointPool.getMin());
        r.setName(DamageBonus.class.getSimpleName());
        List<ActionGroup> g = new ArrayList<ActionGroup>();
        g.add(ActionGroup.COMBAT);
        r.setActionGroup(g);
        return r;
    }

    @Override
    public String toString() {
        if (getDiceCount() == 0) {
            return "0";
        }
        return getDiceCount() + getDice().name();
    }
}

