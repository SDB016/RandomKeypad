package com.otk.fts.myotk.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.otk.fts.myotk.R;
import com.otk.fts.myotk.receiver.StartupReceiver;
import com.otk.fts.myotk.utils.ActivityUtil;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;
import com.otk.fts.myotk.utils.PermissionManager;
import com.otk.fts.myotk.utils.Utils;

import java.util.logging.LogManager;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends Activity{
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;


    private boolean firstchk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                // 기본 잠금화면보다 우선출력
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        // 기본 잠금화면 해제시키기
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        requestPermission(REQUEST_CODE_FOR_PERMISSION_START);

        if(checkPermission()){
            check();
        }

    }

    private void xiaomichk(){
        String manufacturer = "xiaomi";

        if(manufacturer.equalsIgnoreCase(Build.MANUFACTURER)){
            Intent intent1 = new Intent();
            intent1.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            startActivity(intent1);
        }
    }

    private void check(){
        boolean isRun = PreferenceUtil.getBooleanPref(this, PreferenceUtil.FIRST_BOOT, false);
        firstchk = isRun;
        //Toast.makeText(this, "First Boot="+isRun, Toast.LENGTH_SHORT).show();

        if(!isRun) {
            //QLog.d("First Boot!");
            Log.d("Inhyo Test", "check No First");
            //xiaomichk();

            //PreferenceUtil.savePref(this, PreferenceUtil.FIRST_BOOT, true);
            ActivityUtil.move(this, FirstPwActivity.class, true);

        }else{
            Log.d("Inhyo Test", "chechk First");
            //ActivityUtil.moveTop(this, LockScreenActivity.class, true);
            ActivityUtil.moveTop(this, beforeSettingActivity.class, true);
            finishAffinity();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean checkPermission() {
        Log.d("Inhyo Test", "checkPermission");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 마시멜로우 이상일 경우
            if (!Settings.canDrawOverlays(this)) {
                Log.d("Inhyo Test", "canDrawOverlays()");
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
                //Toast.makeText(this, "canDrawOverlays = false", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                //Toast.makeText(this, "canDrawOverlays = true", Toast.LENGTH_SHORT).show();
                Log.d("Inhyo Test", "checkPermission() 1 - true");
                return true;
            }
        } else {
            Log.d("Inhyo Test", "checkPermission() 2 - true");
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Inhyo Test", "onActivityResult()");
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                Log.d("Inhyo Test", "onActivityResult() 1 - false");
                //앱을 사용할수 없음
                //Toast.makeText(this, "권한 동의를 안하면 앱을 사용할수 없습니다.", Toast.LENGTH_SHORT).show();
                finish();

            } else {
                Log.d("Inhyo Test", "onActivityResult() 2 - true");
                //requestPermission(REQUEST_CODE_FOR_PERMISSION_START);
                check();
/*
                if (!Utils.isServiceRunning(MainActivity.this)) {
                    Utils.startService(getApplicationContext());
                } else {
                    ActivityUtil.move(MainActivity.this, LockScreenActivity.class, true);
                }
  */
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


    //퍼미션 체크를 위한 requestCode 상수 정의
    private final static int REQUEST_PERMISSION_SETTINGS = 200;
    private final static int REQUEST_CODE_FOR_PERMISSION_START = 100;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case REQUEST_CODE_FOR_PERMISSION_START:
            {
                //LogManager.printRed("[PermissionManager] [requestPermission] onRequestPermissions : " + grantResults, new Exception());
                if (grantResults.length > 0) {
                    Log.d("Inhyo Test", "[PermissionManager] grantResults.length :" + grantResults.length);
                    int cnt = 0;
                    boolean bAllGreen = true;
                    for (int grantResult : grantResults) {
                        Log.d("Inhyo Test", "[PermissionManager] [requestPermission] 성공 :" + permissions[cnt++]);
                        Log.d("Inhyo Test", "[PermissionManager] [requestPermission] 성공 :" + grantResult);
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {

                            bAllGreen = false;
                            break;
                        }
                    }

                    if (bAllGreen) {
                        Log.d("Inhyo Test", "[PermissionManager] [requestPermission] 성공 :" + new Exception());
                        //LogManager.printRed("[PermissionManager] [requestPermission] 성공 : ", new Exception());
                        //mCLodingHandler = new LodingHandler(1200);
                        if(checkPermission()){

                            //2. 첫번째 실행 체크
                            check();
                        } else {
                            //check();
                        }

                    } else {
                        //LogManager.printRed("[PermissionManager] [requestPermission] 거부 : ", new Exception());

                        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
                        alert_confirm.setMessage("앱 설정 화면에서 앱 권한을 모두 허용해 주셔야 서비스 이용이 가능합니다.")
                                .setCancelable(false)
                                .setPositiveButton("설정하기", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 'YES'
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                                        intent.setData(uri);
                                        startActivityForResult(intent, REQUEST_PERMISSION_SETTINGS);
                                    }
                                }).setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'No'
                                finish();
                            }
                        });
                        AlertDialog alert = alert_confirm.create();
                        alert.show();
                    }

                }
            }
            break;

        }
    }
}
