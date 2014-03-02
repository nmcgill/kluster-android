package com.cs446.kluster;

import android.app.Application;

import com.cs446.kluster.cache.KlusterCache;

public class KlusterApplication extends Application {
	private static KlusterApplication singleton;
	private static KlusterCache mCacheAdapter; 
	
	public KlusterApplication getInstance(){
		return singleton;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		singleton = this;
		mCacheAdapter = new KlusterCache(this);
	}
	
	public static KlusterCache getCacheAdapter() {
		return mCacheAdapter;
	}
}