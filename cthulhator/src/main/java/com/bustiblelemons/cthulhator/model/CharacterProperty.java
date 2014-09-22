package com.bustiblelemons.cthulhator.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by bhm on 20.07.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterProperty implements Serializable {

    @JsonIgnore
    public static final CharacterProperty EMPTY = new CharacterProperty();

    private String            name;
    private int               value;
    private int               maxValue;
    private int               minValue;
    private int               baseValue;
    private PropertyFormat    format;
    private PropertyType      type;
    private List<ActionGroup> actionGroup;
    private Set<Relation>     relations;
    @JsonIgnore
    private int nameResId = -1;
    @JsonIgnore
    private int shortNameResId;

    public List<ActionGroup> getActionGroup() {
        return actionGroup;
    }

    public void setActionGroup(List<ActionGroup> actionGroup) {
        this.actionGroup = actionGroup;
    }

    public Set<Relation> getRelations() {
        return relations;
    }

    public void setRelations(Collection<Relation> relations) {
        this.relations = new HashSet<Relation>(relations);
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
        if (value <= getMaxValue()) {
            this.value = value;
        } else {
            this.value = getMaxValue();
        }
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
        setValue(ret);
        return ret;
    }

    @Override
    public String toString() {
        return "CharacterProperty{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", maxValue=" + maxValue +
                ", minValue=" + minValue +
                ", baseValue=" + baseValue +
                ", format=" + format +
                ", type=" + type +
                ", actionGroup=" + actionGroup +
                ", relations=" + relations +
                ", nameResId=" + nameResId +
                ", shortNameResId=" + shortNameResId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        CharacterProperty property = (CharacterProperty) o;

        if (name != null ? !name.equals(property.name) : property.name != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @JsonIgnore
    public boolean hasRelations() {
        return getRelations() != null && getRelations().size() > 0;
    }

    @JsonIgnore
    public boolean hasResNameId() {
        return nameResId > 0;
    }

    @JsonIgnore
    public boolean increaseValue() {
        if (getValue() + 1 <= getMaxValue()) {
            setValue(getValue() + 1);
            return false;
        }
        return false;
    }

    @JsonIgnore
    public boolean decreaseValue() {
        if (getValue() - 1 <= getMaxValue()) {
            setValue(getValue() - 1);
            return false;
        }
        return false;
    }

    @JsonIgnore
    public boolean isPercentile() {
        return PropertyFormat.PERCENTILE.equals(getFormat());
    }
}
