package com.otk.fts.myotk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.ActivityUtil;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;
import com.otk.fts.myotk.utils.Utils;

public class preLockScreenActivity extends Activity {

    //private TextView txt;
//    private LinearLayout bg_view;
//    private ImageButton showBtn;
//    //private boolean visible;
//    private boolean isActive;

    private WindowManager wm;
    private View mView, decorView;
    private Handler handler;
    private ImageView iv;


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        //isFocus = hasFocus;
//        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
//        if (!hasMenuKey) {
//            //if (isFocus) {
//                decorView.setSystemUiVisibility(
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//            //}
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.prelockscreen);
        handler = new Handler(message -> false);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                //WindowManager.LayoutParams.FLAG_FULLSCREEN |
                //WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        //decorView = getWindow().getDecorView();
        if (!Utils.isServiceRunning(this)) {
            Utils.startService(getApplicationContext());
        }
        //else QLog.d("service is run");



        WindowManager.LayoutParams params;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT);
        } else {
            params = new WindowManager.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                            //|WindowManager.LayoutParams.FLAG_FULLSCREEN
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                            | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON,
                    //| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    //| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    //| WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    PixelFormat.TRANSLUCENT);
        }

        params.gravity = Gravity.CENTER;
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflate.inflate(R.layout.prelockscreen, null);
        mView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        //FrameLayout btnFrame = mView.findViewById(R.id.btnFrame);
        //ImageButton bt = mView.findViewById(R.id.btn_show);
        //btnFrame.setOnClickListener(onClickListener);
        //bt.setOnClickListener(onClickListener);
        iv = mView.findViewById(R.id.gifIv);
        wm.addView(mView, params);

       /* getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        decorView = getWindow().getDecorView();

        if(!Utils.isServiceRunning(this)){
            Utils.startService(this);

        }else{
            QLog.d("service is run");
        }

        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
//                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                        |WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                        |WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                PixelFormat.TRANSLUCENT);
        WindowManager.LayoutParams params;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT);
        } else {
//            params = new WindowManager.LayoutParams(
//                    WindowManager.LayoutParams.MATCH_PARENT,
//                    WindowManager.LayoutParams.MATCH_PARENT,
//                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
//                    PixelFormat.TRANSLUCENT);

            *//*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                flag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                flag = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
            }
​
            params = WindowManager.LayoutParams(
                    width,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    flag,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON,
                    PixelFormat.TRANSLUCENT)*//*


            params = new WindowManager.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            |WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            |WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                            //|WindowManager.LayoutParams.FLAG_FULLSCREEN
                            |WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                            |WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON,
                    //| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    //| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    //| WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    PixelFormat.TRANSLUCENT);
        }

        params.gravity = Gravity.CENTER;
        mView = inflate.inflate(R.layout.prelockscreen, null);

        mView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);


        LinearLayout bg_view = (LinearLayout)mView.findViewById(R.id.bg_layout);
        final FrameLayout btnFrame = (FrameLayout)mView.findViewById(R.id.btnFrame);
        final ImageButton bt =  (ImageButton) mView.findViewById(R.id.btn_show);

        //SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        boolean customBgImg = PreferenceUtil.getBooleanPref(this, PreferenceUtil.CUSTOM_BG);//sf.getBoolean("customBgImg", false);
        String customBgImgPath =  PreferenceUtil.getStringPref(this, PreferenceUtil.CUSTOM_BG_PATH);//sf.getString("pwImgPath","");
        isActive = PreferenceUtil.getBooleanPref(this, PreferenceUtil.IS_LOCK, true); //sf.getBoolean("isLock", true);

        if(customBgImg){
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap originalBm = BitmapFactory.decodeFile(customBgImgPath, options);
            Drawable drawable = new BitmapDrawable(originalBm);
            bg_view.setBackground(drawable);

        }else {
            //bg_layout.setBackgroundResource(R.drawable.sample_bg);
            bg_view.setBackgroundColor(getResources().getColor(android.R.color.black));
        }

        View.OnClickListener onClickListener = v -> {
            if(isActive) {
                Intent intent = new Intent(
                        preLockScreenActivity.this,//현재제어권자
                        LockScreenActivity.class); // 이동할 컴포넌트

                startActivity(intent);
                finish();

            }else {
                finishAffinity();
            }
        };

        btnFrame.setOnClickListener(onClickListener);
        bt.setOnClickListener(onClickListener);
        wm.addView(mView, params);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this).asGif().load(R.drawable.ani).into(iv);
        if(handler!=null)
        handler.postDelayed(() -> {
            if (PreferenceUtil.getBooleanPref(this, PreferenceUtil.IS_LOCK, true)) {
                ActivityUtil.move(preLockScreenActivity.this, LockScreenActivity.class, true);
                if (wm != null) {
                    if (mView != null) {
                        wm.removeViewImmediate(mView);
                        mView = null;
                    }
                    //wm=null;
                }

            } else finishAffinity();

        }, 1300);
    }

    @Override
    public void onDestroy() {
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if (wm != null) {
            if (mView != null) {
                wm.removeViewImmediate(mView);
                mView = null;
            }
            //wm=null;
        }
        super.onDestroy();
    }

   /* private void blink(){
        final Handler handler = new Handler();
        new Thread(() -> {
            int timeToBlink = 1000;    //in milissegunds
            try{Thread.sleep(timeToBlink);}catch (Exception e) {}
            handler.post(() -> {
                if(visible){
                    showBtn.setImageResource(R.drawable.touch_1);
                    //showBtn.setTextColor(getResources().getColor(R.color.c_ffffff));
                    visible=false;
                }else{
                    showBtn.setImageResource(R.drawable.touch_2);
                    //showBtn.setTextColor(00000000);
                    visible=true;
                }

                blink();
            });
        }).start();
    }*/
}
