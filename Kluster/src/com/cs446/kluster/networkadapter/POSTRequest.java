package com.cs446.kluster.networkadapter;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;

import com.cs446.kluster.Photo;

public class POSTRequest extends AsyncTask<Photo, Photo, Boolean> {

	public POSTRequest() {
	}
	   
     @Override
     protected Boolean doInBackground(Photo... urls) {
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

 	@SuppressWarnings("deprecation")
	private Boolean sendPOST(Photo photo) throws IOException {

 	    try {
 	    	JSONArray longlat = new JSONArray();
			longlat.put(photo.getLocation().longitude);
			longlat.put(photo.getLocation().latitude);
			
		    HttpParams params = new BasicHttpParams();
		    params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		    DefaultHttpClient mHttpClient = new DefaultHttpClient(params);

	        HttpPost httppost = new HttpPost("http://klusterapi.herokuapp.com/photos/upload");
	        
			MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE); 
	        
	        multipartEntity.addPart("image", new FileBody(new File(photo.getLocalUrl().getPath())));
	        Log.d("post", photo.getLocalUrl().getPath());
	        multipartEntity.addPart("_event", new StringBody(photo.getEventId().toString(16)));
	        Log.d("post", String.valueOf(photo.getEventId()));
	        multipartEntity.addPart("tags[0]", new StringBody("foo"));
	        Log.d("post", "foo");
	        multipartEntity.addPart("tags[1]", new StringBody("dbar"));
	        Log.d("post", "bar");
	        multipartEntity.addPart("loc", new StringBody(longlat.toString()));
	        Log.d("post", longlat.toString());
	        multipartEntity.addPart("time", new StringBody(photo.getDate().toString()));
	        httppost.setEntity(multipartEntity);
	        Log.d("post", photo.getDate().toString());
	        
	        mHttpClient.execute(httppost, new PhotoUploadResponseHandler());
		}
	    catch (JSONException e) {
	        // TODO Auto-generated catch block
	    }
 	    catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
 	    finally {
 	    }
 	    
		return true;
 	}
 	
	private class PhotoUploadResponseHandler implements ResponseHandler<Object> {

	    @Override
	    public Object handleResponse(HttpResponse response)
	            throws ClientProtocolException, IOException {

	        HttpEntity r_entity = response.getEntity();
	        String responseString = EntityUtils.toString(r_entity);
	        Log.d("UPLOAD", responseString);

	        return null;
	    }

	}
 }