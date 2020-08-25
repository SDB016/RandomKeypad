package com.otk.fts.myotk.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
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
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.ActivityUtil;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;
import com.otk.fts.myotk.utils.StateDrawableBuilder;
import com.otk.fts.myotk.utils.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.*;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;


public class beforeSettingActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    private PhotoView bgCustom;


    private boolean isActive;
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

    private ImageButton btn01;
    private ImageButton btn02;
    private ImageButton btn03;
    private ImageButton btn04;
    private ImageButton btn05;
    private ImageButton btn06;
    private ImageButton btn07;
    private ImageButton btn08;
    private ImageButton btn09;
    private ImageButton btn10;
    private ImageButton btn11;
    private ImageButton btn12;
    private ImageButton mBtnShow;
    private ImageButton mBtnCamera;
    private ImageButton mBtnInput;
    private ImageButton mBtnDel;

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

    private TextView stopTv;

    private int numType;
    private int btnType;
    private int basicBackground;

    private String nulliconPath;
    private String imgRandomPath;

    private int r2niconType;
    private String r2niconPath;

    private int directType;

    private LinearLayout bottomLl;

    // PassWord Size
    private int pwSize;

    // PassWord List
    private ArrayList<Integer> pw;
    // 입력 번호 List
    public ArrayList<Integer> input;
    // 키패드 순서 List
    private ArrayList<Integer> pos;

    // 초기 비밀번호 보이는 시간
    private int f_timer;

    // 비밀번호 입력 시도 제한
    private int inputCnt;

    private Vibrator vibrator;
    private Integer wrongCount, wrongTrigger, wrong_lockTimer;

    private WindowManager wm;
    private View mView;
    private String backupPin;
    private Handler handler;
    private Resources res;
    private boolean displaytimer;
    //private TextView txtTimer;s

    private String gifPath01;
    private boolean isNullKey = false;
    private boolean num2ImgChange = false;
    private boolean imgNumChk = false;
    private boolean isFirst = true;
    private boolean timeZeroChk = false;

    private boolean chkTimer = true;
    private boolean isCameraClick = false;

    private boolean delbtnchk = false;
    private String errorPath = "";

    private TextView time_txtview1;
    private TextView time_txtview2;

    private boolean sleeptime = false;
    private TimerTask tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(msg -> false);
        res = getResources();

        //LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (!Utils.isServiceRunning(beforeSettingActivity.this)) {
            Utils.startService(getApplicationContext());
        } else QLog.d("service is run");

        boolean isLeft = PreferenceUtil.getBooleanPref(this, PreferenceUtil.SHOW_LEFT, true);
        int layout = isLeft ? R.layout.activity_main : R.layout.activity_main_right;
        ViewDataBinding binding = DataBindingUtil.setContentView(this, layout);
        mView = binding.getRoot();
        Log.d("Inhyo Test ", "beforeSettingActivity : 3 ");

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

        btn01 = mView.findViewById(R.id.button1);
        btn01.setOnClickListener(this);
        btn02 = mView.findViewById(R.id.button2);
        btn02.setOnClickListener(this);
        btn03 = mView.findViewById(R.id.button3);
        btn03.setOnClickListener(this);
        btn04 = mView.findViewById(R.id.button4);
        btn04.setOnClickListener(this);
        btn05 = mView.findViewById(R.id.button5);
        btn05.setOnClickListener(this);
        btn06 = mView.findViewById(R.id.button6);
        btn06.setOnClickListener(this);
        btn07 = mView.findViewById(R.id.button7);
        btn07.setOnClickListener(this);
        btn08 = mView.findViewById(R.id.button8);
        btn08.setOnClickListener(this);
        btn09 = mView.findViewById(R.id.button9);
        btn09.setOnClickListener(this);
        btn10 = mView.findViewById(R.id.button10);
        btn10.setOnClickListener(this);
        btn11 = mView.findViewById(R.id.button11);
        btn11.setOnClickListener(this);
        btn12 = mView.findViewById(R.id.button12);
        btn12.setOnClickListener(this);

        stopTv = mView.findViewById(R.id.stopTv);

        mBtnShow = mView.findViewById(R.id.btn_show);
        mBtnInput = mView.findViewById(R.id.btn_input);
        mBtnCamera = mView.findViewById(R.id.btn_camera);
        mBtnDel = mView.findViewById(R.id.btn_delete);

        // txtTimer = (TextView)findViewById(R.id.txt_timer);

        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        isActive = PreferenceUtil.getBooleanPref(this, PreferenceUtil.IS_LOCK, true);
        pwSize = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_SIZE, 4);
        //int lpw = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_LIST, 0);//sf.getInt("pwList", 0);
        String lpwStr = "0";
        //String lpwStr = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST, "00");//sf.getInt("pwList", 0);

        //iNullKey = false;

        switch (pwSize){ //패스워드를 저장하고 설정한 패스워드가 없으면 패스워드 길이에 따라 0, 00등 초깃값으로 설정
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
        //imgBtnEnable(false);
        cleanBtn();

        int lpw = Integer.parseInt(lpwStr);
        backupPin = PreferenceUtil.getStringPref(this, PreferenceUtil.BACKUP_PIN, "0000");//sf.getInt("backupPin", 1234); //백업핀 저장 초깃값:0000
        f_timer = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_TIMER, 0);//sf.getInt("pwTimer", 2000);

        numType = PreferenceUtil.getIntPref(this, PreferenceUtil.NUM_TYPE, 2);//sf.getInt("numType", 3);
        if(numType != 3){
            imgRandomPath = "";
        }

        basicBackground = PreferenceUtil.getIntPref(this, PreferenceUtil.BASIC_BACKGROUND, 0);

        imgRandomPath = PreferenceUtil.getStringPref(this, PreferenceUtil.RANDOMICON_PATH);

        directType = PreferenceUtil.getIntPref(this, PreferenceUtil.DIRECT_TYPE, 0);
        btnType = PreferenceUtil.getIntPref(this, PreferenceUtil.BTN_TYPE, 2);//sf.getInt("btnType", 3);
        nulliconPath = PreferenceUtil.getStringPref(this, PreferenceUtil.NULLICON_PATH);//sf.getInt("btnType", 3);
        if(btnType != 3){
            nulliconPath = "";
        }

        // Custom 배 이미지 여부
        boolean customBgImg = PreferenceUtil.getBooleanPref(this, PreferenceUtil.CUSTOM_BG, false);///// false - color // true - photo

        // Custom 버튼 이미지 경로
        String customBgImgPath = PreferenceUtil.getStringPref(this, PreferenceUtil.CUSTOM_BG_PATH);//sf.getString("pwImgPath","");
        Log.d("Inhyo Test ","customBgImgPath : "+ customBgImgPath);
        inputCnt = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_CNT, 5);


        mBtnInput.setOnTouchListener(this);
        mBtnShow.setOnTouchListener(this);
        mBtnCamera.setOnTouchListener(this);
        mBtnDel.setOnTouchListener(this);

        if (customBgImg) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap originalBm = BitmapFactory.decodeFile(customBgImgPath, options);
            Drawable drawable = new BitmapDrawable(originalBm);
            bg_screen.setBackground(drawable);

        } else {

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
        // 비밀번호 배열 ( 임시비번 = 0000 )
        pw = new ArrayList<>();

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
        //wm.addView(mView, params);

        switchRandomBtn();  //20200409 Test By Inhyo
    }

    @Override
    public void onResume() {
        super.onResume();
        wrongCount = 0;
        wrongTrigger = 0;
        //iNullKey = false;
        //shuffleKeyPad(pos);
        //btnShow();
        //btnEnable(true);
        //run();

        // test버젼
        //wrong_lockTimer = 100;

        // 실제버젼
        wrong_lockTimer = 10000;
    }

    private void switchRandomBtn(){

        if(f_timer == 0){
            if (!timeZeroChk) {
                shuffleKeyPad(pos);
                btnShow();
                btnEnable(true);
            } else {
                btnUnshow();
            }

            //isNullKey = !isNullKey;
        } else {
            if(chkTimer){
                if (!num2ImgChange) {
                    shuffleKeyPad(pos);
                    btnShow();
                    btnEnable(true);
                } else {
                    btnUnshow();
                }
                num2ImgChange = !num2ImgChange;
                handler.removeCallbacksAndMessages(null);
                Log.d("Inhyo Test", "f_timer : " + num2ImgChange);
                handler.postDelayed(this::switchRandomBtn, f_timer);    //2초 뒤에
            } else {
               if (isNullKey) {
                    shuffleKeyPad(pos);
                    btnShow();
                    btnEnable(true);
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


    @Override
    public void onDestroy() {
        if (handler != null) {
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

    private void btnEnable(boolean enable) { //버튼 활성화 혹은 비활성화

        btn01.setEnabled(enable);
        btn02.setEnabled(enable);
        btn03.setEnabled(enable);
        btn04.setEnabled(enable);
        btn05.setEnabled(enable);
        btn06.setEnabled(enable);
        btn07.setEnabled(enable);
        btn08.setEnabled(enable);
        btn09.setEnabled(enable);
        btn10.setEnabled(enable);
        btn11.setEnabled(enable);
        btn12.setEnabled(enable);

    }

    @Override
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
            btnEnable(true);
        } else {
            btnUnshow();
        }

    }

    private void CheckPW() { //입력받은 패스워드를 체크하는 부분
        if (input.size() == pw.size()) { //입력받은 길이와 패스워드 길이가 같을때
            int index = 0; //입력값과 패스워드를 한자리씩 비교하기 위해 인덱스 할당
            int backupPinNum = Integer.parseInt(backupPin);

            while (index < input.size()) { //인덱스가 입력받은 패스워드 길이와 같을때까지 반복
                if (!input.get(index).equals(pw.get(index))) { //입력의 인덱스와 패스워드 인덱스가 다를때
                    vibrator.vibrate(100); //0.1초 진동
                    wrongCount += 1;
                    wrongTrigger += 1;
                    if (wrongTrigger >= inputCnt && wrongCount >= 15) { //trigger와 wrongCount가 조건에 만족할때; 엄청 틀렸을 때
                        // PhoneLockForSeconds();
                        wrongTrigger = 0;
                        pw.clear(); //패스워드 초기화
                        int a = backupPinNum / 1000;
                        int b = (backupPinNum % 1000) / 100;
                        int c = (backupPinNum % 100) / 10;
                        int d = backupPinNum % 10;
                        pw.add(a);
                        pw.add(b);
                        pw.add(c);
                        pw.add(d);  //4자리 백업 비밀번호로 비밀번호 자동 재설정
                        pwSize = 4;
                        mBtnInput.setImageResource(R.drawable.img_trans); //버튼 이미지를 투명하게 만듬

                        PreferenceUtil.savePref(this, PreferenceUtil.PW_SIZE, pwSize); //패스워드 사이즈 저장
                        PreferenceUtil.savePref(this, PreferenceUtil.PW_LIST4, backupPin); //백업핀 저장

                        btnEnable(false); //키패드 비활성화
                        handler.removeCallbacksAndMessages(null); //큐에 있는 모든 메세지 삭제
                        handler.postDelayed(() -> {
                            //cleanBtn();
                            btnUnshow(); //버튼 숨기기
                            btnEnable(true); //버튼 활성화
                        }, wrong_lockTimer);    //10초 뒤에*/
                    } else if (wrongTrigger >= inputCnt) { //wrongtrigger가 입력 카운트보다 많을때
                        btnEnable(false); //버튼 비활성화
                        wrongTrigger=0;
                        sleeptime = true;  //??
                        handler.removeCallbacksAndMessages(null); //큐에 있는 모든 메세지 삭제
                        handler.postDelayed(() -> {
                            btnUnshow(); //버튼 숨기기
                            btnEnable(true); //버튼 활성화
                        }, wrong_lockTimer); //   //10초 뒤에

                    }

                    removeMessage();
                    delbtnchk = false; //삭제 버튼 비활성화
                    handler.postDelayed(() -> {
                        Incorrect();
                        input.clear(); //입력 패스워드 삭제
                        enterInput();
                    }, 200);    //0.2초 뒤에
                    return;
                }
                index++; //인덱스 증가
            }
            //handler.postDelayed(this::Unlock, 200);    //0.2초 뒤에
            Unlock();
        }
    }

    private void Unlock() {
        if (isCameraClick) {
            Log.d("Test : ","unLock() --> isCameraClick ");
            Utils.stopService(getApplicationContext());
            PreferenceUtil.savePref(this, PreferenceUtil.IS_LOCK, false);
            finish();

        } else {
            Log.d("Test : ","unLock() --> isCameraClick false");
            Intent intent = new Intent(this, Settings2Activity.class); // 이동할 컴포넌트
            startActivity(intent);
            finish();
        }
    }

    private void Incorrect() { //버튼을 투명하게 만들고 셔플 후 버튼 보이기
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
        btnEnable(true);
    }

    private void enterInput() { //인덱스 아이콘 증가
        if(pwSize == 1){ //패스워드 길이가 1자리
            delbtnchk = true; //취소버튼 활성화
            if (input.size() == 1) { //입력값 길이가 1 일떄
                mBtnInput.setImageResource(R.drawable.btn_input_1); //인덱스 아이콘_1 출력
            } else { //입력값 길이가 0자리
                delbtnchk = false; //취소버튼 비활성화
                mBtnInput.setImageResource(R.drawable.btn_input_0); //인덱스 아이콘_0 출력
            }
        } else if (pwSize == 2) { //패스워드 길이가 2자리
            delbtnchk = true; //취소버튼 활성화
            if (input.size() == 1) { //입력값 길이가 1 일떼
                mBtnInput.setImageResource(R.drawable.btn_input_1); //인덱스 아이콘_1 출력
            } else if (input.size() == 2) { //입력값 길이가 2 일때
                mBtnInput.setImageResource(R.drawable.btn_input_2); //인덱스 아이콘_2 출력
            } else { //입력값 길이가 0 일떼
                delbtnchk = false; //취소버튼 비활성화
                mBtnInput.setImageResource(R.drawable.btn_input_0); //인덱스 아이콘_0 출력
            }
        } else if (pwSize == 3) { //패스워드 길이가 3자리
            delbtnchk = true; //취소버튼 활성화
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
        } else if (pwSize == 4){ //패스워드 길이가 4자리
            delbtnchk = true; //취소버튼 활성화
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

    public Drawable getDrawableImage(int number) {
        int imgNum = R.drawable.img_trans;

        switch (number) {
            case 0:
                switch (numType) {

                    case 1:
                        return res.getDrawable(R.drawable.btn_trans_0);
                    case 2:
                        return res.getDrawable(R.drawable.btn_line_0);
                    case 3:
                        if(activityChk()){
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
                        if(activityChk()){
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
                        if(activityChk()){
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
                        if(activityChk()){
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
                        if(activityChk()){
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
                        if(activityChk()){
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
                        if(activityChk()){
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
                        if(activityChk()){
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
                        if(activityChk()){
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
                        if(activityChk()){
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
                        if(activityChk()){
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
                        if(activityChk()){
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
                        if(activityChk()){
                            // Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewShow);
                        }
                        return res.getDrawable(R.drawable.tran_3x5_r2n);
                }
                break;
            case 13:
                mBtnDel.setImageResource(R.drawable.btn_del);
                /*
                switch (numType) {

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
                        if(activityChk()){
                            Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewInput);
                        }
                        if(!delbtnchk) {
                            imgBtnViewInput.setImageResource(R.drawable.btn_del_line);
                        } else {
                            imgBtnViewInput.setImageResource(R.drawable.img_line_album);
                        }
                        return res.getDrawable(R.drawable.btn_del_line);
                }*/
                break;
            case 14:
                switch (numType) {
                    case 0:
                        switch (directType){
                            case 0:
                                imgNum = R.drawable.btn_camera;
                                break;

                            case 2:
                                imgNum = R.drawable.btn_calendar;
                                break;

                        }
                        return res.getDrawable(imgNum);
                    case 1:
                        switch (directType){
                            case 0:
                                imgNum = R.drawable.btn_camera;
                                break;

                            case 2:
                                imgNum = R.drawable.btn_calendar;
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

                        }
                        if(activityChk()){
                            //    Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewCamera);
                        }
                        return res.getDrawable(imgNum);
                }
                break;
            case 15: // Null Keypad
                switch (btnType) {  // 0 -- gray // 1- trans // 2 - line // 3 - album // 4 - point

                    case 1:
                        return res.getDrawable(R.drawable.btn_new_btn);
                    //return res.getDrawable(R.drawable.btn_new_btn);
                    case 2:
                        return res.getDrawable(R.drawable.btn_new_btn2);
                    //return res.getDrawable(R.drawable.btn_new_btn2);
                    case 3:
                        //nullpathimg();
                        return res.getDrawable(R.drawable.btn_new_btn3);
                    //return res.getDrawable(R.drawable.blank_zero);
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
                        if(activityChk()){
                            //  Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewShow);
                        }
                        return res.getDrawable(R.drawable.tran_3x5_r2n);
                }
                break;
            case 17:

                imgBtnViewInput.setImageResource(R.drawable.btn_input_0);
                /*
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
                break;*/
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
                        if(activityChk()){
                            //   Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewCamera);
                        }
                        return res.getDrawable(imgNum);
                }
                break;
            case 19:
                mBtnDel.setImageResource(R.drawable.btn_del);
                /*switch (btnType) {
                    case 1:
                        if(!delbtnchk) {

                        }else {
                            imgBtnViewDel.setImageResource(R.drawable.btn_del);
                        }
                        return res.getDrawable(R.drawable.btn_del);
                    case 2:
                        if(!delbtnchk) {
                            imgBtnViewDel.setImageResource(R.drawable.btn_del);
                        } else {
                            imgBtnViewDel.setImageResource(R.drawable.btn_del);
                        }
                        return res.getDrawable(R.drawable.btn_del);
                    case 3:
                        if(activityChk()) {
                            Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewInput);
                        }
                        if(!delbtnchk) {
                            imgBtnViewDel.setImageResource(R.drawable.btn_del);
                        } else {
                            imgBtnViewDel.setImageResource(R.drawable.btn_del);
                        }
                        return res.getDrawable(R.drawable.btn_del);
                }*/
                break;
        }
        return null;
    }

    public void shuffleKeyPad(ArrayList<Integer> posArr) { //키패드 셔플
        int index = 0;
        Random random = new Random();
        boolean[] isCheck = new boolean[12];
        while (index < posArr.size()) {
            int rand_num = random.nextInt(12);
            if (!isCheck[rand_num]) {
                posArr.set(index, rand_num);
                isCheck[rand_num] = true;
                index++;
            }
            if (index == posArr.size())
                break;
        }
    }



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
                                    btnEnable(true);
                                } else {
                                    btnUnshow();
                                }
                            }
                            imgNumChk = !imgNumChk;

                        } else {

                            if(imgNumChk){
                                shuffleKeyPad(pos);
                                btnShow();
                                btnEnable(true);
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
                    handler.removeCallbacksAndMessages(null);
                    imgBtnViewShow.setBackground(res.getDrawable(R.drawable.img_trans));
                    if(!isNullKey){
                        mBtnShow.setBackground(getSelector(12));
                        mBtnShow.setEnabled(true);
                    } else {
                        mBtnShow.setBackground(getSelector(16));
                        mBtnShow.setEnabled(true);
                    }

                } else if (v.getId() == R.id.btn_camera) {
                    if(isNullKey){
                        mBtnCamera.setBackground(getDrawableImage(14));
                        mBtnCamera.setEnabled(true);
                    } else {
                        mBtnCamera.setBackground(getDrawableImage(18));
                        mBtnCamera.setEnabled(true);
                    }
                    isCameraClick = true;
                } else if(v.getId() == R.id.btn_delete){

                }
                break;
        }
        return true;
    }

    private void otherAppRun(int type){
        PackageManager pm = getApplicationContext().getPackageManager();
        Intent intent = intent = pm.getLaunchIntentForPackage("");
        switch (type){
            case 0:
                callCamera();
                break;
            case 1:
                //callCamera();
                intent = pm.getLaunchIntentForPackage("com.nhn.android.nmap");
                break;
            case 2:
                intent = pm.getLaunchIntentForPackage("com.samsung.android.calendar");
                break;
            case 3:
                intent = pm.getLaunchIntentForPackage("");
                break;

        }

        if(intent != null) {
            if(type != 0) {
                intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_NO_ANIMATION | FLAG_ACTIVITY_SINGLE_TOP);
                intent.setFlags(0);
                ActivityUtil.moveTop(this, intent, false);
                //getApplicationContext().startActivity(intent);
                startActivity(intent);
            }
        }
    }

    private void callCamera(){
        Log.d("Inhyo Test", "call Camera Run");

        // if(Utils.isServiceRunning(this)){
        Utils.stopService(this);
        //}

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //i.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT | FLAG_ACTIVITY_SINGLE_TOP);
        try{
            PackageManager pm = getPackageManager();

            final ResolveInfo mInfo = pm.resolveActivity(i,0);
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(mInfo.activityInfo.packageName, mInfo.activityInfo.name));
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_NEW_TASK |FLAG_ACTIVITY_NO_ANIMATION | FLAG_ACTIVITY_SINGLE_TOP);

            //getApplicationContext().startActivity(intent);
            ActivityUtil.moveTop(this, intent, false);
            startActivity(intent);
        } catch (Exception e){
            Log.d("Inhyo Test", "Unable to launch Camera : " + e);
        }
    }

    private void btnShow() {
        cleanBtn();
        btn01.setBackground(getSelector(pos.get(0)));
        btn01.setEnabled(false);
        btn02.setBackground(getSelector(pos.get(1)));
        btn02.setEnabled(false);
        btn03.setBackground(getSelector(pos.get(2)));
        btn03.setEnabled(false);
        btn04.setBackground(getSelector(pos.get(3)));
        btn04.setEnabled(false);
        btn05.setBackground(getSelector(pos.get(4)));
        btn05.setEnabled(false);
        btn06.setBackground(getSelector(pos.get(5)));
        btn06.setEnabled(false);
        btn07.setBackground(getSelector(pos.get(6)));
        btn07.setEnabled(false);
        btn08.setBackground(getSelector(pos.get(7)));
        btn08.setEnabled(false);
        btn09.setBackground(getSelector(pos.get(8)));
        btn09.setEnabled(false);
        btn10.setBackground(getSelector(pos.get(9)));
        btn10.setEnabled(false);
        btn11.setBackground(getSelector(pos.get(10)));
        btn11.setEnabled(false);
        btn12.setBackground(getSelector(pos.get(11)));
        btn12.setEnabled(false);
        mBtnInput.setBackground(getSelector(13));
        //mBtnInput.setEnabled(false);
        mBtnShow.setBackground(getSelector(12));
        //mBtnShow.setEnabled(false);
        mBtnCamera.setBackground(getSelector(14));
        //mBtnCamera.setEnabled(false);

        mBtnDel.setBackground(getSelector(19));

        imgBtnEnable();
    }

    private void btnUnshow() {
        cleanBtn();
        btn01.setBackground(getDrawableImage(15));
        btn01.setEnabled(true);
        btn02.setBackground(getDrawableImage(15));
        btn02.setEnabled(true);
        btn03.setBackground(getDrawableImage(15));
        btn03.setEnabled(true);
        btn04.setBackground(getDrawableImage(15));
        btn04.setEnabled(true);
        btn05.setBackground(getDrawableImage(15));
        btn05.setEnabled(true);
        btn06.setBackground(getDrawableImage(15));
        btn06.setEnabled(true);
        btn07.setBackground(getDrawableImage(15));
        btn07.setEnabled(true);
        btn08.setBackground(getDrawableImage(15));
        btn08.setEnabled(true);
        btn09.setBackground(getDrawableImage(15));
        btn09.setEnabled(true);
        btn10.setBackground(getDrawableImage(15));
        btn10.setEnabled(true);
        btn11.setBackground(getDrawableImage(15));
        btn11.setEnabled(true);
        btn12.setBackground(getDrawableImage(15));
        btn12.setEnabled(true);

        mBtnCamera.setBackground(getDrawableImage(18));
        mBtnCamera.setEnabled(true);
        mBtnShow.setBackground(getDrawableImage(16));
        mBtnShow.setEnabled(true);
        mBtnInput.setBackground(getDrawableImage(13));
        mBtnInput.setEnabled(true);
        mBtnDel.setBackground(getDrawableImage(19));
        mBtnDel.setEnabled(true);
        nullpathimg();

    }


    public StateListDrawable getSelector(int number) {
        Drawable drawable = getDrawableImage(number);
        return new StateDrawableBuilder()
                //.setDisabledDrawable(drawable)
                .setNormalDrawable(drawable)
                .setPressedDrawable(getDrawableImage(15))
                .setSelectedDrawable(getDrawableImage(15))
                .build();
    }

    public boolean activityChk(){
        Activity activity = (Activity) beforeSettingActivity.this;
        if(activity.isFinishing()) {
            return false;
        }
        return true;
    }

    public void nullpathimg(){
        if(activityChk()){
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
                //Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewShow);
                //Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewInput);
                //Glide.with(this).load(nulliconPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewCamera);
            }
        }
    }

    public void cleanBtn(){
        if(activityChk()) {
            if ((btnType == 3)||(numType == 3)) {
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
                //Glide.with(this).load(R.drawable.null_trans_3x5).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewShow);
                //Glide.with(this).load(R.drawable.img_trans).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewInput);
                //Glide.with(this).load(R.drawable.null_trans_3x5).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewCamera);
            }
        }
    }

    public void imgBtnEnable(){
        Log.d("Inhyo Test", "imgRandomPath1 : " + imgRandomPath);
        if(activityChk()) {
            if ((btnType == 3) || (numType == 3)) {

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
                //Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewShow);
                //Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewInput);
                //Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnViewCamera);
            }
        }
    }
}
