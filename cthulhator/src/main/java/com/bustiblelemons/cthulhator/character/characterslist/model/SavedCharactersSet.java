package com.bustiblelemons.cthulhator.character.characterslist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bhm on 13.08.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SavedCharactersSet implements Serializable, Parcelable {
    public static final Parcelable.Creator<SavedCharactersSet> CREATOR = new Parcelable.Creator<SavedCharactersSet>() {
        public SavedCharactersSet createFromParcel(Parcel source) {
            return new SavedCharactersSet(source);
        }

        public SavedCharactersSet[] newArray(int size) {
            return new SavedCharactersSet[size];
        }
    };
    @JsonIgnore
    private static SavedCharactersSet sEmpty;

    static {
        sEmpty = new SavedCharactersSet();
        sEmpty.setCharacters(new ArrayList<SavedCharacter>());
    }

    private List<SavedCharacter> characters;

    public SavedCharactersSet() {
    }

    private SavedCharactersSet(Parcel in) {
        in.readTypedList(characters, SavedCharacter.CREATOR);
    }

    public List<SavedCharacter> getCharacters() {
        return characters;
    }

    public void setCharacters(List<SavedCharacter> characters) {
        this.characters = characters;
    }

    public List<SavedCharacter> get(int offset, int limit) {
        int offsetEnd = offset + limit;
        if (offset > characters.size() || offsetEnd > characters.size()) {
            return Collections.emptyList();
        }
        int start = offset;
        int end = characters.size() > offsetEnd ? offsetEnd : characters.size();
        return this.characters.subList(start, end);
    }

    @JsonIgnore
    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(characters);
    }

    public void add(SavedCharacter character) {
        if (character == null) {
            return;
        }
        if (characters == null) {
            characters = new ArrayList<SavedCharacter>();
        }
        characters.add(character);
    }
}
