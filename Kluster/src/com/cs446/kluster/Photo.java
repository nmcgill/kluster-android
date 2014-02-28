package com.cs446.kluster;

import java.util.Date;

import android.graphics.Paint.Join;
import android.location.Location;
import android.text.format.DateFormat;


public class Photo {
	private long mPhotoId;
	private Location mLocation;
	private java.text.DateFormat mDate;
	private long mUserId;
	private String mUrl;
	private String mTags;
	
	
	
	public Photo() {
		super();
		this.mPhotoId = -1;
		this.mLocation = new Location("NULL");
		this.mDate = null;
		this.mUserId = -1;
		this.mUrl = "";
		this.mTags = "";
	}

	public long getmPhotoId() {
		return mPhotoId;
	}

	public void setmPhotoId(long mPhotoId) {
		this.mPhotoId = mPhotoId;
	}

	public Location getmLocation() {
		return mLocation;
	}

	public void setmLocation(Location mLocation) {
		this.mLocation = mLocation;
	}

	public java.text.DateFormat getmDate() {
		return mDate;
	}

	public void setmDate(java.text.DateFormat dateFormat) {
		this.mDate = dateFormat;
	}

	public long getmUserId() {
		return mUserId;
	}

	public void setmUserId(long mUserId) {
		this.mUserId = mUserId;
	}

	public String getmUrl() {
		return mUrl;
	}

	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}

	public String getmTags() {
		return mTags;
	}

	public void setmTags(String mTags) {
		this.mTags = mTags;
	}
}
