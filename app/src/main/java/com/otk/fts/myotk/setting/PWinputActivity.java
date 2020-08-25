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

public class PWinputActivity extends AppCompatActivity {

    private int inputCnt;
    private LinearLayout mlinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwinput);

        Button mBtnBack = (Button)findViewById(R.id.btn_back_pwinput);
        mlinearLayout = (LinearLayout)findViewById(R.id.linear_title_pwinput);

        TextView txt_input_time0 = (TextView)findViewById(R.id.txt_input_time0);
       // TextView txt_input_time3 = (TextView)findViewById(R.id.txt_input_time3);
        //TextView txt_input_time5 = (TextView)findViewById(R.id.txt_input_time5);
        TextView txt_input_time10 = (TextView)findViewById(R.id.txt_input_time10);

        ImageButton btn_input_time0 = (ImageButton)findViewById(R.id.btn_input0);
        //ImageButton btn_input_time3 = (ImageButton)findViewById(R.id.btn_input3);
        //ImageButton btn_input_time5 = (ImageButton)findViewById(R.id.btn_input5);
        ImageButton btn_input_time10 = (ImageButton)findViewById(R.id.btn_input10);

        inputCnt = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_CNT, 10);

        switch (inputCnt){
            case 0:
                txt_input_time0.setTextColor(Color.BLUE);
                btn_input_time0.setBackgroundResource(R.drawable.onoff_on);

                //txt_input_time3.setTextColor(Color.BLACK);
                //btn_input_time3.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time5.setTextColor(Color.BLACK);
                //btn_input_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time10.setTextColor(Color.BLACK);
                btn_input_time10.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 3:
                txt_input_time0.setTextColor(Color.BLACK);
                btn_input_time0.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time3.setTextColor(Color.BLUE);
                //btn_input_time3.setBackgroundResource(R.drawable.onoff_on);

                //txt_input_time5.setTextColor(Color.BLACK);
                //btn_input_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time10.setTextColor(Color.BLACK);
                btn_input_time10.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 5:
                txt_input_time0.setTextColor(Color.BLACK);
                btn_input_time0.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time3.setTextColor(Color.BLACK);
                //btn_input_time3.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time5.setTextColor(Color.BLUE);
                //btn_input_time5.setBackgroundResource(R.drawable.onoff_on);

                txt_input_time10.setTextColor(Color.BLACK);
                btn_input_time10.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 10:
                txt_input_time0.setTextColor(Color.BLACK);
                btn_input_time0.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time3.setTextColor(Color.BLACK);
                //btn_input_time3.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time5.setTextColor(Color.BLACK);
                //btn_input_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time10.setTextColor(Color.BLUE);
                btn_input_time10.setBackgroundResource(R.drawable.onoff_on);
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

        btn_input_time0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_input_time0.setTextColor(Color.BLUE);
                btn_input_time0.setBackgroundResource(R.drawable.onoff_on);

                //txt_input_time3.setTextColor(Color.BLACK);
                //btn_input_time3.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time5.setTextColor(Color.BLACK);
                //btn_input_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time10.setTextColor(Color.BLACK);
                btn_input_time10.setBackgroundResource(R.drawable.onoff_off);

                inputCnt = 0;
                ConfirmSettings();
            }
        });

        txt_input_time0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_input_time0.setTextColor(Color.BLUE);
                btn_input_time0.setBackgroundResource(R.drawable.onoff_on);

                //txt_input_time3.setTextColor(Color.BLACK);
                //btn_input_time3.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time5.setTextColor(Color.BLACK);
                //btn_input_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time10.setTextColor(Color.BLACK);
                btn_input_time10.setBackgroundResource(R.drawable.onoff_off);

                inputCnt = 0;
                ConfirmSettings();
            }
        });
/*
        btn_input_time3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //txt_input_time0.setTextColor(Color.BLACK);
                //btn_input_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time3.setTextColor(Color.BLUE);
                btn_input_time3.setBackgroundResource(R.drawable.onoff_on);

                txt_input_time5.setTextColor(Color.BLACK);
                btn_input_time5.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time10.setTextColor(Color.BLACK);
                //btn_input_time10.setBackgroundResource(R.drawable.onoff_off);

                inputCnt = 3;
                ConfirmSettings();
            }
        });

        txt_input_time3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //txt_input_time0.setTextColor(Color.BLACK);
                //btn_input_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time3.setTextColor(Color.BLUE);
                btn_input_time3.setBackgroundResource(R.drawable.onoff_on);

                txt_input_time5.setTextColor(Color.BLACK);
                btn_input_time5.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time10.setTextColor(Color.BLACK);
                //btn_input_time10.setBackgroundResource(R.drawable.onoff_off);

                inputCnt = 3;
                ConfirmSettings();
            }
        });*/
/*
        btn_input_time5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //txt_input_time0.setTextColor(Color.BLACK);
                //btn_input_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time3.setTextColor(Color.BLACK);
                btn_input_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time5.setTextColor(Color.BLUE);
                btn_input_time5.setBackgroundResource(R.drawable.onoff_on);

                //txt_input_time10.setTextColor(Color.BLACK);
                //btn_input_time10.setBackgroundResource(R.drawable.onoff_off);

                inputCnt = 5;
                ConfirmSettings();
            }
        });

        txt_input_time5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //txt_input_time0.setTextColor(Color.BLACK);
                //btn_input_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time3.setTextColor(Color.BLACK);
                btn_input_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time5.setTextColor(Color.BLUE);
                btn_input_time5.setBackgroundResource(R.drawable.onoff_on);

                //txt_input_time10.setTextColor(Color.BLACK);
                //btn_input_time10.setBackgroundResource(R.drawable.onoff_off);

                inputCnt = 5;
                ConfirmSettings();
            }
        });
*/
        btn_input_time10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_input_time0.setTextColor(Color.BLACK);
                btn_input_time0.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time3.setTextColor(Color.BLACK);
                //btn_input_time3.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time5.setTextColor(Color.BLACK);
                //btn_input_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time10.setTextColor(Color.BLUE);
                btn_input_time10.setBackgroundResource(R.drawable.onoff_on);

                inputCnt = 10;
                ConfirmSettings();
            }
        });

        txt_input_time10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_input_time0.setTextColor(Color.BLACK);
                btn_input_time0.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time3.setTextColor(Color.BLACK);
                //btn_input_time3.setBackgroundResource(R.drawable.onoff_off);

                //txt_input_time5.setTextColor(Color.BLACK);
                //btn_input_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_input_time10.setTextColor(Color.BLUE);
                btn_input_time10.setBackgroundResource(R.drawable.onoff_on);

                inputCnt = 10;
                ConfirmSettings();
            }
        });
    }

    void ConfirmSettings(){
        Log.d("Inhyo Test","R2NiconPosition - PreferenceUtil.PW_CNT : " + inputCnt);
        PreferenceUtil.savePref(this, PreferenceUtil.PW_CNT, inputCnt);
    }

}
