package com.cs446.kluster;

import android.app.Application;
import android.content.Context;

public class KlusterApplication extends Application {
	private static KlusterApplication singleton;
	private ConfigManager mConfigManager;
	private Context mContext;
	
	public static KlusterApplication getInstance(){
		return singleton;
	}
	
    @Override
    public void onCreate() {
    	super.onCreate();
        init();
    }

    private void init() {
		singleton = this;
        this.mConfigManager = new ConfigManager(this);
		this.mContext = getApplicationContext();
    }

    public ConfigManager getConfigManager() {
        return this.mConfigManager;
    }

	public Context getContext() {
		return this.mContext;
	}
}