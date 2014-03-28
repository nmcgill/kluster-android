package com.cs446.kluster.data;

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

import com.cs446.kluster.models.Event;
import com.cs446.kluster.views.map.MapUtils;

public class EventProvider extends ContentProvider {
	public static final String PROVIDER_NAME = "com.cs446.kluster.Events";
	public static final Uri CONTENT_URI = Uri.parse("content://"+PROVIDER_NAME+"/eventitems");
	
	static final int EVENTITEMS = 1;
	static final int EVENTITEM_ID = 2;
	
	private EventOpenHelper mOpenHelper;
	private SQLiteDatabase mDb;
	
	private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		mUriMatcher.addURI(PROVIDER_NAME, "eventitems", EVENTITEMS);
		mUriMatcher.addURI(PROVIDER_NAME, "eventitems/#", EVENTITEM_ID);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		
		switch (mUriMatcher.match(uri)) {
			case EVENTITEMS:
				count = mDb.delete("eventitems", selection, selectionArgs);
				break;
			case EVENTITEM_ID:
				String id = uri.getPathSegments().get(1);
				count = mDb.delete("eventitems", "_id = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
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
			case EVENTITEMS:
				return "vnd.android.cursor.dir/vnd.kluster.Events ";
			case EVENTITEM_ID:
				return "vnd.android.cursor.item/vnd.kluster.Events ";
			default:
				throw new IllegalArgumentException("Unsupported URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		/* Check to see if the unique GUID is already in table */
		Cursor c = mDb.rawQuery("SELECT * FROM eventitems WHERE eventid = '" + values.getAsString("eventid") + "'", null);
		try {
			if(c.moveToFirst()) {
				return uri;
			}
		}
		finally {
			c.close();
		}
		
		long rowID = mDb.insert("eventitems", "", values);

		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		}
		
		throw new SQLException("Failed to insert");
	}

	@Override
	public boolean onCreate() {
		mOpenHelper = new EventOpenHelper(getContext());
		mDb = mOpenHelper.getWritableDatabase();
		
		return ((mDb == null) ? false : true);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();

        qBuilder.setTables("eventitems");

        if((mUriMatcher.match(uri)) == EVENTITEM_ID) {
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
			case EVENTITEMS:
				count = mDb.update("eventitems", values, selection, selectionArgs);
				break;
			case EVENTITEM_ID:
				count = mDb.update("eventitems", values, selection, selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return count;
	}
	
    public static ContentValues getContentValues(Event item) {
        ContentValues values = new ContentValues();

		values.put("eventid", item.getEventId());
		values.put("name", item.getName());
		values.put("location", MapUtils.latLngToString(item.getLocation()));
		values.put("startdate", Event.getDateFormat().format(item.getStartDate()));
		values.put("enddate", Event.getDateFormat().format(item.getEndDate()));
		values.put("tags", TextUtils.join("", item.getTags()));
		values.put("photos", TextUtils.join(",", item.getPhotos()));

        return values;
    }
	
	protected static final class EventOpenHelper extends SQLiteOpenHelper {
		private static final int DATABASE_VERSION = 1;
		private static final String DATABASE_TABLE_NAME = "eventitems";
		private static final String DATABASE_TABLE_CREATE =
				"CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
						"_id integer primary key autoincrement, " +
						"eventid text not null, " +
						"name text not null, " +
						"location text not null, " +
						"startdate text not null, " + 
						"enddate text not null, " +
						"tags text not null, " +
						"photos text not null);";
		
		public EventOpenHelper(Context context) {
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