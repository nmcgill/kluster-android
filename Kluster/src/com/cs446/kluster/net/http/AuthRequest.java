package com.cs446.kluster.net.http;

import android.util.Base64;

import com.cs446.kluster.ConfigManager;
import com.cs446.kluster.KlusterApplication;


public class AuthRequest extends Request {
    final private static String ENDPOINT_AUTH = "/auth";
    
    public AuthRequest(Method method, String url) {
        super(method, url);
        header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjUzMzRjNTMzNDc1NjZhZGU2N2RlNDE2OCIsImVtYWlsIjoibm1jZ2lsbEBleGFtcGxlLmNvbSIsImV4cGlyZXMiOiIyMDE0LTA0LTI4VDA1OjI3OjEyLjM5NloifQ.IoXnCbZhQJJq0TuSFXXN1-RPSxFl_lG_SqhunrPjgQQ");
    }
    
    public static Request create(String userName, String password) {
    	
	    ConfigManager config = KlusterApplication.getInstance().getConfigManager();
	    String url = config.getProperty(ConfigManager.PROP_URL) + ENDPOINT_AUTH;
		
		String credentials = userName + ":" + password;
		String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

	    Request request = new Request(Method.GET, url);

		request = request.header("Authorization", "Basic " + base64EncodedCredentials);
	    
	    return request;
    }
}