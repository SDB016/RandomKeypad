package com.otk.fts.myotk.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.ActivityUtil;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;

public class MainActivity extends Activity{
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                // 기본 잠금화면보다 우선출력
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                // 기본 잠금화면 해제시키기
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        setContentView(R.layout.temp);

        //1. 권한체크
        if(checkPermission()){

            //2. 첫번째 실행 체크
            check();
        }
    }

    private void check(){
        boolean isRun = PreferenceUtil.getBooleanPref(this, PreferenceUtil.FIRST_BOOT);
        if(!isRun) {
            //QLog.d("First Boot!");
            PreferenceUtil.savePref(this, PreferenceUtil.FIRST_BOOT, true);
            ActivityUtil.move(this, GuideActivity.class, true);
            //checkPermission();
        }else{
            //QLog.d("Not First Boot!");
            //checkPermission();

            if(PreferenceUtil.getBooleanPref(this, PreferenceUtil.IS_LOCK, true)){
                //잠금화면을 사용함
                ActivityUtil.moveTop(this, beforeSettingActivity.class, true);
            }

            else ActivityUtil.moveTop(this, SettingsActivity.class, true);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 마시멜로우 이상일 경우
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
                return false;
            } else {
                //startService(new Intent(MainActivity.this, totalLockScreenService.class));
                //startActivity(new Intent(MainActivity.this, preLockScreenActivity.class));
                //ActivityUtil.move(this, beforeSettingActivity.class, true);
                return true;
            }
        } else {
            //startService(new Intent(MainActivity.this, totalLockScreenService.class));
            //startActivity(new Intent(MainActivity.this, preLockScreenActivity.class));
            //check();
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {

                //앱을 사용할수 없음
                //Toast.makeText(this, "권한 동의를 안하면 앱을 사용할수 없습니다.", Toast.LENGTH_SHORT).show();
                finish();

            } else {
                check();

                //startService(new Intent(MainActivity.this, totalLockScreenService.class));
                //ActivityUtil.move(this, preLockScreenActivity.class, true);
                //ActivityUtil.move(this, GuideActivity.class, true);
            }
        }
    }
}
