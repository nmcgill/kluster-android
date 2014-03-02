package com.cs446.kluster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

public class Photo implements Serializable  {
	private long mPhotoId;
	private long mUserId;
	private long mEventId;
	private LatLng mLocation;
	private Date mDate;
	private String mUrl;
	private ArrayList<String> mTags;
	private Boolean mUploaded;
	private Uri mLocalUrl;
	private String mThumbnailUrl;
	
	public Photo(int pid,
				long uid,
				long eid,
				LatLng loc,
				Date date,
				String url,
				ArrayList<String> tags,
				Boolean uploaded,
				Uri localurl,
				String thumburl) {
		
		mPhotoId = pid;
		mLocation = loc;
		mDate = date;
		mUserId = uid;
		mEventId = eid;
		mUrl = url;
		mTags = tags;
		mUploaded = uploaded;
		mLocalUrl = localurl;
		mThumbnailUrl = thumburl;
	}

	public long getPhotoId() {
		return mPhotoId;
	}

	public LatLng getLocation() {
		return mLocation;
	}
	
	public long getEventId() {
		return mEventId;
	}

	public Date getDate() {
		return mDate;
	}

	public long getUserId() {
		return mUserId;
	}

	public String getUrl() {
		return mUrl;
	}

	public ArrayList<String> getTags() {
		return mTags;
	}
	
	public Boolean getUploaded() {
		return mUploaded;
	}
	
	public Uri getLocalUrl() {
		return mLocalUrl;
	}
	
	public String getThumbnailUrl() {
		return mThumbnailUrl;
	}
	
	public String getTag(String val) {
		return mTags.get(mTags.indexOf(val));
	}
}
