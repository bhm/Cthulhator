package com.bustiblelemons.cthulhator.character.persistance;

import android.os.Parcel;
import android.os.Parcelable;

import com.bustiblelemons.cthulhator.character.description.model.CharacterDescription;
import com.bustiblelemons.cthulhator.character.history.model.BirthData;
import com.bustiblelemons.cthulhator.character.history.model.HistoryEvent;
import com.bustiblelemons.cthulhator.character.possessions.model.Possesion;
import com.bustiblelemons.cthulhator.system.edition.GameEdition;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.time.YearsPeriodImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bhm on 12.08.14.
 */

//TODO move JSONIgnore parts into a retreiver/wrapper class
@JsonIgnoreProperties(ignoreUnknown = true)
public class SavedCharacter implements Parcelable, Serializable {

    @JsonIgnore
    public static final Parcelable.Creator<SavedCharacter> CREATOR     = new Parcelable.Creator<SavedCharacter>() {
        public SavedCharacter createFromParcel(Parcel source) {
            return new SavedCharacter(source);
        }

        public SavedCharacter[] newArray(int size) {
            return new SavedCharacter[size];
        }
    };
    protected           Set<CharacterProperty>             properties  = new HashSet<CharacterProperty>();
    protected           List<Possesion>                    possesions  = new ArrayList<Possesion>();
    protected           Set<HistoryEvent>                  fullHistory = new HashSet<HistoryEvent>();
    private             GameEdition                        edition     = GameEdition.CoC5;
    private CharacterDescription description;
    private BirthData            birth;
    private int                  age;
    private YearsPeriodImpl      period;

    private static AtomicInteger sAtomicId = new AtomicInteger(0);

    @JsonIgnore
    private Integer mId;

    @JsonIgnore
    public int getId() {
        if (mId == null) {
            mId = new Integer(sAtomicId.getAndIncrement());
        }
        return mId;
    }

    public SavedCharacter() {
    }

    protected SavedCharacter(Parcel in) {
        int propSize = in.readInt();
        CharacterProperty[] props = new CharacterProperty[propSize];
        in.readTypedArray(props, CharacterProperty.CREATOR);
        this.properties = new HashSet<CharacterProperty>();
        Collections.addAll(this.properties, props);
        this.possesions = new ArrayList<Possesion>();
        in.readList(this.possesions, List.class.getClassLoader());
        int fullHistorySize = in.readInt();
        HistoryEvent[] events = new HistoryEvent[fullHistorySize];
        in.readTypedArray(events, HistoryEvent.CREATOR);
        this.fullHistory = new HashSet<HistoryEvent>();
        Collections.addAll(this.fullHistory, events);
        int tmpEdition = in.readInt();
        this.edition = tmpEdition == -1 ? null : GameEdition.values()[tmpEdition];
        int tmpPeriod = in.readInt();
        this.period = tmpPeriod == -1 ? null : YearsPeriodImpl.values()[tmpPeriod];
        this.description = in.readParcelable(CharacterDescription.class.getClassLoader());
        this.birth = in.readParcelable(BirthData.class.getClassLoader());
        this.age = in.readInt();
    }

    public YearsPeriodImpl getPeriod() {
        return period;
    }

    public void setPeriod(YearsPeriodImpl period) {
        this.period = period;
    }

    public void setFullHistory(Set<HistoryEvent> fullHistory) {
        this.fullHistory = fullHistory;
    }

    public GameEdition getEdition() {
        if (edition == null) {
            edition = GameEdition.CoC5;
        }
        return edition;
    }

    public void setEdition(GameEdition edition) {
        this.edition = edition;
    }

    public CharacterDescription getDescription() {
        return description;
    }

    public void setDescription(CharacterDescription description) {
        this.description = description;
    }

    public BirthData getBirth() {
        return birth;
    }

    public void setBirth(BirthData birth) {
        this.birth = birth;
    }

    public Set<CharacterProperty> getProperties() {
        return properties;
    }

    public void setProperties(Set<CharacterProperty> properties) {
        this.properties = properties;
    }

    public List<Possesion> getPossesions() {
        return possesions;
    }

    public void setPossesions(List<Possesion> possesions) {
        this.possesions = possesions;
    }

    public Set<HistoryEvent> getFullHistory() {
        if (fullHistory == null) {
            fullHistory = new HashSet<HistoryEvent>(5);
        }
        return fullHistory;
    }

    public void setFullHistory(Collection<HistoryEvent> fullHistory) {
        this.fullHistory = new HashSet<HistoryEvent>(fullHistory);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "SavedCharacter{" +
                "properties=" + properties +
                ", possesions=" + possesions +
                ", fullHistory=" + fullHistory +
                ", edition=" + edition +
                ", description=" + description +
                ", birth=" + birth +
                ", age=" + age +
                '}';
    }

    @JsonIgnore
    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        int propSize = this.properties != null ? this.properties.size() : 0;
        dest.writeInt(propSize);
        CharacterProperty[] props = new CharacterProperty[propSize];
        if (this.properties != null) {
            props = this.properties.toArray(new CharacterProperty[propSize]);
        }
        dest.writeTypedArray(props, flags);
        dest.writeList(this.possesions);
        int historySize = this.fullHistory != null ? this.fullHistory.size() : 0;
        dest.writeInt(historySize);
        HistoryEvent[] h = new HistoryEvent[historySize];
        if (fullHistory != null) {
            h = this.fullHistory.toArray(new HistoryEvent[historySize]);
        }
        dest.writeTypedArray(h, flags);
        dest.writeInt(this.edition == null ? -1 : this.edition.ordinal());
        dest.writeInt(this.period == null ? -1 : this.period.ordinal());
        dest.writeParcelable(this.description, flags);
        dest.writeParcelable(this.birth, flags);
        dest.writeInt(this.age);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SavedCharacter character = (SavedCharacter) o;

        if (birth != null ? !birth.equals(character.birth) : character.birth != null) return false;
        if (description != null ? !description.equals(character.description) : character.description != null)
            return false;
        if (edition != character.edition) return false;
        if (period != character.period) return false;
        if (properties != null ? !properties.equals(character.properties) : character.properties != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = properties != null ? properties.hashCode() : 0;
        result = 31 * result + (edition != null ? edition.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);

        //TODO birth data does not rapport correctly
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        return result;
    }

    public void setId(int id) {
        mId = id;
    }
}