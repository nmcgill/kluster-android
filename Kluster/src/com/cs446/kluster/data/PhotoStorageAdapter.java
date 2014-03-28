package com.cs446.kluster.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.cs446.kluster.models.Photo;

/**
 * Created by Marlin Gingerich on 2014-03-10.
 */
public class PhotoStorageAdapter extends AbstractContentStorageAdapter<Photo> {

    public PhotoStorageAdapter(ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    public Uri getContentUri() {
        return PhotoProvider.CONTENT_URI;
    }

    @Override
    public ContentValues getContentValues(Photo photo) {
        return PhotoProvider.getContentValues(photo);
    }
}