package com.cs446.kluster.model;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.cs446.kluster.map.MapAdapter;
import com.google.android.gms.maps.model.LatLng;

public class Photo implements Parcelable  {
	private BigInteger mPhotoId;
	private BigInteger mUserId;
	private BigInteger mEventId;
	private LatLng mLocation;
	private Date mDate;
	private String mUrl;
	private ArrayList<String> mTags;
	private Boolean mUploaded;
	private String mLocalUrl;
	private String mThumbnailUrl;
	
	public Photo(BigInteger pid,
				BigInteger uid,
				BigInteger eid,
				LatLng loc,
				Date date,
				String url,
				ArrayList<String> tags,
				Boolean uploaded,
				String localurl,
				String thumburl) {
		
		mPhotoId = pid;
		mUserId = uid;
		mEventId = eid;
		mLocation = loc;
		mDate = date;
		mUrl = url;
		mTags = tags;
		mUploaded = uploaded;
		mLocalUrl = localurl;
		mThumbnailUrl = thumburl;
	}

	public BigInteger getPhotoId() {
		return mPhotoId;
	}

	public LatLng getLocation() {
		return mLocation;
	}
	
	public BigInteger getEventId() {
		return mEventId;
	}

	public Date getDate() {
		return mDate;
	}

	public BigInteger getUserId() {
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
	
	public String getLocalUrl() {
		return mLocalUrl;
	}
	
	public String getThumbnailUrl() {
		return mThumbnailUrl;
	}
	
	public String getTag(String val) {
		return mTags.get(mTags.indexOf(val));
	}
	
	public Photo(Parcel in) {
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        String[] strArray = {};
        
		mPhotoId = new BigInteger(in.readString());
		mUserId = new BigInteger(in.readString());
		mEventId = new BigInteger(in.readString());
		mLocation = MapAdapter.StringToLatLng(in.readString());
		try {
			mDate = df.parse(in.readString());
		} catch (ParseException e) {
		}
		mUrl = in.readString();
		in.readStringArray(strArray);
		mTags = new ArrayList<String>(Arrays.asList(strArray));
		mUploaded = Boolean.getBoolean(in.readString());
		mLocalUrl = in.readString();
		mThumbnailUrl = in.readString();		
	}
	

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        
		parcel.writeString(mPhotoId.toString());
		parcel.writeString(mUserId.toString());
		parcel.writeString(mEventId.toString());
		parcel.writeString(MapAdapter.LatLngToString(mLocation));
		parcel.writeString(df.format(mDate));
		parcel.writeString(mUrl);
		parcel.writeArray(mTags.toArray());
		parcel.writeString(Boolean.toString(mUploaded));
		parcel.writeString(mLocalUrl.toString());
		parcel.writeString(mThumbnailUrl);
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
