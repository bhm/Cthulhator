package com.bustiblelemons.cthulhator.model;

import com.bustiblelemons.api.random.names.randomuserdotme.model.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 29.07.14.
 */
public class HistoryEvent implements Serializable {
    private String   name;
    private String   description;
    private long     date;
    private Location location;
    private List<Relation> affected = new ArrayList<Relation>();

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Relation> getAffected() {
        return affected;
    }

    public void setAffected(List<Relation> affected) {
        this.affected = affected;
    }

    public boolean isBefore(long tillDate) {
        return getDate() <= tillDate;
    }
}
