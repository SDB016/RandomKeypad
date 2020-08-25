package com.otk.fts.myotk.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.ActivityUtil;
import com.otk.fts.myotk.utils.PermissionManager;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;
import com.otk.fts.myotk.utils.Utils;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;
    private final static int REQUEST_PERMISSION_SETTINGS = 200;
    private final static int REQUEST_CODE_FOR_PERMISSION_START = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        requestPermission(REQUEST_CODE_FOR_PERMISSION_START);
    }

    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.okBt:
                //checkPermission();
                if (!Utils.isServiceRunning(GuideActivity.this)) {
                    Utils.startService(getApplicationContext());
                } else QLog.d("service is run");
                ActivityUtil.move(this, Settings2Activity.class, true);
                break;

            case R.id.noBt:
                //checkPermission();
                if (!Utils.isServiceRunning(GuideActivity.this)) {
                    Utils.startService(getApplicationContext());
                } else QLog.d("service is run");

                ActivityUtil.move(GuideActivity.this, LockScreenActivity.class, true);
                break;
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

/*
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
                           // check();
                        } else {
                           // check();
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
    }*/


}
