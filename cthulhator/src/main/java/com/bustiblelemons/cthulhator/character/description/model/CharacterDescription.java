package com.bustiblelemons.cthulhator.character.description.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bustiblelemons.cthulhator.character.portrait.model.Portrait;
import com.bustiblelemons.randomuserdotme.model.Location;
import com.bustiblelemons.randomuserdotme.model.Name;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.github.scottmaclure.character.traits.model.RandomTraitsSet;

/**
 * Created by bhm on 12.08.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterDescription implements Serializable, Parcelable {
    public static final Parcelable.Creator<CharacterDescription> CREATOR = new Parcelable.Creator<CharacterDescription>() {
        public CharacterDescription createFromParcel(Parcel source) {
            return new CharacterDescription(source);
        }

        public CharacterDescription[] newArray(int size) {
            return new CharacterDescription[size];
        }
    };
    private Name name;
    private Location location;
    private RandomTraitsSet traits;
    private List<Portrait> portraitList;

    public CharacterDescription() {
    }

    private CharacterDescription(Parcel in) {
        this.name = in.readParcelable(Name.class.getClassLoader());
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.traits = in.readParcelable(RandomTraitsSet.class.getClassLoader());
        this.portraitList = new ArrayList<Portrait>();
        in.readTypedList(this.portraitList, Portrait.CREATOR);
    }

    public RandomTraitsSet getTraits() {
        return traits;
    }

    public void setTraits(RandomTraitsSet traits) {
        this.traits = traits;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Portrait> getPortraitList() {
        return portraitList;
    }

    public void setPortraitList(List<Portrait> portraitList) {
        this.portraitList = portraitList;
    }

    @JsonIgnore
    public void addPortrait(String url) {
        addPortrait(url, false);
    }

    @JsonIgnore
    public void addPortrait(String url, boolean asMain) {
        if (portraitList == null) {
            portraitList = new ArrayList<Portrait>();
        }
        Portrait p = new Portrait();
        p.setMain(asMain);
        p.setUrl(url);
        if (portraitList != null && portraitList.contains(p)) {
            portraitList.remove(p);
        }
        portraitList.add(p);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.name, flags);
        dest.writeParcelable(this.location, flags);
        dest.writeParcelable(this.traits, flags);
        dest.writeTypedList(this.portraitList);
    }
}
