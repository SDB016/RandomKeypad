package com.otk.fts.myotk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.otk.fts.myotk.R;
import com.otk.fts.myotk.setting.BackgroundActivity;
import com.otk.fts.myotk.setting.NullKeyFullActivity;
import com.otk.fts.myotk.setting.PWinputActivity;
import com.otk.fts.myotk.setting.PwNBackupActivity;
import com.otk.fts.myotk.setting.R2NiconPositionActivity;
import com.otk.fts.myotk.setting.RandKeyFullActivity;
import com.otk.fts.myotk.setting.RandomKeyTimerActivity;
import com.otk.fts.myotk.setting.ScreenLockHelpActivity;
import com.otk.fts.myotk.setting.ShortcutActivity;
import com.otk.fts.myotk.setting.SoftwareInfoActivity;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.Utils;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Settings2Activity extends AppCompatActivity {

    ArrayList<SampleData> settingDataList;

    private boolean powerOn;
    private int pwNbackup;
    private int pwSize;
    private int direct;
    private int bg_type;
    private int bg_num;
    private int bg_time;
    private boolean powerOnoff;
    private boolean customBGImg;
    private int phoneGps;
    private int phoneGPSTime = 0;
    private boolean displayTimer;
    private int pwTimer;
    private int pwinputtime;
    private boolean showLeft;
    private int randomkeypad;
    private int nullkeypad;
    private int r2niconType;
    private int directIconType;
    private int pwCount;
    private boolean fpUsed;

    private static final int REQUEST_TITILE0 = 0;
    private static final int REQEUST_ISLOCK = 1;             // 랜덤버튼 사용
    private static final int REQUEST_PWNBACKUP = 2;          // 패스워드
    private static final int REQUEST_PWINPUT_TIMER = 3;      // 패스워드 입력시도 제한
    private static final int REQUEST_RANDOMTIME = 4;         // 랜던버튼 동작 시간
    private static final int REQUEST_R2NICON_POSITION = 5;   // R2N 아이콘 위치
    private static final int REQUEST_DIRECT = 6;             // 바로가기
    private static final int REQUEST_IMAGE_TITME = 7;        // 이미지 변경 타이틀
    private static final int REQUEST_BACKGROUD = 8;          // 배경화면
    private static final int REQUEST_RANDOMKEYPAD_FULL = 9; // 랜덤 숫자 버튼
    private static final int REQUEST_NULLKEYPAD_FULL = 10;   // 널이미지 버튼
    private static final int REQUEST_NORMAL_TITLE = 11;       // 일반 타이틀
    private static final int REQUEST_USED_HELP = 12;         // 사용방법
    private static final int REQUEST_SOFTWARE_INFO = 13;     // 소프트웨어 정보

    private ListView listView;
    private settingAdapter setting_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting4);

        this.InitializeSettingData();

        listView = (ListView)findViewById(R.id.setting_list);
        setting_adapter = new settingAdapter(this, settingDataList);
        listView.setAdapter(setting_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                otherActivity(position);
            }
        });


    }

    public void InitializeSettingData(){
        settingDataList = new ArrayList<SampleData>();
        powerOn = PreferenceUtil.getBooleanPref(this, PreferenceUtil.IS_LOCK, true);
        pwSize = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_SIZE, 4);
        direct = PreferenceUtil.getIntPref(this, PreferenceUtil.DIRECT_TYPE, 0);  // 0 - 카메라 ; 1 - 네비 ; 2 - 캘린더 ; 3 -- 끔
        customBGImg = PreferenceUtil.getBooleanPref(this, PreferenceUtil.CUSTOM_BG, false);
        pwTimer = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_TIMER, 0);
        showLeft = PreferenceUtil.getBooleanPref(this, PreferenceUtil.SHOW_LEFT, true);
        pwCount = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_CNT, 10);
        directIconType = PreferenceUtil.getIntPref(this, PreferenceUtil.DIRECT_ICON_TYPE, 0);
        randomkeypad = PreferenceUtil.getIntPref(this, PreferenceUtil.NUM_TYPE, 2);
        nullkeypad = PreferenceUtil.getIntPref(this, PreferenceUtil.BTN_TYPE, 2);
        settingDataList.add(new SampleData(R.drawable.icon_r2n_icon, "", "", "", R.drawable.onoff_off));

        // 화면 잠금
        if(powerOn){
            settingDataList.add(new SampleData(R.drawable.icon_screenlock, getString(R.string.setting_use_lock), "", "", R.drawable.onoff_on));
        } else {
            settingDataList.add(new SampleData(R.drawable.icon_screenlock, getString(R.string.setting_use_lock), "", "", R.drawable.onoff_off));
        }
        Log.d("Inhyo Test", "PowerON : " + powerOn);

        // 패스워드 백업
        switch (pwSize){
            case 1:
                settingDataList.add(new SampleData(R.drawable.icon_password, getString(R.string.setting_pwbackup),  getString(R.string.pwbackup_1digit), "",  R.drawable.onoff_on));
                break;
            case 2:
                settingDataList.add(new SampleData(R.drawable.icon_password, getString(R.string.setting_pwbackup),  getString(R.string.pwbackup_2digit), "",  R.drawable.onoff_on));
                break;
            case 3:
                settingDataList.add(new SampleData(R.drawable.icon_password, getString(R.string.setting_pwbackup),  getString(R.string.pwbackup_3digit), "",  R.drawable.onoff_on));
                break;
            case 4:
                settingDataList.add(new SampleData(R.drawable.icon_password, getString(R.string.setting_pwbackup),  getString(R.string.pwbackup_4digit), "",  R.drawable.onoff_on));
                break;
        }

        // 패스워드 입력 시도 제한

        switch (pwCount){
            case 0:
                settingDataList.add(new SampleData(R.drawable.icon_pw_input0, getString(R.string.setting_pwinput), getString(R.string.pwinput_chance_0), "" , R.drawable.onoff_off));
                break;
            case 3:
                settingDataList.add(new SampleData(R.drawable.icon_pw_input3, getString(R.string.setting_pwinput), getString(R.string.pwinput_chance_3), "" , R.drawable.onoff_off));
                break;
            case 5:
                settingDataList.add(new SampleData(R.drawable.icon_pw_input5, getString(R.string.setting_pwinput), getString(R.string.pwinput_chance_5), "" , R.drawable.onoff_off));
                break;
            case 10:
                settingDataList.add(new SampleData(R.drawable.icon_pw_input10, getString(R.string.setting_pwinput), getString(R.string.pwinput_chance_10), "" , R.drawable.onoff_off));
                break;
        }

        // 랜덤 키패드 동작 시
        switch (pwTimer){
            case 0:
                settingDataList.add(new SampleData(R.drawable.icon_random_time, getString(R.string.setting_rdkeytime), getString(R.string.rdkeytimer_time_0), "" , R.drawable.onoff_off));
                break;
            case 2000:
                settingDataList.add(new SampleData(R.drawable.icon_random_time, getString(R.string.setting_rdkeytime), getString(R.string.rdkeytimer_time_2), "" , R.drawable.onoff_off));
                break;
            case 3000:
                settingDataList.add(new SampleData(R.drawable.icon_random_time, getString(R.string.setting_rdkeytime), getString(R.string.rdkeytimer_time_3), "" , R.drawable.onoff_off));
                break;
            case 4000:
                settingDataList.add(new SampleData(R.drawable.icon_random_time, getString(R.string.setting_rdkeytime), getString(R.string.rdkeytimer_time_4), "" , R.drawable.onoff_off));
                break;
            case 5000:
                settingDataList.add(new SampleData(R.drawable.icon_random_time, getString(R.string.setting_rdkeytime), getString(R.string.rdkeytimer_time_5), "" , R.drawable.onoff_off));
                break;
            case 10000:
                settingDataList.add(new SampleData(R.drawable.icon_random_time, getString(R.string.setting_rdkeytime), getString(R.string.rdkeytimer_time_10), "" , R.drawable.onoff_off));
                break;
        }

        // R2N 아이콘 위치
        if(showLeft){
            settingDataList.add(new SampleData(R.drawable.icon_r2n_icon, getString(R.string.setting_r2nposition), getString(R.string.r2nposition_left), "", R.drawable.onoff_off));
        } else {
            settingDataList.add(new SampleData(R.drawable.icon_r2n_icon, getString(R.string.setting_r2nposition), getString(R.string.r2nposition_right), "", R.drawable.onoff_off));
        }

        // 바로 가기
        switch (direct){
            case 0:
                settingDataList.add(new SampleData(R.drawable.icon_null_camera, getString(R.string.setting_shortcut), getString(R.string.shortcuts_camera), getString(R.string.shortcuts_subtitle), R.drawable.onoff_off));
                break;
            case 1:
                settingDataList.add(new SampleData(R.drawable.icon_null_navigation, getString(R.string.setting_shortcut), getString(R.string.shortcuts_camera), getString(R.string.shortcuts_subtitle), R.drawable.onoff_off));
                break;
            case 2:
                settingDataList.add(new SampleData(R.drawable.icon_null_calendar, getString(R.string.setting_shortcut), getString(R.string.shortcuts_calendar), getString(R.string.shortcuts_subtitle), R.drawable.onoff_off));
                break;

        }

        // 이미지 Titile
        settingDataList.add(new SampleData(R.drawable.icon_r2n_icon, getString(R.string.setting_image_title), "", "", R.drawable.onoff_off));

        // 배경 화면
        if(customBGImg){
            settingDataList.add(new SampleData(R.drawable.icon_background, getString(R.string.setting_background), getString(R.string.background_switch2_txt), "", R.drawable.onoff_off));
        } else {
            settingDataList.add(new SampleData(R.drawable.icon_background, getString(R.string.setting_background), getString(R.string.background_switch1_txt), "", R.drawable.onoff_off));
        }

        // 랜덤 키패드

        switch (randomkeypad){
            case 0:
                settingDataList.add(new SampleData(R.drawable.icon_gray, getString(R.string.setting_randomkey), getString(R.string.randomkey_trans), "", R.drawable.onoff_off));
                break;
            case 1:
                settingDataList.add(new SampleData(R.drawable.icon_trans, getString(R.string.setting_randomkey), getString(R.string.randomkey_trans), "", R.drawable.onoff_off));
                break;
            case 2:
                settingDataList.add(new SampleData(R.drawable.icon_random_keypad, getString(R.string.setting_randomkey), getString(R.string.randomkey_line), "", R.drawable.onoff_off));
                break;
            case 3:
                settingDataList.add(new SampleData(R.drawable.icon_album, getString(R.string.setting_randomkey), getString(R.string.randomkey_album), "", R.drawable.onoff_off));
                break;

        }

        // 널 키패드
        switch (nullkeypad){
            case 0:
                settingDataList.add(new SampleData(R.drawable.icon_null_gray, getString(R.string.setting_nullkey), getString(R.string.nullkey_trans), "", R.drawable.onoff_off));
                break;
            case 1:
                settingDataList.add(new SampleData(R.drawable.icon_trans, getString(R.string.setting_nullkey), getString(R.string.nullkey_trans), "", R.drawable.onoff_off));
                break;
            case 2:
                settingDataList.add(new SampleData(R.drawable.icon_null_keypad, getString(R.string.setting_nullkey), getString(R.string.nullkey_line), "", R.drawable.onoff_off));
                break;
            case 3:
                settingDataList.add(new SampleData(R.drawable.icon_null_album, getString(R.string.setting_nullkey), getString(R.string.nullkey_album), "", R.drawable.onoff_off));
                break;
        }

        // 프로그램 정보 Title
        settingDataList.add(new SampleData(R.drawable.icon_r2n_icon, getString(R.string.setting_normal_title), "", "", R.drawable.onoff_off));

        // 사용 방법
        settingDataList.add(new SampleData(R.drawable.icon_lock_help, getString(R.string.setting_screenlock_help), "", "", R.drawable.onoff_off));

        // 소프트 웨어 정보
        settingDataList.add(new SampleData(R.drawable.icon_software_info, getString(R.string.setting_software_info), "", "", R.drawable.onoff_off));


    }



    public void otherActivity(int position){
        Intent intent;

        SampleData element;

        switch (position){
            case REQEUST_ISLOCK: // 랜덤 버튼 사용
                powerOn = !powerOn;
                //Toast.makeText(getApplicationContext(),"Other Activity Power on = " + powerOn, Toast.LENGTH_LONG).show();
                Log.d("Inhyo Test", "Other Activity Power on = " + powerOn);
                if(powerOn) {
                    element = new SampleData(R.drawable.icon_screenlock, getString(R.string.setting_use_lock), "", "", R.drawable.onoff_on);
                    Utils.startService(getApplicationContext());
                } else {
                    element = new SampleData(R.drawable.icon_screenlock, getString(R.string.setting_use_lock), "", "", R.drawable.onoff_off);
                    Utils.stopService(getApplicationContext());
                }
                settingDataList.set(position, element);
                PreferenceUtil.savePref(this, PreferenceUtil.IS_LOCK, powerOn);
                break;
            case REQUEST_PWNBACKUP: // 패스워드, 백업 PIN
                intent = new Intent(this, PwNBackupActivity.class); // 이동할 컴포넌트
                startActivityForResult(intent, REQUEST_PWNBACKUP);
                break;
            case REQUEST_PWINPUT_TIMER: // 패스워드 입력 시도 제한
                intent = new Intent(this, PWinputActivity.class); // 이동할 컴포넌트
                startActivityForResult(intent, REQUEST_PWINPUT_TIMER);
                break;
            case REQUEST_RANDOMTIME: // 랜덤버튼 동작시간
                intent = new Intent(this, RandomKeyTimerActivity.class); // 이동할 컴포넌트
                startActivityForResult(intent, REQUEST_RANDOMTIME);
                break;
            case REQUEST_R2NICON_POSITION: // R2N 아이콘 위치
                intent = new Intent(this, R2NiconPositionActivity.class); // 이동할 컴포넌트
                startActivityForResult(intent, REQUEST_R2NICON_POSITION);
                break;
            case REQUEST_DIRECT: // 바로 가기
                intent = new Intent(this, ShortcutActivity.class); // 이동할 컴포넌트
                //startActivity(intent);
                startActivityForResult(intent,REQUEST_DIRECT);
                break;
            case REQUEST_IMAGE_TITME: // 버튼 이미지 Title
                //intent = new Intent(this, RandKeyFullActivity.class); // 이동할 컴포넌트
                //startActivityForResult(intent, REQUEST_RANDOMKEYPAD_FULL);
                break;
            case REQUEST_BACKGROUD: // 배경화면 꾸미기
                intent = new Intent(this, BackgroundActivity.class); // 이동할 컴포넌트
                startActivityForResult(intent, REQUEST_BACKGROUD);
                break;
            case REQUEST_RANDOMKEYPAD_FULL: // 랜덤 키패드
                intent = new Intent(this, RandKeyFullActivity.class); // 이동할 컴포넌트
                startActivityForResult(intent, REQUEST_RANDOMKEYPAD_FULL);
                break;
            case REQUEST_NULLKEYPAD_FULL: // 널 키패드
                intent = new Intent(this, NullKeyFullActivity.class); // 이동할 컴포넌트
                startActivityForResult(intent, REQUEST_NULLKEYPAD_FULL);
                break;
            case REQUEST_NORMAL_TITLE: // 프로그램 정보 Title
                //intent = new Intent(this, ScreenLockHelpActivity.class); // 이동할 컴포넌트
                //startActivity(intent);
                break;
            case REQUEST_USED_HELP: // 사용 방법
                intent = new Intent(this, ScreenLockHelpActivity.class); // 이동할 컴포넌트
                startActivity(intent);
                break;
            case REQUEST_SOFTWARE_INFO: // 소프트웨어 정보
                intent = new Intent(this, SoftwareInfoActivity.class); // 이동할 컴포넌트
                startActivity(intent);
                break;
            case REQUEST_TITILE0:
                break;
        }


        setting_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(getApplicationContext(),"onActivityResult = " + directIcon, Toast.LENGTH_LONG).show();

        SampleData element;

        // 패스워드
        if(requestCode == REQUEST_PWNBACKUP) {
            pwSize = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_SIZE, 2);

            String pwSizeTxt = "";
            switch (pwSize) {
                case 1:
                    pwSizeTxt = getString(R.string.pwbackup_1digit);
                    //element = new SampleData(R.drawable.icon_password, "패스워드", "2자리", "길이, 백업 PIN, 이메일", R.drawable.onoff_on);
                    break;
                case 2:
                    pwSizeTxt = getString(R.string.pwbackup_2digit);
                    //element = new SampleData(R.drawable.icon_password, "패스워드", "4자리", "길이, 백업 PIN, 이메일", R.drawable.onoff_on);
                    break;
                case 3:
                    pwSizeTxt = getString(R.string.pwbackup_3digit);;
                    //element = new SampleData(R.drawable.icon_password, "패스워드", "2자리", "길이, 백업 PIN, 이메일", R.drawable.onoff_on);
                    break;
                case 4:
                    pwSizeTxt = getString(R.string.pwbackup_4digit);;
                    //element = new SampleData(R.drawable.icon_password, "패스워드", "4자리", "길이, 백업 PIN, 이메일", R.drawable.onoff_on);
                    break;
            }

            element = new SampleData(R.drawable.icon_password, getString(R.string.setting_pwbackup), pwSizeTxt, "", R.drawable.onoff_on);
            settingDataList.set(REQUEST_PWNBACKUP, element);
        }

        /// 패스워드 입력 시도 제한
        if(requestCode == REQUEST_PWINPUT_TIMER){
            pwinputtime = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_CNT, 10);

            element = new SampleData(R.drawable.icon_pw_input5, getString(R.string.setting_pwinput), "", "", R.drawable.onoff_on);
            String timer = "";
            switch (pwinputtime){
                case 0:
                    timer = getString(R.string.pwinput_chance_0);
                    element = new SampleData(R.drawable.icon_pw_input0, getString(R.string.setting_pwinput), timer, "", R.drawable.onoff_on);
                    break;
                case 3:
                    timer = getString(R.string.pwinput_chance_3);
                    element = new SampleData(R.drawable.icon_pw_input3, getString(R.string.setting_pwinput), timer, "", R.drawable.onoff_on);
                    break;
                case 5:
                    timer = getString(R.string.pwinput_chance_5);
                    element = new SampleData(R.drawable.icon_pw_input5, getString(R.string.setting_pwinput), timer, "", R.drawable.onoff_on);
                    break;
                case 10:
                    timer = getString(R.string.pwinput_chance_10);
                    element = new SampleData(R.drawable.icon_pw_input10, getString(R.string.setting_pwinput), timer, "", R.drawable.onoff_on);
                    break;

            }
            //Toast.makeText(getApplicationContext(),"onActivityResult icon_random_time = " + pwTimer, Toast.LENGTH_LONG).show();
            //element = new SampleData(R.drawable.icon_random_time, "랜덤 키패드 동작 시간", timer, "", R.drawable.onoff_on);
            settingDataList.set(REQUEST_PWINPUT_TIMER, element);
        }

        if(requestCode == REQUEST_RANDOMTIME){ // 랜덤 버튼 동작 시간
            pwTimer = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_TIMER, 0);

            //settingDataList.add(new SampleData(R.drawable.icon_gps, "휴대폰 위치", "", "이메일로 알림", R.drawable.onoff_on));

            element = new SampleData(R.drawable.icon_random_time, getString(R.string.rdkeytimer_title), "", "", R.drawable.onoff_on);
            switch (pwTimer){
                case 0:
                    element = new SampleData(R.drawable.icon_random_time, getString(R.string.rdkeytimer_title), getString(R.string.rdkeytimer_time_0), "", R.drawable.onoff_on);
                    break;
                case 2000:
                    element = new SampleData(R.drawable.icon_random_time, getString(R.string.rdkeytimer_title), getString(R.string.rdkeytimer_time_2), "", R.drawable.onoff_on);
                    break;
                case 3000:
                    element = new SampleData(R.drawable.icon_random_time, getString(R.string.rdkeytimer_title), getString(R.string.rdkeytimer_time_3), "", R.drawable.onoff_on);
                    break;
                case 4000:
                    element = new SampleData(R.drawable.icon_random_time, getString(R.string.rdkeytimer_title), getString(R.string.rdkeytimer_time_4), "", R.drawable.onoff_on);
                    break;
                case 5000:
                    element = new SampleData(R.drawable.icon_random_time, getString(R.string.rdkeytimer_title), getString(R.string.rdkeytimer_time_5), "", R.drawable.onoff_on);
                    break;
                case 10000:
                    element = new SampleData(R.drawable.icon_random_time, getString(R.string.rdkeytimer_title), getString(R.string.rdkeytimer_time_10), "", R.drawable.onoff_on);
                    break;
            }
            settingDataList.set(REQUEST_RANDOMTIME, element);
        }



        // 랜덤버튼 아이콘 위치
        if(requestCode == REQUEST_R2NICON_POSITION){

            showLeft = PreferenceUtil.getBooleanPref(this, PreferenceUtil.SHOW_LEFT, true);
            element = new SampleData(R.drawable.icon_r2n_icon, getString(R.string.r2nposition_title), getString(R.string.r2nposition_left), "", R.drawable.onoff_off);

            if(showLeft){
                element = new SampleData(R.drawable.icon_r2n_icon, getString(R.string.r2nposition_title), getString(R.string.r2nposition_left), "", R.drawable.onoff_off);
            } else {
                element = new SampleData(R.drawable.icon_r2n_icon, getString(R.string.r2nposition_title), getString(R.string.r2nposition_right), "", R.drawable.onoff_off);
            }

            settingDataList.set(REQUEST_R2NICON_POSITION, element);

        }

       // Toast.makeText(getApplicationContext(),"onActivityResult requestCode = " + requestCode, Toast.LENGTH_LONG).show();
        if(requestCode == REQUEST_DIRECT){ // 바로가기
            direct = PreferenceUtil.getIntPref(this, PreferenceUtil.DIRECT_TYPE, 0);
            element = new SampleData(R.drawable.icon_camera, getString(R.string.shortcuts_title), getString(R.string.shortcuts_camera), getString(R.string.shortcuts_subtitle), R.drawable.onoff_on);
            switch (direct){
                case 0:
                    element = new SampleData(R.drawable.icon_camera, getString(R.string.shortcuts_title), getString(R.string.shortcuts_camera), getString(R.string.shortcuts_subtitle), R.drawable.onoff_on);
                    break;
                case 1:
                    element = new SampleData(R.drawable.icon_navigation, getString(R.string.shortcuts_title), getString(R.string.shortcuts_navi), getString(R.string.shortcuts_subtitle), R.drawable.onoff_on);
                    break;
                case 2:
                    element = new SampleData(R.drawable.calender_icon, getString(R.string.shortcuts_title), getString(R.string.shortcuts_calendar), getString(R.string.shortcuts_subtitle), R.drawable.onoff_on);
                    break;
            }
            settingDataList.set(REQUEST_DIRECT, element);
        }

        // 배경 화면
        if(requestCode == REQUEST_BACKGROUD) {
            //bg_type = PreferenceUtil.getIntPref(this, PreferenceUtil.BACKGROUND_TYPE, 0);
            customBGImg = PreferenceUtil.getBooleanPref(this, PreferenceUtil.CUSTOM_BG, false);
            element = new SampleData(R.drawable.icon_background, getString(R.string.background_title), getString(R.string.background_switch1_txt), "", R.drawable.onoff_on);

            if(customBGImg){
                element = new SampleData(R.drawable.icon_background, getString(R.string.background_title), getString(R.string.background_switch2_txt), "", R.drawable.onoff_on);
            } else {
                element = new SampleData(R.drawable.icon_background, getString(R.string.background_title), getString(R.string.background_switch1_txt), "", R.drawable.onoff_on);
            }
            settingDataList.set(REQUEST_BACKGROUD, element);

        }


        // 랜덤 키패드
        if(requestCode == REQUEST_RANDOMKEYPAD_FULL){
            randomkeypad = PreferenceUtil.getIntPref(this, PreferenceUtil.NUM_TYPE, 2);
            Log.d("Inhyo Test", "randomkeypad = "+randomkeypad);

            element = new SampleData(R.drawable.icon_random_keypad, getString(R.string.randomkey_title), getString(R.string.randomkey_line), "", R.drawable.onoff_off);
            switch (randomkeypad){
                case 0:
                    element = new SampleData(R.drawable.icon_gray, getString(R.string.randomkey_title), "그레이", "", R.drawable.onoff_off);
                    break;
                case 1:
                    element = new SampleData(R.drawable.icon_trans, getString(R.string.randomkey_title), getString(R.string.randomkey_trans), "", R.drawable.onoff_off);
                    break;
                case 2:
                    element = new SampleData(R.drawable.icon_random_keypad, getString(R.string.randomkey_title), getString(R.string.randomkey_line), "", R.drawable.onoff_off);
                    break;
                case 3:
                    element = new SampleData(R.drawable.icon_album, getString(R.string.randomkey_title), getString(R.string.randomkey_album), "", R.drawable.onoff_off);
                    break;

            }

            settingDataList.set(REQUEST_RANDOMKEYPAD_FULL, element);
        }

        // 널 키패드
        if(requestCode == REQUEST_NULLKEYPAD_FULL){

            nullkeypad = PreferenceUtil.getIntPref(this, PreferenceUtil.BTN_TYPE, 2);
            element = new SampleData(R.drawable.icon_random_keypad, getString(R.string.setting_nullkey), getString(R.string.nullkey_line), "", R.drawable.onoff_off);
            switch (nullkeypad){
                case 0:
                    element = new SampleData(R.drawable.icon_null_gray, getString(R.string.setting_nullkey), "그레이", "", R.drawable.onoff_off);
                    break;
                case 1:
                    element = new SampleData(R.drawable.img_trans, getString(R.string.setting_nullkey), getString(R.string.nullkey_trans), "", R.drawable.onoff_off);
                    break;
                case 2:
                    element = new SampleData(R.drawable.icon_null_keypad, getString(R.string.setting_nullkey), getString(R.string.nullkey_line), "", R.drawable.onoff_off);
                    break;
                case 3:
                    element = new SampleData(R.drawable.icon_null_album, getString(R.string.setting_nullkey), getString(R.string.nullkey_album), "", R.drawable.onoff_off);
                    break;

            }
            settingDataList.set(REQUEST_NULLKEYPAD_FULL, element);

        }

        setting_adapter.notifyDataSetChanged();
    }
}

