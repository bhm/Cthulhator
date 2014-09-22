package com.bustiblelemons.cthulhator.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

/**
 * Created by bhm on 29.07.14.
 */
public class Relation implements Serializable {
    private String propertyName;
    private int    modifier;

    private ModifierType modifierType;
    private int          max;
    private int          min;
    private boolean      modifiesMaximum;
    private boolean      modifiesMinimum;

    public ModifierType getModifierType() {
        return modifierType;
    }

    public Relation setModifierType(ModifierType modifierType) {
        this.modifierType = modifierType;
        return this;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public boolean isModifiesMaximum() {
        return modifiesMaximum;
    }

    public void setModifiesMaximum(boolean modifiesMaximum) {
        this.modifiesMaximum = modifiesMaximum;
    }

    public boolean isModifiesMinimum() {
        return modifiesMinimum;
    }

    public void setModifiesMinimum(boolean modifiesMinimum) {
        this.modifiesMinimum = modifiesMinimum;
    }

    public int getModifier() {
        return modifier;
    }

    public Relation setModifier(int modifier) {
        this.modifier = modifier;
        return this;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Relation setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Relation relation = (Relation) o;

        if (propertyName != null ? !propertyName.equals(
                relation.propertyName) : relation.propertyName != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return propertyName != null ? propertyName.hashCode() : 0;
    }

    @JsonIgnore
    public int getBaseValueByRelation(int relatedPropertyValue) {
        switch (modifierType) {
            case MULTIPLY:
                return relatedPropertyValue * modifier;
            case ADDITION:
                return relatedPropertyValue + modifier;
            case DIVISION:
                return relatedPropertyValue / modifier;
            case SUBSTRACT:
                return relatedPropertyValue - modifier;
            default:
                return relatedPropertyValue;
        }
    }
}
