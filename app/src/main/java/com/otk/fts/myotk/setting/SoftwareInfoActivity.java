package com.otk.fts.myotk.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.otk.fts.myotk.R;

import androidx.appcompat.app.AppCompatActivity;

public class SoftwareInfoActivity extends AppCompatActivity {

    private LinearLayout mlinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_softwareinfo);

        Button mBtnBack = (Button)findViewById(R.id.btn_back_swinfo);
        mBtnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        mlinearLayout = (LinearLayout)findViewById(R.id.linear_title_softwareinfo);
        mlinearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                finish();
            }
        });



    }

}
