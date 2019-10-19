package com.otk.fts.myotk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.ActivityUtil;
import com.otk.fts.myotk.utils.QLog;
import com.otk.fts.myotk.utils.Utils;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        if(!Utils.isServiceRunning(this)){
            Utils.startService(getApplicationContext());

        }else{
            QLog.d("service is run");
        }
    }

    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.okBt:
                ActivityUtil.move(this, SettingsActivity.class, true);
                break;

            case R.id.noBt:
                //ActivityUtil.move(this, preLockScreenActivity.class, true);
                ActivityUtil.move(this, LockScreenActivity.class, true);
                break;
        }
    }
}
