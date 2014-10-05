
package com.bustiblelemons.randomuserdotme.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable, Parcelable {
    @JsonIgnore
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private String   sSN;
    private String   cell;
    private String   dob;
    private String   email;
    private String   gender;
    private Location location;
    private String   md5;
    private Name     name;
    private String   password;
    private String   phone;
    private Picture  picture;
    private String   registered;
    private String   salt;
    private String   sha1;
    private String   sha256;
    private String   username;
    private String   version;

    public User() {
    }

    private User(Parcel in) {
        this.sSN = in.readString();
        this.cell = in.readString();
        this.dob = in.readString();
        this.email = in.readString();
        this.gender = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.md5 = in.readString();
        this.name = in.readParcelable(Name.class.getClassLoader());
        this.password = in.readString();
        this.phone = in.readString();
        this.picture = in.readParcelable(Picture.class.getClassLoader());
        this.registered = in.readString();
        this.salt = in.readString();
        this.sha1 = in.readString();
        this.sha256 = in.readString();
        this.username = in.readString();
        this.version = in.readString();
    }

    public String getSSN() {
        return this.sSN;
    }

    public void setSSN(String sSN) {
        this.sSN = sSN;
    }

    public String getCell() {
        return this.cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getDob() {
        return this.dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Name getName() {
        return this.name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Picture getPicture() {
        return this.picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getRegistered() {
        return this.registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSha1() {
        return this.sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getSha256() {
        return this.sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sSN);
        dest.writeString(this.cell);
        dest.writeString(this.dob);
        dest.writeString(this.email);
        dest.writeString(this.gender);
        dest.writeParcelable(this.location, 0);
        dest.writeString(this.md5);
        dest.writeParcelable(this.name, 0);
        dest.writeString(this.password);
        dest.writeString(this.phone);
        dest.writeParcelable(this.picture, 0);
        dest.writeString(this.registered);
        dest.writeString(this.salt);
        dest.writeString(this.sha1);
        dest.writeString(this.sha256);
        dest.writeString(this.username);
        dest.writeString(this.version);
    }
}
