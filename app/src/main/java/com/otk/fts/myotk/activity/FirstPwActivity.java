package com.otk.fts.myotk.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.otk.fts.myotk.R;
import com.otk.fts.myotk.setting.Popup_Password;
import com.otk.fts.myotk.setting.PwNBackupActivity;
import com.otk.fts.myotk.utils.ActivityUtil;
import com.otk.fts.myotk.utils.PermissionManager;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;
import com.otk.fts.myotk.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;

public class FirstPwActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_FOR_PERMISSION_START = 100;
    private final static int REQUEST_PW_INPUT = 200;
    private final static int REQUEST_BACKPIN_INPUT = 300;
    private final static int REQUEST_XIAOMI_SCREENLOCK = 400;
    private final static int REQUEST_XIAOMI_BACKSTART = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpw);

        requestPermission(REQUEST_CODE_FOR_PERMISSION_START);

        String manufacturer = "xiaomi";

        if(manufacturer.equalsIgnoreCase(Build.MANUFACTURER)){
            Intent intent = new Intent(FirstPwActivity.this, PopupXiaomiActivity.class);
            startActivityForResult(intent, REQUEST_XIAOMI_SCREENLOCK);
        } //else {
        InputPW();
        //}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_PW_INPUT) {
            if (resultCode == RESULT_OK) {
                InputBKPIN();
            }
        } else if(requestCode == REQUEST_BACKPIN_INPUT){
            if (resultCode == RESULT_OK) {
                if (!Utils.isServiceRunning(FirstPwActivity.this)) {
                    Utils.startService(getApplicationContext());
                } else QLog.d("service is run");
                PreferenceUtil.savePref(this, PreferenceUtil.FIRST_BOOT, true);
                ActivityUtil.move(FirstPwActivity.this, LockScreenActivity.class, true);
            }
        } else if(requestCode == REQUEST_XIAOMI_SCREENLOCK){
            if(resultCode == RESULT_OK){
                Intent intent = new Intent(FirstPwActivity.this, PopupXiaomiBackActivity.class);
                startActivityForResult(intent, REQUEST_XIAOMI_BACKSTART);
            }
        } else if(requestCode == REQUEST_XIAOMI_BACKSTART){
            if(resultCode == RESULT_OK){
                InputPW();
            }
        }
    }

    private boolean requestPermission( int requestCode ) {

        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.FOREGROUND_SERVICE,

                Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                Manifest.permission.VIBRATE,
                Manifest.permission.DISABLE_KEYGUARD,
                Manifest.permission.WAKE_LOCK,

                Manifest.permission.EXPAND_STATUS_BAR,
                Manifest.permission.CAMERA
        };

        return (PermissionManager.getInstance(this).checkPermissions(requestCode, permissions) == PackageManager.PERMISSION_GRANTED);
    }

    private void InputPW(){

        Intent intent = new Intent(FirstPwActivity.this, PopupFirstPwActivity.class);
        startActivityForResult(intent, REQUEST_PW_INPUT);

    }

    private void InputBKPIN(){
        Intent intent = new Intent(FirstPwActivity.this, PopupFirstBkpinActivity.class);
        startActivityForResult(intent, REQUEST_BACKPIN_INPUT);
    }
}
