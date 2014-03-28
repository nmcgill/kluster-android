package com.cs446.kluster.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;

import com.cs446.kluster.data.EventStorageAdapter;
import com.cs446.kluster.data.PhotoStorageAdapter;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.models.Photo;
import com.cs446.kluster.models.Users;
import com.google.android.gms.maps.model.LatLng;

public class TestData {
	static ContentResolver mContentResolver;
	
	public static void CreateTestData(ContentResolver cr) {
		mContentResolver = cr;
		EventStorageAdapter eventStorage = new EventStorageAdapter(cr);
		PhotoStorageAdapter photoStorage = new PhotoStorageAdapter(cr);
		
		Photo photo1 = new Photo("ad226e5caaf0880d8a3060e6", Users.getUser().getUserId(), "ad226e5caaf0880d8a3060e7", new LatLng(43.469475, -80.553280), new Date(), "https://farm6.staticflickr.com/5209/5371336354_b209a57a00_b_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo2 = new Photo("b78c64155df03a3fae291ca8", Users.getUser().getUserId(), "ad226e5caaf0880d8a3060e7", new LatLng(43.469788, -80.552990), new Date(), "https://farm6.staticflickr.com/5135/5408239435_feb2da1f00_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo3 = new Photo("ff7f35624cacd9b84cb4e161", Users.getUser().getUserId(), "ad226e5caaf0880d8a3060e7", new LatLng(43.469711, -80.553501), new Date(), "https://farm6.staticflickr.com/5056/5408850774_226b5ce3ef_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		
		Photo photo4 = new Photo("ad226e5caaf0880d8a3060a6", Users.getUser().getUserId(), "ad226e5caaf0880d8a3061a6", new LatLng(43.470565, -80.552827), new Date(), "https://farm6.staticflickr.com/5011/5491298842_c999a63b79_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo5 = new Photo("ad226e5caaf0880d8a3060e7", Users.getUser().getUserId(), "ad226e5caaf0880d8a3061a6", new LatLng(43.470618, -80.552487), new Date(), "https://farm6.staticflickr.com/5243/5370780817_4051c42430_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo6 = new Photo("ab226e5caaf0880d8a3060e6", Users.getUser().getUserId(), "ad226e5caaf0880d8a3061a6", new LatLng(43.470764, -80.552839), new Date(), "https://farm6.staticflickr.com/5288/5370785337_dd03dc2781_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo7 = new Photo("ad226e6caaf0880d8a3060e6", Users.getUser().getUserId(), "ad226e5caaf0880d8a3061a6", new LatLng(43.470633, -80.553063), new Date(), "https://farm6.staticflickr.com/5254/5505389616_088859ddfb_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		
		Photo photo8 = new Photo("e4d9a23aab8aae062efd1dca", Users.getUser().getUserId(), "e5d9a23aab8aae062efd1dca", new LatLng(43.471283, -80.551040), new Date(), "https://farm6.staticflickr.com/5176/5407140650_93a14bf958_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo9 = new Photo("e5d9a23aab8aae062efd1dca", Users.getUser().getUserId(), "e5d9a23aab8aae062efd1dca", new LatLng(43.471306, -80.551110), new Date(), "https://farm6.staticflickr.com/5243/5371330214_1aec38fb5c_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo10 = new Photo("e6d9a23aab8aae062efd1dca", Users.getUser().getUserId(), "e5d9a23aab8aae062efd1dca", new LatLng(43.470952, -80.551123), new Date(), "https://farm6.staticflickr.com/5136/5511046696_851e6bd7d8_z_d.jpg", new ArrayList<String>(), true, "file://", "");
		Photo photo11 = new Photo("e7d9a23aab8aae062efd1dca", Users.getUser().getUserId(), "e5d9a23aab8aae062efd1dca", new LatLng(43.471046, -80.550993), new Date(), "https://farm6.staticflickr.com/5044/5370734053_51d70c7368_z_d.jpg", new ArrayList<String>(), true, "file://", "");	
		
		photoStorage.insert(photo1);
		photoStorage.insert(photo2);
		photoStorage.insert(photo3);
		photoStorage.insert(photo4);
		photoStorage.insert(photo5);
		photoStorage.insert(photo6);
		photoStorage.insert(photo7);
		photoStorage.insert(photo8);
		photoStorage.insert(photo9);
		photoStorage.insert(photo10);
		photoStorage.insert(photo11);

		List<String> collection1 = new ArrayList<String>();
		collection1.add(photo1.getPhotoId());
		collection1.add(photo2.getPhotoId());
		collection1.add(photo3.getPhotoId());
		
		List<String> collection2 = new ArrayList<String>();
		collection2.add(photo4.getPhotoId());
		collection2.add(photo5.getPhotoId());
		collection2.add(photo6.getPhotoId());
		collection2.add(photo7.getPhotoId());
		
		List<String> collection3 = new ArrayList<String>();
		collection3.add(photo8.getPhotoId());
		collection3.add(photo9.getPhotoId());
		collection3.add(photo10.getPhotoId());
		collection3.add(photo11.getPhotoId());
		
		Event event1 = new Event("ad226e5caaf0880d8a3060e7", "Camping in the Mountains", new LatLng(43.469460, -80.553256), new Date(), collection1);
		Event event2 = new Event("ad226e5caaf0880d8a3061a6", "Steve's Birthday Party", new LatLng(43.470610, -80.552703), new Date(), collection2);
		Event event3 = new Event("e5d9a23aab8aae062efd1dca", "Red Hot Chili Peppers 2014", new LatLng(43.471129, -80.551166), new Date(), collection3);
		
		eventStorage.insert(event1);
		eventStorage.insert(event2);
		eventStorage.insert(event3);
	}
    
}
