package com.otk.fts.myotk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;
import com.otk.fts.myotk.utils.StateDrawableBuilder;
import com.otk.fts.myotk.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class beforeSettingActivity extends Activity implements View.OnTouchListener, View.OnClickListener {
    private ImageButton mBtnShow;
    private ImageButton mBtnCamera;
    private ImageView img_Input;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    private Button button11;
    private Button button12;

    private Integer numType;
    private Integer btnType;

    private LinearLayout bottomLl;
    private ConstraintLayout bg_screen;

    // Custom 버튼 이미지 여부
    private boolean customBgImg;
    // Custom 버튼 이미지 경로
    private String customBgImgPath;

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

    private Vibrator vibrator;
    private Integer wrongCount, wrongTrigger, wrong_lockTimer;

    private WindowManager wm;
    private View mView;
    private String backupPin;
    private Handler handler;
    private Resources res;
    boolean isLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(msg -> false);
        res = getResources();
        //LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        getWindow().addFlags(
                // 기본 잠금화면보다 우선출력
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        // 기본 잠금화면 해제시키기
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        if(!Utils.isServiceRunning(this)){
            Utils.startService(getApplicationContext());

        }else QLog.d("service is run");

//        WindowManager.LayoutParams params;
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//            params = new WindowManager.LayoutParams(
//                    WindowManager.LayoutParams.MATCH_PARENT,
//                    WindowManager.LayoutParams.MATCH_PARENT,
//                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
//                    PixelFormat.TRANSLUCENT);
//        } else {
//            params = new WindowManager.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                            |WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                            |WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
//                            //|WindowManager.LayoutParams.FLAG_FULLSCREEN
//                            |WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                            |WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON,
//                    //| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                    //| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                    //| WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    PixelFormat.TRANSLUCENT);
//        }
//        params.gravity = Gravity.CENTER;
        //binding = DataBindingUtil.inflate(inflate, R.layout.activity_main, null, false);//DataBindingUtil.setContentView(this, R.layout.activity_main);
        boolean isLeft = PreferenceUtil.getBooleanPref(this, PreferenceUtil.SHOW_LEFT, true);
        int layout = isLeft ? R.layout.activity_main : R.layout.activity_main_right;
        ViewDataBinding binding = DataBindingUtil.setContentView(this, layout);
        mView = binding.getRoot();
        //mView.setLayoutParams(params);

        //mView = inflate.inflate(R.layout.activity_main, null);
//        mView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
//                // Set the content to appear under the system bars so that the
//                // content doesn't resize when the system bars hide and show.
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                // Hide the nav bar and status bar
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        bg_screen = mView.findViewById(R.id.Linear_bg);
        bottomLl = mView.findViewById((R.id.bottomLl));
        button1 = (Button) mView.findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) mView.findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = (Button) mView.findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = (Button) mView.findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = (Button) mView.findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6 = (Button) mView.findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button7 = (Button) mView.findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button8 = (Button) mView.findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button9 = (Button) mView.findViewById(R.id.button9);
        button9.setOnClickListener(this);
        button10 = (Button) mView.findViewById(R.id.button10);
        button10.setOnClickListener(this);
        button11 = (Button) mView.findViewById(R.id.button11);
        button11.setOnClickListener(this);
        button12 = (Button) mView.findViewById(R.id.button12);
        button12.setOnClickListener(this);

        mBtnShow = (ImageButton) mView.findViewById(R.id.btn_show);
        mBtnShow.setOnTouchListener(this);
        img_Input = (ImageView)mView.findViewById(R.id.input_img);
        img_Input.setOnTouchListener(this);
        mBtnCamera = (ImageButton) mView.findViewById((R.id.btn_camera));
        mBtnCamera.setOnTouchListener(this);

        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        //isActive = PreferenceUtil.getBooleanPref(this, PreferenceUtil.IS_LOCK, true);
        pwSize = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_SIZE, 2);
        //int lpw = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_LIST, 0);//sf.getInt("pwList", 0);
        String lpwStr = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST, "00");//sf.getInt("pwList", 0);
        int lpw = Integer.parseInt(lpwStr);
        backupPin = PreferenceUtil.getStringPref(this, PreferenceUtil.BACKUP_PIN, "0000");//sf.getInt("backupPin", 1234);
        f_timer = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_TIMER, 2000);//sf.getInt("pwTimer", 2000);
        numType = PreferenceUtil.getIntPref(this, PreferenceUtil.NUM_TYPE, 2);//sf.getInt("numType", 3);
        btnType = PreferenceUtil.getIntPref(this, PreferenceUtil.BTN_TYPE, 2);//sf.getInt("btnType", 3);
        customBgImg = PreferenceUtil.getBooleanPref(this, PreferenceUtil.CUSTOM_BG,false);//sf.getBoolean("customBgImg", false);
        customBgImgPath = PreferenceUtil.getStringPref(this, PreferenceUtil.CUSTOM_BG_PATH);//sf.getString("pwImgPath","");
        //QLog.d(numType+"/"+btnType);

        //mBtnShow.setImageResource(R.drawable.btn_new_aaa);//bottomSelector(res.getDrawable(R.drawable.input_show)
        //img_Input.setBackground(bottomSelector(res.getDrawable(R.drawable.input_2pw0)));
        //mBtnCamera.setBackground(bottomSelector(res.getDrawable(R.drawable.camera)));

        if(customBgImg){
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap originalBm = BitmapFactory.decodeFile(customBgImgPath, options);
            Drawable drawable = new BitmapDrawable(originalBm);
            bg_screen.setBackground(drawable);

            //File imgFile = new  File(customBgImgPath);
            //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            //bg_screen.setimage(myBitmap);
        }else {
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
        //wm.addView(mView, params);
    }

    @Override
    public void onResume(){
        super.onResume();
        wrongCount = 0;
        wrongTrigger = 0;
        shuffleKeyPad(pos);
        isStart = false;

        if(f_timer == 0){
            btnUnshow();
            bottomLl.setVisibility(View.VISIBLE);
        }

        else{
            btnShow();
            btnEnable(true);

            handler.postDelayed(() -> {
                btnUnshow();
                bottomLl.setVisibility(View.VISIBLE);
            }, f_timer);    //2초 뒤에
        }

        // test버젼
        //wrong_lockTimer = 100;
        
        // 실제버젼
        wrong_lockTimer = 10000;
    }

    @Override
    public void onDestroy(){
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        btnEnable(true);

        //wm.removeViewImmediate(mView);
        finishAffinity();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        btnEnable(true);
        super.onPause();
    }

//    @Override
//    protected void onStop(){
//        btnsEnable();
//        super.onStop();
//        //finishAffinity();
//    }

    private void btnEnable(boolean enable){
        button1.setEnabled(enable);
        button2.setEnabled(enable);
        button3.setEnabled(enable);
        button4.setEnabled(enable);
        button5.setEnabled(enable);
        button6.setEnabled(enable);
        button7.setEnabled(enable);
        button8.setEnabled(enable);
        button9.setEnabled(enable);
        button10.setEnabled(enable);
        button11.setEnabled(enable);
        button12.setEnabled(enable);
        mBtnShow.setEnabled(enable);
        img_Input.setEnabled(enable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if(input.size()<pw.size())
                    input.add(pos.get(0));
                break;
            case R.id.button2:
                if(input.size()<pw.size())
                    input.add(pos.get(1));
                break;
            case R.id.button3:
                if(input.size()<pw.size())
                    input.add(pos.get(2));
                break;
            case R.id.button4:
                if(input.size()<pw.size())
                    input.add(pos.get(3));
                break;
            case R.id.button5:
                if(input.size()<pw.size())
                    input.add(pos.get(4));
                break;
            case R.id.button6:
                if(input.size()<pw.size())
                    input.add(pos.get(5));
                break;
            case R.id.button7:
                if(input.size()<pw.size())
                    input.add(pos.get(6));
                break;
            case R.id.button8:
                if(input.size()<pw.size())
                    input.add(pos.get(7));
                break;
            case R.id.button9:
                if(input.size()<pw.size())
                    input.add(pos.get(8));
                break;
            case R.id.button10:
                if(input.size()<pw.size())
                    input.add(pos.get(9));
                break;
            case R.id.button11:
                if(input.size()<pw.size())
                    input.add(pos.get(10));
                break;
            case R.id.button12:
                if(input.size()<pw.size())
                    input.add(pos.get(11));
                break;
        }

        enterInput();
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

                        btnEnable(false);
                        handler.postDelayed(() -> {
                            btnUnshow();
                            btnEnable(true);
                        }, wrong_lockTimer);    //10초 뒤에
                    } else if(wrongTrigger>=5){
                        // PhoneLockForSeconds();
                        
                        btnEnable(false);
                        wrongTrigger=0;
                        handler.postDelayed(() -> {
                            btnUnshow();
                            btnEnable(true);
                        }, wrong_lockTimer);    //10초 뒤에
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
        if(isCameraClick){
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);
            finish();
        }

        else{
            Intent intent = new Intent(this, SettingsActivity.class); // 이동할 컴포넌트
            startActivity(intent);
            finish();
        }
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
        boolean[] isCheck = new boolean[12];
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

   private boolean isCameraClick = false;

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN :
                //v.setPadding(10,10,10,10);
                //v.setAlpha(0.55f);
                v.setBackground(res.getDrawable(R.drawable.press_blank));
                switch (v.getId()) {
                    //case R.id.btn_del:
                    case R.id.input_img:
                        if(input.size()!=0) {
                            input.remove(input.size() - 1);
                            enterInput();
                        }
                        break;
                    case R.id.btn_show:
                    case R.id.btn_camera:
                        shuffleKeyPad(pos);
                        btnShow();
                        break;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //v.setPadding(0,0,0,0);
                //v.setAlpha(1.0f);
                v.setBackgroundColor(Color.TRANSPARENT);
                if(v.getId()==R.id.btn_show)
                    btnUnshow();
                    isCameraClick = false;
                if(v.getId() == R.id.btn_camera){
                    btnUnshow();
                    isCameraClick = true;
                }
                break;
        }
        return true;
    }

    private void btnShow(){
        button1.setBackground(getSelector(pos.get(0)));
        button1.setEnabled(false);
        button2.setBackground(getSelector(pos.get(1)));
        button2.setEnabled(false);
        button3.setBackground(getSelector(pos.get(2)));
        button3.setEnabled(false);
        button4.setBackground(getSelector(pos.get(3)));
        button4.setEnabled(false);
        button5.setBackground(getSelector(pos.get(4)));
        button5.setEnabled(false);
        button6.setBackground(getSelector(pos.get(5)));
        button6.setEnabled(false);
        button7.setBackground(getSelector(pos.get(6)));
        button7.setEnabled(false);
        button8.setBackground(getSelector(pos.get(7)));
        button8.setEnabled(false);
        button9.setBackground(getSelector(pos.get(8)));
        button9.setEnabled(false);
        button10.setBackground(getSelector(pos.get(9)));
        button10.setEnabled(false);
        button11.setBackground(getSelector(pos.get(10)));
        button11.setEnabled(false);
        button12.setBackground(getSelector(pos.get(11)));
        button12.setEnabled(false);
    }

    private void btnUnshow(){
        button1.setBackground(getDrawableImage(12));
        button1.setEnabled(true);
        button2.setBackground(getDrawableImage(12));
        button2.setEnabled(true);
        button3.setBackground(getDrawableImage(12));
        button3.setEnabled(true);
        button4.setBackground(getDrawableImage(12));
        button4.setEnabled(true);
        button5.setBackground(getDrawableImage(12));
        button5.setEnabled(true);
        button6.setBackground(getDrawableImage(12));
        button6.setEnabled(true);
        button7.setBackground(getDrawableImage(12));
        button7.setEnabled(true);
        button8.setBackground(getDrawableImage(12));
        button8.setEnabled(true);
        button9.setBackground(getDrawableImage(12));
        button9.setEnabled(true);
        button10.setBackground(getDrawableImage(12));
        button10.setEnabled(true);
        button11.setBackground(getDrawableImage(12));
        button11.setEnabled(true);
        button12.setBackground(getDrawableImage(12));
        button12.setEnabled(true);
    }

    public StateListDrawable getSelector(int number) {
        Drawable drawable = getDrawableImage(number);
        return new StateDrawableBuilder()
                //.setDisabledDrawable(drawable)
                .setNormalDrawable(drawable)
                .setPressedDrawable(getDrawableImage(12))
                .setSelectedDrawable(getDrawableImage(12))
                .build();
    }
}
