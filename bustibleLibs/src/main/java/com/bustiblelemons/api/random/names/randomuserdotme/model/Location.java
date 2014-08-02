
package com.bustiblelemons.api.random.names.randomuserdotme.model;

import com.bustiblelemons.model.LocationInfo;

import org.apache.commons.lang.WordUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements LocationInfo, Serializable {
    private String city;
    private String state;
    private String street;
    private String zip;

    private double longitude;
    private double latitude;

    @Override
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = WordUtils.capitalizeFully(city);
    }

    @Override
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = WordUtils.capitalizeFully(state);
    }

    @Override
    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = WordUtils.capitalizeFully(street);
    }

    @Override
    public String getZipCode() {
        return getZip();
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = WordUtils.capitalizeFully(zip);
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
