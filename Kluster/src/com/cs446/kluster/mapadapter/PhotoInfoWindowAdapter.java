package com.cs446.kluster.mapadapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.cs446.kluster.R;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class PhotoInfoWindowAdapter implements InfoWindowAdapter {
	private LayoutInflater mInflator;
	private Uri mImagePath;
	
	public PhotoInfoWindowAdapter(Context context, Uri filepath) {
		mInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mImagePath = filepath;
	}

	@Override
	public View getInfoContents(Marker marker) {
		View v = mInflator.inflate(R.layout.photoviewer_activity, null);
		ImageView imgPreview = (ImageView)v.findViewById(R.id.imgPreview);
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = BitmapFactory.decodeFile(mImagePath.toString(), options);
		
		imgPreview.setImageBitmap(bitmap);
		return v;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}
}
