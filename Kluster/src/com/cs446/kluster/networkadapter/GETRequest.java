package com.cs446.kluster.networkadapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParserException;

import com.cs446.kluster.XmlReader;

import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
         catch (XmlPullParserException e) {
         	Log.e("XML", "Unable to parse XML.");
             return null;
			}
     }

     @Override
     protected void onPostExecute(Boolean completed) {   
    }

 	private Boolean downloadUrl(String myurl) throws IOException, XmlPullParserException {
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

 	        XmlReader reader = new XmlReader(mContentResolver, mUserID);
 
 	        /* Read XML file (specific to this particular XML file) */
 	        return reader.parse(is);
			}
 	    finally {
 	        if (is != null) {
 	            is.close();
 	        } 
 	    }
 	}
 }