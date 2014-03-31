package com.cs446.kluster.net;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.cs446.kluster.data.PhotoStorageAdapter;
import com.cs446.kluster.models.Photo;

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
		UploadFile((Photo)intent.getParcelableExtra("com.cs446.kluster.Photo"));
	}
	
	private boolean UploadFile(Photo photo) {
		RestAdapter restAdapter = new AuthKlusterRestAdapter()
		.build();		
		KlusterService service = restAdapter.create(KlusterService.class);
  		JSONArray longlat = new JSONArray();
		String time = Photo.getDateFormat().format(photo.getDate());
		
		try {
			longlat.put(photo.getLocation().latitude);
			longlat.put(photo.getLocation().longitude);
		} catch (JSONException e) {
		}	
		
		service.createPhoto(new TypedFile("application/octet-stream",
				new File(photo.getUrl())),
				new TypedString(longlat.toString()),
				new TypedString(time),
				new TypedString(photo.getTags().get(2)),
				new TypedString(photo.getTags().get(1)),
				new TypedString(photo.getTags().get(0)),
				new Callback<Photo>() {	
					@Override
					public void success(Photo photo, Response response) {
				        PhotoStorageAdapter storage = new PhotoStorageAdapter(getContentResolver());    
				        storage.insert(photo);
					}			
					@Override
					public void failure(RetrofitError error) {
						Log.e("UPLOAD", error.getResponse().getReason());
					}
				});
		
		return true;
	}
}