package com.otk.fts.myotk.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.PreferenceUtil;

import androidx.appcompat.app.AppCompatActivity;

public class ShortcutActivity extends AppCompatActivity {

    private int directIcon;
    private LinearLayout mlinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);

        Button mBtnBack = (Button)findViewById(R.id.btn_back_shortct);


        TextView txt_camera_on = (TextView)findViewById(R.id.txt_camera_on);
        //TextView txt_navi_on = (TextView)findViewById(R.id.txt_navi_on);
        TextView txt_calendar_on = (TextView)findViewById(R.id.txt_calendar_on);
        //TextView txt_power_off = (TextView)findViewById(R.id.txt_power_off);

        ImageButton btn_camera_on = (ImageButton)findViewById(R.id.btn_camera_on);
        //ImageButton btn_navi_on = (ImageButton)findViewById(R.id.btn_navi_on);
        ImageButton btn_calendar_on = (ImageButton)findViewById(R.id.btn_calendar_on);
        //ImageButton btn_power_off = (ImageButton)findViewById(R.id.btn_power_off);

        mlinearLayout = (LinearLayout)findViewById(R.id.linear_title_shortcut);

        directIcon = PreferenceUtil.getIntPref(this, PreferenceUtil.DIRECT_TYPE, 0);

        switch (directIcon){
            case 0:
                txt_camera_on.setTextColor(Color.BLUE);
                //txt_navi_on.setTextColor(Color.BLACK);
                txt_calendar_on.setTextColor(Color.BLACK);
                //txt_power_off.setTextColor(Color.BLACK);
                btn_camera_on.setBackgroundResource(R.drawable.onoff_on);
                //btn_navi_on.setBackgroundResource(R.drawable.onoff_off);
                btn_calendar_on.setBackgroundResource(R.drawable.onoff_off);
                //btn_power_off.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 1:
                txt_camera_on.setTextColor(Color.BLACK);
                //txt_navi_on.setTextColor(Color.BLUE);
                txt_calendar_on.setTextColor(Color.BLACK);
                //txt_power_off.setTextColor(Color.BLACK);
                btn_camera_on.setBackgroundResource(R.drawable.onoff_off);
                //btn_navi_on.setBackgroundResource(R.drawable.onoff_on);
                btn_calendar_on.setBackgroundResource(R.drawable.onoff_off);
                //btn_power_off.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 2:
                txt_camera_on.setTextColor(Color.BLACK);
                //txt_navi_on.setTextColor(Color.BLACK);
                txt_calendar_on.setTextColor(Color.BLUE);
                //txt_power_off.setTextColor(Color.BLACK);
                btn_camera_on.setBackgroundResource(R.drawable.onoff_off);
                //btn_navi_on.setBackgroundResource(R.drawable.onoff_off);
                btn_calendar_on.setBackgroundResource(R.drawable.onoff_on);
                //btn_power_off.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 3:
                txt_camera_on.setTextColor(Color.BLACK);
                //txt_navi_on.setTextColor(Color.BLACK);
                txt_calendar_on.setTextColor(Color.BLACK);
                //txt_power_off.setTextColor(Color.BLUE);
                btn_camera_on.setBackgroundResource(R.drawable.onoff_off);
                //btn_navi_on.setBackgroundResource(R.drawable.onoff_off);
                btn_calendar_on.setBackgroundResource(R.drawable.onoff_off);
                //btn_power_off.setBackgroundResource(R.drawable.onoff_on);
                break;

        }



        mBtnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        mlinearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btn_camera_on.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_camera_on.setTextColor(Color.BLUE);
                btn_camera_on.setBackgroundResource(R.drawable.onoff_on);

                //txt_navi_on.setTextColor(Color.BLACK);
                //btn_navi_on.setBackgroundResource(R.drawable.onoff_off);

                txt_calendar_on.setTextColor(Color.BLACK);
                btn_calendar_on.setBackgroundResource(R.drawable.onoff_off);

                //txt_power_off.setTextColor(Color.BLACK);
                //btn_power_off.setBackgroundResource(R.drawable.onoff_off);

                directIcon = 0;
                ConfirmSettings();
            }
        });

        txt_camera_on.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_camera_on.setTextColor(Color.BLUE);
                btn_camera_on.setBackgroundResource(R.drawable.onoff_on);

                //txt_navi_on.setTextColor(Color.BLACK);
                //btn_navi_on.setBackgroundResource(R.drawable.onoff_off);

                txt_calendar_on.setTextColor(Color.BLACK);
                btn_calendar_on.setBackgroundResource(R.drawable.onoff_off);

                //txt_power_off.setTextColor(Color.BLACK);
                //btn_power_off.setBackgroundResource(R.drawable.onoff_off);

                directIcon = 0;
                ConfirmSettings();
            }
        });
/*
        btn_navi_on.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_camera_on.setTextColor(Color.BLACK);
                btn_camera_on.setBackgroundResource(R.drawable.onoff_off);

                txt_navi_on.setTextColor(Color.BLUE);
                btn_navi_on.setBackgroundResource(R.drawable.onoff_on);

                txt_calendar_on.setTextColor(Color.BLACK);
                btn_calendar_on.setBackgroundResource(R.drawable.onoff_off);

                //txt_power_off.setTextColor(Color.BLACK);
                //btn_power_off.setBackgroundResource(R.drawable.onoff_off);

                directIcon = 1;
                ConfirmSettings();
            }
        });*/
/*
        txt_navi_on.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_camera_on.setTextColor(Color.BLACK);
                btn_camera_on.setBackgroundResource(R.drawable.onoff_off);

                txt_navi_on.setTextColor(Color.BLUE);
                btn_navi_on.setBackgroundResource(R.drawable.onoff_on);

                txt_calendar_on.setTextColor(Color.BLACK);
                btn_calendar_on.setBackgroundResource(R.drawable.onoff_off);

                //txt_power_off.setTextColor(Color.BLACK);
                //btn_power_off.setBackgroundResource(R.drawable.onoff_off);

                directIcon = 1;
                ConfirmSettings();
            }
        });*/

        btn_calendar_on.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_camera_on.setTextColor(Color.BLACK);
                btn_camera_on.setBackgroundResource(R.drawable.onoff_off);

                //txt_navi_on.setTextColor(Color.BLACK);
                //btn_navi_on.setBackgroundResource(R.drawable.onoff_off);

                txt_calendar_on.setTextColor(Color.BLUE);
                btn_calendar_on.setBackgroundResource(R.drawable.onoff_on);

                //txt_power_off.setTextColor(Color.BLACK);
                //btn_power_off.setBackgroundResource(R.drawable.onoff_off);

                directIcon = 2;
                ConfirmSettings();
            }
        });

        txt_calendar_on.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_camera_on.setTextColor(Color.BLACK);
                btn_camera_on.setBackgroundResource(R.drawable.onoff_off);

                //txt_navi_on.setTextColor(Color.BLACK);
                //btn_navi_on.setBackgroundResource(R.drawable.onoff_off);

                txt_calendar_on.setTextColor(Color.BLUE);
                btn_calendar_on.setBackgroundResource(R.drawable.onoff_on);

                //txt_power_off.setTextColor(Color.BLACK);
                //btn_power_off.setBackgroundResource(R.drawable.onoff_off);

                directIcon = 2;
                ConfirmSettings();
            }
        });
/*
        btn_power_off.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_camera_on.setTextColor(Color.BLACK);
                btn_camera_on.setBackgroundResource(R.drawable.onoff_off);

                txt_navi_on.setTextColor(Color.BLACK);
                btn_navi_on.setBackgroundResource(R.drawable.onoff_off);

                txt_calendar_on.setTextColor(Color.BLACK);
                btn_calendar_on.setBackgroundResource(R.drawable.onoff_off);

                txt_power_off.setTextColor(Color.BLUE);
                btn_power_off.setBackgroundResource(R.drawable.onoff_on);

                directIcon = 3;
                ConfirmSettings();
            }
        });

        txt_power_off.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_camera_on.setTextColor(Color.BLACK);
                btn_camera_on.setBackgroundResource(R.drawable.onoff_off);

                txt_navi_on.setTextColor(Color.BLACK);
                btn_navi_on.setBackgroundResource(R.drawable.onoff_off);

                txt_calendar_on.setTextColor(Color.BLACK);
                btn_calendar_on.setBackgroundResource(R.drawable.onoff_off);

                txt_power_off.setTextColor(Color.BLUE);
                btn_power_off.setBackgroundResource(R.drawable.onoff_on);

                directIcon = 3;
                ConfirmSettings();
            }
        });*/
    }

    void ConfirmSettings(){
        //Toast.makeText(getApplicationContext(),"ShortCut - PreferenceUtil.DIRECT_TYPE :" + directIcon, Toast.LENGTH_LONG).show();
        Log.d("Inhyo Test","ShortCut - PreferenceUtil.DIRECT_TYPE : " + directIcon);
        PreferenceUtil.savePref(this, PreferenceUtil.DIRECT_TYPE, directIcon);
    }

}
