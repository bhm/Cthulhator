package com.bustiblelemons.cthulhator.model;

/**
 * Created by bhm on 29.07.14.
 */
public class Relation {
    private String propertyName;
    private int modifier;

    private ModifierType modifierType;

    public ModifierType getModifierType() {
        return modifierType;
    }

    public void setModifierType(ModifierType modifierType) {
        this.modifierType = modifierType;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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
}
