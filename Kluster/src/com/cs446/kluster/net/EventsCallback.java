package com.cs446.kluster.net;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import android.content.Context;
import android.util.Log;

import com.cs446.kluster.data.EventStorageAdapter;
import com.cs446.kluster.models.Event;

public class EventsCallback implements retrofit.Callback<List<Event>> {
	Context mContext;
	public EventsCallback(Context c) {
		mContext = c;
	}
	
	@Override
	public void failure(RetrofitError error) {
		Log.e("retrofit", error.getResponse().getReason());
		
	}

	@Override
	public void success(List<Event> list, Response response) {
		EventStorageAdapter storage = new EventStorageAdapter(mContext.getContentResolver());
		for (Event e : list) {
			storage.insert(e);
		}
	}
}
