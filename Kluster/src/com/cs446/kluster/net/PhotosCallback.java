package com.cs446.kluster.net;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import android.content.Context;
import android.util.Log;

import com.cs446.kluster.data.PhotoStorageAdapter;
import com.cs446.kluster.models.Photo;

public class PhotosCallback implements retrofit.Callback<List<Photo>> {
	Context mContext;
	public PhotosCallback(Context c) {
		mContext = c;
	}
	
	@Override
	public void failure(RetrofitError error) {
		Log.e("retrofit", error.getResponse().getReason());	
	}

	@Override
	public void success(List<Photo> list, Response response) {
		PhotoStorageAdapter storage = new PhotoStorageAdapter(mContext.getContentResolver());
		for (Photo p : list) {
			storage.insert(p);
		}
	}
}
