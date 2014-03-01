package com.cs446.kluster.networkadapter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.ContentResolver;
import android.os.AsyncTask;
import android.util.Log;

public class POSTRequest extends AsyncTask<String, String, Boolean> {
	private ContentResolver mContentResolver;
	private int mUserID;
	
	public POSTRequest(ContentResolver resolver, int userid) {
		mContentResolver = resolver;
		mUserID = userid;
	}
	   
     @Override
     protected Boolean doInBackground(String... urls) {
         try {
             return sendPOST(urls[0]);
         }
         catch (IOException e) {
         	Log.e("URL", "Unable to establish connection. URL may be invalid.");
             return null;
         }
     }

     @Override
     protected void onPostExecute(Boolean completed) {   
    }

 	private Boolean sendPOST(String url) throws IOException, ClientProtocolException {
 	    HttpClient httpclient = new DefaultHttpClient();
 	    HttpPost httppost = new HttpPost(url);
 	    
 	    try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("param1", "value1"));
			params.add(new BasicNameValuePair("param2", "value2"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
			httppost.setEntity(entity);
			
			HttpResponse response = httpclient.execute(httppost);

		}
	    catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    }
 	    catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
 	    finally {
 	    }
 	    
		return true;
 	}
 }