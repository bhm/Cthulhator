
package com.bustiblelemons.api.random.names.randomuserdotme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bustiblelemons.model.LocationInfo;

import org.apache.commons.lang.WordUtils;

public class Location implements LocationInfo, Parcelable {
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
    private String city;
    private String state;
    private String street;
    private String zip;

    public Location() {
    }

    private Location(Parcel in) {
        this.city = in.readString();
        this.state = in.readString();
        this.street = in.readString();
        this.zip = in.readString();
    }

    public String getCity() {
        return WordUtils.capitalizeFully(this.city);
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return WordUtils.capitalizeFully(this.state);
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return WordUtils.capitalizeFully(this.street);
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return WordUtils.capitalizeFully(this.zip);
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.street);
        dest.writeString(this.zip);
    }
}
