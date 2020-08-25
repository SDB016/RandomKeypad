package com.otk.fts.myotk.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.otk.fts.myotk.R;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenLockHelpActivity extends AppCompatActivity {

    private LinearLayout mlinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenlockhelp);

        Button mBtnBack = (Button)findViewById(R.id.btn_back_help);

        mlinearLayout = (LinearLayout)findViewById(R.id.linear_title_screenlockhelp);

        mlinearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                finish();
            }
        });


        mBtnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

}
