package com.cs446.kluster.net;


import java.io.File;


import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
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

import com.cs446.kluster.ConfigManager;
import com.cs446.kluster.KlusterApplication;
import com.cs446.kluster.models.Photo;

@SuppressWarnings("deprecation")
public class UploadService extends IntentService implements ResponseHandler<Object> {
    final private static String ENDPOINT_PHOTOS = "/photos";
    
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
		UploadFile((Photo)intent.getParcelableExtra("com.cs446.kluster.Photo"), this);
	}

	private boolean UploadFile(Photo photo, ResponseHandler<Object> handler) {
		ConfigManager config = KlusterApplication.getInstance().getConfigManager();
	    
  		JSONArray longlat = new JSONArray();

		String image = photo.getLocalUrl();
		String eventid = photo.getEventId();
		String tagOne = "foo";
		String tagTwo = "bar";
		String time = photo.getDate().toString();
			
  	    try {
			longlat.put(photo.getLocation().longitude);
			longlat.put(photo.getLocation().latitude);
			
 		    HttpParams params = new BasicHttpParams();	    
 		    params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
 		    DefaultHttpClient mHttpClient = new DefaultHttpClient(params);

 	        HttpPost httppost = new HttpPost(config.getProperty(ConfigManager.PROP_URL) + ENDPOINT_PHOTOS);
 	        
 	        httppost.addHeader("Authorization: Bearer ", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjUzMzRjNTMzNDc1NjZhZGU2N2RlNDE2OCIsImVtYWlsIjoibm1jZ2lsbEBleGFtcGxlLmNvbSIsImV4cGlyZXMiOiIyMDE0LTA0LTI3VDAwOjQxOjQwLjQ5MloifQ.ehgvn9o0e2Q-HYCIjBStdDP1g-EKimxl0jZ8Cqdyawg");
		    
 			MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE); 
 	        
 	        multipartEntity.addPart("image", new FileBody(new File(image)));
 	        //multipartEntity.addPart("_event", new StringBody(eventid));
 	        multipartEntity.addPart("tags[0]", new StringBody(tagOne));
 	        multipartEntity.addPart("tags[1]", new StringBody(tagTwo));
 	        multipartEntity.addPart("loc", new StringBody(longlat.toString()));
 	        multipartEntity.addPart("time", new StringBody(time));
 	        
 	        //debug 	        
 	        Log.d("post", image);
 	        Log.d("post", eventid);
 	        Log.d("post", tagOne);
 	        Log.d("post", tagTwo);
 	        Log.d("post", longlat.toString());
 	        Log.d("post", photo.getDate().toString());
 	        
 	        httppost.setEntity(multipartEntity); 	        
 	        mHttpClient.execute(httppost, handler);
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
 	
	@Override
	public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        HttpEntity r_entity = response.getEntity();
        String responseString = EntityUtils.toString(r_entity);
        Log.d("UPLOAD", responseString);

        return null;
	}
}