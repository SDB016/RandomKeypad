package com.otk.fts.myotk.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.otk.fts.myotk.R;

import androidx.appcompat.app.AppCompatActivity;

public class PoweroffActivity extends AppCompatActivity {

    private LinearLayout mlinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poweroff);

        Button mBtnBack = (Button)findViewById(R.id.btn_back_pwoff);

        TextView txt_poweroff_off = (TextView)findViewById(R.id.txt_poweroff_off);
        TextView txt_poweroff_on = (TextView)findViewById(R.id.txt_poweroff_on);

        ImageButton btn_poweroff_off = (ImageButton)findViewById(R.id.btn_power_off);
        ImageButton btn_poweroff_on = (ImageButton)findViewById(R.id.btn_power_on);


        mlinearLayout = (LinearLayout)findViewById(R.id.linear_title_poweroff);

        txt_poweroff_off.setTextColor(Color.BLUE);
        btn_poweroff_off.setBackgroundResource(R.drawable.check_btn_on);

        txt_poweroff_on.setTextColor(Color.BLACK);
        btn_poweroff_on.setBackgroundResource(R.drawable.img_trans);



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


        btn_poweroff_off.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_poweroff_off.setTextColor(Color.BLUE);
                btn_poweroff_off.setBackgroundResource(R.drawable.check_btn_on);

                txt_poweroff_on.setTextColor(Color.BLACK);
                btn_poweroff_on.setBackgroundResource(R.drawable.img_trans);
            }
        });


        btn_poweroff_on.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_poweroff_off.setTextColor(Color.BLACK);
                btn_poweroff_off.setBackgroundResource(R.drawable.img_trans);

                txt_poweroff_on.setTextColor(Color.BLUE);
                btn_poweroff_on.setBackgroundResource(R.drawable.check_btn_on);
            }
        });

    }

}
