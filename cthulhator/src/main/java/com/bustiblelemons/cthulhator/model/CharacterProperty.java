package com.bustiblelemons.cthulhator.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;
import java.util.Random;

/**
 * Created by bhm on 20.07.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterProperty {

    @JsonIgnore
    public static final CharacterProperty EMPTY = new CharacterProperty();

    private String         name;
    private int            value;
    private int            maxValue;
    private int            minValue;
    private int            baseValue;
    private PropertyFormat format;
    private PropertyType   type;

    private List<Relation> relations;
    @JsonIgnore
    private int            nameResId;
    @JsonIgnore
    private int            shortNameResId;

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    @JsonIgnore
    public int getNameResId() {
        return nameResId;
    }

    @JsonIgnore
    public void setNameResId(int nameResId) {
        this.nameResId = nameResId;
    }

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

    @JsonIgnore
    public int getShortNameResId() {
        return shortNameResId;
    }

    @JsonIgnore
    public void setShortNameResId(int shortNameResId) {
        this.shortNameResId = shortNameResId;
    }

    @JsonIgnore
    public int randomValue() {
        Random r = new Random();
        int n = getMaxValue() - getMinValue();
        int roll = r.nextInt(n);
        int ret = roll + getMinValue() + 1;
        return ret;
    }
}
