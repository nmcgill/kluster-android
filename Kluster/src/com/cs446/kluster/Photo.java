package com.cs446.kluster;

import java.util.ArrayList;
import java.util.Date;

import com.google.android.gms.maps.model.LatLng;

import android.location.Location;
import android.net.Uri;

public class Photo {
	private long mPhotoId;
	private LatLng mLocation;
	private Date mDate;
	private long mUserId;
	private String mUrl;
	private ArrayList<String> mTags;
	private Boolean mUploaded;
	private Uri mLocalUrl;
	private String mThumbnailUrl;
	
	public Photo(int pid,
				LatLng loc,
				Date date,
				long uid,
				String url,
				ArrayList<String> tags,
				Boolean uploaded,
				Uri localurl,
				String thumburl) {
		
		mPhotoId = pid;
		mLocation = loc;
		mDate = date;
		mUserId = uid;
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
