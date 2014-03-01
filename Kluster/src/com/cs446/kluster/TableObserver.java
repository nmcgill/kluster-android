package com.cs446.kluster;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

public class TableObserver extends ContentObserver {
    ContentResolver mResolver;
    Account mAccount;
    
	public TableObserver(Account account, Context c) {
		super(new Handler());

		mAccount = account;
		mResolver = c.getContentResolver();
	}
	/*
     * Define a method that's called when data in the
     * observed content provider changes.
     * This method signature is provided for compatibility with
     * older platforms.
     */
    @Override
    public void onChange(boolean selfChange) {
        /*
         * Invoke the method signature available as of
         * Android platform version 4.1, with a null URI.
         */
        onChange(selfChange, null);
    }
    /*
     * Define a method that's called when data in the
     * observed content provider changes.
     */
    @Override
    public void onChange(boolean selfChange, Uri changeUri) {
        /*
         * Ask the framework to run your sync adapter.
         * To maintain backward compatibility, assume that
         * changeUri is null. */
    	
    	Bundle bundle = new Bundle();
    	bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
    	
        ContentResolver.requestSync(mAccount, PhotoProvider.PROVIDER_NAME, bundle);
    }
}