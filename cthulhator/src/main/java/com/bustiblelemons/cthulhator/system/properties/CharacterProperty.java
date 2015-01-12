package com.bustiblelemons.cthulhator.system.properties;

import android.os.Parcel;
import android.os.Parcelable;

import com.bustiblelemons.cthulhator.system.dice.model.ValueSpace;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by bhm on 20.07.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterProperty extends ObservableCharacterProperty
        implements Serializable, Parcelable, Comparable<CharacterProperty> {

    @JsonIgnore
    public static final CharacterProperty                     EMPTY = new CharacterProperty();
    @JsonIgnore
    public static final Parcelable.Creator<CharacterProperty> CREATOR
                                                                    = new Parcelable.Creator<CharacterProperty>() {
        public CharacterProperty createFromParcel(Parcel source) {
            return new CharacterProperty(source);
        }

        public CharacterProperty[] newArray(int size) {
            return new CharacterProperty[size];
        }
    };
    private String            name;
    private int               maxValue;
    private int               minValue;
    private int               value;
    private int               baseValue;
    private PropertyFormat    format;
    private PropertyType      type;
    private List<ActionGroup> actionGroup;
    private Set<Relation>     relations;
    @JsonIgnore
    private int nameResId = -1;
    @JsonIgnore
    private int         shortNameResId;
    @JsonIgnore
    private ActionGroup mMainActionGroup;
    private String      displayValue;

    @JsonProperty("value_space")
    private ValueSpaceSet valueSpaceSet;

    public CharacterProperty() {
    }

    private CharacterProperty(Parcel in) {
        this.name = in.readString();
        this.displayValue = in.readString();
        Collection<ValueSpace> spaces = in.createTypedArrayList(ValueSpace.CREATOR);
        this.valueSpaceSet = new ValueSpaceSet(spaces);
        this.value = in.readInt();
        this.maxValue = in.readInt();
        this.minValue = in.readInt();
        this.baseValue = in.readInt();
        int tmpFormat = in.readInt();
        this.format = tmpFormat == -1 ? null : PropertyFormat.values()[tmpFormat];
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : PropertyType.values()[tmpType];

        readActionGroups(in);

        int relationsSize = in.readInt();
        Relation[] rel = new Relation[relationsSize];
        in.readTypedArray(rel, Relation.CREATOR);
        relations = new HashSet<Relation>();
        Collections.addAll(relations, rel);
        this.nameResId = in.readInt();
        this.shortNameResId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.displayValue);
        List<ValueSpace> spaces;
        if (this.valueSpaceSet != null) {
            spaces = new ArrayList<ValueSpace>(this.valueSpaceSet);
        } else {
            spaces = new ArrayList<ValueSpace>(0);
        }
        dest.writeTypedList(spaces);
        dest.writeInt(this.value);
        dest.writeInt(this.maxValue);
        dest.writeInt(this.minValue);
        dest.writeInt(this.baseValue);
        dest.writeInt(this.format == null ? -1 : this.format.ordinal());
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        parcelizeActionGroups(dest);
        int relationsSize = this.relations != null ? this.relations.size() : 0;
        dest.writeInt(relationsSize);
        Relation[] rel = new Relation[relationsSize];
        if (this.relations != null) {
            rel = this.relations.toArray(new Relation[relationsSize]);
        }
        dest.writeTypedArray(rel, flags);
        dest.writeInt(this.nameResId);
        dest.writeInt(this.shortNameResId);
    }

    @JsonProperty("value_space")
    public ValueSpaceSet getValueSpaceSet() {
        if (valueSpaceSet != null && !valueSpaceSet.isEmpty()) {
            valueSpaceSet = new ValueSpaceSet(getMinValue(), getMaxValue());
        }
        return valueSpaceSet;
    }

    @JsonProperty("value_space")
    public void setValueSpaceSet(ValueSpaceSet valueSpaceSet) {
        this.valueSpaceSet = valueSpaceSet;
    }

    private void readActionGroups(Parcel in) {
        int actionGroupOrdinalsSize = in.readInt();
        int[] actionGroupOrdinals = new int[actionGroupOrdinalsSize];
        in.readIntArray(actionGroupOrdinals);
        this.actionGroup = new ArrayList<ActionGroup>(shortNameResId);
        for (int ordinal : actionGroupOrdinals) {
            ActionGroup group = null;
            if (ordinal >= 0) {
                group = ActionGroup.values()[ordinal];
            }
            this.actionGroup.add(group);
        }
    }

    public List<ActionGroup> getActionGroup() {
        if (actionGroup == null) {
            actionGroup = new ArrayList<ActionGroup>();
        }
        return actionGroup;
    }

    public void setActionGroup(Collection<ActionGroup> collection) {
        setChanged();
        if (collection == null) {
            this.actionGroup = new ArrayList<ActionGroup>();
        } else {
            this.actionGroup = new ArrayList<ActionGroup>(collection);
        }
    }

    public Set<Relation> getRelations() {
        if (relations == null) {
            return Collections.emptySet();
        }
        return relations;
    }

    public void setRelations(Collection<Relation> arg) {
        if (arg != null) {
            setChanged();
            this.relations = new HashSet<Relation>(arg);
        }
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
        setChanged();
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        setChanged();
        this.minValue = minValue;
    }

    public int getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(int baseValue) {
        setChanged();
        this.baseValue = baseValue;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        setChanged();
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        setChanged();
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
        if (valueSpaceSet != null && !valueSpaceSet.isEmpty()) {
            ValueSpace space = valueSpaceSet.getPointPoolByValue(this.value);
            int ret = space.randomValue();
            setValue(ret);
            return ret;
        } else {
            Random r = new Random();
            int n = getMaxValue() - getMinValue();
            int roll = r.nextInt(n);
            int ret = roll + getMinValue() + 1;
            setValue(ret);
            return ret;
        }
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CharacterProperty property = (CharacterProperty) o;

        if (name != null ? !name.equals(property.name) : property.name != null) {
            return false;
        }

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
        if (value + 1 <= getMaxValue()) {
            setChanged();
            value++;
            return true;
        }
        return false;
    }

    @JsonIgnore
    public boolean decreaseValue() {
        if (value - 1 >= getMinValue()) {
            setChanged();
            value--;
            return true;
        }
        return false;
    }

    @JsonIgnore
    public boolean isPercentile() {
        return PropertyFormat.PERCENTILE.equals(getFormat());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    private void parcelizeActionGroups(Parcel dest) {
        if (this.actionGroup == null) {
            dest.writeInt(0);
            dest.writeIntArray(new int[0]);
        } else {
            int size = this.actionGroup.size();
            int[] ordinals = new int[size];
            int pos = 0;
            for (ActionGroup group : this.actionGroup) {
                int ordinal = -1;
                if (group != null) {
                    ordinal = group.ordinal();
                }
                ordinals[pos] = ordinal;
                pos++;
            }
            dest.writeInt(size);
            dest.writeIntArray(ordinals);
        }
    }

    public ActionGroup getMainActionGroup() {
        if (mMainActionGroup == null) {
            List<ActionGroup> groups = getActionGroup();
            for (ActionGroup g : groups) {
                return mMainActionGroup = g;
            }
            mMainActionGroup = ActionGroup.OTHER;
        }
        return mMainActionGroup;
    }

    public String getDisplayValue() {
        if (valueSpaceSet != null && !valueSpaceSet.isEmpty()) {
            return valueSpaceSet.getPointPoolByValue(this.value).getDisplayValue();
        }
        return String.valueOf(getValue());
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public boolean nameMatches(String name) {
        if (name == null) {
            return false;
        }
        return name.equals(this.name);
    }

    @Override
    public int compareTo(CharacterProperty another) {
        if (another == null || this.getValue() > another.getValue()) {
            return 1;
        } else if (this.getValue() < another.getValue()) {
            return -1;
        }
        return 0;
    }
}
