package com.bustiblelemons.cthulhator.model.desc;

import com.bustiblelemons.api.random.names.randomuserdotme.model.Location;
import com.bustiblelemons.api.random.names.randomuserdotme.model.Name;
import com.bustiblelemons.cthulhator.model.Portrait;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

import io.github.scottmaclure.character.traits.model.RandomTraitsSet;

/**
 * Created by bhm on 12.08.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterDescription {
    private Name            name;
    private Location        location;
    private RandomTraitsSet traits;

    public RandomTraitsSet getTraits() {
        return traits;
    }

    public void setTraits(RandomTraitsSet traits) {
        this.traits = traits;
    }

    private List<Portrait> portraitList;

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
}