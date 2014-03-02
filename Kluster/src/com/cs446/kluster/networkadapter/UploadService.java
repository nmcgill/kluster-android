package com.cs446.kluster.networkadapter;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

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

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.cs446.kluster.Photo;

public class UploadService extends IntentService {

	/**
	 * A constructor is required, and must call the super IntentService(String)
	 * constructor with a name for the worker thread.
	 */
	public UploadService() {
		super("UploadService");
	}

	/**
	 * The IntentService calls this method from the default worker thread with
	 * the intent that started the service. When this method returns,
	 * IntentService stops the service, as appropriate.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			UploadFile((Photo)intent.getSerializableExtra("Photo"));
		} catch (IOException e) {
		}
	}

	@SuppressWarnings("deprecation")
	private boolean UploadFile(Photo photo) throws IOException {
		OutputStream os = null;

		try {
			JSONArray longlat = new JSONArray();
			longlat.put(photo.getLocation().longitude);
			longlat.put(photo.getLocation().latitude);
			
		    HttpParams params = new BasicHttpParams();
		    params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		    DefaultHttpClient mHttpClient = new DefaultHttpClient(params);

	        HttpPost httppost = new HttpPost("http://klusterapi.herokuapp.com/");

	        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE); 
	        
	        multipartEntity.addPart("image", new FileBody(new File(photo.getLocalUrl().getPath())));
	        multipartEntity.addPart("_event", new StringBody(String.valueOf(photo.getEventId())));
	        multipartEntity.addPart("tags[0]", new StringBody("foo"));
	        multipartEntity.addPart("tags[1]", new StringBody("dbar"));
	        multipartEntity.addPart("loc", new StringBody(longlat.toString()));
	        multipartEntity.addPart("time", new StringBody(photo.getDate().toString()));
	        httppost.setEntity(multipartEntity);

	        mHttpClient.execute(httppost, new PhotoUploadResponseHandler());

			return true;
		} catch (MalformedURLException ex) {

		} catch (JSONException e) {
			// TODO Auto-generated catch block
		} finally {
			if (os != null) {
				os.close();
			}
		}

		return false;
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