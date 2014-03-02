package com.cs446.kluster;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.cs446.kluster.mapadapter.MapAdapter;
import com.google.android.gms.maps.model.LatLng;

public class Photo implements Parcelable  {
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
	
	public Photo(Parcel in) {
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        
		mPhotoId = in.readLong();
		mUserId = in.readLong();
		mEventId = in.readLong();
		mLocation = MapAdapter.StringToLatLng(in.readString());
		try {
			mDate = df.parse(in.readString());
		} catch (ParseException e) {
		}
		mUrl = in.readString();
		mTags = in.readArrayList(String.class.getClassLoader());
		mUploaded = Boolean.getBoolean(in.readString());
		mLocalUrl = Uri.parse(in.readString());
		mThumbnailUrl = in.readString();		
	}
	

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        
		parcel.writeLong(mPhotoId);
		parcel.writeLong(mUserId);
		parcel.writeLong(mEventId);
		parcel.writeString(MapAdapter.LatLngToString(mLocation));
		parcel.writeString(df.format(mDate));
		parcel.writeString(mUrl);
		parcel.writeList(mTags);
		parcel.writeString(Boolean.toString(mUploaded));
		parcel.writeString(mLocalUrl.toString());
		parcel.writeString(mThumbnailUrl);
	}
	
	public static final Parcelable.Creator CREATOR = 
			new Parcelable.Creator() {
		public Photo createFromParcel(Parcel in) { 
			return new Photo(in);
			}
		public Photo[] newArray(int size) {
			return new Photo[size]; }
		};
}
