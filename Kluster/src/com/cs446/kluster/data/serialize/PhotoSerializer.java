package com.cs446.kluster.data.serialize;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.List;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;

import com.cs446.kluster.models.Photo;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Marlin Gingerich on 2014-03-09.
 */
public class PhotoSerializer implements Serializer<Photo> {

    @TargetApi(11)
    public Photo read(Reader reader) throws IOException {
        JsonReader jr = new JsonReader(reader);
        try {
            return PhotoSerializer.readPhoto(jr);
        } catch (IOException e) {
            return null;
        } finally {
            jr.close();
        }
    }

    public void write(Writer writer, Photo photo) {

    }

    public static Photo readPhoto(JsonReader reader) throws IOException {
        String photoId = null;
        String eventId = null;
        String userId = null;
        Double[] loc = null;
        List<String> tags = null;
        String url = null;
        Date createdAt = new Date();
        Boolean uploaded = false;
        String thumburl = null;
        long lastUpdatedAt = -1;
        
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
                loc = SerializerUtils.readDoublesArray(reader).toArray(new Double[2]);
            } else if (name.equals("url")) {
                url = reader.nextString();
            } else if (name.equals("tags")) {
                tags = SerializerUtils.readStringArray(reader);
            } else if (name.equals("created")) {
                //createdAt = reader.nextLong();
                reader.skipValue();
            } else if (name.equals("lastUpdated")) {
                //lastUpdatedAt = reader.nextLong();
                reader.skipValue();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new Photo(photoId, userId, eventId, new LatLng(loc[0], loc[1]), createdAt, url, tags, uploaded, "", thumburl);
        
    }
}