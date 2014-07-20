package com.bustiblelemons.cthulhator.model;

/**
 * Created by bhm on 20.07.14.
 */
public class CharacterProperty {
    private String name;
    private int value;
    private PropertyFormat format;

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
