package com.cs446.kluster.fragments;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import com.cs446.kluster.MainActivity;
import com.cs446.kluster.R;
import com.cs446.kluster.cache.StorageAdapter;

public class PhotoGridAdapter extends SimpleCursorAdapter {
	
	private final LayoutInflater mInflator;
	private MainActivity mActivity;
	
	public PhotoGridAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		
		mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mActivity = (MainActivity)context;
	}
	
	@Override
	public Object getItem(int position) {
		getCursor().moveToPosition(position);
		
		return getCursor().getString(getCursor().getColumnIndex("remoteurl"));
	}
	
	@Override
    public void bindView(View view, Context context, Cursor cursor) { 
        ImageView imgBackground = (ImageView)view.getTag(R.id.photogrid_imgBackground);
        
        String remoteurl = cursor.getString(cursor.getColumnIndex("remoteurl"));
        
	    StorageAdapter.getCache().loadBitmap(remoteurl, imgBackground, mActivity);

	    imgBackground.invalidate();
	}
	
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
    	View view = mInflator.inflate(R.layout.photogridcell_layout, parent, false);
    	
        view.setTag(R.id.photogrid_imgBackground, view.findViewById(R.id.photogrid_imgBackground));
        
    	return view;
    }
}