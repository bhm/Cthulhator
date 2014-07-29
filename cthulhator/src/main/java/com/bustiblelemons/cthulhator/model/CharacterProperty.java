package com.bustiblelemons.cthulhator.model;

/**
 * Created by bhm on 20.07.14.
 */
public class CharacterProperty {
    public static final CharacterProperty EMPTY = new CharacterProperty();
    private String         name;
    private int            value;
    private int            maxValue;
    private int            minValue;
    private int            baseValue;
    private PropertyFormat format;
    private PropertyType   type;

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(int baseValue) {
        this.baseValue = baseValue;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public PropertyFormat getFormat() {
        return format;
    }

    public void setFormat(PropertyFormat format) {
        this.format = format;
    }
}
