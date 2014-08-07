package com.bustiblelemons.cthulhator.settings;

import android.content.Context;

import com.bustiblelemons.cthulhator.model.OnlinePhotoSearchQuery;
import com.bustiblelemons.cthulhator.model.OnlinePhotoSearchQueryImpl;
import com.bustiblelemons.settings.BaseSettings;
import com.bustiblelemons.storage.Storage;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;


/**
 * Created 9 Dec 2013
 */
public class Settings extends BaseSettings {

    public static final String CACHE_FOLDER = "cache_folder";
    private static String sPortraitSettingsFilename = "portraits.settings";


    public static OnlinePhotoSearchQuery getLastPortratiSettings(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = Storage.getStorageFile(context, sPortraitSettingsFilename);
            return mapper.readValue(file, OnlinePhotoSearchQueryImpl.class);
        } catch (IOException e) {
            e.printStackTrace();
            return OnlinePhotoSearchQueryImpl.defaults();
        }
    }

    public static void saveLastOnlinePhotoSearchQuery(Context context, OnlinePhotoSearchQuery query) {
        ObjectMapper m = new ObjectMapper();
        try {
            File file = Storage.getStorageFile(context, sPortraitSettingsFilename);
            m.writeValue(file, (OnlinePhotoSearchQueryImpl) query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
