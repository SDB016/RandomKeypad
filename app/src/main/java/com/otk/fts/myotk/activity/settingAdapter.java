package com.otk.fts.myotk.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.otk.fts.myotk.R;

import java.util.ArrayList;

public class settingAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<SampleData> settingData;

    public settingAdapter(Context context, ArrayList<SampleData> data){
        mContext = context;
        settingData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount(){
        return settingData.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public SampleData getItem(int position){
        return settingData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Log.d("Inhyo","position = " + position);
        View view = mLayoutInflater.inflate(R.layout.setting_list, null);
        LinearLayout title = (LinearLayout)view.findViewById(R.id.list_title);
        LinearLayout mainList =(LinearLayout)view.findViewById(R.id.mainlist);
        LinearLayout mainTitle = (LinearLayout)view.findViewById(R.id.maintitle);

        mainList.setVisibility(View.VISIBLE);
        mainTitle.setVisibility(View.GONE);
        ImageView iconView = (ImageView)view.findViewById(R.id.setting_icon);
        TextView settingName = (TextView)view.findViewById(R.id.setting_name);
        TextView settingSubName = (TextView)view.findViewById(R.id.setting_subname);
        TextView settingNameDesc = (TextView)view.findViewById(R.id.setting_desc);
        ImageView imageView = (ImageView)view.findViewById(R.id.setting_onoff);

        TextView mainTitleTxt = (TextView)view.findViewById(R.id.maintitletxt);

        iconView.setImageResource(settingData.get(position).getIcon());
        settingName.setText(settingData.get(position).getSettingName());
        settingSubName.setText(settingData.get(position).getSettingSubName());
        settingNameDesc.setText(settingData.get(position).getSettingNameDesc());
        imageView.setImageResource(settingData.get(position).getOnoff());

        if(position == 1){
            settingSubName.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        } else {
            settingSubName.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        }

        if(position == 7 || position == 11 || position == 0){
            mainList.setVisibility(View.GONE);
            mainTitle.setVisibility(View.VISIBLE);

            if(position == 7){
                mainTitleTxt.setText(R.string.setting_image_title);
            } else if(position == 11){
                mainTitleTxt.setText(R.string.setting_normal_title);
            } else if(position == 0){
                mainTitleTxt.setText("");
            }
        }
        return view;
    }
}
