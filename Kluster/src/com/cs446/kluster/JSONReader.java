package com.cs446.kluster;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentResolver;
import android.location.Location;
import android.util.JsonReader;

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
	private static final String ns = null;
	private int mUserID;
	private ContentResolver mContentResolver;

	public JSONReader(ContentResolver c, int user) {
		mContentResolver = c;
		mUserID = user;
	}

//	private void AddtoPhotoProvider(Photo item) {
//
//		// **TODO: DO WE EVEN WANT TO USE A PROVIDER? */
//
//		ContentValues values = new ContentValues();
//		DateFormat df = new SimpleDateFormat("MMM dd h:mmaa", Locale.US);
//
//		values.put("title", item.getID());
//		values.put("location", item.getLocation().toString());
//
//		// UNCOMMENT THIS LINE IF WE ARE USING A PROVIDER
//		// mContentResolver.insert(PhotoProvider.CONTENT_URI, values);
//	}

	public List readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
          return readPhotoArray(reader);
        }
         finally {
          reader.close();
        }
      }

	public List readPhotoArray(JsonReader reader) throws IOException {
		List photos = new ArrayList<Photo>();

		reader.beginArray();
		while (reader.hasNext()) {
			String name = reader.nextName();

			if (name.equals("photo")) {
				photos.add(readPhoto(reader));
			} else {
				reader.skipValue();
			}
		}
		reader.endArray();
		return photos;
	}

	public Photo readPhoto(JsonReader reader) throws IOException {
	    //Photo photo = new Photo();
		int photoId=-1;
		Location location = null;
		DateFormat dateFormat = new SimpleDateFormat("MMM dd h:mmaa", Locale.US);
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
				//dateFormat; //TODO: Set Date according to what Marlin gives back
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
		return new Photo(photoId, location, dateFormat, userId, url, new ArrayList<String>(Arrays.asList(tags)));
	}

	private Location readLocation(JsonReader reader) throws IOException {
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
		Location location=new Location("null");
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		reader.endObject();
		return location;
	}
}