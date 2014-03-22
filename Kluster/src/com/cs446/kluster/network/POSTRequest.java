package com.cs446.kluster.network;

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

import com.cs446.kluster.photo.Photo;

@SuppressWarnings("deprecation")
public class POSTRequest extends AsyncTask<Photo, Photo, Boolean> implements ResponseHandler<Object> {

	public POSTRequest() {
	}
	   
     @Override
     protected Boolean doInBackground(Photo... urls) {
         try {
             return sendPOST(urls[0], this);
         }
         catch (IOException e) {
         	Log.e("URL", "Unable to establish connection. URL may be invalid.");
             return null;
         }
     }

     @Override
     protected void onPostExecute(Boolean completed) {   
    }
     
 	public static Boolean sendPOST(Photo photo, ResponseHandler<Object> handler) throws IOException { 	
  		JSONArray longlat = new JSONArray();

		String image = photo.getLocalUrl();
		String eventid = photo.getEventId().toString(16);
		String tagOne = "foo";
		String tagTwo = "bar";
		String time = photo.getDate().toString();
			
  	    try {
			longlat.put(photo.getLocation().longitude);
			longlat.put(photo.getLocation().latitude);
			
 		    HttpParams params = new BasicHttpParams();
 		    params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
 		    DefaultHttpClient mHttpClient = new DefaultHttpClient(params);

 	        HttpPost httppost = new HttpPost("http://klusterapi.herokuapp.com/photos/upload");
 	        
 			MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE); 
 	        
 	        multipartEntity.addPart("image", new FileBody(new File(image)));
 	        multipartEntity.addPart("_event", new StringBody(eventid));
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