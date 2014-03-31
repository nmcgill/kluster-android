package com.cs446.kluster.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.cs446.kluster.models.Event;

public class EventStorageAdapter extends AbstractContentStorageAdapter<Event> {

    public EventStorageAdapter(ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    public Uri getContentUri() {
        return EventProvider.CONTENT_URI;
    }
    
    public ContentValues getContentValues(Event item) {
        return EventProvider.getContentValues(item);
    }
}