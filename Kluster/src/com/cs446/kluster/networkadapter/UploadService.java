package com.cs446.kluster.networkadapter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

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
			UploadFile();
		} catch (IOException e) {
		}
	}

	private boolean UploadFile() throws IOException {
		OutputStream os = null;

		try {
			URL url = new URL("kluster.com/upload");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setDoInput(true);
			int response = conn.getResponseCode();
			Log.w("Download URL", "The response is: " + response);
			os = conn.getOutputStream();

			// os.write(File);

			return true;
		} catch (MalformedURLException ex) {

		} finally {
			if (os != null) {
				os.close();
			}
		}

		return false;
	}
}