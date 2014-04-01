package com.cs446.kluster.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import com.cs446.kluster.R;
import com.cs446.kluster.data.PhotoProvider.PhotoOpenHelper;
import com.squareup.picasso.Picasso;

public class PhotoGridAdapter extends SimpleCursorAdapter {
	
	private final LayoutInflater mInflator;
	private Activity mActivity;
	
	public PhotoGridAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		
		mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mActivity = (Activity)context;
	}
	
	@Override
	public Object getItem(int position) {
		getCursor().moveToPosition(position);
		
		return getCursor().getString(getCursor().getColumnIndex("url"));
	}
	
	@Override
    public void bindView(View view, Context context, Cursor cursor) { 
       ImageView imgBackground = (ImageView)view.getTag(R.id.photogrid_imgBackground);
        
//        imgBackground.setOnClickListener(new OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				PhotoViewerFragment fragment = new PhotoViewerFragment();
//					getCursor().moveToPosition(position)
//					Bundle bundle = new Bundle();
//					bundle.putString("url", getCursor().getString(getCursor().getColumnIndex(PhotoOpenHelper.COLUMN_URL)));
//					bundle.putString("up", getCursor().getString(getCursor().getColumnIndex(PhotoOpenHelper.COLUMN_RATING_UP)));
//					bundle.putString("down", getCursor().getString(getCursor().getColumnIndex(PhotoOpenHelper.COLUMN_RATING_DOWN)));
//					bundle.putString("photoid", getCursor().getString(getCursor().getColumnIndex(PhotoOpenHelper.COLUMN_PHOTO_ID)));
//					bundle.putString("userid", getCursor().getString(getCursor().getColumnIndex(PhotoOpenHelper.COLUMN_USER_ID)));
//					
//					fragment.setArguments(bundle);
//					mActivity.getFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(fragment.toString()).commit();
//			}
//		});
        
        String url = cursor.getString(cursor.getColumnIndex(PhotoOpenHelper.COLUMN_URL));
        
        Picasso.with(mActivity).load(url).into(imgBackground);
        
	    //KlusterApplication.getInstance().getCache().loadBitmap(url, imgBackground, mActivity);

	    imgBackground.invalidate();
	}
	
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
    	View view = mInflator.inflate(R.layout.photogridcell_layout, parent, false);
    	
        view.setTag(R.id.photogrid_imgBackground, view.findViewById(R.id.photogrid_imgBackground));
        
    	return view;
    }
}