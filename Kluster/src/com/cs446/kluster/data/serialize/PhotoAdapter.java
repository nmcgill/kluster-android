package com.cs446.kluster.data.serialize;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.TargetApi;

import com.cs446.kluster.map.MapUtils;
import com.cs446.kluster.models.Photo;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class PhotoAdapter extends TypeAdapter<Photo> {
	
    public Photo read(JsonReader reader) throws IOException {
      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return null;
      }
      
      String photoId = null;
      String eventId = null;
      String userId = null;
      Double[] loc = null;
      List<String> tags = null;
      String[] urls = new String[4];
      Date createdAt = new Date();
      String[] rating = new String[2];

      reader.beginObject();
      while (reader.hasNext()) {
          String name = reader.nextName();
          if (name.equals("_id")) {
              photoId = reader.nextString();
          } else if (name.equals("_event")) {
              eventId = reader.nextString();
          } else if (name.equals("_contributor")) {
              userId = reader.nextString();
          } else if (name.equals("loc") && reader.peek() != JsonToken.NULL) {
              loc = readDoublesArray(reader).toArray(new Double[2]);
          } else if (name.equals("time")) {
          	try {
					createdAt = Photo.getDateFormat().parse(reader.nextString());
				} catch (ParseException e) {
					createdAt = new Date();
				}
          } else if (name.equals("url") && reader.peek() != JsonToken.NULL) {
          	reader.beginObject();
          	reader.nextName();
          	urls[1] = reader.nextString(); // small
          	reader.nextName();
          	urls[2] = reader.nextString(); // medium
          	reader.nextName();
          	urls[0] = reader.nextString(); // default
          	reader.nextName();
          	urls[3] = reader.nextString(); // thumb
          	reader.endObject();
          } else if (name.equals("tags") && reader.peek() != JsonToken.NULL) {
              tags = readStringArray(reader);
          } else if (name.equals("rating")) {
          	reader.beginObject();
          	reader.nextName();
          	rating[0] = Integer.toString(reader.nextInt());
          	reader.nextName();
          	rating[1] = Integer.toString(reader.nextInt());
          	reader.endObject();
          } else {
              reader.skipValue();
          }
      }
      reader.endObject();

      return new Photo(photoId, userId, eventId, new LatLng(loc[0], loc[1]), createdAt, urls, tags, rating);
    }
    
    public void write(JsonWriter writer, Photo value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }
      writer.value(value.getPhotoId());
      writer.value(value.getUserId());
      writer.value(value.getEventId());
      writer.value(MapUtils.latLngToString(value.getLocation()));
      writer.value(Photo.getDateFormat().format(value.getDate()));

    }
    
    @TargetApi(11)
    public static List<String> readStringArray(JsonReader reader) throws IOException {
        List<String> strings = new ArrayList<String>();

        reader.beginArray();
        while (reader.hasNext()) {
            strings.add(reader.nextString());
        }
        reader.endArray();
        return strings;
    }

    @TargetApi(11)
    public static List<Double> readDoublesArray(JsonReader reader) throws IOException {
        List<Double> doubles = new ArrayList<Double>();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }
  }