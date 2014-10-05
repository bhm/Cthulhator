package com.bustiblelemons.cthulhator.character.portrait.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by bhm on 29.07.14.
 */
public class Portrait implements Serializable, Parcelable {
    public static final Parcelable.Creator<Portrait> CREATOR = new Parcelable.Creator<Portrait>() {
        public Portrait createFromParcel(Parcel source) {
            return new Portrait(source);
        }

        public Portrait[] newArray(int size) {
            return new Portrait[size];
        }
    };
    private String  name;
    private String  url;
    private byte[]  data;
    private boolean isMain;

    public Portrait() {
    }

    private Portrait(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.data = in.createByteArray();
        this.isMain = in.readByte() != 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Portrait portrait = (Portrait) o;

        if (url != null ? !url.equals(portrait.url) : portrait.url != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeByteArray(this.data);
        dest.writeByte(isMain ? (byte) 1 : (byte) 0);
    }
}
