package com.otk.fts.myotk.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.PreferenceUtil;

public class Popup_BackupPIN extends Activity {

    private Button btnClose;
    private Button btnPINOK;
    private EditText editPIN;
    private EditText editPINChk;

    private String backupPIN;

    private String tmpPW;
    private String tmpPWChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_popup_);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_backuppin);

        backupPIN = PreferenceUtil.getStringPref(this, PreferenceUtil.BACKUP_PIN);

        btnClose = (Button)findViewById(R.id.btn_backup_close);
        btnPINOK = (Button)findViewById(R.id.btn_pin_ok);
        editPIN = (EditText)findViewById(R.id.edit_input_pin);
        editPINChk = (EditText)findViewById(R.id.edit_input_pin_chk);


        if(backupPIN.length() > 0){
            editPIN.setText(backupPIN);
            editPINChk.setText(backupPIN);
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPINOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmpPW = editPIN.getText().toString();
                tmpPWChk = editPINChk.getText().toString();


                if(backupPINChk(tmpPW, tmpPWChk)){
                    ConfirmSettings();
                    finish();
                }

            }
        });

    }

    public boolean backupPINChk(String inputPIN1, String inputPIN2){

        if(inputPIN1.equals(inputPIN2)){

            return true;
        } else {
            Toast.makeText(getApplicationContext(),"백업 PIN을 확인해 주세요  ", Toast.LENGTH_LONG).show();
        }

        return false;
    }

    public void ConfirmSettings(){
        //Log.d("Inhyo Test","ScreenLock - PreferenceUtil.BTN_TYPE : " + r2nIconType);
        PreferenceUtil.savePref(this, PreferenceUtil.BACKUP_PIN, tmpPW);

    }
}
