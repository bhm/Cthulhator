package com.bustiblelemons.cthulhator.character.characterslist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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

    private List<SavedCharacter> mCharacters;

    public SavedCharactersSet() {
        mCharacters = new ArrayList<SavedCharacter>();
    }

    private SavedCharactersSet(Parcel in) {
        in.readTypedList(mCharacters, SavedCharacter.CREATOR);
    }

    public List<SavedCharacter> getCharacters() {
        return mCharacters;
    }

    public void setCharacters(Collection<SavedCharacter> characters) {
        this.mCharacters = new ArrayList<SavedCharacter>(characters);
    }

    public List<SavedCharacter> get(int offset, int limit) {
        int offsetEnd = offset + limit;
        if (offset > mCharacters.size() || offsetEnd > mCharacters.size()) {
            return Collections.emptyList();
        }
        int start = offset;
        int end = mCharacters.size() > offsetEnd ? offsetEnd : mCharacters.size();
        return this.mCharacters.subList(start, end);
    }

    @JsonIgnore
    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mCharacters);
    }

    public void add(SavedCharacter character) {
        if (character == null) {
            return;
        }
        if (mCharacters == null) {
            mCharacters = new ArrayList<SavedCharacter>();
        }
        mCharacters.add(character);
    }
}
