package com.otk.fts.myotk.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.otk.fts.myotk.BuildConfig;


public class QLog {
    static final String TAG = "myotk";

    /** Log Level Error **/
    public static final void e(@NonNull String message) {
        if (BuildConfig.DEBUG) Log.e(TAG, buildLogMsg(message));
    }

    /** Log Level Warning **/
    public static final void w(@NonNull String message) {
        if (BuildConfig.DEBUG) Log.w(TAG, buildLogMsg(message));
    }

    /** Log Level Information **/
    public static final void i(@NonNull String message) {
        if (BuildConfig.DEBUG) Log.i(TAG, buildLogMsg(message));
    }

    /** Log Level Debug **/
    public static final void d(@NonNull String message) {
        if (BuildConfig.DEBUG) Log.d(TAG, buildLogMsg(message));
    }

    /** Log Level Verbose **/
    public static final void v(@NonNull String message) {
        if (BuildConfig.DEBUG) Log.v(TAG, buildLogMsg(message));
    }

    private static String buildLogMsg(@NonNull String message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
        StringBuilder sb = new StringBuilder();
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("_");
        sb.append(ste.getMethodName());
        sb.append("> ");
        sb.append(message);
        return sb.toString();
    }
}
