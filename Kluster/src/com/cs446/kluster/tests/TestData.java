package com.cs446.kluster.tests;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;

import com.cs446.kluster.event.Event;
import com.cs446.kluster.event.EventProvider;
import com.cs446.kluster.map.MapAdapter;
import com.cs446.kluster.photo.Photo;
import com.cs446.kluster.photo.PhotoProvider;
import com.cs446.kluster.user.Users;
import com.google.android.gms.maps.model.LatLng;

public class TestData {
	static ContentResolver mContentResolver;
	
	public static void CreateTestData(ContentResolver cr) {
		mContentResolver = cr;
		
		Photo photo1 = new Photo(new BigInteger("ad226e5caaf0880d8a3060e6", 16), Users.getUser().getUserId(), new BigInteger("ad226e5caaf0880d8a3060e7", 16), new LatLng(43.469475, -80.553280), new Date(), "https://farm6.staticflickr.com/5209/5371336354_b209a57a00_b_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo2 = new Photo(new BigInteger("b78c64155df03a3fae291ca8", 16), Users.getUser().getUserId(), new BigInteger("ad226e5caaf0880d8a3060e7", 16), new LatLng(43.469788, -80.552990), new Date(), "https://farm6.staticflickr.com/5135/5408239435_feb2da1f00_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo3 = new Photo(new BigInteger("ff7f35624cacd9b84cb4e161", 16), Users.getUser().getUserId(), new BigInteger("ad226e5caaf0880d8a3060e7", 16), new LatLng(43.469711, -80.553501), new Date(), "https://farm6.staticflickr.com/5056/5408850774_226b5ce3ef_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		
		Photo photo4 = new Photo(new BigInteger("ad226e5caaf0880d8a3060a6", 16), Users.getUser().getUserId(), new BigInteger("ad226e5caaf0880d8a3061a6", 16), new LatLng(43.470565, -80.552827), new Date(), "https://farm6.staticflickr.com/5011/5491298842_c999a63b79_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo5 = new Photo(new BigInteger("ad226e5caaf0880d8a3060e7", 16), Users.getUser().getUserId(), new BigInteger("ad226e5caaf0880d8a3061a6", 16), new LatLng(43.470618, -80.552487), new Date(), "https://farm6.staticflickr.com/5243/5370780817_4051c42430_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo6 = new Photo(new BigInteger("ab226e5caaf0880d8a3060e6", 16), Users.getUser().getUserId(), new BigInteger("ad226e5caaf0880d8a3061a6", 16), new LatLng(43.470764, -80.552839), new Date(), "https://farm6.staticflickr.com/5288/5370785337_dd03dc2781_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo7 = new Photo(new BigInteger("ad226e6caaf0880d8a3060e6", 16), Users.getUser().getUserId(), new BigInteger("ad226e5caaf0880d8a3061a6", 16), new LatLng(43.470633, -80.553063), new Date(), "https://farm6.staticflickr.com/5254/5505389616_088859ddfb_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		
		Photo photo8 = new Photo(new BigInteger("e4d9a23aab8aae062efd1dca", 16), Users.getUser().getUserId(), new BigInteger("e5d9a23aab8aae062efd1dca", 16), new LatLng(43.471283, -80.551040), new Date(), "https://farm6.staticflickr.com/5176/5407140650_93a14bf958_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo9 = new Photo(new BigInteger("e5d9a23aab8aae062efd1dca", 16), Users.getUser().getUserId(), new BigInteger("e5d9a23aab8aae062efd1dca", 16), new LatLng(43.471306, -80.551110), new Date(), "https://farm6.staticflickr.com/5243/5371330214_1aec38fb5c_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo10 = new Photo(new BigInteger("e6d9a23aab8aae062efd1dca", 16), Users.getUser().getUserId(), new BigInteger("e5d9a23aab8aae062efd1dca", 16), new LatLng(43.470952, -80.551123), new Date(), "https://farm6.staticflickr.com/5136/5511046696_851e6bd7d8_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo11 = new Photo(new BigInteger("e7d9a23aab8aae062efd1dca", 16), Users.getUser().getUserId(), new BigInteger("e5d9a23aab8aae062efd1dca", 16), new LatLng(43.471046, -80.550993), new Date(), "https://farm6.staticflickr.com/5044/5370734053_51d70c7368_z_d.jpg", new ArrayList<String>(), true, "file://", "");	
		
		AddtoContentPhotoProvider(photo1);
		AddtoContentPhotoProvider(photo2);
		AddtoContentPhotoProvider(photo3);
		AddtoContentPhotoProvider(photo4);
		AddtoContentPhotoProvider(photo5);
		AddtoContentPhotoProvider(photo6);
		AddtoContentPhotoProvider(photo7);
		AddtoContentPhotoProvider(photo8);
		AddtoContentPhotoProvider(photo9);
		AddtoContentPhotoProvider(photo10);
		AddtoContentPhotoProvider(photo11);

		List<BigInteger> collection1 = new ArrayList<BigInteger>();
		collection1.add(photo1.getPhotoId());
		collection1.add(photo2.getPhotoId());
		collection1.add(photo3.getPhotoId());
		
		List<BigInteger> collection2 = new ArrayList<BigInteger>();
		collection2.add(photo4.getPhotoId());
		collection2.add(photo5.getPhotoId());
		collection2.add(photo6.getPhotoId());
		collection2.add(photo7.getPhotoId());
		
		List<BigInteger> collection3 = new ArrayList<BigInteger>();
		collection3.add(photo8.getPhotoId());
		collection3.add(photo9.getPhotoId());
		collection3.add(photo10.getPhotoId());
		collection3.add(photo11.getPhotoId());
		
		Event event1 = new Event(new BigInteger("ad226e5caaf0880d8a3060e7", 16), new LatLng(43.469460, -80.553256), new Date(), collection1);
		Event event2 = new Event(new BigInteger("ad226e5caaf0880d8a3061a6", 16), new LatLng(43.470610, -80.552703), new Date(), collection2);
		Event event3 = new Event(new BigInteger("e5d9a23aab8aae062efd1dca", 16), new LatLng(43.471129, -80.551166), new Date(), collection3);
		
		AddtoContentEventProvider(event1);
		AddtoContentEventProvider(event2);
		AddtoContentEventProvider(event3);
	}
	
    private static void AddtoContentPhotoProvider(Photo item) {
		ContentValues values = new ContentValues();
    	
		String tags =  "";
		for (String str : item.getTags()) {
			tags += str + ",";
		}
		//trim last ','
		if (tags.length() > 0) {
			tags.substring(0, tags.length()-1);
		}

		values.put("photoid", item.getPhotoId().toString(16));
		values.put("userid", item.getUserId().toString(16));
		values.put("eventid", item.getEventId().toString(16));
		values.put("location", MapAdapter.LatLngToString(item.getLocation()));
		values.put("date", item.getDate().toString());
		values.put("remoteurl", item.getUrl());
		values.put("tags", tags);
		values.put("uploaded", item.getUploaded());
		values.put("localurl", item.getLocalUrl());
		values.put("thumburl", item.getThumbnailUrl());

		mContentResolver.insert(PhotoProvider.CONTENT_URI, values);
    }
    
    private static void AddtoContentEventProvider(Event item) {
		ContentValues values = new ContentValues();
		
		String photos =  "";
		for (BigInteger str : item.getPhotos()) {
			photos += str.toString(16) + ",";
		}
		//trim last ','
		if (photos.length() > 0) {
			photos.substring(0, photos.length()-1);
		}
		
		values.put("eventid", item.getEventId().toString(16));
		values.put("location", MapAdapter.LatLngToString(item.getLocation()));
		values.put("date", item.getDate().toString());
		values.put("photos", photos);

		mContentResolver.insert(EventProvider.CONTENT_URI, values);
    }
    
}
