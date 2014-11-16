package com.bustiblelemons.cthulhator.character.characterslist.logic;

import android.content.Context;
import android.text.TextUtils;

import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.description.model.CharacterDescription;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.randomuserdotme.model.Location;

import java.util.Locale;

/**
 * Created by bhm on 22.09.14.
 */
public class SavedCharacterTransformer {

    private Context mContext;

    private SavedCharacterTransformer() {
    }

    public static SavedCharacterTransformer getInstance() {
        return LazyHolder.INSTANCE;
    }

    public SavedCharacterTransformer withContext(Context context) {
        this.mContext = context;
        return this;
    }

    public CharacterInfo transform(final SavedCharacter savedCharacter) {
        if (savedCharacter != null) {
            CharacterInfoImpl impl = new CharacterInfoImpl();
            impl.setName(savedCharacter.getName());
            impl.setUrl(savedCharacter.getPhotoUrl());
            String extrainfo = getMainCharacteristics(savedCharacter);
            impl.setExtrainfo(extrainfo);
            String mainInfo = getLocationInfo(savedCharacter);
            impl.setMainInfo(mainInfo);
            impl.setId(savedCharacter.getId());
            mContext = null;
            return impl;
        }
        mContext = null;
        return null;
    }

    private String getLocationInfo(SavedCharacter savedCharacter) {
        String location = "";
        CharacterDescription des = savedCharacter.getDescription();
        if (des != null && des.getLocation() != null) {
            Location loc = des.getLocation();
            location = String.format(Locale.ENGLISH, "%s %s",
                    loc.getCity(),
                    loc.getState());
        }
        return String.format(Locale.ENGLISH, "%s", location);
    }

    private String getMainCharacteristics(SavedCharacter savedCharacter) {
        StringBuilder b = new StringBuilder();
        String prefix = "";
        for (CharacterProperty p : savedCharacter.getTopCharacteristics(3)) {
            b.append(prefix);
            String name = p.getName();
            if (mContext != null && p.getNameResId() > 0) {
                name = mContext.getString(p.getNameResId());
            }
            if (!TextUtils.isEmpty(name)) {
                b.append(name);
            }
            prefix = ", ";
        }
        prefix = "\n";
        for (CharacterProperty p : savedCharacter.getTopSkills(3)) {
            b.append(prefix);
            String name = p.getName();
            if (mContext != null && p.getNameResId() > 0) {
                name = mContext.getString(p.getNameResId());
            }
            if (!TextUtils.isEmpty(name)) {
                b.append(name);
            }
            prefix = ", ";
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
        private int    id;

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

        @Override
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
