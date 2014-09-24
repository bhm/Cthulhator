package com.bustiblelemons.cthulhator.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bustiblelemons.api.random.names.randomuserdotme.model.Location;

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
}
