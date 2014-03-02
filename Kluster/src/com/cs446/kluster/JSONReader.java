package com.cs446.kluster;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.location.Location;
import android.net.Uri;
import android.util.JsonReader;

import com.cs446.kluster.mapadapter.MapAdapter;
import com.google.android.gms.maps.model.LatLng;

// Sample JSON from request to Kluster API
//{
//    "photo": {
//        "photoId": 13249871032,
//        "location": {
//            "lat": 32.423,
//            "long": -127.234
//        },
//        "time": 204239108478,
//        "userId": 2,
//        "url": "http: //photos.kluster.com?id=13249871032",
//        “tags”: “foo,bar”
//    }
//}

public class JSONReader {
	private ContentResolver mContentResolver;

	public JSONReader(ContentResolver c) {
		mContentResolver = c;
	}

	public Boolean readJsonStream(InputStream in) throws IOException, ParseException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
    		reader.beginArray();
    		while (reader.hasNext()) {
    			String name = reader.nextName();

    			if (name.equals("photo")) {
    				AddtoPhotoProvider(readPhoto(reader));
    			} else {
    				reader.skipValue();
    			}
    		}
    		reader.endArray();
        }
         finally {
          reader.close();
        }
        
        return true;
    }
	
	private void AddtoPhotoProvider(Photo item) {
		ContentValues values = new ContentValues();
		
		values.put("photoid", item.getPhotoId());
		values.put("location", MapAdapter.LatLngToString(item.getLocation()));
		values.put("date", item.getDate().toString());
		values.put("userid", item.getUserId());
		values.put("url", item.getUrl());
		values.put("tags", item.getTags().toString());
		values.put("localurl", item.getLocalUrl().toString());
		values.put("thumburl", item.getThumbnailUrl());
		values.put("uploaded", item.getUploaded());

		mContentResolver.insert(PhotoProvider.CONTENT_URI, values);
	}

	public Photo readPhoto(JsonReader reader) throws IOException, ParseException {
	    //Photo photo = new Photo();
		int photoId=-1;
		LatLng location = null;
		Date date = null;
		long userId = -1;
		String url="";
		String[] tags = null;
		
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("photoID")) {
				photoId=reader.nextInt();
			} else if (name.equals("location")) {
				location=readLocation(reader);
			} else if (name.equals("time")) {
		        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
		        date = df.parse(reader.nextString());
			} else if (name.equals("userId")) {
				userId=reader.nextLong();
			} else if (name.equals("url")) {
				url=reader.nextString();
			} else if (name.equals("tags")) {
				tags=(reader.nextString()).split(",");
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return new Photo(photoId, location, date, userId, url, new ArrayList<String>(Arrays.asList(tags)), true, Uri.parse(""), "");
	}

	private LatLng readLocation(JsonReader reader) throws IOException {
		double latitude=0;
		double longitude=0;

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("lat")) {
				latitude = reader.nextDouble();
			} else if (name.equals("long")) {
				longitude = reader.nextDouble();
			} else {
				reader.skipValue();
			}
		}
		
		reader.endObject();
		return new LatLng(latitude, longitude);
	}
}