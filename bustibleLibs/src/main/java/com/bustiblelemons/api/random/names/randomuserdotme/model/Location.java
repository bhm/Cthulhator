
package com.bustiblelemons.api.random.names.randomuserdotme.model;

import com.bustiblelemons.model.LocationInfo;

import java.io.Serializable;

public class Location implements Serializable, LocationInfo {
    private String city;
    private String state;
    private String street;
    private String zip;

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
