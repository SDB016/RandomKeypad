package com.otk.fts.myotk.activity;

public class SampleData {
    private int icon;
    private String settingName;
    private String settingSubName;
    private String settingNameDesc;
    private int onoff;

    public SampleData(int icon, String settingName, String settingSubName, String settingNameDesc, int onoff){
        this.icon = icon;
        this.settingName = settingName;
        this.settingSubName = settingSubName;
        this.settingNameDesc = settingNameDesc;
        this.onoff = onoff;

    }

    public int getIcon(){
        return this.icon;
    }

    public String getSettingName(){
        return this.settingName;
    }

    public String getSettingSubName(){ return this.settingSubName; }

    public String getSettingNameDesc(){ return  this.settingNameDesc; }

    public int getOnoff(){ return this.onoff; }
}
