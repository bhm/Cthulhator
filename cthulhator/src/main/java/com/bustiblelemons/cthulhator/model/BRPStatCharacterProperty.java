package com.bustiblelemons.cthulhator.model;

import com.bustiblelemons.cthulhator.model.brp.statistics.BRPStatistic;
import com.bustiblelemons.cthulhator.model.dice.PointPool;
import com.bustiblelemons.cthulhator.model.dice.PointPoolFromDiceFactory;
import com.bustiblelemons.cthulhator.model.dice.PolyHedralDice;

/**
 * Created by bhm on 10.09.14.
 */
public class BRPStatCharacterProperty extends CharacterProperty {

    private BRPStatCharacterProperty(Builder b) {
        setMaxValue(b.getMax());
        setMinValue(b.getMin());
        setFormat(b.getFormat());
        setType(b.getType());
    }

    public static CharacterProperty fromStatistic(BRPStatistic statistic) {
        PointPoolFromDiceFactory fromDice = new PointPoolFromDiceFactory();
        CharacterProperty r = fillValues(statistic, fromDice);
        //TODO Retreive title id once for a given name propertyTyp_propertyName
        r.setNameResId(0);
        r.setShortNameResId(0);
        return r;
    }

    private static CharacterProperty fillValues(BRPStatistic statistic, PointPoolFromDiceFactory fromDice) {
        switch (statistic) {
            case CON:
            case DEX:
            case STR:
            case APP:
            case POW:
                fromDice.addDicePool(3, PolyHedralDice.D6);
                return fromPointPool(fromDice.build());
            case INT:
            case SIZ:
                fromDice.addDicePool(2, PolyHedralDice.D6).addModifiers(6);
                return fromPointPool(fromDice.build());
            case EDU:
                fromDice.addDicePool(3, PolyHedralDice.D6).addModifiers(3);
                return fromPointPool(fromDice.build());
            default:
                return fromPointPool(fromDice.build());
        }
    }

    private static CharacterProperty fromPointPool(PointPool p) {
        Builder b = new Builder();
        b.setBaseValue(p.getMin());
        b.setMin(p.getMin());
        b.setMax(p.getMax());
        return b.build();
    }


    public static class Builder {
        private int            max       = 100;
        private int            min       = 0;
        private int            baseValue = min;
        private PropertyType   type   = PropertyType.STATISTIC;
        private PropertyFormat format = PropertyFormat.NUMBER;

        public int getBaseValue() {
            return baseValue;
        }

        public Builder setBaseValue(int baseValue) {
            this.baseValue = baseValue;
            return this;
        }

        public int getMax() {
            return max;
        }

        public Builder setMax(int max) {
            this.max = max;
            return this;
        }

        public int getMin() {
            return min;
        }

        public Builder setMin(int min) {
            this.min = min;
            setBaseValue(min);
            return this;
        }

        public PropertyType getType() {
            return type;
        }

        public Builder setType(PropertyType type) {
            this.type = type;
            return this;
        }

        public PropertyFormat getFormat() {
            return format;
        }

        public Builder setFormat(PropertyFormat format) {
            this.format = format;
            return this;
        }

        public CharacterProperty build() {
            return new BRPStatCharacterProperty(this);
        }
    }
}
