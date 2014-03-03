package com.cs446.kluster;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.cs446.kluster.cache.StorageAdapter;

public class AlbumsBrowserAdapter extends SimpleCursorAdapter {
	
	private final LayoutInflater mInflator;
	private MainActivity mActivity;
	
	public AlbumsBrowserAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		
		mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mActivity = (MainActivity)context;
	}
	
	@Override
    public void bindView(View view, Context context, Cursor cursor) { 
        TextView txtThumbnailText = (TextView)view.getTag(R.id.gridTextCaption);
        ImageView imgThumbnail = (ImageView)view.getTag(R.id.gridImageThumbnail);
        
        txtThumbnailText.setText(cursor.getString(cursor.getColumnIndex("location")));
        Uri location = Uri.parse(cursor.getString(cursor.getColumnIndex("localurl")));
        StorageAdapter.getCache().loadBitmap(location.getPath(), imgThumbnail, mActivity);

		imgThumbnail.invalidate();
	}
	
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
    	View view = mInflator.inflate(R.layout.photoalbumgrid_layout, parent, false);
    	
        view.setTag(R.id.gridImageThumbnail, view.findViewById(R.id.gridImageThumbnail));
        view.setTag(R.id.gridTextCaption, view.findViewById(R.id.gridTextCaption));
        
    	return view;
    }
}
