package com.cs446.kluster.map;


import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.cs446.kluster.R;
import com.cs446.kluster.cache.StorageAdapter;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class PhotoInfoWindowAdapter implements InfoWindowAdapter {
	private LayoutInflater mInflator;
	private String mPath;
	private Context mContext;
	
	public PhotoInfoWindowAdapter(Context context, String path) {
		mContext = context;
		mInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mPath = path;
	}

	@Override
	public View getInfoContents(Marker marker) {
		View v = mInflator.inflate(R.layout.photoviewer_activity, null);
		ImageView imgPreview = (ImageView)v.findViewById(R.id.imgPreview);
		
        StorageAdapter.getCache().loadBitmapfromFile(mPath, imgPreview, mContext);
		return v;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}
}
