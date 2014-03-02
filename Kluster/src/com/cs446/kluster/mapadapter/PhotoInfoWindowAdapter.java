package com.cs446.kluster.mapadapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.cs446.kluster.R;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class PhotoInfoWindowAdapter implements InfoWindowAdapter {
	private LayoutInflater mInflator;
	
	public PhotoInfoWindowAdapter(Context context) {
		mInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getInfoContents(Marker marker) {
		View v = mInflator.inflate(R.layout.photoviewer_activity, null);
		ImageView imgPreview = (ImageView)v.findViewById(R.id.imgPreview);
		return v;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}
}
