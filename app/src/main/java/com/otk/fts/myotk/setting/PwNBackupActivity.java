package com.otk.fts.myotk.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.PreferenceUtil;

import androidx.appcompat.app.AppCompatActivity;

public class PwNBackupActivity extends AppCompatActivity {

    private Button mBtnBack;

    private ImageButton btn1PwOn;
    private ImageButton btn2PwOn;
    private ImageButton btn3PwOn;
    private ImageButton btn4PwOn;

    private EditText edit2PwTxt;
    private EditText edit2PwChkTxt;
    private EditText edit4PwTxt;
    private EditText edit4PwChkTxt;
    private EditText editPinTxt;
    private EditText editPinChkTxt;
    private EditText editEmailTxt;



    private TextView txt1PW;
    private TextView txt2PW;
    private TextView txt3PW;
    private TextView txt4PW;
    private TextView txt1PWTitle;
    private TextView txt2PWTitle;
    private TextView txt3PWTitle;
    private TextView txt4PWTitle;
    private TextView txtBackupPin;
    //private TextView txtInputEmail;

    private int pwSize;
    private String strPw;
    private String strPwChk;
    private String strPin;
    private String strPinChk;
    private String inputEmail;
    private LinearLayout mlinearLayout;


    private int REQUEST_PWSIZE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwnbackup);

        btn2PwOn = (ImageButton)findViewById(R.id.btn_2pw);
        btn4PwOn = (ImageButton)findViewById(R.id.btn_4pw);
        btn1PwOn = (ImageButton)findViewById(R.id.btn_1pw);
        btn3PwOn = (ImageButton)findViewById(R.id.btn_3pw);

        //txtInputEmail = (TextView)findViewById(R.id.txt_input_email);

        /*
        edit2PwTxt = (EditText)findViewById(R.id.edit_input_2pw);
        edit2PwChkTxt = (EditText)findViewById(R.id.edit_input_2pw_chk);
        edit4PwTxt = (EditText)findViewById(R.id.edit_input_4pw);
        edit4PwChkTxt = (EditText)findViewById(R.id.edit_input_4pw_chk);

        editPinTxt = (EditText)findViewById(R.id.edit_input_pin);
        editPinChkTxt = (EditText)findViewById(R.id.edit_input_pin_chk);
        */
        //editEmailTxt = (EditText)findViewById(R.id.edit_input_email);

        txt1PW = (TextView)findViewById(R.id.txt_pw1);
        txt2PW = (TextView)findViewById(R.id.txt_pw2);
        txt3PW = (TextView)findViewById(R.id.txt_pw3);
        txt4PW = (TextView)findViewById(R.id.txt_pw4);

        txt1PWTitle = (TextView)findViewById(R.id.txt_pw1_title);
        txt2PWTitle = (TextView)findViewById(R.id.txt_pw2_title);
        txt3PWTitle = (TextView)findViewById(R.id.txt_pw3_title);
        txt4PWTitle = (TextView)findViewById(R.id.txt_pw4_title);

        txtBackupPin = (TextView)findViewById(R.id.txt_backup_pin);

        mBtnBack = (Button)findViewById(R.id.btn_back_pwnbu);

        mlinearLayout = (LinearLayout)findViewById(R.id.linear_title_pwnbackup);

        pwSize = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_SIZE, 4);
        //strPw = PreferenceUtil.getStringPref(this, PreferenceUtil.PW_LIST, "00");

        //strPwChk = strPw;
        strPin = PreferenceUtil.getStringPref(this, PreferenceUtil.BACKUP_PIN, "1234");
        inputEmail = PreferenceUtil.getStringPref(this, PreferenceUtil.BG_INPUT_EMAIL);

        //txtInputEmail.setText(inputEmail);
        //txtBackupPin.setText(strPin);

        if(pwSize == 1){
            btn1PwOn.setBackgroundResource(R.drawable.onoff_on);
            btn2PwOn.setBackgroundResource(R.drawable.onoff_off);
            btn3PwOn.setBackgroundResource(R.drawable.onoff_off);
            btn4PwOn.setBackgroundResource(R.drawable.onoff_off);
            txt1PW.setEnabled(true);
            txt2PW.setEnabled(false);
            txt3PW.setEnabled(false);
            txt4PW.setEnabled(false);
        } else if(pwSize == 2) {
            btn1PwOn.setBackgroundResource(R.drawable.onoff_off);
            btn2PwOn.setBackgroundResource(R.drawable.onoff_on);
            btn3PwOn.setBackgroundResource(R.drawable.onoff_off);
            btn4PwOn.setBackgroundResource(R.drawable.onoff_off);
            txt1PW.setEnabled(false);
            txt2PW.setEnabled(true);
            txt3PW.setEnabled(false);
            txt4PW.setEnabled(false);
        } else if(pwSize == 3) {
            btn1PwOn.setBackgroundResource(R.drawable.onoff_off);
            btn2PwOn.setBackgroundResource(R.drawable.onoff_off);
            btn3PwOn.setBackgroundResource(R.drawable.onoff_on);
            btn4PwOn.setBackgroundResource(R.drawable.onoff_off);
            txt1PW.setEnabled(false);
            txt2PW.setEnabled(false);
            txt3PW.setEnabled(true);
            txt4PW.setEnabled(false);
        } else if(pwSize == 4) {
            btn1PwOn.setBackgroundResource(R.drawable.onoff_off);
            btn2PwOn.setBackgroundResource(R.drawable.onoff_off);
            btn3PwOn.setBackgroundResource(R.drawable.onoff_off);
            btn4PwOn.setBackgroundResource(R.drawable.onoff_on);
            txt1PW.setEnabled(false);
            txt2PW.setEnabled(false);
            txt3PW.setEnabled(false);
            txt4PW.setEnabled(true);
        }

        //editPinTxt.setText(strPin);
        //editPinChkTxt.setText(strPin);
        //editEmailTxt.setText(inputEmail);
/*
        txtInputEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PwNBackupActivity.this, Popup_InputEmail.class);
                startActivity(intent);
            }
        });
*/

        btn1PwOn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                btn1PwOn.setBackgroundResource(R.drawable.onoff_on);
                btn2PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn3PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn4PwOn.setBackgroundResource(R.drawable.onoff_off);
                txt1PW.setEnabled(true);
                txt2PW.setEnabled(false);
                txt3PW.setEnabled(false);
                txt4PW.setEnabled(false);
                pwSize = 1;

                ConfirmSettings();
            }
        });

        txt1PW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PwNBackupActivity.this, Popup_Password.class);
                intent.putExtra("pwsize", pwSize);
                //startActivityForResult(intent, REQUEST_PWSIZE);
                startActivity(intent);
            }
        });

        txt1PWTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1PwOn.setBackgroundResource(R.drawable.onoff_on);
                btn2PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn3PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn4PwOn.setBackgroundResource(R.drawable.onoff_off);
                txt1PW.setEnabled(true);
                txt2PW.setEnabled(false);
                txt3PW.setEnabled(false);
                txt4PW.setEnabled(false);
                pwSize = 1;

                ConfirmSettings();
            }
        });

        btn2PwOn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                btn1PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn2PwOn.setBackgroundResource(R.drawable.onoff_on);
                btn3PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn4PwOn.setBackgroundResource(R.drawable.onoff_off);
                txt1PW.setEnabled(false);
                txt2PW.setEnabled(true);
                txt3PW.setEnabled(false);
                txt4PW.setEnabled(false);
                pwSize = 2;

                ConfirmSettings();
            }
        });

        txt2PW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PwNBackupActivity.this, Popup_Password.class);
                intent.putExtra("pwsize", pwSize);
                //startActivityForResult(intent, REQUEST_PWSIZE);
                startActivity(intent);
            }
        });

        txt2PWTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn2PwOn.setBackgroundResource(R.drawable.onoff_on);
                btn3PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn4PwOn.setBackgroundResource(R.drawable.onoff_off);
                txt1PW.setEnabled(false);
                txt2PW.setEnabled(true);
                txt3PW.setEnabled(false);
                txt4PW.setEnabled(false);
                pwSize = 2;

                ConfirmSettings();
            }
        });

        btn3PwOn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                btn1PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn2PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn3PwOn.setBackgroundResource(R.drawable.onoff_on);
                btn4PwOn.setBackgroundResource(R.drawable.onoff_off);
                txt1PW.setEnabled(false);
                txt2PW.setEnabled(false);
                txt3PW.setEnabled(true);
                txt4PW.setEnabled(false);
                pwSize = 3;

                ConfirmSettings();
            }
        });

        txt3PW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PwNBackupActivity.this, Popup_Password.class);
                intent.putExtra("pwsize", pwSize);
                //startActivityForResult(intent, REQUEST_PWSIZE);
                startActivity(intent);
            }
        });

        txt3PWTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn2PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn3PwOn.setBackgroundResource(R.drawable.onoff_on);
                btn4PwOn.setBackgroundResource(R.drawable.onoff_off);
                txt1PW.setEnabled(false);
                txt2PW.setEnabled(false);
                txt3PW.setEnabled(true);
                txt4PW.setEnabled(false);
                pwSize = 3;

                ConfirmSettings();
            }
        });

        btn4PwOn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                btn1PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn2PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn3PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn4PwOn.setBackgroundResource(R.drawable.onoff_on);
                txt1PW.setEnabled(false);
                txt2PW.setEnabled(false);
                txt3PW.setEnabled(false);
                txt4PW.setEnabled(true);
                pwSize = 4;
                ConfirmSettings();
            }
        });

        txt4PW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PwNBackupActivity.this, Popup_Password.class);
                intent.putExtra("pwsize", pwSize);
                //startActivityForResult(intent, REQUEST_PWSIZE);
                startActivity(intent);
            }
        });

        txt4PWTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn2PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn3PwOn.setBackgroundResource(R.drawable.onoff_off);
                btn4PwOn.setBackgroundResource(R.drawable.onoff_on);
                txt1PW.setEnabled(false);
                txt2PW.setEnabled(false);
                txt3PW.setEnabled(false);
                txt4PW.setEnabled(true);
                pwSize = 4;
                ConfirmSettings();
            }
        });



        txtBackupPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PwNBackupActivity.this, Popup_BackupPIN.class);
                intent.putExtra("pwsize", 3);
                startActivity(intent);
            }
        });

        mlinearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                //if(passwordChk()){
                //    if(pinChk()){
                        //ConfirmSettings();
                        //ConfirmSettings_PWPIN();
                        finish();
                //    }
                //}

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(getApplicationContext(),"onActivityResult = " + directIcon, Toast.LENGTH_LONG).show();
        if(requestCode == REQUEST_PWSIZE) {
            pwSize = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_SIZE, 2);
        }
    }

    public boolean passwordChk(){
        String tmpPw1 = "";
        String tmpPw2 = "";

        if(pwSize == 1){
            tmpPw1 = edit2PwTxt.getText().toString();
            tmpPw2 = edit2PwChkTxt.getText().toString();
        } else if(pwSize == 2){
            tmpPw1 = edit4PwTxt.getText().toString();
            tmpPw2 = edit4PwChkTxt.getText().toString();
        } else if(pwSize == 3){
            tmpPw1 = edit4PwTxt.getText().toString();
            tmpPw2 = edit4PwChkTxt.getText().toString();
        } else if(pwSize == 4){
            tmpPw1 = edit4PwTxt.getText().toString();
            tmpPw2 = edit4PwChkTxt.getText().toString();
        }


        if(tmpPw1.equals(tmpPw2)){
            strPw = tmpPw1;
            return true;
        } else {
            Toast.makeText(getApplicationContext(),"Password를 확인해 주세요 ", Toast.LENGTH_LONG).show();
        }

        return false;
    }

    public boolean pinChk(){
        String tmpPw1 = "";
        String tmpPw2 = "";

        tmpPw1 = editPinTxt.getText().toString();
        tmpPw2 = editPinChkTxt.getText().toString();

        inputEmail = editEmailTxt.getText().toString();

        if(tmpPw1.equals(tmpPw2)){
            strPin = editPinTxt.getText().toString();
            return true;
        } else {
            Toast.makeText(getApplicationContext(),"백업 PIN을 확인해 주세요 ", Toast.LENGTH_LONG).show();
        }

        return false;
    }


    public void ConfirmSettings_PWPIN(){
        //Log.d("Inhyo Test","ScreenLock - PreferenceUtil.BTN_TYPE : " + r2nIconType);
        //PreferenceUtil.savePref(this, PreferenceUtil.PW_LIST, strPw);
        PreferenceUtil.savePref(this, PreferenceUtil.BACKUP_PIN, strPin);
    }

    public void ConfirmSettings(){
        //Log.d("Inhyo Test","ScreenLock - PreferenceUtil.BTN_TYPE : " + r2nIconType);
        //PreferenceUtil.savePref(this, PreferenceUtil.BG_INPUT_EMAIL, inputEmail);
        PreferenceUtil.savePref(this, PreferenceUtil.PW_SIZE, pwSize);
    }

}
