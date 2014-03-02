package com.cs446.kluster;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PhotoTilesAdapter extends SimpleCursorAdapter {
	
	private final LayoutInflater mInflator;
	private MainActivity mActivity;
	
	public PhotoTilesAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		
		mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mActivity = (MainActivity)context;
	}
	
	@Override
    public void bindView(View view, Context context, Cursor cursor) { 
        TextView txtThumbnailText = (TextView)view.getTag(R.id.PhotoAlbumThumbnailText);
        ImageView imgThumbnail = (ImageView)view.getTag(R.id.PhotoAlbumThumbnailImage);
        
        txtThumbnailText.setText(cursor.getInt(cursor.getColumnIndex("eventid")));
		
        //** WILL BE ADDING THIS SOON. LOAD IMAGE FROM CACHE */
        //String location = cursor.getString(cursor.getColumnIndex("thumburl"));
        //mActivity.loadBitmap(location, imgThumbnail, mActivity);
		
		imgThumbnail.invalidate();
	}
	
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
    	View view = mInflator.inflate(R.layout.photoalbumthumbnail_fragment, parent, false);
    	
        view.setTag(R.id.PhotoAlbumThumbnailImage, view.findViewById(R.id.PhotoAlbumThumbnailImage));
        view.setTag(R.id.PhotoAlbumThumbnailText, view.findViewById(R.id.PhotoAlbumThumbnailText));
        
    	return view;
    }
}