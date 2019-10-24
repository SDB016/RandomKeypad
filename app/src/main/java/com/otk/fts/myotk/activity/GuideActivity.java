package com.otk.fts.myotk.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.ActivityUtil;
import com.otk.fts.myotk.utils.QLog;
import com.otk.fts.myotk.utils.Utils;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.okBt:
                ActivityUtil.move(this, SettingsActivity.class, true);
                break;

            case R.id.noBt:
                //TedPermission 라이브러리 -> 카메라 권한 획득
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        //Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                        //ActivityUtil.move(this, preLockScreenActivity.class, true);
                        if (!Utils.isServiceRunning(GuideActivity.this)) {
                            Utils.startService(getApplicationContext());
                        } else QLog.d("service is run");
                        ActivityUtil.move(GuideActivity.this, LockScreenActivity.class, true);
                    }
                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        //Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                        finishAffinity();
                    }
                };

                TedPermission.with(this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission] ")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .check();
                break;
        }
    }
}
