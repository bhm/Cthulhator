package com.bustiblelemons.cthulhator.system.damage;

import com.bustiblelemons.cthulhator.system.dice.PointPoolFromDiceBuilder;
import com.bustiblelemons.cthulhator.system.dice.model.PointPool;
import com.bustiblelemons.cthulhator.system.dice.model.PolyhedralDice;
import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.PropertyFormat;
import com.bustiblelemons.cthulhator.system.properties.PropertyType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiv on 26.10.14.
 */
enum DamageBonusCoC5 implements DamageBonus {
    Scanty(2, 12) {
        private PointPool mPointPool;

        @Override
        int getDiceCount() {
            return -1;
        }

        @Override
        public PointPool getPointPool() {
            if (mPointPool == null) {
                mPointPool = new PointPool.Builder()
                        .setMax(-6).setMax(0)
                        .build();
            }
            return mPointPool;
        }

        @Override
        public PolyhedralDice getDice() {
            return PolyhedralDice.D6;
        }
    },
    Meagre(13, 16) {
        private PointPool mPointPool;

        @Override
        int getDiceCount() {
            return -1;
        }

        @Override
        public PointPool getPointPool() {
            if (mPointPool == null) {
                mPointPool = new PointPool.Builder()
                        .setMax(-4).setMax(0)
                        .build();
            }
            return mPointPool;
        }

        @Override
        public PolyhedralDice getDice() {
            return PolyhedralDice.D4;
        }
    },
    Average(17, 24) {
        @Override
        int getDiceCount() {
            return 0;
        }

        @Override
        public PolyhedralDice getDice() {
            return PolyhedralDice.D0;
        }

        @Override
        public PointPool getPointPool() {
            return PointPool.ZERO;
        }
    },
    Exceptional(25, 32) {
        @Override
        int getDiceCount() {
            return 1;
        }

        @Override
        public PolyhedralDice getDice() {
            return PolyhedralDice.D4;
        }
    },
    Abnormal(33, 40) {
        @Override
        int getDiceCount() {
            return 1;
        }
    },
    Preternatural0(41, 56) {
        @Override
        int getDiceCount() {
            return 2;
        }
    },
    Preternatural1(57, 72) {
        @Override
        int getDiceCount() {
            return 3;
        }
    },
    Preternatural2(73, 88) {
        @Override
        int getDiceCount() {
            return 4;
        }
    },
    Preternatural3(89, 104) {
        @Override
        int getDiceCount() {
            return 5;
        }
    },
    Preternatural4(105, 120) {
        @Override
        int getDiceCount() {
            return 6;
        }
    },
    Preternatural5(121, 136) {
        @Override
        int getDiceCount() {
            return 7;
        }
    },
    Preternatural6(137, 152) {
        @Override
        int getDiceCount() {
            return 8;
        }
    },
    Preternatural7(153, 168) {
        @Override
        int getDiceCount() {
            return 9;
        }
    },
    Preternatural8(169, 184) {
        @Override
        int getDiceCount() {
            return 10;
        }
    };
    private int       mMax;
    private int       mMin;
    private PointPool mPointPool;

    DamageBonusCoC5(int min, int max) {
        this.mMin = min;
        this.mMax = max;
    }

    public static DamageBonus fromProperties(int con, int siz) {
        return fromPropertySum(con + siz);
    }

    private static DamageBonus fromPropertySum(int i) {
        for (DamageBonusCoC5 bonusCoC5 : values()) {
            if (i <= bonusCoC5.getMax() && i >= bonusCoC5.getMin()) {
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
                DamageBonusCoC5 lastEnum = getLastEnum();
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
        return 16;
    }

    private DamageBonusCoC5 getLastEnum() {
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
        r.setFormat(PropertyFormat.NUMBER);
        r.setType(PropertyType.DAMAGE_BONUS);
        PointPool pointPool = getPointPool();
        r.setMinValue(pointPool.getMin());
        r.setMaxValue(pointPool.getMax());
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
