package com.cs446.kluster.views.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.cs446.kluster.KlusterApplication;
import com.cs446.kluster.R;
import com.cs446.kluster.data.PhotoProvider;
import com.cs446.kluster.views.activities.MainActivity;
import com.cs446.kluster.views.activities.PhotoGridActivity;

public class EventGridAdapter extends SimpleCursorAdapter implements LoaderManager.LoaderCallbacks<Cursor> {
	private final LayoutInflater mInflator;
	private MainActivity mActivity;
	private List<EventPagerAdapter> mPagerAdapterList;
	
	public EventGridAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		
		mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mActivity = (MainActivity)context;
		mPagerAdapterList = new ArrayList<EventGridAdapter.EventPagerAdapter>();
	}
	
	@Override
	public Object getItem(int position) {
		getCursor().moveToPosition(position);
		
		return getCursor().getString(getCursor().getColumnIndex("eventid"));
	}
	
	@Override
    public void bindView(View view, Context context, Cursor cursor) { 
        TextView txtTitle = (TextView)view.getTag(R.id.eventgrid_txtTitle);
        ViewPager mPager = (ViewPager)view.getTag(R.id.eventgrid_pager);
        
        mPagerAdapterList.add(cursor.getPosition(), new EventPagerAdapter(cursor.getString(cursor.getColumnIndex("eventid")),
        																	cursor.getString(cursor.getColumnIndex("name"))));
        mPager.setAdapter(mPagerAdapterList.get(cursor.getPosition()));
        
        mActivity.getLoaderManager().initLoader(cursor.getPosition(), null, this);

       txtTitle.setText(cursor.getString(cursor.getColumnIndex("name")));

	}
	
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
    	View view = mInflator.inflate(R.layout.eventgridcell_layout, parent, false);
    	
        view.setTag(R.id.eventgrid_pager, view.findViewById(R.id.eventgrid_pager));
        view.setTag(R.id.eventgrid_txtTitle, view.findViewById(R.id.eventgrid_txtTitle));
        
    	return view;
    }

	@Override
	public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
		String[] selectionArgs = {mPagerAdapterList.get(loaderID).getEventId()};
		return new CursorLoader(mActivity, PhotoProvider.CONTENT_URI, null, "eventid = ?", selectionArgs, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mPagerAdapterList.get(loader.getId()).swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mPagerAdapterList.get(loader.getId()).swapCursor(null);
	}
	
    private class EventPagerAdapter extends PagerAdapter {
    	Cursor mCursor;
    	String mEventId;
    	String mEventName;
    	
        public EventPagerAdapter(String eventid, String eventname) {
        	mEventId = eventid;
        	mEventName = eventname;
        }
        
        public String getEventId() {
        	return mEventId;
        }
        
        public void swapCursor(Cursor cursor) {
        	mCursor = cursor;
        	notifyDataSetChanged();
        }

        public void destroyItem(View view, int position, Object object) {
            ((ViewPager)view).removeView((ImageView)object);
        }
        
        @Override
        public Object instantiateItem(View view, int position) {
        	if (mCursor == null) {
        		return null;
        	}
        	
        	mCursor.moveToPosition(position);
        	

            ImageView imgMain = new ImageView(mActivity);
            imgMain.setLayoutParams(new LayoutParams(
            		LayoutParams.MATCH_PARENT,
            		LayoutParams.MATCH_PARENT)); 
            imgMain.setScaleType(ScaleType.CENTER_CROP);
            
            
            imgMain.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				Intent intent = new Intent(mActivity, PhotoGridActivity.class);
    				Bundle bundle = new Bundle();
    				bundle.putString("eventid", mEventId);
    				bundle.putString("eventname", mEventName);
    				intent.putExtra("events", bundle);
    				
    				mActivity.startActivity(intent);
    			}
    		});

            KlusterApplication.getInstance().getCache().loadBitmap((String)mCursor.getString(mCursor.getColumnIndex("url")), imgMain, mActivity);
            
          
            ((ViewPager)view).addView(imgMain);
            
            
            imgMain.invalidate();
            
            return imgMain;
        }

        @Override
        public int getCount() {
            if (mCursor == null)
                return 0;
            else
                return mCursor.getCount();
        }

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
    }
}
