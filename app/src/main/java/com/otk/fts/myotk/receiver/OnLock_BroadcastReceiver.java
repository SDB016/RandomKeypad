package com.otk.fts.myotk.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.otk.fts.myotk.activity.LockScreenActivity;
import com.otk.fts.myotk.activity.preLockScreenActivity;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class OnLock_BroadcastReceiver extends BroadcastReceiver {


    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
                Log.d("aaa", "ACTION_SCREEN_ON");
                show(context);
                break;

            case Intent.ACTION_BOOT_COMPLETED: {
                Log.d("aaa", "ACTION_SCREEN_OFF");
                show(context);
                break;
            }
            case Intent.ACTION_SCREEN_OFF:
                Log.d("aaa", "ACTION_SCREEN_OFF");
                //show(context);
                //Intent i = new Intent(context, LockScreenService.class);
                //context.startService(i);

//                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
//                try {
//                    pendingIntent.send();
//                } catch (PendingIntent.CanceledException e) {
//                    e.printStackTrace();
//                }

                /*if (telephonyManager == null) {
                    telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
                }

                if (isPhoneIdle) {
                    Intent i = new Intent(context, LockScreenService.class);
                    //context.stopService(i);

                }*/
                break;
        }
    }

    private void show(Context context) {
        if(PreferenceUtil.getBooleanPref(context, PreferenceUtil.IS_LOCK, true)){
            if (telephonyManager == null) {
                telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
            }

            //Intent i = new Intent(context, preLockScreenActivity.class);
            Intent i = new Intent(context, LockScreenActivity.class);
            //i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            context.startActivity(i);
        }
    }

    private TelephonyManager telephonyManager = null;
    private boolean isFirst = false;

    private PhoneStateListener phoneListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:

                    //잠금화면이 실행중이였던 상태체
                    if(isFirst && PreferenceUtil.getBooleanPref(mContext,PreferenceUtil.LOCK_USING,false)){
                        PreferenceUtil.savePref(mContext, PreferenceUtil.LOCK_USING, false);
                        show(mContext);
                    }
                    isFirst = true;
                    QLog.d("CALL_STATE_IDLE");
                    break;

                case TelephonyManager.CALL_STATE_RINGING:
                    //isPhoneIdle = false;
                    //QLog.d("CALL_STATE_RINGING");
                    break;

                    //전화를 걸거나 받았을때
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //isPhoneIdle = false;
                    QLog.d("CALL_STATE_OFFHOOK");
                    //show(mContext);
                    break;
            }
        }
    };

    private void getAllAct(){
        try {
            ActivityInfo[] list = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES).activities;
            for (ActivityInfo activityInfo : list) {
                QLog.d("List of running activities" + activityInfo.name);
            }
        }

        catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
