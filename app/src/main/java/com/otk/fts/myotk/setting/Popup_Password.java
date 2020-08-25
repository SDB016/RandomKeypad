package com.otk.fts.myotk.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.PreferenceUtil;

public class Popup_Password extends Activity {

    private Button btnClose;
    private Button btnPWOK;
    private EditText editPW;
    private EditText editPWChk;


    private int pwSize;
    private String tmpPW;
    private String tmpPWChk;

    private String originPW1;
    private String originPW2;
    private String originPW3;
    private String originPW4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_popup_);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_password2);

        originPW1 = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST1);
        originPW2 = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST2);
        originPW3 = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST3);
        originPW4 = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST4);

        btnClose = (Button)findViewById(R.id.btn_popup_close);
        btnPWOK = (Button)findViewById(R.id.btn_password_ok);
        editPW = (EditText)findViewById(R.id.edit_input_pw);
        editPWChk = (EditText)findViewById(R.id.edit_input_pw_chk);

        Intent intent = getIntent();
        pwSize = intent.getIntExtra("pwsize",0);

        if(pwSize == 1){
            if(originPW1.length() > 0){
                editPW.setText(originPW1.toString());
                editPWChk.setText(originPW1.toString());
            }
        } else if(pwSize == 2){
            if(originPW2.length() > 0){
                editPW.setText(originPW2.toString());
                editPWChk.setText(originPW2.toString());
            }
        } else if(pwSize == 3){
            if(originPW3.length() > 0){
                editPW.setText(originPW3.toString());
                editPWChk.setText(originPW3.toString());
            }
        } else if(pwSize == 4){
            if(originPW4.length() > 0){
                editPW.setText(originPW4.toString());
                editPWChk.setText(originPW4.toString());
            }
        }


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPWOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmpPW = editPW.getText().toString();
                tmpPWChk = editPWChk.getText().toString();

                if(sizeChk(tmpPW)){
                    if(sizeChk(tmpPWChk)){
                        if(passwordChk(tmpPW, tmpPWChk)){
                            ConfirmSettings(pwSize);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "비밀번호를 확인해 주세요 ", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해 주세요 ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }



    public boolean passwordChk(String inputPW1, String inputPW2){

        if(inputPW1.equals(inputPW2)){

            return true;
        } else {
            Toast.makeText(getApplicationContext(),"비밀번호를 확인해 주세요  ", Toast.LENGTH_LONG).show();
        }

        return false;
    }

    public boolean sizeChk(String inputTxt){

        if(pwSize == 1) {
            if (inputTxt.length() == 1) {
                return true;
            }
        } else if(pwSize == 2){
            if (inputTxt.length() == 2) {
                return true;
            }
        } else if(pwSize == 3){
            if (inputTxt.length() == 3) {
                return true;
            }
        } else if(pwSize == 4){
            if (inputTxt.length() == 4) {
                return true;
            }
        }

        return false;
    }

    public void ConfirmSettings(int size){
        //Log.d("Inhyo Test","ScreenLock - PreferenceUtil.BTN_TYPE : " + r2nIconType);
        PreferenceUtil.savePref(this, PreferenceUtil.PW_SIZE, pwSize);

        if(size == 1){
            PreferenceUtil.savePref(this, PreferenceUtil.PW_LIST1, tmpPW);
        } else if(size == 2){
            PreferenceUtil.savePref(this, PreferenceUtil.PW_LIST2, tmpPW);
        } else if(size == 3){
            PreferenceUtil.savePref(this, PreferenceUtil.PW_LIST3, tmpPW);
        } else if(size == 4){
            PreferenceUtil.savePref(this, PreferenceUtil.PW_LIST4, tmpPW);
        }

    }

}
