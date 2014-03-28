package com.cs446.kluster.data;

import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Marlin Gingerich on 2014-03-10.
 */
public interface StorageAdapter<T> {
    public Uri insert(T item);
    public Uri upsert(T item);
    public int update(T item, String where, String[] selectionArgs);
    public int delete(String where, String[] selectionArgs);
    public Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder);
}