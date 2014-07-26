
package com.bustiblelemons.api.random.names.randomuserdotme.model;

import com.bustiblelemons.model.LocationInfo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements LocationInfo {
    private String city;
    private String state;
    private String street;
    private String zip;

    @Override
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String getZipCode() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
