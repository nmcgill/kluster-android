package com.cs446.kluster.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

import com.cs446.kluster.data.EventStorageAdapter;
import com.cs446.kluster.data.PhotoStorageAdapter;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.models.Photo;
import com.google.android.gms.maps.model.LatLng;

public class TestData {
	static ContentResolver mContentResolver;
	
	public static void CreateTestData(Context c) {
		mContentResolver = c.getContentResolver();
		
		SharedPreferences pref = c.getSharedPreferences("User", Context.MODE_PRIVATE);
		String userid = pref.getString("id", "531238e5f330ede5deafbc4e");
		
		EventStorageAdapter eventStorage = new EventStorageAdapter(mContentResolver);
		PhotoStorageAdapter photoStorage = new PhotoStorageAdapter(mContentResolver);
		
		String url = "https://farm6.staticflickr.com/5209/5371336354_b209a57a00_b_d.jpg,_b_d.jpg";
		Photo photo1 = new Photo("ad226e5caaf0880d8a3060e6", userid, "ad226e5caaf0880d8a3060e7", new LatLng(43.469475, -80.553280), new Date(), new String[] {url, url, url, url}, new ArrayList<String>(), new String[] {"0", "0"});
		url = "https://farm6.staticflickr.com/5135/5408239435_feb2da1f00_z_d.jpg";
		Photo photo2 = new Photo("b78c64155df03a3fae291ca8", userid, "ad226e5caaf0880d8a3060e7", new LatLng(43.469788, -80.552990), new Date(), new String[] {url, url, url, url}, new ArrayList<String>(), new String[] {"0", "0"});
		url = "https://farm6.staticflickr.com/5056/5408850774_226b5ce3ef_z_d.jpg";
		Photo photo3 = new Photo("ff7f35624cacd9b84cb4e161", userid, "ad226e5caaf0880d8a3060e7", new LatLng(43.469711, -80.553501), new Date(), new String[] {url, url, url, url}, new ArrayList<String>(), new String[] {"0", "0"});
		
		url = "https://farm6.staticflickr.com/5011/5491298842_c999a63b79_z_d.jpg";
		Photo photo4 = new Photo("ad226e5caaf0880d8a3060a6", userid, "ad226e5caaf0880d8a3061a6", new LatLng(43.470565, -80.552827), new Date(), new String[] {url, url, url, url}, new ArrayList<String>(), new String[] {"0", "0"});
		url = "https://farm6.staticflickr.com/5243/5370780817_4051c42430_z_d.jpg";
		Photo photo5 = new Photo("ad226e5caaf0880d8a3060e7", userid, "ad226e5caaf0880d8a3061a6", new LatLng(43.470618, -80.552487), new Date(), new String[] {url, url, url, url}, new ArrayList<String>(), new String[] {"0", "0"});
		url = "https://farm6.staticflickr.com/5288/5370785337_dd03dc2781_z_d.jpg";
		Photo photo6 = new Photo("ab226e5caaf0880d8a3060e6", userid, "ad226e5caaf0880d8a3061a6", new LatLng(43.470764, -80.552839), new Date(), new String[] {url, url, url, url}, new ArrayList<String>(), new String[] {"0", "0"});
		url = "https://farm6.staticflickr.com/5254/5505389616_088859ddfb_z_d.jpg";
		Photo photo7 = new Photo("ad226e6caaf0880d8a3060e6", userid, "ad226e5caaf0880d8a3061a6", new LatLng(43.470633, -80.553063), new Date(), new String[] {url, url, url, url}, new ArrayList<String>(), new String[] {"0", "0"});
		
		url = "https://farm6.staticflickr.com/5176/5407140650_93a14bf958_z_d.jpg";
		Photo photo8 = new Photo("e4d9a23aab8aae062efd1dca", userid, "e5d9a23aab8aae062efd1dca", new LatLng(43.471283, -80.551040), new Date(), new String[] {url, url, url, url}, new ArrayList<String>(), new String[] {"0", "0"});
		url = "https://farm6.staticflickr.com/5243/5371330214_1aec38fb5c_z_d.jpg";
		Photo photo9 = new Photo("e5d9a23aab8aae062efd1dca", userid, "e5d9a23aab8aae062efd1dca", new LatLng(43.471306, -80.551110), new Date(), new String[] {url, url, url, url}, new ArrayList<String>(), new String[] {"0", "0"});
		url = "https://farm6.staticflickr.com/5136/5511046696_851e6bd7d8_z_d.jpg";
		Photo photo10 = new Photo("e6d9a23aab8aae062efd1dca", userid, "e5d9a23aab8aae062efd1dca", new LatLng(43.470952, -80.551123), new Date(), new String[] {url, url, url, url}, new ArrayList<String>(), new String[] {"0", "0"});
		url = "https://farm6.staticflickr.com/5044/5370734053_51d70c7368_z_d.jpg";
		Photo photo11 = new Photo("e7d9a23aab8aae062efd1dca", userid, "e5d9a23aab8aae062efd1dca", new LatLng(43.471046, -80.550993), new Date(), new String[] {url, url, url, url}, new ArrayList<String>(), new String[] {"0", "0"});	
		
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
		collection1.add(photo1.getSmallUrl());
		collection1.add(photo2.getSmallUrl());
		collection1.add(photo3.getSmallUrl());
		
		List<String> collection2 = new ArrayList<String>();
		collection2.add(photo4.getSmallUrl());
		collection2.add(photo5.getSmallUrl());
		collection2.add(photo6.getSmallUrl());
		collection2.add(photo7.getSmallUrl());
		
		List<String> collection3 = new ArrayList<String>();
		collection3.add(photo8.getSmallUrl());
		collection3.add(photo9.getSmallUrl());
		collection3.add(photo10.getSmallUrl());
		collection3.add(photo11.getSmallUrl());
		
		List<String> tags = new ArrayList<String>();
		tags.add("test");
		
		Event event1 = new Event("ad226e5caaf0880d8a3060e7", "Camping in the Mountains", new LatLng(43.469460, -80.553256), new Date(), new Date(), tags, collection1);
		Event event2 = new Event("ad226e5caaf0880d8a3061a6", "Steve's Birthday Party", new LatLng(43.470610, -80.552703), new Date(), new Date(), tags, collection2);
		Event event3 = new Event("e5d9a23aab8aae062efd1dca", "Red Hot Chili Peppers 2014", new LatLng(43.471129, -80.551166), new Date(), new Date(), tags, collection3);
		
		eventStorage.insert(event1);
		eventStorage.insert(event2);
		eventStorage.insert(event3);
	}
    
}
