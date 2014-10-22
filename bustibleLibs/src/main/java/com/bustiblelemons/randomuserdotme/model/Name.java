
package com.bustiblelemons.randomuserdotme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.lang.WordUtils;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Name implements Serializable, Parcelable {
    @JsonIgnore
    public static final Parcelable.Creator<Name> CREATOR = new Parcelable.Creator<Name>() {
        public Name createFromParcel(Parcel source) {
            return new Name(source);
        }

        public Name[] newArray(int size) {
            return new Name[size];
        }
    };
    private String first;
    private String last;
    private String title;

    public Name() {
    }

    private Name(Parcel in) {
        this.first = in.readString();
        this.last = in.readString();
        this.title = in.readString();
    }

    public String getFirst() {
        return WordUtils.capitalizeFully(this.first);
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return WordUtils.capitalizeFully(this.last);
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getTitle() {
        return WordUtils.capitalizeFully(this.title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public String getFullName() {
        return String.format("%s %s", getFirst(), getLast());
    }

    @JsonIgnore
    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.first);
        dest.writeString(this.last);
        dest.writeString(this.title);
    }
}
