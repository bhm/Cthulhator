package com.bustiblelemons.cthulhator.model;

import com.bustiblelemons.api.random.names.randomuserdotme.model.Location;

/**
 * Created by bhm on 29.07.14.
 */
public class BirthData {
    private Location location;
    private String   description;
    private long     date;

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
}
