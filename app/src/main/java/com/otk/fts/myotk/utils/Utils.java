package com.otk.fts.myotk.utils;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.otk.fts.myotk.services.LockScreenService;

public class Utils {
    public static void startService(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, LockScreenService.class));
        } else {
            context.startService(new Intent(context, LockScreenService.class));
        }
    }

    public static void stopService(Context context){
        context.stopService(new Intent(context, LockScreenService.class));
    }

    /**
     *서비스가 실행중인지 확인
     */
    public static boolean isServiceRunning(Context context) {
        String serviceName = "com.otk.fts.myotk.services.LockScreenService";
        ActivityManager manager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
