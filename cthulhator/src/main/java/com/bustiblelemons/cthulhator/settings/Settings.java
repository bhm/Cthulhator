package com.bustiblelemons.cthulhator.settings;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.bustiblelemons.cthulhator.fragments.dialog.RandomCharSettings;
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
    private static String sRandomCharSettingsFilename = "random.char.settings";


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

    public static RandomCharSettings getLastRandomCharSettings(Context context) {
        ObjectMapper m = new ObjectMapper();
        try {
            File file = Storage.getStorageFile(context, sRandomCharSettingsFilename);
            return m.readValue(file, RandomCharSettings.class);
        } catch (IOException e) {
            return RandomCharSettings.defaults();
        }
    }

    public static void saveLastRandomCharSettings(Context context, RandomCharSettings randomCharSettings) {
        ObjectMapper m = new ObjectMapper();
        try {
            File file = Storage.getStorageFile(context, sRandomCharSettingsFilename);
            m.writeValue(file, randomCharSettings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
