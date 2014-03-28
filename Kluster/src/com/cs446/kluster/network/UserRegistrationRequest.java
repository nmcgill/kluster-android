package com.cs446.kluster.network;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.cs446.kluster.user.UserInfo;

public class UserRegistrationRequest extends AsyncTask<UserInfo, Void, Boolean> implements ResponseHandler<Object>{

	@Override
	public Object handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Boolean doInBackground(UserInfo... params) {
		// TODO Auto-generated method stub
		return makeUserRegistrationRequest(params[0]);
	}

	
	Boolean makeUserRegistrationRequest(UserInfo userInfo){
		
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpResponse response;
        JSONObject nameJson = new JSONObject();
        JSONObject requestJson = new JSONObject();

 	    try {
 	    	
			nameJson.put("last", userInfo.getmLastName());
	        nameJson.put("first", userInfo.getmFirstName());
	        requestJson.put("username", userInfo.getmUserName());
	        requestJson.put("password", userInfo.getmPassword());
	        requestJson.put("email", userInfo.getmUserEmail());
	        requestJson.put("name", nameJson);
			
	        Log.w("UserRegistrationRequest", requestJson.toString());
	        
 	    	String url="http://205.234.153.42/users";
 	    	
 	    	StringEntity stringEntity=new StringEntity(requestJson.toString());

 	        HttpPost httppost = new HttpPost(url);
 	        httppost.setHeader("Accept", "application/json");
 	        httppost.setHeader("Content-type", "application/json");
 	        httppost.setEntity(stringEntity); 	        
 	        response=client.execute(httppost);
 	        
 	        
 	        Log.w("UserRegistrationRequest", Integer.toString(response.getStatusLine().getStatusCode()));
 	       
 	        
 	        if(response.getStatusLine().getStatusCode() >= 300){
 	        	return false;
 	        }

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
}
