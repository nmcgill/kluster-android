package com.cs446.kluster.net;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.cs446.kluster.ConfigManager;
import com.cs446.kluster.KlusterApplication;
import com.cs446.kluster.models.User;
import com.cs446.kluster.net.http.Request;

public class UserRequest extends Request {

    final private static String ENDPOINT_USERS = "/users";

    public UserRequest(String url) {
        super(Method.POST, url);
    }

    public static UserRequest create(User user) {
        ConfigManager config = KlusterApplication.getInstance().getConfigManager();
        String url = config.getProperty(ConfigManager.PROP_URL) + ENDPOINT_USERS;
        
        UserRequest request = new UserRequest(url);
        
        request.header("Accept", "application/json");
        request.header("Content-type", "application/json");
        
        JSONObject nameJson = new JSONObject();
        JSONObject requestJson = new JSONObject();

        try {
			nameJson.put("last", user.getLastName());
	        nameJson.put("first", user.getFirstName());
	        requestJson.put("username", user.getUserName());
	        requestJson.put("password", user.getPassword());
	        requestJson.put("email", user.getUserEmail());
	        requestJson.put("name", nameJson);	        
	        
	        request.entity(new StringEntity(requestJson.toString()));
        }
 	    catch (JSONException e) {
 	    }
        catch (UnsupportedEncodingException e) {
		}
  	    finally {
  	    }
	  	    
        return request;
    }
}