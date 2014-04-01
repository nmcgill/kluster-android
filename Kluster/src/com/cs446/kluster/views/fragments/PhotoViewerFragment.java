package com.cs446.kluster.views.fragments;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs446.kluster.R;
import com.cs446.kluster.data.PhotoProvider;
import com.cs446.kluster.data.PhotoProvider.PhotoOpenHelper;
import com.cs446.kluster.net.AuthKlusterRestAdapter;
import com.cs446.kluster.net.KlusterService;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

public class PhotoViewerFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.photoview_layout, container, false);
		
		RestAdapter adapter = new AuthKlusterRestAdapter()
		.build();
		KlusterService service = adapter.create(KlusterService.class);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		String url = getArguments().getString("url");
		final Integer up = Integer.parseInt(getArguments().getString("up"));
		final Integer down = Integer.parseInt(getArguments().getString("down"));
		final String id = getArguments().getString("photoid");
		String userid = getArguments().getString("userid");
		
		ImageView imgMain =(ImageView)getView().findViewById(R.id.photoview_imgMain);
		ImageView imgUp = (ImageView)getView().findViewById(R.id.photoview_imgUp);
		final TextView txtUp = (TextView)getView().findViewById(R.id.photoview_txtUp);
		ImageView imgDown = (ImageView)getView().findViewById(R.id.photoview_imgDown);
		final TextView txtDown = (TextView)getView().findViewById(R.id.photoview_txtDown);
		
		txtUp.setText(Integer.toString(up));
		txtDown.setText(Integer.toString(down));
		
		imgUp.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				RestAdapter adapter = new AuthKlusterRestAdapter()
				.build();
				KlusterService service = adapter.create(KlusterService.class);
				
				JsonObject object = new JsonObject();
				object.addProperty("up", 1);
				service.ratePhotoUp(id, object, new Callback<Response>() {
					@Override
					public void failure(RetrofitError error) {
						Log.e("Rating", error.getResponse().getReason());
					}

					@Override
					public void success(Response response1, Response response2) {	
						ContentValues values = new ContentValues();
						values.put(PhotoOpenHelper.COLUMN_RATING_UP, up+1);
						
						getActivity().getContentResolver().update(PhotoProvider.CONTENT_URI,
								values, "photoid = ?", new String[] { id });
						
						txtUp.setText(Integer.toString(up+1));
					}
				});
			}
		});
		
		imgDown.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				RestAdapter adapter = new AuthKlusterRestAdapter()
				.build();
				KlusterService service = adapter.create(KlusterService.class);
				
				JsonObject object = new JsonObject();
				object.addProperty("down", 1);
				service.ratePhotoUp(id, object, new Callback<Response>() {
					@Override
					public void failure(RetrofitError error) {
						Log.e("Rating", error.getResponse().getReason());
					}

					@Override
					public void success(Response response1, Response response2) {	
						ContentValues values = new ContentValues();
						values.put(PhotoOpenHelper.COLUMN_RATING_DOWN, down+1);
						
						getActivity().getContentResolver().update(PhotoProvider.CONTENT_URI,
								values, "photoid = ?", new String[] { id });						

						txtDown.setText(Integer.toString(down+1));
					}
				});
			}
		});
         
		Picasso.with(getActivity()).load(url).into(imgMain);
        
		//KlusterApplication.getInstance().getCache().loadBitmap(url, imgMain, getActivity());		
	}
}