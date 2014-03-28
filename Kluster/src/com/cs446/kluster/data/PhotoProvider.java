package com.cs446.kluster.data;

import com.cs446.kluster.models.Photo;
import com.cs446.kluster.views.map.MapUtils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class PhotoProvider extends ContentProvider {
	public static final String PROVIDER_NAME = "com.cs446.kluster.Photos";
	public static final Uri CONTENT_URI = Uri.parse("content://"+PROVIDER_NAME+"/photoitems");
	
	static final int PHOTOITEMS = 1;
	static final int PHOTOITEM_ID = 2;
	
	private PhotoOpenHelper mOpenHelper;
	private SQLiteDatabase mDb;
	
	private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		mUriMatcher.addURI(PROVIDER_NAME, "photoitems", PHOTOITEMS);
		mUriMatcher.addURI(PROVIDER_NAME, "photoitems/#", PHOTOITEM_ID);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		
		switch (mUriMatcher.match(uri)) {
			case PHOTOITEMS:
				count = mDb.delete("photoitems", selection, selectionArgs);
				break;
			case PHOTOITEM_ID:
				String id = uri.getPathSegments().get(1);
				count = mDb.delete("photoitems", "_id = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (mUriMatcher.match(uri)) {
			case PHOTOITEMS:
				return "vnd.android.cursor.dir/vnd.kluster.Photos ";
			case PHOTOITEM_ID:
				return "vnd.android.cursor.item/vnd.kluster.Photos ";
			default:
				throw new IllegalArgumentException("Unsupported URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		/* Check to see if the unique GUID is already in table */
		Cursor c = mDb.rawQuery("SELECT * FROM photoitems WHERE photoid = '" + values.getAsString("photoid") + "'", null);
		try {
			if(c.moveToFirst()) {
				return uri;
			}
		}
		finally {
			c.close();
		}
		
		long rowID = mDb.insert("photoitems", "", values);

		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		}
		
		throw new SQLException("Failed to insert");
	}

	@Override
	public boolean onCreate() {
		mOpenHelper = new PhotoOpenHelper(getContext());
		mDb = mOpenHelper.getWritableDatabase();
		
		return ((mDb == null) ? false : true);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();

        qBuilder.setTables("photoitems");

        if((mUriMatcher.match(uri)) == PHOTOITEM_ID) {
            qBuilder.appendWhere("_id=" + uri.getPathSegments().get(1));
        }

        Cursor c = qBuilder.query(mDb, projection, selection, selectionArgs, null, null, sortOrder);
        
        c.setNotificationUri(getContext().getContentResolver(), uri);
        
        return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count = 0;
		
		switch (mUriMatcher.match(uri)) {
			case PHOTOITEMS:
				count = mDb.update("photoitems", values, selection, selectionArgs);
				break;
			case PHOTOITEM_ID:
				count = mDb.update("photoitems", values, selection, selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return count;
	}
	
    public static ContentValues getContentValues(Photo item) {
        ContentValues values = new ContentValues();

		values.put("photoid", item.getPhotoId());
		values.put("userid", item.getUserId());
		values.put("eventid", item.getEventId());
		values.put("location", MapUtils.latLngToString(item.getLocation()));
		values.put("date", Photo.getDateFormat().format(item.getDate()));
		values.put("url", item.getUrl());
		values.put("mediumurl", item.getMediumUrl());
		values.put("thumburl", item.getThumbUrl());
		values.put("tags", TextUtils.join(",", item.getTags()));
		values.put("rating", TextUtils.join(",", item.getRatings()));

        return values;
    }

	protected static final class PhotoOpenHelper extends SQLiteOpenHelper {
		private static final int DATABASE_VERSION = 1;
		private static final String DATABASE_TABLE_NAME = "photoitems";
		private static final String DATABASE_TABLE_CREATE =
				"CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
						"_id integer primary key autoincrement, " +
						"photoid text not null, " +
						"userid text not null, " +
						"eventid text not null, " +
						"location text not null, " +
						"date text not null, " + 
						"url text not null, " +
						"mediumurl text not null, " +
						"thumburl text not null, " +
						"tags text not null, " +
						"rating text not null);";
		
		public PhotoOpenHelper(Context context) {
			super(context, DATABASE_TABLE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_TABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
			onCreate(db);
		}
	}
}