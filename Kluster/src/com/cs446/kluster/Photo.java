package com.cs446.kluster;

import android.location.Location;


public class Photo {
	private int mPhotoID;
	private Location mLocation;
	/**TODO: Add as needed */
	
	public int getID() {
		return mPhotoID;
	}
	
	public Location getLocation() {
		return mLocation;
	}
	
	
	public Photo(int id, Location loc) {
		mPhotoID = id;
		mLocation = loc;
	}
}
