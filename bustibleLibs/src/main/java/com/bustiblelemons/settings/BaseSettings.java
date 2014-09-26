package com.bustiblelemons.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created 9 Dec 2013
 */
public class BaseSettings {
    public static final String USE_MOBILE = "use_mobile";
    private static SharedPreferences INSTANCE;

    public static SharedPreferences getPreferences(Context context) {
        return INSTANCE == null ? INSTANCE = PreferenceManager.getDefaultSharedPreferences(
                context) : INSTANCE;
    }

    public static void setValue(Context context, String key, long val) {
        BaseSettings.getPreferences(context).edit().putLong(key, val).commit();
    }

    public static void setValue(Context context, String key, String value) {
        BaseSettings.getPreferences(context).edit().putString(key, value).commit();
    }

    public static void setValue(Context context, String key, boolean value) {
        BaseSettings.getPreferences(context).edit().putBoolean(key, value).commit();
    }

    public static void setValue(Context context, String key, JSONObject jsonObject) {
        BaseSettings.getPreferences(context).edit().putString(key, jsonObject.toString()).commit();
    }

    public static void setValue(Context context, String key, JSONArray jsonArray) {
        BaseSettings.getPreferences(context).edit().putString(key, jsonArray.toString()).commit();
    }

    public static void setValue(Context context, String key, int value) {
        BaseSettings.getPreferences(context).edit().putInt(key, value).commit();
    }

    public static void setValue(Context context, String key, float value) {
        BaseSettings.getPreferences(context).edit().putFloat(key, value).commit();
    }

    public static boolean useMobileData(Context context) {
        return getPreferences(context).getBoolean(USE_MOBILE, false);
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getPreferences(context).getString(key, defaultValue);
    }

    public static JSONObject getJsonObject(Context context, String key) {
        try {
            return new JSONObject(getPreferences(context).getString(key, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public static <T extends Enum<T>> T getEnum(Context context, String key, Class<T> type,
                                                String defaultValue) {
        try {
            String name = getString(context, key, defaultValue);
            return Enum.valueOf(type, name);
        } catch (IllegalArgumentException e) {
            return Enum.valueOf(type, defaultValue);
        }
    }

    public static <T extends Enum> void setEnum(Context context, String key, T val) {
        setValue(context, key, val.name());
    }

    public static JSONArray getJsonArray(Context context, String key) {
        try {
            return new JSONArray(getPreferences(context).getString(key, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

}
