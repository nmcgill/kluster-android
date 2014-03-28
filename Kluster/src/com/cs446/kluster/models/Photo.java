package com.cs446.kluster.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

import com.cs446.kluster.views.map.MapUtils;
import com.google.android.gms.maps.model.LatLng;

public class Photo implements Parcelable  {
	private String mPhotoId;
	private String mUserId;
	private String mEventId;
	private LatLng mLocation;
	private Date mDate;
	private String[] mUrl;
	private List<String> mTags;
	private String[] mRating;
	
	public Photo(String pid,
			String uid,
			String eid,
			LatLng loc,
			Date date,
			String[] url,
			List<String> tags,
			String[] rating) {
		
		mPhotoId = pid;
		mUserId = uid;
		mEventId = eid;
		mLocation = loc;
		mDate = date;
		mUrl = url;
		mTags = tags;
		mRating = rating;

	}

	public String getPhotoId() {
		return mPhotoId;
	}

	public LatLng getLocation() {
		return mLocation;
	}
	
	public String getEventId() {
		return mEventId;
	}

	public Date getDate() {
		return mDate;
	}

	public String getUserId() {
		return mUserId;
	}
	
	public String[] getAllUrls() {
		return mUrl;
	}
	
	public String getUrl() {
		return mUrl[0];
	}
	
	public String getMediumUrl() {
		return mUrl[1];
	}
	
	public String getThumbUrl() {
		return mUrl[2];
	}

	public List<String> getTags() {
		return mTags;
	}
	
	public String getRatingsDown() {
		return mRating[0];
	}
	
	public String getRatingsUp() {
		return mRating[1];
	}
	
	public String[] getRatings() {
		return mRating;
	}
		
	public String getTag(String val) {
		return mTags.get(mTags.indexOf(val));
	}
	
	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:zzz'Z'", Locale.US);
	}
	
	public Photo(Parcel in) {
        String[] strArray = {};
        
		mPhotoId = in.readString();
		mUserId = in.readString();
		mEventId = in.readString();
		mLocation = MapUtils.stringToLatLng(in.readString());
		try {
			mDate = getDateFormat().parse(in.readString());
		} catch (ParseException e) {
		}
		in.readStringArray(mUrl);
		in.readStringArray(strArray);
		mTags = new ArrayList<String>(Arrays.asList(strArray));	
	}
	

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(mPhotoId.toString());
		parcel.writeString(mUserId.toString());
		parcel.writeString(mEventId.toString());
		parcel.writeString(MapUtils.latLngToString(mLocation));
		parcel.writeString(getDateFormat().format(mDate));
		parcel.writeStringArray(mUrl);
		parcel.writeArray(mTags.toArray());
	}
	
	public static final Parcelable.Creator<Photo> CREATOR = 
			new Parcelable.Creator<Photo>() {
		public Photo createFromParcel(Parcel in) { 
			return new Photo(in);
			}
		public Photo[] newArray(int size) {
			return new Photo[size]; }
		};
}
