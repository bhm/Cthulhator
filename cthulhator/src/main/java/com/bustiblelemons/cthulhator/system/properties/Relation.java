package com.bustiblelemons.cthulhator.system.properties;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 29.07.14.
 */
@JsonIgnoreProperties
public class Relation implements Parcelable, Serializable {
    @JsonIgnore
    public static final Parcelable.Creator<Relation> CREATOR = new Parcelable.Creator<Relation>() {
        public Relation createFromParcel(Parcel source) {
            return new Relation(source);
        }

        public Relation[] newArray(int size) {
            return new Relation[size];
        }
    };
    private List<String> propertyNames;
    private int          modifier;
    private ModifierType modifierType;
    private int          max;
    private int          min;
    private boolean      modifiesMaximum;
    private boolean      modifiesMinimum;

    public Relation() {
    }

    private Relation(Parcel in) {
        this.modifier = in.readInt();
        int tmpModifierType = in.readInt();
        this.propertyNames = new ArrayList<String>();
        in.readStringList(propertyNames);
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

    public List<String> getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(List<String> propertyNames) {
        this.propertyNames = propertyNames;
    }

    public Relation addPropertyName(@NonNull String propertyName) {
        if (propertyNames == null) {
            propertyNames = new ArrayList<String>();
        }
        if (!TextUtils.isEmpty(propertyName)) {
            propertyNames.add(propertyName);
        }
        return this;
    }

    @JsonIgnore
    public int getValueByRelation(@NonNull Relation.Retreiver retreiver) {
        if (ModifierType.AVERAGE.equals(this.modifierType)) {
            if (retreiver != null) {
                int sum = 0;
                int mod = 0;
                for (String propertyName : propertyNames) {
                    if (propertyName != null) {
                        int value = retreiver.retreivePropertValue(propertyName);
                        sum = sum + value;
                        mod++;
                    }
                }
                modifier = mod;
                return getCalculatedValue(sum);
            }
        }
        return 0;
    }

    @JsonIgnore
    protected int getCalculatedValue(int valueFromProperties) {
        switch (modifierType) {
        case MULTIPLY:
            return valueFromProperties * modifier;
        case ADDITION:
            return valueFromProperties + modifier;
        case DIVISION:
            return valueFromProperties / modifier;
        case SUBSTRACT:
            return valueFromProperties - modifier;
        case AVERAGE:
            return valueFromProperties / modifier;
        default:
            return valueFromProperties;
        }
    }

    @JsonIgnore
    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.modifier);
        dest.writeInt(this.modifierType == null ? -1 : this.modifierType.ordinal());
        dest.writeStringList(this.propertyNames);
        dest.writeInt(this.max);
        dest.writeInt(this.min);
        dest.writeByte(modifiesMaximum ? (byte) 1 : (byte) 0);
        dest.writeByte(modifiesMinimum ? (byte) 1 : (byte) 0);
    }

    public Relation withModifier(int mod) {
        this.modifier = mod;
        return this;
    }

    public Relation withRelation(String name) {
        addPropertyName(name);
        return this;
    }

    public Relation withModifierType(ModifierType type) {
        setModifierType(type);
        return this;
    }


    public interface Retreiver {
        int retreivePropertValue(String propertyName);
    }
}
