package com.bustiblelemons.cthulhator.model.cache;

import com.bustiblelemons.api.random.names.randomuserdotme.model.Location;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.desc.CharacterDescription;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;

import java.util.Locale;

/**
 * Created by bhm on 22.09.14.
 */
public class SavedCharacterTransformer {
    public static CharacterInfo transform(final SavedCharacter savedCharacter) {
        if (savedCharacter != null) {
            return new CharacterInfo() {
                @Override
                public String getName() {
                    return savedCharacter.getName();
                }

                @Override
                public String getMainInfo() {
                    String location = "";
                    CharacterDescription des = savedCharacter.getDescription();
                    if (des != null && des.getLocation() != null) {
                        Location loc = des.getLocation();
                        location = String.format(Locale.ENGLISH, "%s %s",
                                loc.getCity(),
                                loc.getState());
                    }
                    int age = savedCharacter.getAge();
                    return String.format(Locale.ENGLISH, "%s, %s", age, location);
                }

                @Override
                public String getExtraInfo() {
                    StringBuilder b = new StringBuilder();
                    String prefix = "";
                    for (CharacterProperty p : savedCharacter.getTopCharacteristics(3)) {
                        b.append(prefix);
                        b.append(p.getName());
                        prefix = ", ";
                    }
                    for (CharacterProperty p : savedCharacter.getTopSkills(3)) {
                        b.append(prefix);
                        b.append(p.getName());
                    }
                    return b.toString();
                }

                @Override
                public String getPortraitUrl() {
                    return savedCharacter.getPhotoUrl();
                }
            };
        }
        return null;
    }
}
