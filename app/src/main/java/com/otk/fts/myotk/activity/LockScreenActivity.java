package com.otk.fts.myotk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.otk.fts.myotk.services.LockScreenService;
import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.ActivityUtil;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;
import com.otk.fts.myotk.utils.StateDrawableBuilder;
import com.otk.fts.myotk.utils.Utils;

import java.util.ArrayList;
import java.util.Random;

public class LockScreenActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    private boolean isActive;

    private Context mContext;
    private Button mBtnButton1;
    private Button mBtnButton2;
    private Button mBtnButton3;
    private Button mBtnButton4;
    private Button mBtnButton5;
    private Button mBtnButton6;
    private Button mBtnButton7;
    private Button mBtnButton8;
    private Button mBtnButton9;
    private Button mBtnButton10;
    private Button mBtnButton11;
    private Button mBtnButton12;
    private ImageButton mBtnShow;
    private ImageButton mBtnDel;

    //private Button txtInput0;
    //private Button txtInput1;
    //private Button txtInput2;
    //private Button txtInput3;

    private ImageView img_Input;
    private Integer numType;
    private Integer btnType;

    private LinearLayout bottomLl;

    // Custom 버튼 Drawable
    private Drawable custom_btn_drawable;

    // PassWord Size
    private int pwSize;

    // PassWord List
    private ArrayList pw;
    // 입력 번호 List
    public ArrayList<Integer> input;
    // 키패드 순서 List
    private ArrayList<Integer> pos;

    // 초기 비밀번호 보이는 시간
    private Integer f_timer;

    boolean isStart = false;

    Vibrator vibrator;
    private Integer wrongCount;
    private Integer wrongTrigger;
    private Integer wrong_lockTimer;

    private String backupPin;

    private WindowManager wm;
    private View mView;
    private Handler handler;
    private Resources res;

    //private ImageView iv;
    //private FrameLayout fr;
//
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        //isFocus = hasFocus;
//        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
//        if (!hasMenuKey) {
//            //if (isFocus) {
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//            //}
//        }
//    }

    private TelephonyManager telephonyManager = null;
    private boolean isPhoneIdle = true;

    private PhoneStateListener phoneListener = new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String incomingNumber){

            switch(state){
                case TelephonyManager.CALL_STATE_IDLE :
                    //isPhoneIdle = true;
                    //QLog.d("CALL_STATE_IDLE");
                    break;

                    //전화가 울릴때 화면 종료
                case TelephonyManager.CALL_STATE_RINGING :
                    isPhoneIdle = false;
                    QLog.d("CALL_STATE_RINGING");
                    PreferenceUtil.savePref(LockScreenActivity.this, PreferenceUtil.LOCK_USING, true);
                    Utils.stopService(LockScreenActivity.this);
                    finishAffinity();
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK :
                    //isPhoneIdle = false;
                    //QLog.d("CALL_STATE_OFFHOOK");
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        QLog.d("onKeyDown");
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // 여기에 뒤로가기 버튼을 눌렀을 때 행동 입력
                return false;

            case KeyEvent.KEYCODE_HOME:
                // 여기에 홈 버튼을 눌렀을 때 행동 입력
                QLog.d("KEYCODE_HOME");
                btnShow();
                return false;
            case KeyEvent.KEYCODE_MENU:
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onUserLeaveHint(){
        super.onUserLeaveHint();
        QLog.d("KEYCODE_HOME");
        return;
    }

    /*public boolean onKeyDown(int keyCode, KeyEvent event) {
        QLog.d("onKeyDown");
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // 여기에 뒤로가기 버튼을 눌렀을 때 행동 입력
                return false;

            case KeyEvent.KEYCODE_HOME:
                // 여기에 홈 버튼을 눌렀을 때 행동 입력
                QLog.d("KEYCODE_HOME");
                btnShow();
                return false;
            case KeyEvent.KEYCODE_MENU:
                return false;
        }

        return super.onKeyDown(keyCode, event);

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(msg -> false);
        res = getResources();
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        getWindow().addFlags(
                // 기본 잠금화면보다 우선출력
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        // 기본 잠금화면 해제시키기
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        //decorView = getWindow().getDecorView();

        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

        if(!Utils.isServiceRunning(this)){
            Utils.startService(getApplicationContext());
        }

        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

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

        mView = inflate.inflate(R.layout.activity_lock, null);
        //iv = mView.findViewById(R.id.gifIv);
        //fr = mView.findViewById(R.id.fr);
        mView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        ConstraintLayout bg_screen = mView.findViewById(R.id.Linear_bg);
        bottomLl = mView.findViewById((R.id.bottomLl));
/*
        int uiOption = bg_screen.getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        bg_screen.setSystemUiVisibility( uiOption );
*/
        mContext = getApplicationContext();
        mBtnButton1 = (Button) mView.findViewById(R.id.button1);
        mBtnButton1.setOnClickListener(this);
        mBtnButton2 = (Button) mView.findViewById(R.id.button2);
        mBtnButton2.setOnClickListener(this);
        mBtnButton3 = (Button) mView.findViewById(R.id.button3);
        mBtnButton3.setOnClickListener(this);
        mBtnButton4 = (Button) mView.findViewById(R.id.button4);
        mBtnButton4.setOnClickListener(this);
        mBtnButton5 = (Button) mView.findViewById(R.id.button5);
        mBtnButton5.setOnClickListener(this);
        mBtnButton6 = (Button) mView.findViewById(R.id.button6);
        mBtnButton6.setOnClickListener(this);
        mBtnButton7 = (Button) mView.findViewById(R.id.button7);
        mBtnButton7.setOnClickListener(this);
        mBtnButton8 = (Button) mView.findViewById(R.id.button8);
        mBtnButton8.setOnClickListener(this);
        mBtnButton9 = (Button) mView.findViewById(R.id.button9);
        mBtnButton9.setOnClickListener(this);
        mBtnButton10 = (Button) mView.findViewById(R.id.button10);
        mBtnButton10.setOnClickListener(this);
        mBtnButton11 = (Button) mView.findViewById(R.id.button11);
        mBtnButton11.setOnClickListener(this);
        mBtnButton12 = (Button) mView.findViewById(R.id.button12);
        mBtnButton12.setOnClickListener(this);
        mBtnShow = (ImageButton) mView.findViewById(R.id.btn_show);
        mBtnShow.setOnTouchListener(this);

        img_Input = (ImageView)mView.findViewById(R.id.input_img);
        mBtnDel = (ImageButton) mView.findViewById((R.id.btn_del));
        mBtnDel.setOnTouchListener(this);

        isActive = PreferenceUtil.getBooleanPref(this, PreferenceUtil.IS_LOCK, true);
        pwSize = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_SIZE, 2);
        String lpwStr = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST, "00");//sf.getInt("pwList", 0);
        int lpw = Integer.parseInt(lpwStr);
        backupPin = PreferenceUtil.getStringPref(this, PreferenceUtil.BACKUP_PIN, "0000");//sf.getInt("backupPin", 1234);
        f_timer = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_TIMER, 2000);//sf.getInt("pwTimer", 2000);
        numType = PreferenceUtil.getIntPref(this, PreferenceUtil.NUM_TYPE, 0);//sf.getInt("numType", 3);
        btnType = PreferenceUtil.getIntPref(this, PreferenceUtil.BTN_TYPE, 0);//sf.getInt("btnType", 3);

        QLog.d(numType+"/"+btnType);
        // Custom 버튼 이미지 여부
        boolean customBgImg = PreferenceUtil.getBooleanPref(this, PreferenceUtil.CUSTOM_BG, false);//sf.getBoolean("customBgImg", false);
        // Custom 버튼 이미지 경로
        String customBgImgPath = PreferenceUtil.getStringPref(this, PreferenceUtil.CUSTOM_BG_PATH);//sf.getString("pwImgPath","");

        if(customBgImg){
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap originalBm = BitmapFactory.decodeFile(customBgImgPath, options);
            Drawable drawable = new BitmapDrawable(originalBm);
            //drawable.setAlpha(30);
            bg_screen.setBackground(drawable);

        }else {
            //Drawable originbg = res.getDrawable(R.drawable.sample_bg);
            //originbg.setAlpha(30);
            //bg_screen.setBackgroundResource(R.drawable.sample_bg);
            //bg_screen.setBackgroundColor(res.getColor(android.R.color.black));
            bg_screen.setBackground(res.getDrawable(R.drawable.background_1));
        }

        // 비밀번호 배열 ( 임시비번 = 12 )
        pw = new ArrayList();
        if(lpwStr.equals("00")) {
            pw.add(0);
            pw.add(0);
            img_Input.setImageResource(R.drawable.input_2pw0);
        }else{
            if(pwSize==2){
                // 비밀번호 사이즈 2
                int a = lpw/10;
                int b = lpw%10;
                pw.add(a);
                pw.add(b);
                img_Input.setImageResource(R.drawable.input_2pw0);
            }else{
                // 비밀번호 사이즈 4
                int a = lpw/1000;
                int b = (lpw%1000)/100;
                int c = (lpw%100)/10;
                int d = lpw%10;
                pw.add(a);
                pw.add(b);
                pw.add(c);
                pw.add(d);
                img_Input.setImageResource(R.drawable.input_4pw0);
            }
        }

        // 입력받을 배열 생성
        input = new ArrayList<>();

        // 키패드 위치 배열
        pos = new ArrayList<>();
        pos.add(0);
        pos.add(1);
        pos.add(2);
        pos.add(3);
        pos.add(4);
        pos.add(5);
        pos.add(6);
        pos.add(7);
        pos.add(8);
        pos.add(9);
        pos.add(10);
        pos.add(11);

        wrongCount = 0;
        wrongTrigger = 0;

        //lockHomeButton();
        wm.addView(mView, params);


        // 테스트 버전
        //wrong_lockTimer = 100;
        // 실제 버전
        wrong_lockTimer = 10000;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(isActive) {
            wrongCount = 0;
            wrongTrigger = 0;
            bottomLl.setVisibility(View.INVISIBLE);
            shuffleKeyPad(pos);
            isStart = false;

            run();
        } else finishAffinity();
    }

    private void run(){
        if(f_timer == 0){
            btnUnshow();
            bottomLl.setVisibility(View.VISIBLE);
        }

        else{
            btnShow();
            btnsEnable();

            handler.postDelayed(() -> {
                btnUnshow();
                //btnsEnable();
                bottomLl.setVisibility(View.VISIBLE);
            }, f_timer);    //2초 뒤에
        }
    }

   private void btnsDisable(){
        mBtnButton1.setEnabled(false);
        mBtnButton2.setEnabled(false);
        mBtnButton3.setEnabled(false);
        mBtnButton4.setEnabled(false);
        mBtnButton5.setEnabled(false);
        mBtnButton6.setEnabled(false);
        mBtnButton7.setEnabled(false);
        mBtnButton8.setEnabled(false);
        mBtnButton9.setEnabled(false);
        mBtnButton10.setEnabled(false);
        mBtnButton11.setEnabled(false);
        mBtnButton12.setEnabled(false);
        mBtnShow.setEnabled(false);
        mBtnDel.setEnabled(false);
    }

    private void btnsEnable(){
        mBtnButton1.setEnabled(true);
        mBtnButton2.setEnabled(true);
        mBtnButton3.setEnabled(true);
        mBtnButton4.setEnabled(true);
        mBtnButton5.setEnabled(true);
        mBtnButton6.setEnabled(true);
        mBtnButton7.setEnabled(true);
        mBtnButton8.setEnabled(true);
        mBtnButton9.setEnabled(true);
        mBtnButton10.setEnabled(true);
        mBtnButton11.setEnabled(true);
        mBtnButton12.setEnabled(true);
        mBtnShow.setEnabled(true);
        mBtnDel.setEnabled(true);
    }

    private void CheckPW() {
        if(input.size()==pw.size()){
            int index = 0;
            int backupPinNum = Integer.parseInt(backupPin);

            while(index<input.size()){
                if(input.get(index)!=pw.get(index)) {
                    vibrator.vibrate(100);
                    wrongCount += 1;
                    wrongTrigger += 1;
                    if(wrongTrigger>=5&&wrongCount>=55) {
                        // PhoneLockForSeconds();

                    }else if(wrongTrigger>=5&&wrongCount>=50){
                        // PhoneLockForSeconds();
                        wrongTrigger=0;
                        pw.clear();
                        int a = backupPinNum/1000;
                        int b = (backupPinNum%1000)/100;
                        int c = (backupPinNum%100)/10;
                        int d = backupPinNum%10;
                        pw.add(a);
                        pw.add(b);
                        pw.add(c);
                        pw.add(d);
                        pwSize = 4;
                        img_Input.setImageResource(R.drawable.input_4pw0);
                        PreferenceUtil.savePref(this, PreferenceUtil.PW_SIZE, pwSize);
                        PreferenceUtil.savePref(this, PreferenceUtil.PW_LIST, backupPin);

                        btnsDisable();
                        handler.postDelayed(() -> {
                            btnUnshow();
                            btnsEnable();
                        }, wrong_lockTimer);    //10초 뒤에
                    } else if(wrongTrigger>=5){
                        // PhoneLockForSeconds();
                        btnsDisable();
                        wrongTrigger=0;
                        handler.postDelayed(() -> {
                            btnUnshow();
                            btnsEnable();
                        }, wrong_lockTimer);    //10초 뒤에
                    } else {
                        /*
                        Toast toast = Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                        */
                    }
                    handler.postDelayed(() -> {
                        Incorrect();
                        input.clear();
                        enterInput();
                    }, 200);    //0.2초 뒤에
                    return;
                }
                index++;
            }
            handler.postDelayed(this::Unlock, 200);    //0.2초 뒤에
        }

    }

    private void Unlock(){
        //wm.removeViewImmediate(mView);
        finishAffinity();
    }

    private void Incorrect(){
        if(pwSize==2) {
            img_Input.setImageResource(R.drawable.input_2pw0);
        }else if(pwSize==4){
            img_Input.setImageResource(R.drawable.input_4pw0);
        }

        shuffleKeyPad(pos);
    }

    private void enterInput() {

        if(pwSize==2) {
            if (input.size() == 1) {
                img_Input.setImageResource(R.drawable.input_2pw1);
            } else if (input.size() == 2) {
                img_Input.setImageResource(R.drawable.input_2pw2);
            } else {
                img_Input.setImageResource(R.drawable.input_2pw0);
            }
        }else {
            switch (input.size()) {
                case 0:
                    img_Input.setImageResource(R.drawable.input_4pw0);
                    break;
                case 1:
                    img_Input.setImageResource(R.drawable.input_4pw1);
                    break;
                case 2:
                    img_Input.setImageResource(R.drawable.input_4pw2);
                    break;
                case 3:
                    img_Input.setImageResource(R.drawable.input_4pw3);
                    break;
                case 4:
                    img_Input.setImageResource(R.drawable.input_4pw4);
                    break;
            }
        }

        if(input.size()==pw.size())
            CheckPW();
    }

    @Override
    protected void onPause() {
        btnsEnable();
        super.onPause();
    }

    @Override
    public void onDestroy(){
        if(wm!=null)
            if(mView!=null)
                wm.removeViewImmediate(mView);

        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }

        if (telephonyManager != null) {
            telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_NONE);
        }

        btnsEnable();
        finishAffinity();
        super.onDestroy();
    }

    public Drawable getDrawableImage(int number) {
        switch (number) {
            case 0:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.percent35_0);
                    case 1:
                        return res.getDrawable(R.drawable.percent100_0);
                    case 2:
                        return res.getDrawable(R.drawable.line_0);
                    case 3:
                        return res.getDrawable(R.drawable.percent0_0);
                }
                break;
            case 1:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.percent35_1);
                    case 1:
                        return res.getDrawable(R.drawable.percent100_1);
                    case 2:
                        return res.getDrawable(R.drawable.line_1);
                    case 3:
                        return res.getDrawable(R.drawable.percent0_1);
                }
                break;
            case 2:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.percent35_2);
                    case 1:
                        return res.getDrawable(R.drawable.percent100_2);
                    case 2:
                        return res.getDrawable(R.drawable.line_2);
                    case 3:
                        return res.getDrawable(R.drawable.percent0_2);
                }
                break;
            case 3:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.percent35_3);
                    case 1:
                        return res.getDrawable(R.drawable.percent100_3);
                    case 2:
                        return res.getDrawable(R.drawable.line_3);
                    case 3:
                        return res.getDrawable(R.drawable.percent0_3);
                }
                break;
            case 4:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.percent35_4);
                    case 1:
                        return res.getDrawable(R.drawable.percent100_4);
                    case 2:
                        return res.getDrawable(R.drawable.line_4);
                    case 3:
                        return res.getDrawable(R.drawable.percent0_4);
                }
                break;
            case 5:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.percent35_5);
                    case 1:
                        return res.getDrawable(R.drawable.percent100_5);
                    case 2:
                        return res.getDrawable(R.drawable.line_5);
                    case 3:
                        return res.getDrawable(R.drawable.percent0_5);
                }
                break;
            case 6:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.percent35_6);
                    case 1:
                        return res.getDrawable(R.drawable.percent100_6);
                    case 2:
                        return res.getDrawable(R.drawable.line_6);
                    case 3:
                        return res.getDrawable(R.drawable.percent0_6);
                }
                break;
            case 7:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.percent35_7);
                    case 1:
                        return res.getDrawable(R.drawable.percent100_7);
                    case 2:
                        return res.getDrawable(R.drawable.line_7);
                    case 3:
                        return res.getDrawable(R.drawable.percent0_7);
                }
                break;
            case 8:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.percent35_8);
                    case 1:
                        return res.getDrawable(R.drawable.percent100_8);
                    case 2:
                        return res.getDrawable(R.drawable.line_8);
                    case 3:
                        return res.getDrawable(R.drawable.percent0_8);
                }
                break;
            case 9:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.percent35_9);
                    case 1:
                        return res.getDrawable(R.drawable.percent100_9);
                    case 2:
                        return res.getDrawable(R.drawable.line_9);
                    case 3:
                        return res.getDrawable(R.drawable.percent0_9);
                }
                break;
            case 10:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.percent35_num);
                    case 1:
                        return res.getDrawable(R.drawable.percent100_num);
                    case 2:
                        return res.getDrawable(R.drawable.line_num);
                    case 3:
                        return res.getDrawable(R.drawable.percent0_num);
                }
                break;
            case 11:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.percent35_as);
                    case 1:
                        return res.getDrawable(R.drawable.percent100_as);
                    case 2:
                        return res.getDrawable(R.drawable.line_as);
                    case 3:
                        return res.getDrawable(R.drawable.percent0_as);
                }
                break;
            case 12:
                switch (btnType) {
                    case 0:
                        return res.getDrawable(R.drawable.btn_new_btn3);
                    case 1:
                        return res.getDrawable(R.drawable.btn_new_btn);
                    case 2:
                        return res.getDrawable(R.drawable.btn_new_btn2);
                    case 3:
                        return res.getDrawable(R.drawable.btn_new_btn4);
                }
                break;
        }
        return null;
    }

    public void shuffleKeyPad(ArrayList posArr) {
        int index = 0;
        Random random = new Random();
        boolean isCheck[] = new boolean[12];
        while(index < posArr.size()){
            int rand_num = random.nextInt(12);
            if(!isCheck[rand_num]){
                posArr.set(index, rand_num);
                isCheck[rand_num] = true;
                index++;
            }
            if(index==posArr.size())
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if(input.size()<pw.size())
                    input.add(pos.get(0));
                enterInput();
                break;
            case R.id.button2:
                if(input.size()<pw.size())
                    input.add(pos.get(1));
                enterInput();
                break;
            case R.id.button3:
                if(input.size()<pw.size())
                    input.add(pos.get(2));
                enterInput();
                break;
            case R.id.button4:
                if(input.size()<pw.size())
                    input.add(pos.get(3));
                enterInput();
                break;
            case R.id.button5:
                if(input.size()<pw.size())
                    input.add(pos.get(4));
                enterInput();
                break;
            case R.id.button6:
                if(input.size()<pw.size())
                    input.add(pos.get(5));
                enterInput();
                break;
            case R.id.button7:
                if(input.size()<pw.size())
                    input.add(pos.get(6));
                enterInput();
                break;
            case R.id.button8:
                if(input.size()<pw.size())
                    input.add(pos.get(7));
                enterInput();
                break;
            case R.id.button9:
                if(input.size()<pw.size())
                    input.add(pos.get(8));
                enterInput();
                break;
            case R.id.button10:
                if(input.size()<pw.size())
                    input.add(pos.get(9));
                enterInput();
                break;
            case R.id.button11:
                if(input.size()<pw.size())
                    input.add(pos.get(10));
                enterInput();
                break;
            case R.id.button12:
                if(input.size()<pw.size())
                    input.add(pos.get(11));
                enterInput();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN :
                //v.setPadding(10,10,10,10);
                v.setAlpha(0.55f);
                switch (v.getId()) {
                    case R.id.btn_del:
                        if(input.size()!=0) {
                            input.remove(input.size() - 1);
                            enterInput();
                        }
                        break;

                    case R.id.btn_show:
                        shuffleKeyPad(pos);
                        btnShow();
                        break;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //v.setPadding(0,0,0,0);
                v.setAlpha(1.0f);
                if(v.getId()==R.id.btn_show)
                    btnUnshow();
                break;
        }
        return true;
    }

    private void btnShow(){
        mBtnButton1.setBackground(getSelector(pos.get(0)));
        mBtnButton1.setEnabled(false);
        mBtnButton2.setBackground(getSelector(pos.get(1)));
        mBtnButton2.setEnabled(false);
        mBtnButton3.setBackground(getSelector(pos.get(2)));
        mBtnButton3.setEnabled(false);
        mBtnButton4.setBackground(getSelector(pos.get(3)));
        mBtnButton4.setEnabled(false);
        mBtnButton5.setBackground(getSelector(pos.get(4)));
        mBtnButton5.setEnabled(false);
        mBtnButton6.setBackground(getSelector(pos.get(5)));
        mBtnButton6.setEnabled(false);
        mBtnButton7.setBackground(getSelector(pos.get(6)));
        mBtnButton7.setEnabled(false);
        mBtnButton8.setBackground(getSelector(pos.get(7)));
        mBtnButton8.setEnabled(false);
        mBtnButton9.setBackground(getSelector(pos.get(8)));
        mBtnButton9.setEnabled(false);
        mBtnButton10.setBackground(getSelector(pos.get(9)));
        mBtnButton10.setEnabled(false);
        mBtnButton11.setBackground(getSelector(pos.get(10)));
        mBtnButton11.setEnabled(false);
        mBtnButton12.setBackground(getSelector(pos.get(11)));
        mBtnButton12.setEnabled(false);
    }

    private void btnUnshow(){
        mBtnButton1.setBackground(getDrawableImage(12));
        mBtnButton1.setEnabled(true);
        mBtnButton2.setBackground(getDrawableImage(12));
        mBtnButton2.setEnabled(true);
        mBtnButton3.setBackground(getDrawableImage(12));
        mBtnButton3.setEnabled(true);
        mBtnButton4.setBackground(getDrawableImage(12));
        mBtnButton4.setEnabled(true);
        mBtnButton5.setBackground(getDrawableImage(12));
        mBtnButton5.setEnabled(true);
        mBtnButton6.setBackground(getDrawableImage(12));
        mBtnButton6.setEnabled(true);
        mBtnButton7.setBackground(getDrawableImage(12));
        mBtnButton7.setEnabled(true);
        mBtnButton8.setBackground(getDrawableImage(12));
        mBtnButton8.setEnabled(true);
        mBtnButton9.setBackground(getDrawableImage(12));
        mBtnButton9.setEnabled(true);
        mBtnButton10.setBackground(getDrawableImage(12));
        mBtnButton10.setEnabled(true);
        mBtnButton11.setBackground(getDrawableImage(12));
        mBtnButton11.setEnabled(true);
        mBtnButton12.setBackground(getDrawableImage(12));
        mBtnButton12.setEnabled(true);
    }

    public StateListDrawable getSelector(int number){
        Drawable drawable = getDrawableImage(number);
        return new StateDrawableBuilder()
                //.setDisabledDrawable(drawable)
                .setNormalDrawable(drawable)
                .setPressedDrawable(getDrawableImage(12))
                .setSelectedDrawable(getDrawableImage(12))
                .build();
    }

}
