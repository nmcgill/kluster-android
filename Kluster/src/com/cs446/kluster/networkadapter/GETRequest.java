package com.cs446.kluster.networkadapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

import android.content.ContentResolver;
import android.os.AsyncTask;
import android.util.Log;

import com.cs446.kluster.JSONReader;

public class GETRequest extends AsyncTask<String, String, Boolean> {
	private ContentResolver mContentResolver;
	private int mUserID;	
	
	public GETRequest(ContentResolver resolver, int userid) {
		mContentResolver = resolver;
		mUserID = userid;
	}
	   
     @Override
     protected Boolean doInBackground(String... urls) {
         try {
             return downloadUrl(urls[0]);
         }
         catch (IOException e) {
         	Log.e("URL", "Unable to retrieve web page. URL may be invalid.");
             return null;
         }
         catch (ParseException e) {
         	Log.e("XML", "Unable to parse date.");
             return null;
			}
     }

     @Override
     protected void onPostExecute(Boolean completed) {   
    }

 	private Boolean downloadUrl(String myurl) throws IOException, ParseException {
 	    InputStream is = null;

 	    try {
 	        URL url = new URL(myurl);
 	        
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
 	        return reader.readJsonStream(is);
			}
 	    finally {
 	        if (is != null) {
 	            is.close();
 	        } 
 	    }
 	}
 }