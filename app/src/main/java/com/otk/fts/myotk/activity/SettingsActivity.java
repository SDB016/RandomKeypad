package com.otk.fts.myotk.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.core.util.AtomicFile;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.util.Util;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.otk.fts.myotk.BuildConfig;
import com.otk.fts.myotk.services.LockScreenService;
import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.ActivityUtil;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;
import com.otk.fts.myotk.utils.Utils;
import com.soundcloud.android.crop.Crop;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class SettingsActivity extends Activity{
    private boolean powerOn, boot_first, isLeft;
    private CheckBox useLock, dontUse;
    private CheckBox spw1, spw2;
    private EditText input, recheck;

    private CheckBox timer0,timer1, timer2, timer3;
    private CheckBox num_img1, num_img2, num_img3, num_img4;
    private CheckBox btn_img1, btn_img2, btn_img3, btn_img4;

    private CheckBox btn_bg1;
    private CheckBox btn_bg2;

    private int pwSize;
    private int timer;

    private ImageView img0, img1, img2;
    private File tempFile;

    private boolean customBgImg;
    private String bgImgFilePath;

    private int numType;
    private int btnType;

    private EditText input_pin, check_pin;

    private static final int PICK_FROM_ALBUM = 0;
    private static final int CROP_FROM_ALBUM = 1;

    private Handler handler;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        res = getResources();
        handler = new Handler(message -> false);

        //TedPermission 라이브러리 -> 카메라 권한 획득
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
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

        //SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        //powerOn = sf.getBoolean("isLock", true);

        powerOn = PreferenceUtil.getBooleanPref(this, PreferenceUtil.IS_LOCK, true);
        customBgImg = PreferenceUtil.getBooleanPref(this, PreferenceUtil.CUSTOM_BG, false);//sf.getBoolean("customBgImg", false);
        bgImgFilePath = PreferenceUtil.getStringPref(this, PreferenceUtil.CUSTOM_BG_PATH); //sf.getString("pwImgPath","");
        String originPw = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST, "00");//Integer.toString(sf.getInt("pwList",00));
        String st_pin = PreferenceUtil.getStringPref(this, PreferenceUtil.BACKUP_PIN, "0000");//Integer.toString(sf.getInt("backupPin", 0));
        timer = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_TIMER, 2000); //sf.getInt("pwSize",2);//sf.getInt("pwTimer", 2000);
        numType = PreferenceUtil.getIntPref(this, PreferenceUtil.NUM_TYPE, 2); //sf.getInt("numType", 3);
        btnType = PreferenceUtil.getIntPref(this, PreferenceUtil.BTN_TYPE, 2); //sf.getInt("btnType", 3);

        boot_first = false;
        boot_first = PreferenceUtil.getBooleanPref(this, PreferenceUtil.FIRST_BOOT2, false);
        //Log.d("setting", "firstBoot? = "+boot_first);

        TextView infoTv = findViewById(R.id.info1);
        infoTv.setText(getString(R.string.setting_info, BuildConfig.VERSION_NAME));

        useLock = findViewById(R.id.check_use);
        useLock.setChecked(powerOn);
        useLock.setOnClickListener(v -> {
            Log.d("power","power1 : " + powerOn);
            if(useLock.isChecked()) {
                useLock.setChecked(true);
                dontUse.setChecked(false);
                powerOn = true;
            }else{
                dontUse.setChecked(true);
                powerOn = false;
            }
        }) ;
        dontUse = findViewById(R.id.check_dont);
        dontUse.setChecked(!powerOn);
        dontUse.setOnClickListener(v -> {
            Log.d("power","power2 : " + powerOn);
            if(dontUse.isChecked()) {
                useLock.setChecked(false);
                dontUse.setChecked(true);
                powerOn = false;
            }else{
                useLock.setChecked(true);
                powerOn = true;
            }
        }) ;

        spw1 = findViewById(R.id.pwSize2);
        spw2 = findViewById(R.id.pwSize4);
        img0 = findViewById(R.id.numImg);
        img1 = findViewById(R.id.testImg);
        img2 = findViewById(R.id.testbg);

        spw1.setOnClickListener(v -> {
            Log.d("Activity", "CheckBox1 Clicked!");
            pwSize = 2;
            spw1.setChecked(true);
            spw2.setChecked(false);
            input.setText("");
            recheck.setText("");
            input.setHint(R.string.setting_enter_2dit);
            recheck.setHint(R.string.setting_enter_2dit);
        }) ;
        spw2.setOnClickListener(v -> {
            pwSize = 4;
            spw2.setChecked(true);
            spw1.setChecked(false);
            input.setText("");
            recheck.setText("");
            input.setHint(R.string.setting_enter_4dit);
            recheck.setHint(R.string.setting_enter_4dit);

        });

        isLeft = PreferenceUtil.getBooleanPref(this, PreferenceUtil.SHOW_LEFT, true);
        CheckBox show_bt_left = findViewById(R.id.show_bt_left);
        CheckBox show_bt_right = findViewById(R.id.show_bt_right);
        show_bt_left.setChecked(isLeft);
        show_bt_left.setOnClickListener(view -> {
            if(show_bt_left.isChecked()) {
                show_bt_left.setChecked(true);
                show_bt_right.setChecked(false);
                isLeft = true;
            }else{
                show_bt_right.setChecked(false);
                isLeft = false;
            }
        });
        show_bt_right.setChecked(!isLeft);
        show_bt_right.setOnClickListener(view -> {
            if(show_bt_right.isChecked()) {
                show_bt_right.setChecked(true);
                show_bt_left.setChecked(false);
                isLeft = false;
            }else{
                show_bt_right.setChecked(false);
                isLeft = true;
            }
        });

        input = findViewById(R.id.input_pw);
        recheck = findViewById(R.id.re_pw);

        // 백업 핀 설정
        input_pin = findViewById(R.id.input_pin);
        check_pin = findViewById(R.id.check_pin);

        if(!boot_first){
            input.setText("00");
            recheck.setText("00");
            input_pin.setText("0000");
            check_pin.setText("0000");
        }else{
            input.setText(originPw);
            recheck.setText(originPw);
            input_pin.setText(st_pin);
            check_pin.setText(st_pin);
        }

        pwSize = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_SIZE, 2); //sf.getInt("pwSize",2);
        switch(pwSize){
            case 2:
                spw1.setChecked(true);
                spw2.setChecked(false);
                input.setHint(R.string.setting_enter_2dit);
                recheck.setHint(R.string.setting_enter_2dit);
                break;
            case 4:
                spw2.setChecked(true);
                spw1.setChecked(false);
                input.setHint(R.string.setting_enter_4dit);
                recheck.setHint(R.string.setting_enter_4dit);
                break;
        }

        timer0 = findViewById(R.id.set_time_0);
        timer0.setOnClickListener(v -> {
            timer = 0;
            timer0.setChecked(true);
            timer1.setChecked(false);
            timer2.setChecked(false);
            timer3.setChecked(false);
        });

        timer1 = findViewById(R.id.set_time_1);
        timer1.setOnClickListener(v -> {
            timer = 2000;
            timer0.setChecked(false);
            timer1.setChecked(true);
            timer2.setChecked(false);
            timer3.setChecked(false);
        });
        timer2 = findViewById(R.id.set_time_2);
        timer2.setOnClickListener(v -> {
            timer = 3000;
            timer0.setChecked(false);
            timer1.setChecked(false);
            timer2.setChecked(true);
            timer3.setChecked(false);
        });
        timer3 = findViewById(R.id.set_time_3);
        timer3.setOnClickListener(v -> {
            timer = 4000;
            timer0.setChecked(false);
            timer1.setChecked(false);
            timer2.setChecked(false);
            timer3.setChecked(true);
        });

        switch(timer){
            case 0:
                timer0.setChecked(true);
                timer1.setChecked(false);
                timer2.setChecked(false);
                timer3.setChecked(false);
                break;
            case 2000:
                timer0.setChecked(false);
                timer1.setChecked(true);
                timer2.setChecked(false);
                timer3.setChecked(false);
                break;
            case 3000:
                timer0.setChecked(false);
                timer1.setChecked(false);
                timer2.setChecked(true);
                timer3.setChecked(false);
                break;
            case 4000:
                timer0.setChecked(false);
                timer1.setChecked(false);
                timer2.setChecked(false);
                timer3.setChecked(true);
                break;
        }

        num_img1 = findViewById(R.id.check_num_1);
        num_img1.setOnClickListener(v -> {
            // 숫자버튼 - 투명도 0프로
            num_img1.setChecked(true);
            num_img2.setChecked(false);
            num_img3.setChecked(false);
            num_img4.setChecked(false);
            img0.setBackground(res.getDrawable(R.drawable.percent100_num));
            numType = 1;
        });
        num_img2 = findViewById(R.id.check_num_2);
        num_img2.setOnClickListener(v -> {
            // 숫자버튼 - 외곽선
            num_img1.setChecked(false);
            num_img2.setChecked(true);
            num_img3.setChecked(false);
            num_img4.setChecked(false);
            img0.setBackground(res.getDrawable(R.drawable.line_num));
            numType = 2;
        });
        num_img3 = findViewById(R.id.check_num_3);
        num_img3.setOnClickListener(v -> {
            // 숫자버튼 - 투명도 35프로
            num_img1.setChecked(false);
            num_img2.setChecked(false);
            num_img3.setChecked(true);
            num_img4.setChecked(false);
            img0.setBackground(res.getDrawable(R.drawable.percent35_num));
            numType = 0;
        });
        num_img4 = findViewById(R.id.check_num_4);
        num_img4.setOnClickListener(v -> {
            // 숫자버튼 - 투명도 100프로
            num_img1.setChecked(false);
            num_img2.setChecked(false);
            num_img3.setChecked(false);
            num_img4.setChecked(true);
            img0.setBackground(res.getDrawable(R.drawable.percent0_num));
            numType = 3;
        });


        btn_img1 = findViewById(R.id.check_nullpad_gray);
        btn_img1.setOnClickListener(v -> {
            // 기존 버튼 이미지 사용
            btn_img1.setChecked(true);
            btn_img2.setChecked(false);
            btn_img3.setChecked(false);
            btn_img4.setChecked(false);
            img1.setBackground(res.getDrawable(R.drawable.percent100_blank));
            btnType = 1;
            //customBtnImg = false;
        });
        btn_img2 = findViewById(R.id.check_nullpad_blue);
        btn_img2.setOnClickListener(v -> {
            // 버튼 이미지2

            btnType = 2;
            img1.setBackground(res.getDrawable(R.drawable.line_blank));
            btn_img1.setChecked(false);
            btn_img2.setChecked(true);
            btn_img3.setChecked(false);
            btn_img4.setChecked(false);
            //makeDialog();
        });

        btn_img3 = findViewById(R.id.check_nullpad_red);
        btn_img3.setOnClickListener(v -> {
            // 버튼 이미지3

            btnType = 0;
            img1.setBackground(res.getDrawable(R.drawable.percent35_blank));
            btn_img1.setChecked(false);
            btn_img2.setChecked(false);
            btn_img3.setChecked(true);
            btn_img4.setChecked(false);
        });

        btn_img4 = findViewById(R.id.check_nullpad_green);
        btn_img4.setOnClickListener(v -> {
            // 버튼 이미지3

            btnType = 3;
            img1.setBackground(res.getDrawable(R.drawable.percent0_blank));
            btn_img1.setChecked(false);
            btn_img2.setChecked(false);
            btn_img3.setChecked(false);
            btn_img4.setChecked(true);
        });

        img2.setOnClickListener(v -> {
            // 디폴트 배경화면 사용
            btn_bg1.setChecked(false);
            btn_bg2.setChecked(true);
            makeDialog();
        });

        btn_bg1 = findViewById(R.id.check_bg_default);
        //img2.setBackground(res.getDrawable(R.drawable.sample_bg));
        //img2.setBackgroundColor(res.getColor(android.R.color.black));
        btn_bg1.setOnClickListener(v -> {
            // 디폴트 배경화면 사용

            btn_bg1.setChecked(true);
            btn_bg2.setChecked(false);
            customBgImg = false;
            //img2.setBackgroundColor(res.getColor(android.R.color.black));
            img2.setBackground(res.getDrawable(R.drawable.background_1));

            //img2.setImageResource(R.drawable.sample_bg);
            //Log.d("bg", ""+customBgImg);
        });
        btn_bg2 = findViewById(R.id.check_bg_image);
        btn_bg2.setOnClickListener(v -> {
            // 앨범에서 이미지 선택

            btn_bg1.setChecked(false);
            btn_bg2.setChecked(true);
            makeDialog();
        });

        TextView confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(v -> ConfirmSettings());
        TextView cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> CancelSettings());

        if(customBgImg) {
            setImage();
            btn_bg1.setChecked(false);
            btn_bg2.setChecked(true);
        } else{
            btn_bg1.setChecked(true);
            btn_bg2.setChecked(false);
        }

        if(numType==0){
            num_img1.setChecked(false);
            num_img2.setChecked(false);
            num_img3.setChecked(true);
            num_img4.setChecked(false);
            img0.setBackground(res.getDrawable(R.drawable.percent35_num));
        }else if(numType==1){
            num_img1.setChecked(true);
            num_img2.setChecked(false);
            num_img3.setChecked(false);
            num_img4.setChecked(false);
            img0.setBackground(res.getDrawable(R.drawable.percent100_num));
        }else if(numType==2){
            num_img1.setChecked(false);
            num_img2.setChecked(true);
            num_img3.setChecked(false);
            num_img4.setChecked(false);
            img0.setBackground(res.getDrawable(R.drawable.line_num));
        }else if(numType==3){
            num_img1.setChecked(false);
            num_img2.setChecked(false);
            num_img3.setChecked(false);
            num_img4.setChecked(true);
            img0.setBackground(res.getDrawable(R.drawable.percent0_num));
        }

        if(btnType==0){
            btn_img1.setChecked(false);
            btn_img2.setChecked(false);
            btn_img3.setChecked(true);
            btn_img4.setChecked(false);
            img1.setBackground(res.getDrawable(R.drawable.percent35_blank));
        }else if(btnType==1){
            btn_img1.setChecked(true);
            btn_img2.setChecked(false);
            btn_img3.setChecked(false);
            btn_img4.setChecked(false);
            img1.setBackground(res.getDrawable(R.drawable.percent100_blank));
        }else if(btnType==2){
            btn_img1.setChecked(false);
            btn_img2.setChecked(true);
            btn_img3.setChecked(false);
            btn_img4.setChecked(false);
            img1.setBackground(res.getDrawable(R.drawable.line_blank));
        }else if(btnType==3){
            btn_img1.setChecked(false);
            btn_img2.setChecked(false);
            btn_img3.setChecked(false);
            btn_img4.setChecked(true);
            img1.setBackground(res.getDrawable(R.drawable.percent0_blank));
        }

        //다음 눌렀을때 포커스 이동안되게 처리
        recheck.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {

                // 특정 동작 지정
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onStop(){
        // 일단 현재는 종료될 때 세팅 되는걸로 설정
        // 설정 완료 버튼으로 고쳐야됨;;
        //img2.setBackground(res.getDrawable(R.drawable.sample_bg));
        super.onStop();
    }

    void ConfirmSettings(){
        String text = input.getText().toString();
        String pwCheck = recheck.getText().toString();
        String txt_pin = input_pin.getText().toString();
        String pinCheck = check_pin.getText().toString();

        if(!text.equals(pwCheck) || TextUtils.isEmpty(text)
        || (pwSize==2 && text.length()!=2) || (pwSize==4 && text.length()!=4)
        ) {
            Toast.makeText(getApplicationContext(), R.string.setting_check_password, Toast.LENGTH_SHORT).show();

        } else if((!input_pin.equals(pinCheck) && input_pin.length()!=4) ){
            Toast.makeText(getApplicationContext(), R.string.setting_check_backup_pin, Toast.LENGTH_SHORT).show();
        } else {
            boot_first = true;
            PreferenceUtil.savePref(this, PreferenceUtil.FIRST_BOOT2, boot_first);
            PreferenceUtil.savePref(this, PreferenceUtil.IS_LOCK, powerOn);
            PreferenceUtil.savePref(this, PreferenceUtil.PW_SIZE, pwSize);
            PreferenceUtil.savePref(this, PreferenceUtil.PW_LIST, text);
            PreferenceUtil.savePref(this, PreferenceUtil.PW_TIMER, timer);
            PreferenceUtil.savePref(this, PreferenceUtil.CUSTOM_BG, customBgImg);
            PreferenceUtil.savePref(this, PreferenceUtil.CUSTOM_BG_PATH, bgImgFilePath);
            PreferenceUtil.savePref(this, PreferenceUtil.BACKUP_PIN, txt_pin);
            PreferenceUtil.savePref(this, PreferenceUtil.NUM_TYPE, numType);
            PreferenceUtil.savePref(this, PreferenceUtil.BTN_TYPE, btnType);
            PreferenceUtil.savePref(this, PreferenceUtil.SHOW_LEFT, isLeft);

            Toast.makeText(getApplicationContext(), R.string.setting_complete, Toast.LENGTH_SHORT).show();
            handler.postDelayed(() -> {
                if(PreferenceUtil.getBooleanPref(this, PreferenceUtil.IS_LOCK, true)) {
                    Utils.startService(getApplicationContext());
                    ActivityUtil.move(SettingsActivity.this, LockScreenActivity.class);
                }else{
                    Utils.stopService(getApplicationContext());
                }

                //ActivityUtil.move(SettingsActivity.this, preLockScreenActivity.class);
                finishAffinity();
            }, 200);    //0.5초 뒤에
        }
    }

   private void CancelSettings(){
        Toast.makeText(getApplicationContext(), R.string.setting_cancel, Toast.LENGTH_SHORT).show();
        handler.postDelayed(() -> {
            if(PreferenceUtil.getBooleanPref(this, PreferenceUtil.IS_LOCK, true)) {
                ActivityUtil.move(SettingsActivity.this, LockScreenActivity.class);
            }
            //ActivityUtil.move(SettingsActivity.this, preLockScreenActivity.class);
            finishAffinity();
        }, 200);    //2초 뒤에
    }

    private void makeDialog(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setTitle(R.string.setting_dialog_album_title).setMessage(R.string.setting_dialog_album_msg)
                .setIcon(R.drawable.album64)
                .setPositiveButton(R.string.setting_dialog_album_posi_bt,
                        (dialogInterface, id) -> {
                            //Log.v("알림", "다이얼로그 > 앨범선택 선택");
                            //앨범에서 선택
                            selectAlbum(1);
                            //Intent intent = new Intent("com.android.camera.action.CROP");
                            //startService(intent);
                            // 인텐트+뷰 넘기기
                        }).setNegativeButton(R.string.cancel,
                (dialog, id) -> {
                    //Log.v("알림", "다이얼로그 > 취소 선택");
                    // 취소 클릭. dialog 닫기.

                    btn_bg1.performClick();
                    dialog.cancel();
                });
        AlertDialog alert = alt_bld.create();
        alt_bld.setCancelable(false);
        alert.show();
    }

    //앨범 선택 클릭
    public void selectAlbum(int i){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        //finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            switch (requestCode) {
                case PICK_FROM_ALBUM: {
                    if (data!=null && data.getData() != null) {
                        Uri photoUri = data.getData();

                        if(photoUri!=null){
                            cropImageFromAlbum(photoUri);
                        }
                    }
                    else{
                        btn_bg1.setChecked(true);
                        btn_bg2.setChecked(false);
                    }
                    break;
                }
                case Crop.REQUEST_CROP: {
                    //setImage();
                    //Toast.makeText(this, "크롭 리퀘스트", Toast.LENGTH_SHORT).show();
                }
                case CROP_FROM_ALBUM:{
                    setImage();
                }
            }
        }

        else{
            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        tempFile = null;
                    }
                }
            }

            btn_bg1.setChecked(true);
            btn_bg2.setChecked(false);
        }

    }

    private void cropImageFromAlbum(Uri inputUri){

        Uri outputUri = null;
        // File 객체의 URI 를 얻는다.
        try {
            tempFile = createImageFile();

            /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {// API 24 이상 일경우..
                String strpa = getApplicationContext().getPackageName();
                outputUri = FileProvider.getUriForFile(this, strpa + ".fileprovider", tempFile);
            }
            else
            {// API 24 미만 일경우..
                outputUri = Uri.fromFile(tempFile);
            }*/

        } catch (IOException e) {
            //Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        outputUri = Uri.fromFile(tempFile);

        Intent intent = getCropIntent(inputUri, outputUri);
        bgImgFilePath = tempFile.getAbsolutePath();
        //Log.d("PATH","경로 : " + bgImgFilePath.toString());
        customBgImg = true;
        startActivityForResult(intent, CROP_FROM_ALBUM);
        //setImage();
    }

    private Intent getCropIntent(Uri inputUri, Uri outputUri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(inputUri, "image/*");
        intent.putExtra("aspectX", "1");
        intent.putExtra("aspectY", "1");
        intent.putExtra("outputX", "200");
        intent.putExtra("outputY", "200");
        intent.putExtra("scale", "true");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

        return intent;
    }

    private void setImage() {

        /*
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;

        Log.d("image","img");

        Bitmap originalBm;
        if(customBtnImg)
            originalBm = BitmapFactory.decodeFile(btnImgFilePath, options);
        else
            originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        int height = originalBm.getHeight();
        int width = originalBm.getWidth();

        Bitmap resized = null;
        while (height > 200) {
            resized = Bitmap.createScaledBitmap(originalBm, (width * 200) / height, 200, true);
            height = resized.getHeight();
            width = resized.getWidth();
        }

        //originalBm = setRoundCorner(originalBm, 1200);
        //img1.setImageBitmap(originalBm);
        RoundedAvatarDrawable tempRoundD= new RoundedAvatarDrawable(resized);
        img1.setBackground(tempRoundD);
        */

        /*
        Glide.with(getApplicationContext())
                .load(photoURI)
                .into(img1);
        */

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;

        //Log.d("bg","setImg");

        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        //File storageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/example.jpg");
        //Uri uri = FileProvider.getUriForFile(this, "com.otk.fts.myotk.fileprovider", file);
        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        //File storageDir = new File(getFilesDir(), "ireh");

        Bitmap originalBm = BitmapFactory.decodeFile(bgImgFilePath, options);

        //String imgPath = rep.getImgList().get(0);
        /*File file = new File(bgImgFilePath);
        AtomicFile atomicFile =  new AtomicFile(file);
        FileOutputStream fos = null;
        Bitmap bitmap = null;
        try {
            // read the current image
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            // open the stream (backup the current content)
            // from now on (and until finishWrite/failWrite) we cannot read the file directly
            fos = atomicFile.startWrite();
            //Log.d("showmethebitmap", bitmap.toString()); //Error: bitmap is null !
            OutputStream oos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG,0, oos);
            // flush but do not close the stream (@see AtomicFile doc)
            oos.flush();
            // close the stream, remove the backup
            atomicFile.finishWrite(fos);
        } catch (IOException e) {
            // recover the content from the backup
            atomicFile.failWrite(fos);
            //throw e;
        }*/

        /*
        if(customBgImg)
            originalBm = BitmapFactory.decodeFile(bgImgFilePath, options);
        else
            originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        */


        customBgImg = true;
        Drawable drawable = new BitmapDrawable(scaleDown(originalBm, 2048, true));
        img2.setBackground(drawable);
        //img2.setImageBitmap(originalBm);

        btn_bg1.setChecked(false);
        btn_bg2.setChecked(true);
    }

    public Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
       try{
           float ratio = Math.min(
                   (float) maxImageSize / realImage.getWidth(),
                   (float) maxImageSize / realImage.getHeight());
           int width = Math.round((float) ratio * realImage.getWidth());
           int height = Math.round((float) ratio * realImage.getHeight());
        return Bitmap.createScaledBitmap(realImage, width, height, filter);

       }catch (Exception e){
           QLog.e(e.toString());
       }

       return null;
    }

    private void cropImage(Uri photoUri) {
        /**
         *  갤러리에서 선택한 경우에는 tempFile 이 없으므로 새로 생성해줍니다.
         */
        if(tempFile == null) {
            try {
                tempFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }
        }
        //크롭 후 저장할 Uri
        Uri savingUri = Uri.fromFile(tempFile);
        Crop.of(photoUri, savingUri).asSquare().start(this);
    }

    public File createImageFile() throws IOException{
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imageFile= null;

        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
          //File storageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/example.jpg");
            //Uri uri = FileProvider.getUriForFile(this, "com.otk.fts.myotk.fileprovider", file);
        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        //File storageDir = new File(getFilesDir(), "ireh");

        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");
        if(!storageDir.exists()){
            Log.v("알림","storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }

        Log.v("알림","storageDir 존재함 " + storageDir.toString());
        imageFile = new File(storageDir,imgFileName);

        //mCurrentBtnPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }


    /*
    public void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentBtnPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this,"사진이 저장되었습니다",Toast.LENGTH_SHORT).show();
    }
    */

    /*
    public static Bitmap setRoundCorner(Bitmap bitmap, int pixel) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, pixel, pixel, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    */
}