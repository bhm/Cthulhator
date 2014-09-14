package com.bustiblelemons.cthulhator.model;

/**
 * Created by bhm on 29.07.14.
 */
public class Relation {
    private String propertyName;
    private int    modifier;

    private ModifierType modifierType;

    public ModifierType getModifierType() {
        return modifierType;
    }

    public Relation setModifierType(ModifierType modifierType) {
        this.modifierType = modifierType;
        return this;
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

    public int getBaseValueByRelation(int value) {
        switch (modifierType) {
            case MULTIPLY:
                return value * modifier;
            case ADDITION:
                return value + modifier;
            case DIVISION:
                return value / modifier;
            case SUBSTRACT:
                return value - modifier;
            default:
                return value;
        }
    }
}
