package com.cs446.kluster;

import java.text.DateFormat;
import java.util.ArrayList;

import android.location.Location;

public class Photo {
	private long mPhotoId;
	private Location mLocation;
	private DateFormat mDate;
	private long mUserId;
	private String mUrl;
	private ArrayList<String> mTags;
	
	public Photo(int pid, Location loc, DateFormat date, long uid, String url, ArrayList<String> tags) {
		mPhotoId = pid;
		mLocation = loc;
		mDate = date;
		mUserId = uid;
		mUrl = url;
		mTags = tags;
	}
	
	public Photo(int pid, Location loc) {
		this(pid, loc, null, -1, "", new ArrayList<String>());
	}

	public long getPhotoId() {
		return mPhotoId;
	}

	public Location getLocation() {
		return mLocation;
	}

	public DateFormat getDate() {
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
	
	public String getTag(String val) {
		return mTags.get(mTags.indexOf(val));
	}
}
