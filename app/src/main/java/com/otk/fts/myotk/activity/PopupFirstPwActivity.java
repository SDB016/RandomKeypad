package com.otk.fts.myotk.activity;

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

public class PopupFirstPwActivity extends Activity {


    private EditText editPW1;
    private EditText editPW2;
    private Button btnPWOK;

    private String tmpPW;
    private String tmpPWChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_popup_);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_firstpw);

        editPW1 = (EditText)findViewById(R.id.edit_default_pw);
        editPW2 = (EditText)findViewById(R.id.edit_default_pw_chk);
        btnPWOK = (Button)findViewById(R.id.btn_default_pw);


        btnPWOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmpPW = editPW1.getText().toString();
                tmpPWChk = editPW2.getText().toString();



                if(passwordChk(tmpPW, tmpPWChk)){
                    ConfirmSettings();

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();
                }

            }
        });



    }




    public boolean passwordChk(String inputPW1, String inputPW2){

        if((inputPW1.length() == 4)&&(inputPW2.length() == 4)) {
            if (inputPW1.equals(inputPW2)) {

                return true;
            } else {
                Toast.makeText(getApplicationContext(), "비밀번호를 확인해 주세요  ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "4자리를 입력 해주세요  ", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public void ConfirmSettings(){
        //Log.d("Inhyo Test","ScreenLock - PreferenceUtil.BTN_TYPE : " + r2nIconType);
//        PreferenceUtil.savePref(this, PreferenceUtil.PW_SIZE, pwSize);

          PreferenceUtil.savePref(this, PreferenceUtil.PW_LIST4, tmpPW);
  //    }

    }
}
