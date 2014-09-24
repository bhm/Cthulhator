package com.bustiblelemons.cthulhator.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bustiblelemons.api.random.names.randomuserdotme.model.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 29.07.14.
 */
public class HistoryEvent implements Parcelable {
    public static final Parcelable.Creator<HistoryEvent> CREATOR = new Parcelable.Creator<HistoryEvent>() {
        public HistoryEvent createFromParcel(Parcel source) {
            return new HistoryEvent(source);
        }

        public HistoryEvent[] newArray(int size) {
            return new HistoryEvent[size];
        }
    };
    private String   name;
    private String   description;
    private long     date;
    private Location location;
    private List<Relation> affected = new ArrayList<Relation>();

    public HistoryEvent() {
    }

    private HistoryEvent(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.date = in.readLong();
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.affected = new ArrayList<Relation>();
        in.readTypedList(this.affected, Relation.CREATOR);
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeLong(this.date);
        dest.writeParcelable(this.location, flags);
        dest.writeTypedList(this.affected);
    }
}
