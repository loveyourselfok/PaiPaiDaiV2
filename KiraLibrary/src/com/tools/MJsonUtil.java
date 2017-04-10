package com.tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MJsonUtil {

	public static String getString(JSONObject object, String key) {
		if (object.has(key)) {
			try {
				return object.getString(key);
			} catch (JSONException e) {
				Log_Util.showLogCompletion("errorKey=" + key, "ERROR_KEY");
				return "";
			}
		} else {
			Log_Util.showLogCompletion("no exist key=" + key, "NO_EXIST_KEY");
			return "";
		}
	}

	public static String getString(JSONObject object, String key,
			String defaultValue) {
		if (object.has(key)) {
			try {
				return object.getString(key);
			} catch (JSONException e) {
				Log_Util.showLogCompletion("errorKey=" + key, "ERROR_KEY");
				return defaultValue;
			}
		} else {
			Log_Util.showLogCompletion("no exist key=" + key, "NO_EXIST_KEY");
			return defaultValue;
		}
	}

	public static int getInt(JSONObject object, String key) {
		if (object.has(key)) {
			try {
				return object.getInt(key);
			} catch (JSONException e) {
				Log_Util.showLogCompletion("errorKey=" + key, "ERROR_KEY");
				return -1;
			}
		} else {
			Log_Util.showLogCompletion("no exist key=" + key, "NO_EXIST_KEY");
			return -1;
		}
	}

	public static int getInt(JSONObject object, String key, int defaultValue) {
		if (object.has(key)) {
			try {
				return object.getInt(key);
			} catch (JSONException e) {
				Log_Util.showLogCompletion("errorKey=" + key, "ERROR_KEY");
				return defaultValue;
			}
		} else {
			Log_Util.showLogCompletion("no exist key=" + key, "NO_EXIST_KEY");
			return defaultValue;
		}
	}

	public static long getLong(JSONObject object, String key) {
		if (object.has(key)) {
			try {
				return object.getLong(key);
			} catch (JSONException e) {
				Log_Util.showLogCompletion("errorKey=" + key, "ERROR_KEY");
				return -1;
			}
		} else {
			Log_Util.showLogCompletion("no exist key=" + key, "NO_EXIST_KEY");
			return -1;
		}
	}

	public static double getDouble(JSONObject object, String key) {
		if (object.has(key)) {
			try {
				return object.getDouble(key);
			} catch (JSONException e) {
				Log_Util.showLogCompletion("errorKey=" + key, "ERROR_KEY");
				return -1;
			}
		} else {
			Log_Util.showLogCompletion("no exist key=" + key, "NO_EXIST_KEY");
			return -1;
		}
	}

	public static double getDouble(JSONObject object, String key,
			double defaultValue) {
		if (object.has(key)) {
			try {
				return object.getDouble(key);
			} catch (JSONException e) {
				Log_Util.showLogCompletion("errorKey=" + key, "ERROR_KEY");
				return defaultValue;
			}
		} else {
			Log_Util.showLogCompletion("no exist key=" + key, "NO_EXIST_KEY");
			return defaultValue;
		}
	}

	public static boolean getBoolean(JSONObject object, String key) {
		if (object.has(key)) {
			try {
				return object.getBoolean(key);
			} catch (JSONException e) {
				Log_Util.showLogCompletion("errorKey=" + key, "ERROR_KEY");
				return false;
			}
		} else {
			Log_Util.showLogCompletion("no exist key=" + key, "NO_EXIST_KEY");
			return false;
		}
	}

	public static JSONArray getJSONArray(JSONObject object, String key) {
		if (object.has(key)) {
			try {
				return object.getJSONArray(key);
			} catch (JSONException e) {
				Log_Util.showLogCompletion("errorKey=" + key, "ERROR_KEY");
				return new JSONArray();
			}
		} else {
			Log_Util.showLogCompletion("no exist key=" + key, "NO_EXIST_KEY");
			return new JSONArray();
		}
	}
	
	public static JSONObject getJsonObject(String jsonStr){
		JSONObject jsonObject=null;
		try {
			jsonObject=new JSONObject(jsonStr);
			return jsonObject;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static JSONObject getJsonObject(JSONArray array,	int index){
		JSONObject jsonObject=null;
		try {
			jsonObject = (JSONObject) array.get(index);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
