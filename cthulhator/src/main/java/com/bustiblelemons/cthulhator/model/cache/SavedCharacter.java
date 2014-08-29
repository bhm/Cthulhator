package com.bustiblelemons.cthulhator.model.cache;

import com.bustiblelemons.cthulhator.model.CthulhuEdition;
import com.bustiblelemons.cthulhator.model.Portrait;
import com.bustiblelemons.cthulhator.model.desc.CharacterDescription;

import java.util.List;

/**
 * Created by bhm on 12.08.14.
 */
public class SavedCharacter {

    private CthulhuEdition edition;

    public CthulhuEdition getEdition() {
        return edition;
    }

    public void setEdition(CthulhuEdition edition) {
        this.edition = edition;
    }

    private CharacterDescription description;

    public CharacterDescription getDescription() {
        return description;
    }

    public void setDescription(CharacterDescription description) {
        this.description = description;
    }

    public String getName() {
        return description.getName().getFullName();
    }

    public String getPhotoUrl() {
        List<Portrait> portraitList = description.getPortraitList();
        if (portraitList != null && portraitList.size() > 0) {
            return portraitList.get(0) != null ? portraitList.get(0).getUrl() : null;
        }
        return null;
    }
}
