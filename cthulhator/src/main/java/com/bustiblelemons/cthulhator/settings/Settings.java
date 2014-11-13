package com.bustiblelemons.cthulhator.settings;

import android.content.Context;

import com.bustiblelemons.cthulhator.character.portrait.model.OnlineSearchUISettings;
import com.bustiblelemons.cthulhator.settings.character.CharacterSettings;
import com.bustiblelemons.cthulhator.settings.character.CharacterSettingsImpl;
import com.bustiblelemons.cthulhator.system.edition.CthulhuEdition;
import com.bustiblelemons.settings.BaseSettings;
import com.bustiblelemons.storage.Storage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


/**
 * Created 9 Dec 2013
 */
public class Settings extends BaseSettings {

    public static final  String CACHE_FOLDER                = "cache_folder";
    private static final String EDITION                     = "edition";
    private static       String sPortraitSettingsFilename   = "portraits.settings";
    private static       String sRandomCharSettingsFilename = "random.char.settings";


    public static CharacterSettings getLastPortraitSettings(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = Storage.getStorageFile(context, sPortraitSettingsFilename);
            return mapper.readValue(file, CharacterSettingsImpl.class);
        } catch (IOException e) {
            e.printStackTrace();
            return CharacterSettingsImpl.defaults();
        }
    }

    public static void saveLastOnlinePhotoSearchQuery(Context context, CharacterSettings query) {
        ObjectMapper m = new ObjectMapper();
        try {
            File file = Storage.getStorageFile(context, sPortraitSettingsFilename);
            m.writeValue(file, (CharacterSettingsImpl) query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static OnlineSearchUISettings getLastKnownPhotoSearchUISettings(Context context) {
        ObjectMapper m = new ObjectMapper();
        try {
            File file = Storage.getStorageFile(context, sRandomCharSettingsFilename);
            return m.readValue(file, OnlineSearchUISettings.class);
        } catch (IOException e) {
            e.printStackTrace();
            return OnlineSearchUISettings.defaults();
        }
    }

    public static void setLastKnownPhotoSearchUISettings(Context context, OnlineSearchUISettings onlineSearchUISettings) {
        ObjectMapper m = new ObjectMapper();
        try {
            File file = Storage.getStorageFile(context, sRandomCharSettingsFilename);
            m.writeValue(file, onlineSearchUISettings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setEdition(Context context, CthulhuEdition ed) {
        setEnum(context, EDITION, ed);
    }

    public static CthulhuEdition getEdition(Context context) {
        return getEnum(context, EDITION, CthulhuEdition.class, CthulhuEdition.CoC5.name());
    }
}
