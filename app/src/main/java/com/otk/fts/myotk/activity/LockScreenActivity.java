///////////////////////////////////////////////
/*
    File Name : LockScreenActivity
    Create Date : 2020.08.24
    Function :
    - 화면 잠금 상태에서 비번 입력을 위한 화면

 */
///////////////////////////////////////////////


package com.otk.fts.myotk.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
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
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import com.bumptech.glide.Glide;
import com.otk.fts.myotk.receiver.AlarmReceiver;
import com.otk.fts.myotk.services.LockScreenService;
import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.ActivityUtil;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;
import com.otk.fts.myotk.utils.StateDrawableBuilder;
import com.otk.fts.myotk.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

import static android.speech.tts.TextToSpeech.ERROR;


// Lock Screen
public class LockScreenActivity extends Activity implements View.OnTouchListener, View.OnClickListener {
    private static final String TAG00 = "Inhyo Test";

    private TextToSpeech tts;



    private boolean isActive;

    // button은 기본적으로 2개가 겹쳐 있음
    // 숫자를 보여주는 button 과 이미지가 올라가서 보여주는 button이 있음

    private ImageView imgBtnView01;
    private ImageView imgBtnView02;
    private ImageView imgBtnView03;
    private ImageView imgBtnView04;
    private ImageView imgBtnView05;
    private ImageView imgBtnView06;
    private ImageView imgBtnView07;
    private ImageView imgBtnView08;
    private ImageView imgBtnView09;
    private ImageView imgBtnView10;
    private ImageView imgBtnView11;
    private ImageView imgBtnView12;

    private ImageView imgBtnViewShow;
    private ImageView imgBtnViewCamera;
    private ImageView imgBtnViewInput;
    private ImageView imgBtnViewDel;

    private ImageButton mBtn01;
    private ImageButton mBtn02;
    private ImageButton mBtn03;
    private ImageButton mBtn04;
    private ImageButton mBtn05;
    private ImageButton mBtn06;
    private ImageButton mBtn07;
    private ImageButton mBtn08;
    private ImageButton mBtn09;
    private ImageButton mBtn10;
    private ImageButton mBtn11;
    private ImageButton mBtn12;

    private ImageButton mBtnShow;
    private ImageButton mBtnCamera;
    private ImageButton mBtnInput;
    private ImageButton mBtnDel;

    private TextView stopTv;
    private int numType;
    private int btnType;
    private int r2niconType;
    private String r2niconPath;

    private int directType;

    private String nulliconPath;
    private String imgRandomPath;
    private int basicBackground;

    private LinearLayout bottomLl;

    // PassWord Size
    private int pwSize;

    // PassWord List
    private ArrayList pw;
    // 입력 번호 List
    public ArrayList<Integer> input;
    // 키패드 순서 List
    private ArrayList<Integer> pos;

    // 초기 비밀번호 보이는 시간
    private int f_timer;
    // 비밀번호 입력 시도 제한
    private int inputCnt;

    Vibrator vibrator;
    private int wrongCount;
    private int wrongTrigger;
    private int wrong_lockTimer;

    private String backupPin;

    private WindowManager wm;
    private View mView;
    private Handler handler;
    private Handler handler2;

    private Resources res;

    private TextView time_txtview;

    private boolean displaytimer;

    private boolean isNullKey = false;
    private boolean num2ImgChange = false;
    private boolean chkTimer = true;
    private boolean timeZeroChk = false;
    private boolean isFirst = true;
    private boolean imgNumChk = false;


    boolean isCameraClick = false;
    private boolean delbtnchk = false;

    private String errorPath = "";
    private String errorNullPath = "";
    private AlarmManager alarmManager = null;


    private TelephonyManager telephonyManager = null;
    private PhoneStateListener phoneListener = new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String incomingNumber){

            switch(state){
                case TelephonyManager.CALL_STATE_IDLE :
                    //isPhoneIdle = true;
                    QLog.d("LOCK CALL_STATE_IDLE");
                    break;

                //전화가 울릴때 화면 종료
                case TelephonyManager.CALL_STATE_RINGING :
                    //isPhoneIdle = false;
                    QLog.d("CALL_STATE_RINGING");
                    PreferenceUtil.savePref(LockScreenActivity.this, PreferenceUtil.LOCK_USING, true);
                    //Utils.stopService(getApplicationContext());
                    finishAffinity();
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK :
                    //isPhoneIdle = false;
                    QLog.d("CALL_STATE_OFFHOOK");
                    break;

            }
        }
    };

    private AlarmReceiver alarmReceiver = new AlarmReceiver(){

        @Override
        public void onReceive(Context context, Intent intent){

            QLog.d("alarmReceiver ---- alarm Test");
            Toast.makeText(getApplicationContext(), "Alarm Test", Toast.LENGTH_SHORT).show();
        }


    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        QLog.d("onKeyDown");
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // 여기에 뒤로가기 버튼을 눌렀을 때 행동 입력
                errorPath = imgRandomPath;
                errorNullPath = nulliconPath;
                return false;

            case KeyEvent.KEYCODE_HOME:
                // 여기에 홈 버튼을 눌렀을 때 행동 입력
                QLog.d("KEYCODE_HOME");
                shuffleKeyPad(pos); //키패드 셔플
                btnShow();
                btnsEnable();
                return false;

            case KeyEvent.KEYCODE_MENU:
                errorPath = imgRandomPath;
                errorNullPath = nulliconPath;
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onUserLeaveHint(){
        super.onUserLeaveHint();
        errorPath = imgRandomPath;
        errorNullPath = nulliconPath;
        QLog.d("KEYCODE_HOME");
        return;
    }

    private void Speech(){
        String text = "비밀번호가 다릅니다".trim();
        tts.setPitch((float)1.0); //톤
        tts.setSpeechRate((float)1.0); //재생속도
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Inhyo Test ", "handler = new Handler(msg -> false); - onCreate 1");
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        handler = new Handler(msg -> false);

        res = getResources();
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        getWindow().addFlags(
                // 기본 잠금화면보다 우선출력
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        // 기본 잠금화면 해제시키기
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            if(telephonyManager!=null)
                telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

        IntentFilter offfilter = new IntentFilter (Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenoff, offfilter);

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
                    PixelFormat.TRANSLUCENT);
        }

        params.gravity = Gravity.CENTER;

        boolean isLeft = PreferenceUtil.getBooleanPref(this, PreferenceUtil.SHOW_LEFT, true);
        int layout = isLeft? R.layout.activity_main_lock : R.layout.activity_main_right_lock;
        ViewDataBinding binding = DataBindingUtil.inflate(inflate, layout, null, false);//DataBindingUtil.setContentView(this, layout);
        mView = binding.getRoot();

        imgRandomPath = PreferenceUtil.getStringPref(this, PreferenceUtil.RANDOMICON_PATH);

        mView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        wm.addView(mView, params);

        ConstraintLayout bg_screen = mView.findViewById(R.id.Linear_bg);
        bottomLl = mView.findViewById((R.id.bottomLl));

        imgBtnView01 = mView.findViewById(R.id.img_button01);
        imgBtnView02 = mView.findViewById(R.id.img_button02);
        imgBtnView03 = mView.findViewById(R.id.img_button03);
        imgBtnView04 = mView.findViewById(R.id.img_button04);
        imgBtnView05 = mView.findViewById(R.id.img_button05);
        imgBtnView06 = mView.findViewById(R.id.img_button06);
        imgBtnView07 = mView.findViewById(R.id.img_button07);
        imgBtnView08 = mView.findViewById(R.id.img_button08);
        imgBtnView09 = mView.findViewById(R.id.img_button09);
        imgBtnView10 = mView.findViewById(R.id.img_button10);
        imgBtnView11 = mView.findViewById(R.id.img_button11);
        imgBtnView12 = mView.findViewById(R.id.img_button12);

        imgBtnViewCamera = mView.findViewById(R.id.img_button_camera);
        imgBtnViewShow = mView.findViewById(R.id.img_button_show);
        imgBtnViewInput = mView.findViewById(R.id.img_input);
        imgBtnViewDel = mView.findViewById(R.id.img_delete);

        mBtn01 = (ImageButton) mView.findViewById(R.id.button1);
        mBtn01.setOnClickListener(this);
        mBtn02 = (ImageButton) mView.findViewById(R.id.button2);
        mBtn02.setOnClickListener(this);
        mBtn03 = (ImageButton) mView.findViewById(R.id.button3);
        mBtn03.setOnClickListener(this);
        mBtn04 = (ImageButton) mView.findViewById(R.id.button4);
        mBtn04.setOnClickListener(this);
        mBtn05 = (ImageButton) mView.findViewById(R.id.button5);
        mBtn05.setOnClickListener(this);
        mBtn06 = (ImageButton) mView.findViewById(R.id.button6);
        mBtn06.setOnClickListener(this);
        mBtn07 = (ImageButton) mView.findViewById(R.id.button7);
        mBtn07.setOnClickListener(this);
        mBtn08 = (ImageButton) mView.findViewById(R.id.button8);
        mBtn08.setOnClickListener(this);
        mBtn09 = (ImageButton) mView.findViewById(R.id.button9);
        mBtn09.setOnClickListener(this);
        mBtn10 = (ImageButton) mView.findViewById(R.id.button10);
        mBtn10.setOnClickListener(this);
        mBtn11 = (ImageButton) mView.findViewById(R.id.button11);
        mBtn11.setOnClickListener(this);
        mBtn12 = (ImageButton) mView.findViewById(R.id.button12);
        mBtn12.setOnClickListener(this);


        mBtnShow = mView.findViewById(R.id.btn_show);
        mBtnInput = mView.findViewById(R.id.btn_input);
        mBtnCamera = mView.findViewById(R.id.btn_camera);
        mBtnDel = mView.findViewById(R.id.btn_delete);

        stopTv = mView.findViewById(R.id.stopTv);
        isActive = PreferenceUtil.getBooleanPref(this, PreferenceUtil.IS_LOCK, true);
        pwSize = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_SIZE, 4);

        String lpwStr = "0";

        isNullKey = false;


        switch (pwSize){
            case 1:
                lpwStr = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST1, "0");//sf.getInt("pwList", 0);
                break;
            case 2:
                lpwStr = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST2, "00");//sf.getInt("pwList", 0);
                break;
            case 3:
                lpwStr = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST3, "000");//sf.getInt("pwList", 0);
                break;
            case 4:
                lpwStr = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST4, "0000");//sf.getInt("pwList", 0);
                break;

        }

        int lpw = Integer.parseInt(lpwStr);

        backupPin = PreferenceUtil.getStringPref(this, PreferenceUtil.BACKUP_PIN, "1234");//sf.getInt("backupPin", 1234);
        f_timer = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_TIMER, 0);//sf.getInt("pwTimer", 2000);
        numType = PreferenceUtil.getIntPref(this, PreferenceUtil.NUM_TYPE, 2);//sf.getInt("numType", 3);

        if(numType != 3){
            imgRandomPath = "";
        }
        directType = PreferenceUtil.getIntPref(this, PreferenceUtil.DIRECT_TYPE, 0);
        btnType = PreferenceUtil.getIntPref(this, PreferenceUtil.BTN_TYPE, 2);//sf.getInt("btnType", 3);
        nulliconPath = PreferenceUtil.getStringPref(this, PreferenceUtil.NULLICON_PATH);//sf.getInt("btnType", 3);

        basicBackground = PreferenceUtil.getIntPref(this, PreferenceUtil.BASIC_BACKGROUND, 0);
        //int bgScreenSelect = PreferenceUtil.getIntPref(this, PreferenceUtil.BACKGROUND_SELECT);

        if(btnType != 3){
            nulliconPath = "";
        }

        mBtnInput.setOnTouchListener(this);
        mBtnShow.setOnTouchListener(this);
        mBtnCamera.setOnTouchListener(this);
        mBtnDel.setOnTouchListener(this);

        inputCnt = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_CNT, 5);

        // Custom 버튼 이미지 여부
        boolean customBgImg = PreferenceUtil.getBooleanPref(this, PreferenceUtil.CUSTOM_BG, false);//sf.getBoolean("customBgImg", false);

        String customBgImgPath = PreferenceUtil.getStringPref(this, PreferenceUtil.CUSTOM_BG_PATH);//sf.getString("pwImgPath","");

        if (customBgImg) {
            //if(bgScreenSelect == 0 ){
            //    bg_screen.setBackgroundResource(R.drawable.default_img);
            // } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap originalBm = BitmapFactory.decodeFile(customBgImgPath, options);
            Drawable drawable = new BitmapDrawable(originalBm);
            bg_screen.setBackground(drawable);
            //}


        } else {
            //bg_screen.setBackground(res.getDrawable(R.drawable.background_1));
            switch (basicBackground){
                case 0:
                    bg_screen.setBackgroundResource(R.drawable.default_img);
                    break;
                case 1:
                    bg_screen.setBackgroundResource(R.color.colorBlack);
                    break;
                case 2:
                    bg_screen.setBackgroundResource(R.color.colorRed);
                    break;
                case 3:
                    bg_screen.setBackgroundResource(R.color.colorGreen);
                    break;
                case 4:
                    bg_screen.setBackgroundResource(R.color.lightblue6);
                    break;

            }
        }

        // 비밀번호 배열 ( 임시비번 = 00 )
        pw = new ArrayList();

        if(lpwStr.equals("0")) {
            pw.add(0);
            mBtnInput.setImageResource(R.drawable.btn_input_0);
        }else if(lpwStr.equals("00")) {
            pw.add(0);
            pw.add(0);
            mBtnInput.setImageResource(R.drawable.btn_input_0);
        }else if(lpwStr.equals("000")) {
            pw.add(0);
            pw.add(0);
            pw.add(0);
            mBtnInput.setImageResource(R.drawable.btn_input_0);
        }else if(lpwStr.equals("0000")){
            pw.add(0);
            pw.add(0);
            pw.add(0);
            pw.add(0);
            mBtnInput.setImageResource(R.drawable.btn_input_0);
        }else{
            if(pwSize==1){
                // 비밀번호 사이즈 1
                int a = lpw%10;
                pw.add(a);
                mBtnInput.setImageResource(R.drawable.btn_input_0);
            } else if(pwSize==2){
                // 비밀번호 사이즈 2
                int a = lpw/10;
                int b = lpw%10;
                pw.add(a);
                pw.add(b);
                mBtnInput.setImageResource(R.drawable.btn_input_0);
            } else if(pwSize==3){
                // 비밀번호 사이즈 3
                int a = lpw/100;
                int b = (lpw%100)/10;
                int c = lpw%10;
                pw.add(a);
                pw.add(b);
                pw.add(c);
                mBtnInput.setImageResource(R.drawable.btn_input_0);
            } else{
                // 비밀번호 사이즈 4
                int a = lpw/1000;
                int b = (lpw%1000)/100;
                int c = (lpw%100)/10;
                int d = lpw%10;
                pw.add(a);
                pw.add(b);
                pw.add(c);
                pw.add(d);
                mBtnInput.setImageResource(R.drawable.btn_input_0);
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
        Log.d(TAG00, "SwitchRandomBtn- start");
        switchRandomBtn();

        wrong_lockTimer = 10000;
    }

    @Override
    public void onResume(){
        super.onResume();
        isNullKey = false;
        if(isActive) {
            wrongCount = 0;
            wrongTrigger = 0;
            //Log.d(TAG00, "SwitchRandomBtn- resume");
        } else finishAffinity();
    }

    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);

        if(!hasFocus){
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    /*
         Function Name : switchRandomBtn
         키패드 동작 시간에 따라 숫자키패드와 이미지 키패드를 보여주는 함수

     */

    private void switchRandomBtn(){

        if(f_timer == 0){
            if (!timeZeroChk) {
                shuffleKeyPad(pos);
                btnShow();
                btnsEnable();
            } else {
                btnUnshow();
            }

        } else {
            if (chkTimer) {
                if (!num2ImgChange) {
                    shuffleKeyPad(pos);
                    btnShow();
                    btnsEnable();
                } else {
                    btnUnshow();
                }
                num2ImgChange = !num2ImgChange;
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(this::switchRandomBtn, f_timer);    //2초 뒤에
            } else {
                if (isNullKey) {
                    shuffleKeyPad(pos);
                    btnShow();
                    btnsEnable();
                } else {
                    btnUnshow();
                }
                isNullKey = !isNullKey;
            }
        }
    }


    private void removeMessage(){
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
        } else {
            handler = new Handler(msg -> false);
            handler.postDelayed(this::run, 100);
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void run(){

        Log.d("Inhyo Test", "================> RUN() =======================");

    }


    /*
         Function Name : btnsDisable
         버튼들을 비활성화

     */
    private void btnsDisable(){

        mBtn01.setEnabled(false);
        mBtn02.setEnabled(false);
        mBtn03.setEnabled(false);
        mBtn03.setEnabled(false);
        mBtn04.setEnabled(false);
        mBtn05.setEnabled(false);
        mBtn06.setEnabled(false);
        mBtn07.setEnabled(false);
        mBtn08.setEnabled(false);
        mBtn09.setEnabled(false);
        mBtn10.setEnabled(false);
        mBtn11.setEnabled(false);
        mBtn12.setEnabled(false);

    }

    /*
         Function Name : btnsEnable
         버튼들을 활성화

     */
    private void btnsEnable(){

        mBtn01.setEnabled(true);
        mBtn02.setEnabled(true);
        mBtn03.setEnabled(true);
        mBtn04.setEnabled(true);
        mBtn05.setEnabled(true);
        mBtn06.setEnabled(true);
        mBtn07.setEnabled(true);
        mBtn08.setEnabled(true);
        mBtn09.setEnabled(true);
        mBtn10.setEnabled(true);
        mBtn11.setEnabled(true);
        mBtn12.setEnabled(true);

    }

    /*
         Function Name : CheckPW
         패스워드 확인하는 함수

     */
    private void CheckPW() {
        if(input.size()==pw.size()){
            int index = 0;
            int backupPinNum = Integer.parseInt(backupPin);
            while(index<input.size()){
                if (!input.get(index).equals(pw.get(index))) {
                    vibrator.vibrate(100);
                    Log.d("test", "incorrect");
                    Speech();

                    wrongCount += 1;
                    wrongTrigger += 1;
                    if(wrongTrigger >=inputCnt &&wrongCount>=10){
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
                        mBtnInput.setImageResource(R.drawable.img_trans);
                        PreferenceUtil.savePref(this, PreferenceUtil.PW_SIZE, pwSize);
                        PreferenceUtil.savePref(this, PreferenceUtil.PW_LIST4, backupPin);
                        btnUnshow();
                        btnsDisable();
                        removeMessage();

                        handler.postDelayed(() -> {
                            btnUnshow();
                            btnsEnable();
                            Log.d("Inhyo Test", "hanlder - delay : wrong_lockTimer");
                        }, wrong_lockTimer);    //10초 뒤에
                    } else if(wrongTrigger >= inputCnt){
                        btnUnshow();
                        btnsDisable();
                        wrongTrigger=0;
                        removeMessage();

                        handler.postDelayed(() -> {
                            btnUnshow();
                            btnsEnable();
                            Log.d("Inhyo Test", "hanlder - delay : wrong_lockTimer");
                        }, wrong_lockTimer);    //10초 뒤에


                    }
                    removeMessage();
                    delbtnchk = false;
                    handler.postDelayed(() -> {
                        Incorrect();
                        input.clear();
                        enterInput();
                    }, 200);    //0.2초 뒤에
                    return;
                }
                index++;
            }
            Unlock();
        }
    }

    private void Unlock(){

        if (isCameraClick) {
            // Utils.stopService(getApplicationContext());
            // PreferenceUtil.savePref(this, PreferenceUtil.IS_LOCK, false);
            //finish();

        }
        finishAffinity();
    }

    /*
         Function Name : Incorrect
         패스워드가 틀렸을때 랜덤하게 키패드 바꾸는 함수

     */
    private void Incorrect(){
        if (pwSize == 1) {
            mBtnInput.setImageResource(R.drawable.img_trans);
        } else if (pwSize == 2) {
            mBtnInput.setImageResource(R.drawable.img_trans);
        } else if (pwSize == 3) {
            mBtnInput.setImageResource(R.drawable.img_trans);
        } else if (pwSize == 4) {
            mBtnInput.setImageResource(R.drawable.img_trans);
        }

        shuffleKeyPad(pos);  //20200409 Test By Inhyo
        btnShow();
        btnsEnable();
    }

    /*
             Function Name : enterInput
             패스워드가 입력시 패스워드 길이에 따른 처리 함

    */
    private void enterInput() {

        if(pwSize == 1){
            delbtnchk = true;
            if (input.size() == 1) {
                mBtnInput.setImageResource(R.drawable.btn_input_1);
            } else {
                delbtnchk = false;
                mBtnInput.setImageResource(R.drawable.btn_input_0);
            }
        } else if (pwSize == 2) {
            delbtnchk = true;
            if (input.size() == 1) {
                mBtnInput.setImageResource(R.drawable.btn_input_1);
            } else if (input.size() == 2) {
                mBtnInput.setImageResource(R.drawable.btn_input_2);
            } else {
                delbtnchk = false;
                mBtnInput.setImageResource(R.drawable.btn_input_0);
            }
        } else if (pwSize == 3) {
            delbtnchk = true;
            switch (input.size()) {
                case 0:
                    delbtnchk = false;
                    mBtnInput.setImageResource(R.drawable.btn_input_0);
                    break;
                case 1:
                    mBtnInput.setImageResource(R.drawable.btn_input_1);
                    break;
                case 2:
                    mBtnInput.setImageResource(R.drawable.btn_input_2);
                    break;
                case 3:
                    mBtnInput.setImageResource(R.drawable.btn_input_3);
                    break;
            }
        } else if (pwSize == 4){
            delbtnchk = true;
            switch (input.size()) {
                case 0:
                    delbtnchk = false;
                    mBtnInput.setImageResource(R.drawable.btn_input_0);
                    break;
                case 1:
                    mBtnInput.setImageResource(R.drawable.btn_input_1);
                    break;
                case 2:
                    mBtnInput.setImageResource(R.drawable.btn_input_2);
                    break;
                case 3:
                    mBtnInput.setImageResource(R.drawable.btn_input_3);
                    break;
                case 4:
                    mBtnInput.setImageResource(R.drawable.btn_input_4);
                    break;
            }
        }

        if (input.size() == pw.size())
            CheckPW();
    }

    @Override
    protected void onPause() {
        btnsEnable();
        super.onPause();
    }

    @Override
    public void onDestroy(){
        QLog.d("onDestroy");
        try{
            if(wm!=null)
                if(mView!=null && isFinishing())
                    wm.removeViewImmediate(mView);

            if(handler!=null){
                handler = null;
            }

            if (telephonyManager != null) {
                telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_NONE);
            }

            if(alarmManager != null){

            }
            unregisterBroadcast();
            //btnsEnable();
            finishAffinity();
        }catch (Exception e){
            QLog.e(e.toString());
        }

        super.onDestroy();
    }

    private void unregisterBroadcast(){
        unregisterReceiver(screenoff);
    }

    BroadcastReceiver screenoff = new BroadcastReceiver() {
        public static final String Screenoff = "android.intent.action.SCREEN_OFF";

        @Override
        public void onReceive(Context context, Intent intent) {
            if(!intent.getAction().equals(Screenoff))return;

            Log.d(TAG00, "SwitchRandomBtn- onReceive");
            switchRandomBtn();
            //run();
            Log.d("Inhyo Test", "Power off");
        }
    };

    /*
             Function Name : getDrawableImage
             숫자 버튼 마다 투명/라인/이미지 등을 처리하기 위한 함수

    */

    public Drawable getDrawableImage(int number) { //버튼 이미지 가져오기
        int imgNum = R.drawable.img_trans;

        if(imgRandomPath.length() == 0){
            if(numType == 3){
                imgRandomPath = errorPath;
                nulliconPath = errorNullPath;
            }

        }
        switch (number) {
            case 0:
                switch (numType) {
                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_0);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_0);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView01);
                        }
                        return res.getDrawable(R.drawable.random_line_3x5_0);
                }
                break;
            case 1:
                switch (numType) {
                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_1);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_1);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView02);
                        }
                        return res.getDrawable(R.drawable.random_line_3x5_1);
                }
                break;
            case 2:
                switch (numType) {
                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_2);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_2);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView03);
                        }
                        return res.getDrawable(R.drawable.random_line_3x5_2);
                }
                break;
            case 3:
                switch (numType) {
                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_3);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_3);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView04);
                        }
                        return res.getDrawable(R.drawable.random_line_3x5_3);
                }
                break;
            case 4:
                switch (numType) {
                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_4);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_4);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView05);
                        }
                        return res.getDrawable(R.drawable.random_line_3x5_4);
                }
                break;
            case 5:
                switch (numType) {
                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_5);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_5);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView06);
                        }
                        return res.getDrawable(R.drawable.random_line_3x5_5);
                }
                break;
            case 6:
                switch (numType) {
                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_6);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_6);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView07);
                        }
                        return res.getDrawable(R.drawable.random_line_3x5_6);
                }
                break;
            case 7:
                switch (numType) {
                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_7);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_7);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView08);
                        }
                        return res.getDrawable(R.drawable.random_line_3x5_7);
                }
                break;
            case 8:
                switch (numType) {
                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_8);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_8);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView09);
                        }
                        return res.getDrawable(R.drawable.random_line_3x5_8);
                }
                break;
            case 9:
                switch (numType) {
                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_9);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_9);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView10);
                        }
                        return res.getDrawable(R.drawable.random_line_3x5_9);
                }
                break;
            case 10:
                switch (numType) {
                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_num);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_num);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView11);
                        }
                        return res.getDrawable(R.drawable.random_line_3x5_num);
                }
                break;
            case 11:
                switch (numType) {
                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_as);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_as);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView12);
                        }
                        return res.getDrawable(R.drawable.random_line_3x5_as);
                }
                break;
            case 12:
                switch (numType) {
                    case 0:
                        return res.getDrawable(R.drawable.tran_3x5_r2n);
                    case 1:
                        return res.getDrawable(R.drawable.tran_3x5_r2n);
                    case 2:
                        return res.getDrawable(R.drawable.tran_3x5_r2n);
                    case 3:
                        if(activityChk()) {
                            //   Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewShow);
                        }
                        return res.getDrawable(R.drawable.tran_3x5_r2n);
                }
                break;
            case 13:
                mBtnDel.setImageResource(R.drawable.btn_del);

                break;
            case 14:
                switch (numType) {

                    case 1:
                        switch (directType){
                            case 0:
                                imgNum = R.drawable.btn_camera;
                                break;
                            case 2:
                                imgNum = R.drawable.btn_calendar;
                                break;
                            case 3:
                                imgNum = R.drawable.img_trans;
                                break;
                        }

                        return res.getDrawable(imgNum);
                    case 2:
                        switch (directType){
                            case 0:
                                imgNum = R.drawable.btn_camera;
                                break;
                            case 2:
                                imgNum = R.drawable.btn_calendar;
                                break;
                            case 3:
                                imgNum = R.drawable.img_trans;
                                break;
                        }
                        return res.getDrawable(imgNum);
                    case 3:
                        switch (directType){
                            case 0:
                                imgNum = R.drawable.btn_camera;
                                break;
                            case 2:
                                imgNum = R.drawable.btn_calendar;
                                break;
                            case 3:
                                imgNum = R.drawable.img_trans;
                                break;
                        }
                        if(activityChk()) {
                        }
                        return res.getDrawable(imgNum);
                }
                break;
            case 15: // Null Keypad
                switch (btnType) {  // 0 -- gray // 1- trans // 2 - line // 3 - album
                    case 1:
                        return res.getDrawable(R.drawable.btn_new_btn);
                    case 2:
                        return res.getDrawable(R.drawable.btn_new_btn2);
                    case 3:
                        return res.getDrawable(R.drawable.btn_new_btn3);
                    case 4:
                        return res.getDrawable(R.drawable.btn_new_btn4);

                }
                break;
            case 16:
                switch (btnType) {
                    case 0:
                        return res.getDrawable(R.drawable.tran_3x5_r2n);
                    case 1:
                        return res.getDrawable(R.drawable.tran_3x5_r2n);
                    case 2:
                        return res.getDrawable(R.drawable.tran_3x5_r2n);
                    case 3:
                        if(activityChk()) {
                        }
                        return res.getDrawable(R.drawable.tran_3x5_r2n);
                }
                break;

            case 17:
                switch (btnType) {

                    case 1:
                        if(!delbtnchk) {
                            imgBtnViewInput.setImageResource(R.drawable.btn_del_trans);
                        }else {
                            imgBtnViewInput.setImageResource(R.drawable.img_trans);
                        }
                        return res.getDrawable(R.drawable.img_trans);
                    case 2:
                        if(!delbtnchk) {
                            imgBtnViewInput.setImageResource(R.drawable.btn_del_line);
                        } else {
                            imgBtnViewInput.setImageResource(R.drawable.img_line_album);
                        }
                        return res.getDrawable(R.drawable.img_line_album);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewInput);
                        }
                        if(!delbtnchk) {
                            imgBtnViewInput.setImageResource(R.drawable.btn_del_line);
                        } else {
                            imgBtnViewInput.setImageResource(R.drawable.img_line_album);
                        }
                        return res.getDrawable(R.drawable.img_line_album);
                }
                break;

            case 18:
                switch (btnType) {
                    case 1:
                        switch (directType){
                            case 0:
                                imgNum = R.drawable.btn_camera;
                                break;
                            case 2:
                                imgNum = R.drawable.btn_calendar;
                                break;
                            case 3:
                                imgNum = R.drawable.img_trans;
                                break;
                        }

                        return res.getDrawable(imgNum);
                    case 2:
                        switch (directType){
                            case 0:
                                imgNum = R.drawable.btn_camera;
                                break;
                            case 2:
                                imgNum = R.drawable.btn_calendar;
                                break;
                            case 3:
                                imgNum = R.drawable.img_trans;
                                break;
                        }
                        return res.getDrawable(imgNum);
                    case 3:
                        switch (directType){
                            case 0:
                                imgNum = R.drawable.btn_camera;
                                break;
                            case 2:
                                imgNum = R.drawable.btn_calendar;
                                break;
                            case 3:
                                imgNum = R.drawable.img_trans;
                                break;
                        }
                        if(activityChk()) {
                        }
                        return res.getDrawable(imgNum);
                }
                break;

            case 19:
                mBtnDel.setImageResource(R.drawable.btn_del);

                break;
        }
        return null;
    }

    /*
         Function Name : shuffleKeyPad

         숫자 버튼의 순서를 랜덤하게 바꾸는 함수

     */
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

    /*
             Function Name : onClick

             숫자 버튼을 클릭할때 처음 호출되는 함수

    */
    public void onClick(View v) {
        v.setBackground(res.getDrawable(R.drawable.press_blank));

        switch (v.getId()) {
            case R.id.button1:
                if (input.size() < pw.size())
                    input.add(pos.get(0));
                break;
            case R.id.button2:
                if (input.size() < pw.size())
                    input.add(pos.get(1));
                break;
            case R.id.button3:
                if (input.size() < pw.size())
                    input.add(pos.get(2));
                break;
            case R.id.button4:
                if (input.size() < pw.size())
                    input.add(pos.get(3));
                break;
            case R.id.button5:
                if (input.size() < pw.size())
                    input.add(pos.get(4));
                break;
            case R.id.button6:
                if (input.size() < pw.size())
                    input.add(pos.get(5));
                break;
            case R.id.button7:
                if (input.size() < pw.size())
                    input.add(pos.get(6));
                break;
            case R.id.button8:
                if (input.size() < pw.size())
                    input.add(pos.get(7));
                break;
            case R.id.button9:
                if (input.size() < pw.size())
                    input.add(pos.get(8));
                break;
            case R.id.button10:
                if (input.size() < pw.size())
                    input.add(pos.get(9));
                break;
            case R.id.button11:
                if (input.size() < pw.size())
                    input.add(pos.get(10));
                break;
            case R.id.button12:
                if (input.size() < pw.size())
                    input.add(pos.get(11));
                break;
        }

        enterInput();
        handler.postDelayed(this::showBtn, 50);
    }

    private void showBtn(){

        if (!isNullKey) {
            btnShow();
            btnsEnable();
        } else {
            btnUnshow();
        }
    }

    /*
         Function Name : onTouch
         숫자 버튼을 제외한 카메라, 브레인락 아이콘 버튼 등을 클릭할때 호출되는 함수
     */
    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setBackground(res.getDrawable(R.drawable.press_blank));
                switch (v.getId()) {
                    case R.id.btn_delete:
                        if (input.size() != 0) {
                            input.remove(input.size() - 1);
                            enterInput();
                        } else {
                            enterInput();
                        }
                        break;
                    case R.id.btn_show:
                        imgBtnViewShow.setBackground(res.getDrawable(R.drawable.press_blank));
                        if(chkTimer) {
                            chkTimer = false;
                        }

                        if(isFirst){
                            handler.removeCallbacksAndMessages(null);
                            isFirst = false;
                            if(f_timer == 0) {
                                timeZeroChk = !timeZeroChk;

                                btnUnshow();
                            } else {

                                if(imgNumChk){
                                    shuffleKeyPad(pos);
                                    btnShow();
                                    btnsEnable();
                                } else {
                                    btnUnshow();
                                }
                            }
                            imgNumChk = !imgNumChk;

                        } else {

                            if(imgNumChk){
                                shuffleKeyPad(pos);
                                btnShow();
                                btnsEnable();
                            } else {
                                btnUnshow();
                            }
                            imgNumChk = !imgNumChk;

                        }

                        break;

                    case R.id.btn_camera:
                        otherAppRun(directType);
                        if(isNullKey){

                        } else {
                            imgBtnEnable();
                        }
                        break;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                v.setBackgroundColor(Color.TRANSPARENT);

                if (v.getId() == R.id.btn_show) {
                    imgBtnViewShow.setBackground(res.getDrawable(R.drawable.img_trans));
                    if(!isNullKey){
                        imgBtnEnable();
                        mBtnShow.setBackground(getSelector(12));
                        mBtnShow.setEnabled(true);
                    } else {
                        mBtnShow.setBackground(getSelector(16));
                        mBtnShow.setEnabled(true);
                    }
                } else if (v.getId() == R.id.btn_camera) {
                    if(isNullKey){
                        mBtnCamera.setBackground(getDrawableImage(18));
                        mBtnCamera.setEnabled(true);
                    } else {
                        imgBtnEnable();
                        mBtnCamera.setBackground(getDrawableImage(14));
                        mBtnCamera.setEnabled(true);
                    }

                    isCameraClick = true;
                } else if(v.getId() == R.id.btn_delete){

                }
                break;
        }
        return true;
    }

    /*
         Function Name : otherAppRun
         바로가기 버튼을 카메라 / 캘린더 바꿔서 보여주는 함수
     */

    private void otherAppRun(int type){

        Log.d("Inhyo Test", "otherAppRun() -- " + type);

        PackageManager pm = getApplicationContext().getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage("");
        switch (type){
            case 0:
                callCamera();
                break;
            case 1:
                intent = pm.getLaunchIntentForPackage("com.nhn.android.nmap");
                break;
            case 2:
                intent = pm.getLaunchIntentForPackage("com.samsung.android.calendar");
                break;
        }

        if(intent != null) {
            if(type != 0) {
                intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_NO_ANIMATION | FLAG_ACTIVITY_SINGLE_TOP);
                intent.setFlags(0);
                startActivity(intent);;
            }
        }
    }

    /*
         Function Name : callCamera
         카메라 호출 함수
     */
    private void callCamera(){
        Log.d("Inhyo Test", "call Camera Run");

        if(Utils.isServiceRunning(this)){
            // Utils.stopService(this);
        }

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            PackageManager pm = getPackageManager();
            final ResolveInfo mInfo = pm.resolveActivity(i,0);
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(mInfo.activityInfo.packageName, mInfo.activityInfo.name));
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(intent);
        } catch (Exception e){
            Log.d("Inhyo Test", "Unable to launch Camera : " + e);
        }
    }


    private void btnShow(){

        cleanBtn();
        mBtn01.setBackground(getSelector(pos.get(0)));
        mBtn01.setEnabled(false);
        mBtn02.setBackground(getSelector(pos.get(1)));
        mBtn02.setEnabled(false);
        mBtn03.setBackground(getSelector(pos.get(2)));
        mBtn03.setEnabled(false);
        mBtn04.setBackground(getSelector(pos.get(3)));
        mBtn04.setEnabled(false);
        mBtn05.setBackground(getSelector(pos.get(4)));
        mBtn05.setEnabled(false);
        mBtn06.setBackground(getSelector(pos.get(5)));
        mBtn06.setEnabled(false);
        mBtn07.setBackground(getSelector(pos.get(6)));
        mBtn07.setEnabled(false);
        mBtn08.setBackground(getSelector(pos.get(7)));
        mBtn08.setEnabled(false);
        mBtn09.setBackground(getSelector(pos.get(8)));
        mBtn09.setEnabled(false);
        mBtn10.setBackground(getSelector(pos.get(9)));
        mBtn10.setEnabled(false);
        mBtn11.setBackground(getSelector(pos.get(10)));
        mBtn11.setEnabled(false);
        mBtn12.setBackground(getSelector(pos.get(11)));
        mBtn12.setEnabled(false);

        mBtnInput.setBackground(getSelector(13));
        mBtnShow.setBackground(getSelector(12));
        mBtnCamera.setBackground(getSelector(14));
        mBtnDel.setBackground(getSelector(19));

        imgBtnEnable();
    }

    private void btnUnshow(){
        cleanBtn();
        mBtn01.setBackground(getDrawableImage(15));
        mBtn01.setEnabled(true);
        mBtn02.setBackground(getDrawableImage(15));
        mBtn02.setEnabled(true);
        mBtn03.setBackground(getDrawableImage(15));
        mBtn03.setEnabled(true);
        mBtn04.setBackground(getDrawableImage(15));
        mBtn04.setEnabled(true);
        mBtn05.setBackground(getDrawableImage(15));
        mBtn05.setEnabled(true);
        mBtn06.setBackground(getDrawableImage(15));
        mBtn06.setEnabled(true);
        mBtn07.setBackground(getDrawableImage(15));
        mBtn07.setEnabled(true);
        mBtn08.setBackground(getDrawableImage(15));
        mBtn08.setEnabled(true);
        mBtn09.setBackground(getDrawableImage(15));
        mBtn09.setEnabled(true);
        mBtn10.setBackground(getDrawableImage(15));
        mBtn10.setEnabled(true);
        mBtn11.setBackground(getDrawableImage(15));
        mBtn11.setEnabled(true);
        mBtn12.setBackground(getDrawableImage(15));
        mBtn12.setEnabled(true);
        mBtnCamera.setBackground(getDrawableImage(18));
        mBtnCamera.setEnabled(true);
        mBtnShow.setBackground(getDrawableImage(16));  // 16
        mBtnShow.setEnabled(true);
        mBtnInput.setBackground(getDrawableImage(13));
        mBtnInput.setEnabled(true);
        mBtnDel.setBackground(getSelector(19));
        mBtnDel.setEnabled(true);
        nullpathimg();
    }

    public StateListDrawable getSelector(int number){
        Drawable drawable = getDrawableImage(number);
        return new StateDrawableBuilder()
                .setNormalDrawable(drawable)
                .build();
    }


    /*
             Function Name : nullpathimg
             이미지 버튼의 이미지를 초기화 하기 위한 함수
         */
    public void nullpathimg(){
        Log.d("Inhyo Test", "nulliconPath1 : " + nulliconPath);
        if(activityChk()) {
            if(btnType == 3) {
                Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView01);
                Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView02);
                Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView03);
                Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView04);
                Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView05);
                Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView06);
                Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView07);
                Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView08);
                Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView09);
                Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView10);
                Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView11);
                Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView12);
            }
        }
    }

    public boolean activityChk(){
        Activity activity = (Activity) LockScreenActivity.this;
        if(activity.isFinishing()) {
            return false;
        }
        return true;
    }

    public void cleanBtn(){

        if(activityChk()) {
            if ((btnType == 3) || (numType == 3)) {
                Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView01);
                Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView02);
                Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView03);
                Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView04);
                Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView05);
                Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView06);
                Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView07);
                Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView08);
                Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView09);
                Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView10);
                Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView11);
                Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView12);
            }
        }
    }

    public void imgBtnEnable(){
        Log.d("Inhyo Test", "imgRandomPath1 : " + imgRandomPath);
        if(activityChk()) {
            if ((btnType == 3) || (numType == 3)) {
                if(imgRandomPath.length() == 0){
                    imgRandomPath = errorPath;
                }
                Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView01);
                Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView02);
                Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView03);
                Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView04);
                Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView05);
                Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView06);
                Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView07);
                Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView08);
                Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView09);
                Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView10);
                Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView11);
                Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView12);
            }
        }
    }
}
