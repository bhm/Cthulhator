package com.bustiblelemons.cthulhator.character.possessions.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bustiblelemons.cthulhator.system.properties.Relation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 29.07.14.
 */
public class Possesion implements Parcelable {
    public static final Parcelable.Creator<Possesion> CREATOR = new Parcelable.Creator<Possesion>() {
        public Possesion createFromParcel(Parcel source) {
            return new Possesion(source);
        }

        public Possesion[] newArray(int size) {
            return new Possesion[size];
        }
    };
    private long   size;
    private String unit;
    private String name;
    private String description;
    private List<Relation> relations = new ArrayList<Relation>();

    public Possesion() {
    }

    private Possesion(Parcel in) {
        this.size = in.readLong();
        this.unit = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        in.readTypedList(relations, Relation.CREATOR);
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.size);
        dest.writeString(this.unit);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeTypedList(relations);
    }
}
