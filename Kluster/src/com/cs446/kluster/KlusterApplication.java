package com.cs446.kluster;

import android.app.Application;

import com.cs446.kluster.cache.KlusterCache;

public class KlusterApplication extends Application {
	private static KlusterApplication singleton;
	private KlusterCache mKlusterCache; 
	private ConfigManager mConfigManager;
	
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
		this.mKlusterCache = KlusterCache.getInstance(this);
    }

    public ConfigManager getConfigManager() {
        return this.mConfigManager;
    }
	
	public KlusterCache getCache() {
		return this.mKlusterCache;
	}
}