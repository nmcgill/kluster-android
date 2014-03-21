package com.cs446.kluster.event;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class Event {
	private BigInteger mEventId;
	private LatLng mLocation;
	private Date mDate;
	private List<BigInteger> mPhotos;
	
	public Event(BigInteger id, LatLng loc, Date date, List<BigInteger> photos) {
		mEventId = id;
		mLocation = loc;
		mDate = date;
		mPhotos = photos;
	}
	
	public BigInteger getEventId() {
		return mEventId;
	}
	
	public LatLng getLocation() {
		return mLocation;
	}
	
	public Date getDate() {
		return mDate;
	}
	
	public List<BigInteger> getPhotos() {
		if (mPhotos == null) {
			return new ArrayList<BigInteger>();
		}
		else {
			return mPhotos;
		}
	}
}
