package com.cs446.kluster.cache;

import com.cs446.kluster.KlusterApplication;

public class StorageAdapter {
	public static KlusterCache getCache() {
		return KlusterApplication.getCacheAdapter();
	}
}
