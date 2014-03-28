package com.cs446.kluster.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Marlin Gingerich on 2014-03-10.
 */
abstract class AbstractContentStorageAdapter<T> implements StorageAdapter<T> {

    ContentResolver mContentResolver;

    AbstractContentStorageAdapter(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }

    @Override
    public Uri insert(T item) {
        ContentValues values = this.getContentValues(item);
        return this.mContentResolver.insert(this.getContentUri(), values);
    }

    @Override
    public Uri upsert(T item) {
        ContentValues values = this.getContentValues(item);
        values.put(StorageConstants.INSERT_OR_UPDATE, true);
        return this.mContentResolver.insert(this.getContentUri(), values);
    }

    @Override
    public int update(T item, String where, String[] selectionArgs) {
        ContentValues values = this.getContentValues(item);
        return this.mContentResolver.update(this.getContentUri(), values, where, selectionArgs);
    }

    @Override
    public int delete(String where, String[] selectionArgs) {
        return this.mContentResolver.delete(this.getContentUri(), where, selectionArgs);
    }

    @Override
    public Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return this.mContentResolver.query(this.getContentUri(), projection, selection, selectionArgs, sortOrder);
    }

    public abstract Uri getContentUri();

    public abstract ContentValues getContentValues(T item);

}