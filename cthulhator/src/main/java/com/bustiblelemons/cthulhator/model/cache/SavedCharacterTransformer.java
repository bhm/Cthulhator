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

    private SavedCharacterTransformer() {
    }

    public static SavedCharacterTransformer getInstance() {
        return LazyHolder.INSTANCE;
    }

    public CharacterInfo transform(final SavedCharacter savedCharacter) {
        if (savedCharacter != null) {
            CharacterInfoImpl impl = new CharacterInfoImpl();
            impl.setName(savedCharacter.getName());
            impl.setUrl(savedCharacter.getPhotoUrl());
            String extrainfo = getExtraInfo(savedCharacter);
            impl.setExtrainfo(extrainfo);
            String mainInfo = getMainInfo(savedCharacter);
            impl.setMainInfo(mainInfo);
            return impl;
        }
        return null;
    }

    private String getMainInfo(SavedCharacter savedCharacter) {
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

    private String getExtraInfo(SavedCharacter savedCharacter) {
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

    private static final class LazyHolder {
        public static final SavedCharacterTransformer INSTANCE = new SavedCharacterTransformer();
    }

    private class CharacterInfoImpl implements CharacterInfo {
        private String name;
        private String extrainfo;
        private String mainInfo;
        private String url;

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getMainInfo() {
            return mainInfo;
        }

        public void setMainInfo(String mainInfo) {
            this.mainInfo = mainInfo;
        }

        @Override
        public String getExtraInfo() {
            return extrainfo;
        }

        public void setExtrainfo(String extrainfo) {
            this.extrainfo = extrainfo;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String getPortraitUrl() {
            return url;
        }
    }
}
