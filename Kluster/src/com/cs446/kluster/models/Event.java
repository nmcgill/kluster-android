package com.cs446.kluster.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.model.LatLng;

public class Event {
	private String mEventId;
	private String mName;
	private LatLng mLocation;
	private Date mStartDate;
	private Date mEndDate;
	private List<String> mTags;
	private List<String> mPhotos;
	
	public Event(String id, String name, LatLng loc, Date startDate, Date endDate, List<String> tags, List<String> photos) {
		mEventId = id;
		mName = name;
		mLocation = loc;
		mStartDate = startDate;
		mEndDate = endDate;
		mTags = tags;
		mPhotos = photos;
	}
	
	public String getEventId() {
		return mEventId;
	}
	
	public String getName() {
		return mName;
	}
	
	public LatLng getLocation() {
		return mLocation;
	}
	
	public Date getDate() {
		return getStartDate();
	}
	
	public Date getStartDate() {
		return mStartDate;
	}
	public Date getEndDate() {
		return mEndDate;
	}
	
	public List<String> getTags() {
		return mTags;
	}
	
	public List<String> getPhotos() {
		if (mPhotos == null) {
			return new ArrayList<String>();
		}
		else {
			return mPhotos;
		}
	}	
	
	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
	}
}
