package com.cs446.kluster.views.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.cs446.kluster.R;
import com.cs446.kluster.data.EventProvider.EventOpenHelper;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.views.activities.MainActivity;
import com.cs446.kluster.views.activities.PhotoGridActivity;
import com.squareup.picasso.Picasso;

public class EventGridAdapter extends SimpleCursorAdapter {
	private final LayoutInflater mInflator;
	private MainActivity mActivity;
	
	public EventGridAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		
		mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mActivity = (MainActivity)context;
	}
	
	@Override
	public Object getItem(int position) {
		getCursor().moveToPosition(position);
		
		return getCursor().getString(getCursor().getColumnIndex(EventOpenHelper.COLUMN_EVENT_ID));
	}
	
	@Override
    public void bindView(View view, Context context, Cursor cursor) { 
		SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy hh:mmaa", Locale.US);
        TextView txtTitle = (TextView)view.getTag(R.id.eventgrid_txtTitle);
        TextView txtDate = (TextView)view.getTag(R.id.eventgrid_txtDate);
        ViewPager mPager = (ViewPager)view.getTag(R.id.eventgrid_pager);
        
    	String id = cursor.getString(cursor.getColumnIndex(EventOpenHelper.COLUMN_EVENT_ID));
    	String name = cursor.getString(cursor.getColumnIndex(EventOpenHelper.COLUMN_EVENT_NAME));
    	String photos = cursor.getString(cursor.getColumnIndex(EventOpenHelper.COLUMN_PHOTOS)); 	

    	EventPagerAdapter pager = new EventPagerAdapter(id, name, photos); 

        mPager.setAdapter(pager);
        txtTitle.setText(cursor.getString(cursor.getColumnIndex(EventOpenHelper.COLUMN_EVENT_NAME)));   
		try {
			txtDate.setText(df.format(Event.getDateFormat().parse(cursor.getString(cursor.getColumnIndex(EventOpenHelper.COLUMN_STARTTIME)))));
		} catch (ParseException e) {
			Log.e("EventGridAdapter", e.toString());
		}
	}
	
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
    	View view = mInflator.inflate(R.layout.eventgridcell_layout, parent, false);

        view.setTag(R.id.eventgrid_pager, view.findViewById(R.id.eventgrid_pager));
        view.setTag(R.id.eventgrid_txtTitle, view.findViewById(R.id.eventgrid_txtTitle));
        view.setTag(R.id.eventgrid_txtDate, view.findViewById(R.id.eventgrid_txtDate));
        
    	return view;
    }
	
    private class EventPagerAdapter extends PagerAdapter {
    	String mEventId;
    	String mEventName;
    	String[] mPhotos;
    	
        public EventPagerAdapter(String eventid, String eventname, String photos) {
        	mEventId = eventid;
        	mEventName = eventname;
        	mPhotos = TextUtils.split(photos, ",");
        }

        public void destroyItem(View view, int position, Object object) {
            ((ViewPager)view).removeView((ImageView)object);
        }
        
        @Override
        public Object instantiateItem(View view, int position) {

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

            Picasso.with(mActivity).load(mPhotos[position]).into(imgMain);

            ((ViewPager)view).addView(imgMain);
            
            return imgMain;
        }

        @Override
        public int getCount() {
        	return mPhotos.length;
        }

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
    }
}
