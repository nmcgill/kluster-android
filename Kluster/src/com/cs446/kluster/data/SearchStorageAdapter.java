package com.cs446.kluster.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.cs446.kluster.models.Event;

public class SearchStorageAdapter extends AbstractContentStorageAdapter<Event> {

    public SearchStorageAdapter(ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    public Uri getContentUri() {
        return SearchProvider.CONTENT_URI;
    }

    @Override
    public ContentValues getContentValues(Event event) {
        return SearchProvider.getContentValues(event);
    }
}