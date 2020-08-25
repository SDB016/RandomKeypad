package com.otk.fts.myotk.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.otk.fts.myotk.R;

public class Popup_Bg_Photo extends Activity {


    private Button btnOk;
    private Button btnClose;
    private Button btnCancel;

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_background_bk);

        btnOk = (Button)findViewById(R.id.btn_bg_ok);
        btnClose = (Button)findViewById(R.id.btn_bg_close);
        btnCancel = (Button)findViewById(R.id.btn_bg_cancel);

        position = getIntent().getIntExtra("POSITION",0);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("POSITION", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void ConfirmSettings(){
    }


}
