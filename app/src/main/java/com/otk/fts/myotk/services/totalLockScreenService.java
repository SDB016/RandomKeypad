package com.otk.fts.myotk.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.otk.fts.myotk.receiver.OnLock_BroadcastReceiver;
import com.otk.fts.myotk.R;

public class totalLockScreenService extends Service {
    private BroadcastReceiver mReceiver;

    WindowManager wm;
    View mView;
    View lView;

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new OnLock_BroadcastReceiver();
        registerReceiver(mReceiver, filter);

        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        Log.d("View", "Total Service!");

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                /*
                /*ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                */
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        |WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        |WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        |WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER;
        mView = inflate.inflate(R.layout.prelockscreen, null);
        lView = inflate.inflate(R.layout.activity_main, null);
        LinearLayout bg_view = (LinearLayout)mView.findViewById(R.id.bg_layout);


        int uiOption = bg_view.getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        bg_view.setSystemUiVisibility( uiOption );

        /*
        if(!versionCheck())
            bg_view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        */
        final ImageButton bt =  (ImageButton) mView.findViewById(R.id.btn_show);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("View", "View Disabled!");
                if(wm!=null) {
                    if(mView!=null) {
                        wm.removeViewImmediate(mView);
                        mView = null;
                    }
                    //wm=null;
                }
            }
        });


        wm.addView(lView, params);
        wm.addView(mView, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if( intent == null)
        {
            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            mReceiver = new OnLock_BroadcastReceiver();
            registerReceiver(mReceiver, filter);
        }
        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
        if(wm != null) {
            if(mView != null) {
                wm.removeViewImmediate(mView);
                mView = null;
            }
            if(lView!=null){
                wm.removeViewImmediate(lView);
                lView = null;
            }
            wm = null;
        }
        Log.d("Service", "Service Dead");

        // 서비스 다시 켜보기?
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean versionCheck() {
        return Build.VERSION.SDK_INT >= 11 ? true : false;
    }
}
