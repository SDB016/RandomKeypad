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
    public final static String IS_LOCK = "isLock";              // 화면 잠금
    public final static String PW_SIZE = "pwSize";              // 패스워드 길이
    public final static String PW_LIST = "pwList";              // 패스워드 1
    public final static String PW_LIST1 = "pwList1";              // 패스워드 1
    public final static String PW_LIST2 = "pwList2";            // 패스워드 2
    public final static String PW_LIST3 = "pwList3";              // 패스워드 3
    public final static String PW_LIST4 = "pwList4";            // 패스워드 4
    public final static String BACKUP_PIN = "backupPin";        // 백업핀
    public final static String PW_TIMER = "pwTimer";            // 키패드 동작 시
    public final static String NUM_TYPE = "numType";            // 랜덤 키패드
    public final static String BTN_TYPE = "btnType";            // Null 키패드
    public final static String FIRST_BOOT2 = "firstBoot2";
    public final static String LOCK_USING = "lockUsing";
    public final static String SHOW_LEFT = "showLeft";          // R2N 키패드 동작 시간

    public final static String PW_CNT = "pwCount";             // 패스워드 입력 시도 제한
    public final static String DIRECT_TYPE = "directIcon";  // 바로가기 아이콘

    //public final static String POWER_OFF = "powerOff";         //전원 끄
    //public final static String PHONE_GPS = "phoneGps";          // GPS 보내기
    //public final static String DISPLAY_TIMER = "displayTimer";  // 널 화면 시
    //public final static String R2NICON_TYPE = "r2nIconType";    //
    //public final static String R2NICON_PATH = "r2nIconPath";

    public final static String NULLICON_TYPE = "nullIconType";    //
    public final static String NULLICON_PATH = "nullIconPath";

    public final static String RANDOMICON_TYPE = "randomIconType";    //
    public final static String RANDOMICON_PATH = "randomIconPath";

    public final static String DIRECT_ICON_TYPE = "directIconType";  ///

    public final static String BASIC_BACKGROUND = "basicBackground";  //
    public final static String BACKGROUND_TYPE = "backgroundType";
    public final static String BACKGROUND_SELECT = "backgroundSelect";
    //public final static String BG_SCREEN_TIME = "bgScreenTime";

    public final static String BG_INPUT_EMAIL = "bgInputEmail";
    //public final static String FP_USED = "fpUsed";

    public final static String PHOTO_PATH_01 = "photoPath01";
    public final static String PHOTO_PATH_02 = "photoPath02";
    public final static String PHOTO_PATH_03 = "photoPath03";
    public final static String PHOTO_PATH_04 = "photoPath04";
    public final static String PHOTO_PATH_05 = "photoPath05";
    public final static String PHOTO_PATH_06 = "photoPath06";
/*
    public final static String GIF_PATH_01 = "gifPath01";
    public final static String GIF_PATH_02 = "gifPath02";
    public final static String GIF_PATH_03 = "gifPath03";
    public final static String GIF_PATH_04 = "gifPath04";
    public final static String GIF_PATH_05 = "gifPath05";
    public final static String GIF_PATH_06 = "gifPath06";

    public final static String SCREEN_PATH_01 = "screenPath01";
    public final static String SCREEN_PATH_02 = "screenPath02";
    public final static String SCREEN_PATH_03 = "screenPath03";
    public final static String SCREEN_PATH_04 = "screenPath04";
    public final static String SCREEN_PATH_05 = "screenPath05";
    public final static String SCREEN_PATH_06 = "screenPath06";*/

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
