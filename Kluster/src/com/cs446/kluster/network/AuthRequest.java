package com.cs446.kluster.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.ContentResolver;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.cs446.kluster.json.AuthResponseReader;
import com.cs446.kluster.json.JSONReader;
import com.cs446.kluster.user.UserAuthInfo;

//TODO: Merge this with the GETRequest file some how
//TODO: I don't think a provider is needed for this.
//TODO: Create and throw exception for invalid username and password based on server response when it is implemented.

public class AuthRequest extends AsyncTask<String, String, UserAuthInfo> {
	private ContentResolver mContentResolver;

	public AuthRequest() {
		// mContentResolver = resolver;
	}

	@Override
	protected UserAuthInfo doInBackground(String... urls) {
		try {
			return makeAuthRequest(urls[0], urls[1]);
		} catch (IOException e) {
			Log.e("URL", "Unable to retrieve web page. URL may be invalid.");
			return null;
		} catch (ParseException e) {
			Log.e("XML", "Unable to parse date.");
			return null;
		}
	}

	private UserAuthInfo makeAuthRequest(String userName, String password)
			throws IOException, ParseException {
		InputStream is = null;

		try {
			String myUrl = "http://205.234.153.42/auth";
			URL url = new URL(myUrl);

			String credentials = userName + ":" + password;
			Log.w("Auth", "Credentials: "
					+ credentials);
			
			String base64EncodedCredentials = Base64.encodeToString(
					credentials.getBytes(), Base64.NO_WRAP);

			HttpUriRequest request = new HttpGet(myUrl);
			request.addHeader("Authorization", "Basic "
					+ base64EncodedCredentials);

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);
			
			if(response.getStatusLine().getStatusCode() >= 300){
				return null;
			}
			
			//if(response.getStatusLine().toString())

			AuthResponseReader reader = new AuthResponseReader(mContentResolver);
			HttpEntity jsonEntity=response.getEntity();
			is=jsonEntity.getContent();
//			Log.w("Auth", "The response is: "
//					+ EntityUtils.toString(jsonEntity));
			/* Read XML file (specific to this particular XML file) */
			
			return reader.readJsonStream(is);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return null;
	}
}