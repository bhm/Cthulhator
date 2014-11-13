
package com.bustiblelemons.randomuserdotme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bustiblelemons.model.LocationInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.lang.WordUtils;

import java.io.Serializable;
import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements LocationInfo, Parcelable, Serializable {
    @JsonIgnore
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

    @JsonIgnore
    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.street);
        dest.writeString(this.zip);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Location location = (Location) o;

        if (city != null ? !city.equals(location.city) : location.city != null) {
            return false;
        }
        if (state != null ? !state.equals(location.state) : location.state != null) {
            return false;
        }
        if (street != null ? !street.equals(location.street) : location.street != null) {
            return false;
        }
        if (zip != null ? !zip.equals(location.zip) : location.zip != null) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        Locale l = Locale.getDefault();
        final StringBuilder sb = new StringBuilder();
        sb.append(street)
                .append("\n");
        if ("pl".equalsIgnoreCase(l.getLanguage())) {
            sb.append(zip)
                    .append(" ")
                    .append(city);
        } else {
            sb.append(city)
                    .append(" ")
                    .append("\n")
                    .append(state)
                    .append(" ")
                    .append(zip);
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        return result;
    }
}
