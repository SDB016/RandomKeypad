package com.otk.fts.myotk.receiver;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.otk.fts.myotk.activity.LockScreenActivity;
import com.otk.fts.myotk.services.LockScreenService;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;

import static android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;


public class OnLock_BroadcastReceiver extends BroadcastReceiver {
    private Context mContext;
    SharedPreferences pref;
    String get_state;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;

        pref = context.getSharedPreferences("pref", Activity.MODE_PRIVATE);
        get_state = pref.getString("state","");

        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
                //boolean isLock = PreferenceUtil.getBooleanPref(mContext, PreferenceUtil.LOCK_USING,false);
                Log.d("Inhyo Test", "ACTION_SCREEN_ON " + isPhoneIdle);
                if(!isPhoneIdle) {
                    show(context, true);
                    AlarmReceiverChk(context, intent);
                }
                break;

            case Intent.ACTION_BOOT_COMPLETED: {
                Log.d("Inhyo Test", "ACTION_BOOT_COMPLETED");
                show(context, true);
                break;
            }
            case Intent.ACTION_SCREEN_OFF:
                //context.stopService(intent);
                Log.d("Inhyo Test", "ACTION_SCREEN_OFF");
                break;

            case Intent.ACTION_SHUTDOWN:
                Log.d("Inhyo Test", "SHUTDOWN----------------->");


                show(context, true);
                break;
            case Intent.ACTION_REBOOT:
                Log.d("Inhyo Test", "REBOOT -------------------->");
                break;
            case Intent.ACTION_PACKAGE_ADDED:
                Log.d("Inhyo Test", "ADDED -------------------->");
                sendmail();
                break;
            case Intent.ACTION_MY_PACKAGE_REPLACED:
                Log.d("Inhyo Test", "ACTION_MY_PACKAGE_REPLACED -------------------->");
                show(context, true);
                break;

            case Intent.ACTION_POWER_DISCONNECTED:
                Log.d("Inhyo Test", "ACTION_POWER_DISCONNECTED ");
                break;

            case Intent.ACTION_DREAMING_STARTED:
                Log.d("Inhyo Test", "ACTION_DREAMING_STARTED ");
                break;

            case Intent.ACTION_DREAMING_STOPPED:
                Log.d("Inhyo Test", "ACTION_DREAMING_STOPPED ");
                break;
        }
    }

    private void show(Context context, boolean screenOnOff) {
        if(PreferenceUtil.getBooleanPref(context, PreferenceUtil.IS_LOCK, true)){
            if(screenOnOff){
                if (telephonyManager == null){
                    telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager != null) {
                        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
                    }
                }

            }

            Log.d("Inhyo Test", "alarmManager- IS_LOCK -- true");

            Intent i = new Intent(context, LockScreenActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            context.stopService(i);
            context.startActivity(i);


        }
        Log.d("Inhyo Test", "alarmManager- IS_LOCK -- false");
    }

    private TelephonyManager telephonyManager = null;
    private AlarmManager alarmManager = null;
    private boolean isFirst = false;
    boolean isPhoneIdle;

    private PhoneStateListener phoneListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    QLog.d("CALL_STATE_IDLE");

                    //잠금화면이 실행중이였던 상태체크
                    boolean isLock = PreferenceUtil.getBooleanPref(mContext, PreferenceUtil.LOCK_USING,false);
                    QLog.d("isFirst : " + isFirst);
                    QLog.d("isLock : " + isLock);
                    if(isFirst && isLock){
                        PreferenceUtil.savePref(mContext, PreferenceUtil.LOCK_USING, false);
                        show(mContext, false);
                    }
                    isPhoneIdle = false;
                    isFirst = true;
                    break;

                case TelephonyManager.CALL_STATE_RINGING:
                    isPhoneIdle = true;
                    //QLog.d("CALL_STATE_RINGING");
                    break;

                    //전화를 걸거나 받았을때
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    isPhoneIdle = true;
                    QLog.d("CALL_STATE_OFFHOOK");
                    break;


            }
        }
    };

    private BroadcastReceiver mTimeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Inhyo Test","mTimeReceiver --> ");
        }
    };

    private void AlarmReceiverChk(final Context context, final Intent intent){
        switch (get_state){
            case "ALARM_ON":

        }
    }

    private void sendmail(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("plain/Text");

    }

    private void chkAlarm(){
        boolean isLock = PreferenceUtil.getBooleanPref(mContext, PreferenceUtil.LOCK_USING,false);
        Log.d("Inhyo Test","chkAlarm --> " + isLock);


        if(!isLock){

            Intent i = new Intent(mContext, LockScreenActivity.class);
            Log.d("Inhyo Test","chkAlarm --> startActivity = " + isLock);

            mContext.stopService(i);
/*
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d("Inhyo Test","chkAlarm --> startForegroundService");

                Intent in = new Intent(mContext, LockScreenService.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(in);
            } else {
                Intent in = new Intent(mContext, LockScreenService.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //mContext.startService(in);
                mContext.startActivity(in);
            }*/
            show(mContext, false);

        }
    }


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
