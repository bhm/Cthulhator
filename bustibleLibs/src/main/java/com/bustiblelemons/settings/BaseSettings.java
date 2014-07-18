package com.bustiblelemons.settings;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created 9 Dec 2013
 */
public class BaseSettings {
	public static final String USE_MOBILE = "use_mobile";
	private static SharedPreferences INSTANCE;

	public static SharedPreferences getPreferences(Context context) {
		return INSTANCE == null ? INSTANCE = PreferenceManager.getDefaultSharedPreferences(context) : INSTANCE;
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

	public static JSONObject getJsonObject(Context context, String key) {
		try {
			return new JSONObject(getPreferences(context).getString(key, ""));
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject();
		}
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
