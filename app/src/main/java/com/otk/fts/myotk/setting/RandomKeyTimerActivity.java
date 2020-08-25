package com.otk.fts.myotk.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.PreferenceUtil;

import androidx.appcompat.app.AppCompatActivity;

public class RandomKeyTimerActivity extends AppCompatActivity {

    private int timer;
    private LinearLayout mlinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randkeytimer);

        Button mBtnBack = (Button)findViewById(R.id.btn_back_rdkey_time);
        mlinearLayout = (LinearLayout)findViewById(R.id.linear_title_randkeytimer);


        ImageButton btn_rdkey_time0 = (ImageButton)findViewById(R.id.btn_rdkey_time0);
        ImageButton btn_rdkey_time2 = (ImageButton)findViewById(R.id.btn_rdkey_time2);
        ImageButton btn_rdkey_time3 = (ImageButton)findViewById(R.id.btn_rdkey_time3);
        ImageButton btn_rdkey_time4 = (ImageButton)findViewById(R.id.btn_rdkey_time4);
        ImageButton btn_rdkey_time5 = (ImageButton)findViewById(R.id.btn_rdkey_time5);
        ImageButton btn_rdkey_time10 = (ImageButton)findViewById(R.id.btn_rdkey_time10);

        TextView txt_rdkey_time0 = (TextView)findViewById(R.id.txt_rdkey_time0);
        TextView txt_rdkey_time2 = (TextView)findViewById(R.id.txt_rdkey_time2);
        TextView txt_rdkey_time3 = (TextView)findViewById(R.id.txt_rdkey_time3);
        TextView txt_rdkey_time4 = (TextView)findViewById(R.id.txt_rdkey_time4);
        TextView txt_rdkey_time5 = (TextView)findViewById(R.id.txt_rdkey_time5);
        TextView txt_rdkey_time10 = (TextView)findViewById(R.id.txt_rdkey_time10);

        TextView txt_rdkey_time_title = (TextView)findViewById(R.id.txt_title_randomtimer);

        timer = PreferenceUtil.getIntPref(this, PreferenceUtil.PW_TIMER, 0);

        switch(timer){
            case 0:
                txt_rdkey_time0.setTextColor(Color.BLUE);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);
                break;

            case 2000:
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLUE);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 3000:
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLUE);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 4000:
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLUE);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 5000:
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLUE);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 10000:
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLUE);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_on);
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

        btn_rdkey_time0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                txt_rdkey_time0.setTextColor(Color.BLUE);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);

                timer = 0;
                ConfirmSettings();
            }
        });

        txt_rdkey_time0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                txt_rdkey_time0.setTextColor(Color.BLUE);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);

                timer = 0;
                ConfirmSettings();
            }
        });

        btn_rdkey_time2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Cancel 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLUE);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);

                timer = 2000;
                ConfirmSettings();
            }
        });

        txt_rdkey_time2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Cancel 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLUE);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);

                timer = 2000;
                ConfirmSettings();
            }
        });

        btn_rdkey_time3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Cancel 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);


                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLUE);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);

                timer = 3000;
                ConfirmSettings();
            }
        });

        txt_rdkey_time3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Cancel 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLUE);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);

                timer = 3000;
                ConfirmSettings();
            }
        });

        btn_rdkey_time4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Cancel 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLUE);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);

                timer = 4000;
                ConfirmSettings();
            }
        });

        txt_rdkey_time4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Cancel 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLUE);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);

                timer = 4000;
                ConfirmSettings();
            }
        });

        btn_rdkey_time5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Cancel 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLUE);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);

                timer = 5000;
                ConfirmSettings();
            }
        });

        txt_rdkey_time5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Cancel 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLUE);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_on);

                txt_rdkey_time10.setTextColor(Color.BLACK);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_off);

                timer = 5000;
                ConfirmSettings();
            }
        });

        btn_rdkey_time10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Cancel 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLUE);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_on);

                timer = 10000;
                ConfirmSettings();
            }
        });

        txt_rdkey_time10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Cancel 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                txt_rdkey_time0.setTextColor(Color.BLACK);
                btn_rdkey_time0.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time2.setTextColor(Color.BLACK);
                btn_rdkey_time2.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time3.setTextColor(Color.BLACK);
                btn_rdkey_time3.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time4.setTextColor(Color.BLACK);
                btn_rdkey_time4.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time5.setTextColor(Color.BLACK);
                btn_rdkey_time5.setBackgroundResource(R.drawable.onoff_off);

                txt_rdkey_time10.setTextColor(Color.BLUE);
                btn_rdkey_time10.setBackgroundResource(R.drawable.onoff_on);

                timer = 10000;
                ConfirmSettings();
            }
        });

    }

    void ConfirmSettings(){
        //Log.d("Inhyo Test","ScreenLock - PreferenceUtil.PW_TIMER : " + timer);
        PreferenceUtil.savePref(this, PreferenceUtil.PW_TIMER, timer);
    }

}
