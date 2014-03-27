package com.cs446.kluster.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class Event {
	private String mEventId;
	private String mName;
	private LatLng mLocation;
	private Date mDate;
	private List<String> mPhotos;
	
	public Event(String id, String name, LatLng loc, Date date, List<String> photos) {
		mEventId = id;
		mName = name;
		mLocation = loc;
		mDate = date;
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
		return mDate;
	}
	
	public List<String> getPhotos() {
		if (mPhotos == null) {
			return new ArrayList<String>();
		}
		else {
			return mPhotos;
		}
	}
}
