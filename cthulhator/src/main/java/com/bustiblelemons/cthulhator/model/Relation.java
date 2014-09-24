package com.bustiblelemons.cthulhator.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by bhm on 29.07.14.
 */
public class Relation implements Parcelable {
    public static final Parcelable.Creator<Relation> CREATOR = new Parcelable.Creator<Relation>() {
        public Relation createFromParcel(Parcel source) {
            return new Relation(source);
        }

        public Relation[] newArray(int size) {
            return new Relation[size];
        }
    };
    private String       propertyName;
    private int          modifier;
    private ModifierType modifierType;
    private int          max;
    private int          min;
    private boolean      modifiesMaximum;
    private boolean      modifiesMinimum;

    public Relation() {
    }

    private Relation(Parcel in) {
        this.propertyName = in.readString();
        this.modifier = in.readInt();
        int tmpModifierType = in.readInt();
        this.modifierType = tmpModifierType == -1 ? null : ModifierType.values()[tmpModifierType];
        this.max = in.readInt();
        this.min = in.readInt();
        this.modifiesMaximum = in.readByte() != 0;
        this.modifiesMinimum = in.readByte() != 0;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.propertyName);
        dest.writeInt(this.modifier);
        dest.writeInt(this.modifierType == null ? -1 : this.modifierType.ordinal());
        dest.writeInt(this.max);
        dest.writeInt(this.min);
        dest.writeByte(modifiesMaximum ? (byte) 1 : (byte) 0);
        dest.writeByte(modifiesMinimum ? (byte) 1 : (byte) 0);
    }
}
