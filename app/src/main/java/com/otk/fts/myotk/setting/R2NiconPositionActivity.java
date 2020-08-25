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

public class R2NiconPositionActivity extends AppCompatActivity {

    private boolean isLeft;

    private LinearLayout mlinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r2niconposition);

        mlinearLayout = (LinearLayout)findViewById(R.id.linear_title_r2niconposition);

        Button mBtnBack = (Button)findViewById(R.id.btn_back_r2n_posi);

        TextView txt_position_right = (TextView)findViewById(R.id.txt_position_right);
        TextView txt_position_left = (TextView)findViewById(R.id.txt_position_left);

        ImageButton btn_position_right = (ImageButton)findViewById(R.id.btn_position_right);
        ImageButton btn_position_left = (ImageButton)findViewById(R.id.btn_position_left);

        isLeft = PreferenceUtil.getBooleanPref(this, PreferenceUtil.SHOW_LEFT, true);

        Log.d("Inhyo Test","R2NiconPosition - isLeft : "+isLeft);

        if(isLeft){

            txt_position_right.setTextColor(Color.BLACK);
            btn_position_right.setBackgroundResource(R.drawable.onoff_off);

            txt_position_left.setTextColor(Color.BLUE);
            btn_position_left.setBackgroundResource(R.drawable.onoff_on);

        } else {

            txt_position_right.setTextColor(Color.BLUE);
            btn_position_right.setBackgroundResource(R.drawable.onoff_on);

            txt_position_left.setTextColor(Color.BLACK);
            btn_position_left.setBackgroundResource(R.drawable.onoff_off);
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

        btn_position_right.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("Inhyo Test","R2NiconPosition - Right");
                txt_position_right.setTextColor(Color.BLUE);
                btn_position_right.setBackgroundResource(R.drawable.onoff_on);

                txt_position_left.setTextColor(Color.BLACK);
                btn_position_left.setBackgroundResource(R.drawable.onoff_off);
                isLeft = false;
                ConfirmSettings();
            }
        });

        txt_position_right.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("Inhyo Test","R2NiconPosition - Right");
                txt_position_right.setTextColor(Color.BLUE);
                btn_position_right.setBackgroundResource(R.drawable.onoff_on);

                txt_position_left.setTextColor(Color.BLACK);
                btn_position_left.setBackgroundResource(R.drawable.onoff_off);
                isLeft = false;
                ConfirmSettings();
            }
        });

        btn_position_left.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("Inhyo Test","R2NiconPosition - Left");
                txt_position_right.setTextColor(Color.BLACK);
                btn_position_right.setBackgroundResource(R.drawable.onoff_off);

                txt_position_left.setTextColor(Color.BLUE);
                btn_position_left.setBackgroundResource(R.drawable.onoff_on);
                isLeft = true;
                ConfirmSettings();
            }
        });

        txt_position_left.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("Inhyo Test","R2NiconPosition - Left");
                txt_position_right.setTextColor(Color.BLACK);
                btn_position_right.setBackgroundResource(R.drawable.onoff_off);

                txt_position_left.setTextColor(Color.BLUE);
                btn_position_left.setBackgroundResource(R.drawable.onoff_on);
                isLeft = true;
                ConfirmSettings();
            }
        });
    }

    void ConfirmSettings(){
        Log.d("Inhyo Test","R2NiconPosition - PreferenceUtil.SHOW_LEFT : " + isLeft);
        PreferenceUtil.savePref(this, PreferenceUtil.SHOW_LEFT, isLeft);
    }

}
