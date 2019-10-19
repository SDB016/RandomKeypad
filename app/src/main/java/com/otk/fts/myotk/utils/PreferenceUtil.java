package com.otk.fts.myotk.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;


public class PreferenceUtil {
    public final static String PREFERENCES_NAME = "sFile";
    public final static String FIRST_BOOT = "firstBoot";
    public final static String CUSTOM_BG = "customBgImg";
    public final static String CUSTOM_BG_PATH = "pwImgPath";
    public final static String IS_LOCK = "isLock";
    public final static String PW_SIZE = "pwSize";
    public final static String PW_LIST = "pwList";
    public final static String BACKUP_PIN = "backupPin";
    public final static String PW_TIMER = "pwTimer";
    public final static String NUM_TYPE = "numType";
    public final static String BTN_TYPE = "btnType";
    public final static String FIRST_BOOT2 = "firstBoot2";

    public final static String LOCK_USING = "lockUsing";


    private static SharedPreferences getPref(Context context){
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getPrefEditor(Context context){
        return getPref(context).edit();
    }

    public static void savePref(Context context, String key, String value){
        getPrefEditor(context).putString(key, value).apply();
    }

    public static void savePref(Context context, String key, int value){
        getPrefEditor(context).putInt(key, value).apply();
    }

    public static void savePref(Context context, String key, boolean value){
        getPrefEditor(context).putBoolean(key, value).apply();
    }

    public static void savePref(Context context, String key, long value){
        getPrefEditor(context).putLong(key, value).apply();
    }

    public static void savePref(Context context, String key, float value){
        getPrefEditor(context).putFloat(key, value).apply();
    }

    public static void savePref(Context context, String key, HashSet<String> value){
        Set<String> set = new HashSet<>();
        set.add(String.valueOf(value.size()+1));
        getPrefEditor(context).putStringSet(key, set);
    }

    public static String getStringPref(Context context, String key){
        return getPref(context).getString(key, "");
    }

    public static String getStringPref(Context context, String key, String defaultValue){
        return getPref(context).getString(key, defaultValue);
    }

    public static int getIntPref(Context context, String key){
        return getPref(context).getInt(key, 0);
    }

    public static int getIntPref(Context context, String key, int defaultValue){
        return getPref(context).getInt(key, defaultValue);
    }

    public static float getFloatPref(Context context, String key){
        return getPref(context).getFloat(key, 0F);
    }

    public static float getFloatPref(Context context, String key, float defaultValue){
        return getPref(context).getFloat(key, defaultValue);
    }

    public static long getLongPref(Context context, String key){
        return getPref(context).getLong(key, 0L);
    }

    public static long getLongPref(Context context, String key, long defaultValue){
        return getPref(context).getLong(key, defaultValue);
    }

    public static boolean getBooleanPref(Context context, String key){
        return getPref(context).getBoolean(key, false);
    }

    public static boolean getBooleanPref(Context context, String key, boolean defaultValue){
        return getPref(context).getBoolean(key, defaultValue);
    }

    public static HashSet<String> getSetPref(Context context, String key){
        return (HashSet<String>) getPref(context).getStringSet(key, new HashSet<>());
    }
}
