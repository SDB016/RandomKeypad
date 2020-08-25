package com.otk.fts.myotk.utils;

import android.app.Activity;
import android.content.Intent;

public class ActivityUtil{

    public static void moveTop(Activity activity, Class cls) {
        move(activity, cls, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

    public static void moveTop(Activity activity, Class cls, boolean isFinish) {
        move(activity, null, cls, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP, isFinish);
    }

    public static void moveTop(Activity activity, Intent intent, boolean isFinish) {
        move(activity, intent, null, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP, isFinish);
    }

    public static void move(Activity activity, Class cls) {
        move(activity,null, cls, 0, false);
    }

    public static void move(Activity activity,Class cls, int flag) {
        move(activity, null, cls, flag, false);
    }

    public static void move(Activity activity, Class cls, boolean isFinish) {
        move(activity, null, cls, 0, isFinish);
    }

    public static void moveIntentString(Activity activity, Class cls, String key, String value){
        Intent intent = new Intent(activity, cls);
        intent.putExtra(key, value);
        move(activity, intent, cls, 0, false);
    }

    public static void move(Activity activity, Intent intent, Class cls, int flag, boolean isFinish) {
        Intent mIntent = intent == null ? new Intent(activity, cls) : intent;
        if (flag > 0) mIntent.setFlags(flag);
        if (isFinish) activity.finish();
        activity.startActivity(mIntent);
    }
}
