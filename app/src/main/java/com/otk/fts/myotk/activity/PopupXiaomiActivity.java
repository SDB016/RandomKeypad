package com.otk.fts.myotk.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.otk.fts.myotk.R;

public class PopupXiaomiActivity  extends Activity {

    Button btnAllow;
    Button btnDeny;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_popup_);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_screenlock);

        btnAllow = (Button) findViewById(R.id.btn_xiaomi_allow);
        btnDeny = (Button)findViewById(R.id.btn_xiaomi_deny);


        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String manufacturer = "xiaomi";

                if(manufacturer.equalsIgnoreCase(Build.MANUFACTURER)){
                    Intent intent1 = new Intent();
                    intent1.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                    //intent1.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.BackgroundManagementActivity));
                    setResult(RESULT_OK, intent1);
                    startActivity(intent1);

                    finish();
                }

            }
        });

        btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }

}
