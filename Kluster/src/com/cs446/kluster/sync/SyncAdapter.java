package com.cs446.kluster.sync;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.cs446.kluster.json.JSONReader;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    
    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;
    
    /** Set up the sync adapter */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);

        mContentResolver = context.getContentResolver();
    }
    
    /*
     * Specify the code you want to run in the sync adapter. The entire
     * sync adapter runs in a background thread, so you don't have to set
     * up your own background processing.
     */
    @Override
    public void onPerformSync(
            Account account,
            Bundle extras,
            String authority,
            ContentProviderClient provider,
            SyncResult syncResult) {
    	
    	/** TODO: Put ALL data transfer code here. */
    	Log.d("SYNC", "Performing sync");
    	InputStream is = null;

    	try {
    		try {
	 	        URL url;
	 	        
	 	        url = new URL("http://205.234.153.42/photos");
	 	        
	 	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	 	        conn.setReadTimeout(10000 /* milliseconds */);
	 	        conn.setConnectTimeout(15000 /* milliseconds */);
	 	        conn.setRequestMethod("GET");
	 	        conn.setDoInput(true);
	 	        int response = conn.getResponseCode();
	 	        Log.w("Download URL", "The response is: " + response);
	 	        is = conn.getInputStream();
	
	 	        JSONReader reader = new JSONReader(mContentResolver);
	 
	 	        /* Read XML file (specific to this particular XML file) */
	 	        reader.readJsonStream(is);
			}
 	    	finally {
	 	        if (is != null) {
	 	            is.close();
	 	        } 
 	    	}
    	}
    	catch (MalformedURLException e) {
    	}
    	catch (ParseException e) {	
    	}
    	catch (IOException e) {	
    	}
    }
}