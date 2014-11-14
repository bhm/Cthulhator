package com.bustiblelemons.cthulhator.character.history.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bustiblelemons.cthulhator.system.properties.Relation;
import com.bustiblelemons.randomuserdotme.model.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bhm on 29.07.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryEvent implements Serializable, Parcelable, Comparable<HistoryEvent> {
    @JsonIgnore
    public static final  Parcelable.Creator<HistoryEvent> CREATOR            = new Parcelable.Creator<HistoryEvent>() {
        public HistoryEvent createFromParcel(Parcel source) {
            return new HistoryEvent(source);
        }

        public HistoryEvent[] newArray(int size) {
            return new HistoryEvent[size];
        }
    };
    @JsonIgnore
    private static       java.lang.String                 sFormat            = "MMMM dd, yyyy";
    @JsonIgnore
    private static final SimpleDateFormat                 SIMPLE_DATE_FORMAT = new SimpleDateFormat(
            sFormat);
    private String   name;
    private String   description;
    private long     date;
    private Location location;
    private List<Relation> affected = new ArrayList<Relation>();
    @JsonIgnore
    private String  formatedDate;
    @JsonIgnore
    private boolean dateChanged;

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

    @JsonIgnore
    public String getFormatedDate() {
        if (dateChanged) {
            formatedDate = SIMPLE_DATE_FORMAT.format(new Date(getDate()));
        }
        return formatedDate;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        dateChanged = date != this.date;
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

    @JsonIgnore
    public boolean isBetween(TimeSpan timeSpan) {
        if (timeSpan == null) {
            return false;
        }
        return getDate() >= timeSpan.getBeginEpoch() && getDate() <= timeSpan.getEndEpoch();
    }

    @Override
    public int compareTo(HistoryEvent another) {
        if (another == null) {
            return 1;
        }
        if (getDate() > another.getDate()) {
            return 1;
        } else if (getDate() < another.getDate()) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HistoryEvent that = (HistoryEvent) o;

        if (date != that.date) {
            return false;
        }
        if (affected != null ? !affected.equals(that.affected) : that.affected != null) {
            return false;
        }
        if (description != null ? !description.equals(
                that.description) : that.description != null) {
            return false;
        }
        if (formatedDate != null ? !formatedDate.equals(
                that.formatedDate) : that.formatedDate != null) {
            return false;
        }
        if (location != null ? !location.equals(that.location) : that.location != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (affected != null ? affected.hashCode() : 0);
        result = 31 * result + (formatedDate != null ? formatedDate.hashCode() : 0);
        return result;
    }
}
