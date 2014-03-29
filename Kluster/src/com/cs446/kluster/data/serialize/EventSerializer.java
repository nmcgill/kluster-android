package com.cs446.kluster.data.serialize;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.cs446.kluster.models.Event;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Marlin Gingerich on 2014-03-09.
 */
public class EventSerializer implements Serializer<Event> {

    @TargetApi(11)
    public Event read(Reader reader) throws IOException {
        JsonReader jr = new JsonReader(reader);
        try {
            return EventSerializer.readEvent(jr);
        } catch (IOException e) {
            return null;
        } finally {
            jr.close();
        }
    }
    
    @TargetApi(11)
    public Event read(JsonReader jr) throws IOException {
    	return EventSerializer.readEvent(jr);
    }

    public void write(Writer writer, Event event) {

    }

    public static Event readEvent(JsonReader reader) throws IOException {
        String eventId = null;
        String eventName = "EventName";
        Double[] loc = null;
        List<String> photos = null;
        List<String> tags = null;
        Date startdate = null;
        Date enddate = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("_id")) {
                eventId = reader.nextString();
            } else if (name.equals("loc") && reader.peek() != JsonToken.NULL) {
                loc = SerializerUtils.readDoublesArray(reader).toArray(new Double[2]);
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
                tags = SerializerUtils.readStringArray(reader); 
            }
            else if (name.equals("photos") && reader.peek() != JsonToken.NULL) {
                photos = SerializerUtils.readStringArray(reader);
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new Event(eventId, eventName, new LatLng(loc[0], loc[1]), startdate, enddate, tags, photos);
        
    }
}