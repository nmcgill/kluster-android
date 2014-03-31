package com.cs446.kluster.data.serialize;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.annotation.TargetApi;
import android.text.TextUtils;
import android.util.Log;

import com.cs446.kluster.map.MapUtils;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.models.Photo;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class EventAdapter extends TypeAdapter<Event> {
	
    public Event read(JsonReader reader) throws IOException {
      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return null;
      }
      
      String eventId = null;
      String eventName = "EventName";
      Double[] loc = null;
      List<String> photos = new ArrayList<String>();
      List<String> tags = null;
      Date startdate = null;
      Date enddate = null;

      reader.beginObject();
      while (reader.hasNext()) {
          String name = reader.nextName();
          if (name.equals("_id")) {
              eventId = reader.nextString();
          } else if (name.equals("loc") && reader.peek() != JsonToken.NULL) {
        	  List<Double> list = readDoublesArray(reader);
              Collections.reverse(list);
              loc = list.toArray(new Double[2]);
          }
          else if (name.equals("name")) {
              eventName = reader.nextString();
          }
          else if (name.equals("start_time")) {
          	try {
					startdate = Event.getDateFormat().parse(reader.nextString());
				} catch (ParseException e) {
					Log.e("readEvent", "Could not parse start time:" + e);
					startdate = new Date();
				}
          }
          else if (name.equals("end_time")) {
          	try {
					enddate = Event.getDateFormat().parse(reader.nextString());
				} catch (ParseException e) {
					Log.e("readEvent", "Could not end time" + e);
					enddate = new Date();
				}
          }
          else if (name.equals("tags") && reader.peek() != JsonToken.NULL) {
              tags = readStringArray(reader); 
          }
          else if (name.equals("photos") && reader.peek() != JsonToken.NULL) {
          	reader.beginArray();
          	while (reader.peek() != JsonToken.END_ARRAY) {
          		PhotoAdapter adapter = new PhotoAdapter();
          		Photo photo = adapter.read(reader);
          		photos.add(photo.getSmallUrl());
          	}
          	reader.endArray();
          }
          else {
              reader.skipValue();
          }
      }
      reader.endObject();

      return new Event(eventId, eventName, new LatLng(loc[0], loc[1]), startdate, enddate, tags, photos);
    }
    
    public void write(JsonWriter writer, Event value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }
      
      writer.value(value.getEventId());
      writer.value(value.getName());
      writer.value(MapUtils.latLngToString(value.getLocation()));
      writer.value(Event.getDateFormat().format(value.getStartDate()));
      writer.value(Event.getDateFormat().format(value.getEndDate()));
      writer.value(TextUtils.join(",", value.getTags()));
      writer.value(TextUtils.join(",", value.getPhotos()));
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