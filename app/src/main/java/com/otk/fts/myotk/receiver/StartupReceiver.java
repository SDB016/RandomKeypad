package com.otk.fts.myotk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.otk.fts.myotk.activity.MainActivity;

import androidx.databinding.adapters.TextViewBindingAdapter;

public class StartupReceiver extends BroadcastReceiver {

    public StartupReceiver(){
        Log.d("Inhyo","StartupReceiveR");
    }

    @Override
    public void onReceive(Context context, Intent intent){
        Log.d("Inhyo","StartupReceiver-onReceive");

        context.startService(new Intent(context, MainActivity.class));

        /*
        String packageName = intent.getData().getSchemeSpecificPart();
        String action = intent.getAction();
        Log.d("Inhyo","StartupReceiver-packageName : "+ packageName);
        if(action.equals(Intent.ACTION_PACKAGE_ADDED)){
            Log.d("Inhyo","Package Added :" + packageName);
        } else if(action.equals(Intent.ACTION_PACKAGE_REMOVED)){
            Log.d("Inhyo","Package Removed :" + packageName);
        }else if(action.equals(Intent.ACTION_MY_PACKAGE_REPLACED)){
            Log.d("Inhyo","Package ACTION_MY_PACKAGE_REPLACED :" + packageName);
        }
*/

        /*
        Log.d("Inhyo","StartupReceiver-onReceive");
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        */

    }
}
