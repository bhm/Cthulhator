package com.bustiblelemons.cthulhator.model.cache;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bhm on 13.08.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SavedCharactersSet {
    private static SavedCharactersSet sEmpty;

    static {
        sEmpty = new SavedCharactersSet();
        sEmpty.setCharacters(new ArrayList<SavedCharacter>());
    }

    private List<SavedCharacter> characters;

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

    public static SavedCharactersSet empty() {
        return sEmpty;
    }

    public void addDescription() {

    }
}
