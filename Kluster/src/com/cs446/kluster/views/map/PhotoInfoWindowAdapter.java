package com.cs446.kluster.views.map;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.cs446.kluster.R;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class PhotoInfoWindowAdapter implements InfoWindowAdapter, OnClickListener {
	private LayoutInflater mInflator;
	private Context mContext;
	
	public PhotoInfoWindowAdapter(Context context) {
		mContext = context;
		mInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getInfoContents(Marker marker) {
		View v = mInflator.inflate(R.layout.infowindow_layout, null);

		return v;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}