package com.bustiblelemons.cthulhator.character.history.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bustiblelemons.randomuserdotme.model.Location;

import java.io.Serializable;

/**
 * Created by bhm on 29.07.14.
 */
public class BirthData implements Serializable, Parcelable {
    public static final Parcelable.Creator<BirthData> CREATOR = new Parcelable.Creator<BirthData>() {
        public BirthData createFromParcel(Parcel source) {
            return new BirthData(source);
        }

        public BirthData[] newArray(int size) {
            return new BirthData[size];
        }
    };
    private Location location;
    private String   description;
    private long     date;

    public BirthData() {
    }

    private BirthData(Parcel in) {
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.description = in.readString();
        this.date = in.readLong();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.location, 0);
        dest.writeString(this.description);
        dest.writeLong(this.date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BirthData birthData = (BirthData) o;

        if (date != birthData.date) return false;
        if (description != null ? !description.equals(birthData.description) : birthData.description != null)
            return false;
        if (location != null ? !location.equals(birthData.location) : birthData.location != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        return result;
    }
}
