package com.otk.fts.myotk.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.PreferenceUtil;

public class Popup_InputEmail extends Activity {

    private EditText inputEmail1;
    private EditText inputEmail2;

    private Button btnOk;
    private Button btnClose;

    private String inputEmail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_popup_);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pupup_inputemail);

        inputEmail1 = (EditText)findViewById(R.id.edit_input_email1);
        inputEmail2 = (EditText)findViewById(R.id.edit_input_email2);

        btnOk = (Button)findViewById(R.id.btn_input_close);
        btnClose = (Button)findViewById(R.id.btn_input_ok);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEmail = inputEmail1 + "@" + inputEmail2;

            }
        });

    }

    public void ConfirmSettings(){
        PreferenceUtil.savePref(this, PreferenceUtil.BG_INPUT_EMAIL, inputEmail);
    }


}
