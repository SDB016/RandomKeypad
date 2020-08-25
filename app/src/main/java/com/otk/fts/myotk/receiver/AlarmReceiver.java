package com.otk.fts.myotk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.otk.fts.myotk.services.LockScreenService;
import com.otk.fts.myotk.utils.PreferenceUtil;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {



        Log.d("Inhyo Test","AlarmReceiver----------------------------------->");

        PreferenceUtil.savePref(context, PreferenceUtil.LOCK_USING, true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent in = new Intent(context, LockScreenService.class);
            context.startForegroundService(in);
        } else {
            Intent in = new Intent(context, LockScreenService.class);
            context.startService(in);
        }
    }
}


