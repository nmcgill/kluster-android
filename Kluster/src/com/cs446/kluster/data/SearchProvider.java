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
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.cs446.kluster.map.MapUtils;
import com.cs446.kluster.models.Event;

public class SearchProvider extends ContentProvider {
	
	public static final String PROVIDER_NAME = "com.cs446.kluster.Search";
	public static final Uri CONTENT_URI = Uri.parse("content://"+PROVIDER_NAME+"/"+SearchOpenHelper.DATABASE_TABLE_NAME);
	
	static final int EVENTITEMS = 1;
	static final int EVENTITEM_ID = 2;
	
	private SearchOpenHelper mOpenHelper;
	private SQLiteDatabase mDb;
	
	private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		mUriMatcher.addURI(PROVIDER_NAME, SearchOpenHelper.DATABASE_TABLE_NAME, EVENTITEMS);
		mUriMatcher.addURI(PROVIDER_NAME, SearchOpenHelper.DATABASE_TABLE_NAME+"/#", EVENTITEM_ID);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		
		switch (mUriMatcher.match(uri)) {
			case EVENTITEMS:
				count = mDb.delete(SearchOpenHelper.DATABASE_TABLE_NAME, selection, selectionArgs);
				break;
			case EVENTITEM_ID:
				String id = uri.getPathSegments().get(1);
				count = mDb.delete(SearchOpenHelper.DATABASE_TABLE_NAME, "_id = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
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
				return "vnd.android.cursor.dir/vnd.kluster.Search ";
			case EVENTITEM_ID:
				return "vnd.android.cursor.item/vnd.kluster.Search ";
			default:
				throw new IllegalArgumentException("Unsupported URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		/* Check to see if the unique GUID is already in table */
		Cursor c = mDb.rawQuery("SELECT * FROM " + SearchOpenHelper.DATABASE_TABLE_NAME + " WHERE eventid = '" + values.getAsString("eventid") + "'", null);
		try {
			if(c.moveToFirst()) {
				long rowID = mDb.update(SearchOpenHelper.DATABASE_TABLE_NAME, values, "eventid = ?", new String[] { values.getAsString("eventid") });
				if (rowID > 0) {
					Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
					getContext().getContentResolver().notifyChange(_uri, null);
					return _uri;
				}
				return uri;
			}
		}
		finally {
			c.close();
		}
		
		long rowID = mDb.insert(SearchOpenHelper.DATABASE_TABLE_NAME, "", values);

		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		}
		
		throw new SQLException("Failed to insert");
	}

	@Override
	public boolean onCreate() {
		mOpenHelper = new SearchOpenHelper(getContext());
		mDb = mOpenHelper.getWritableDatabase();
		
		return ((mDb == null) ? false : true);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();

        qBuilder.setTables(SearchOpenHelper.DATABASE_TABLE_NAME);

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
				count = mDb.update(SearchOpenHelper.DATABASE_TABLE_NAME, values, selection, selectionArgs);
				break;
			case EVENTITEM_ID:
				count = mDb.update(SearchOpenHelper.DATABASE_TABLE_NAME, values, selection, selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return count;
	}
	
    public static ContentValues getContentValues(Event item) {
        ContentValues values = new ContentValues();

		values.put(SearchOpenHelper.COLUMN_EVENT_ID, item.getEventId());
		values.put(SearchOpenHelper.COLUMN_EVENT_NAME, item.getName());
		values.put(SearchOpenHelper.COLUMN_LOCATION, MapUtils.latLngToString(item.getLocation()));
		values.put(SearchOpenHelper.COLUMN_STARTTIME, Event.getDateFormat().format(item.getStartDate()));
		values.put(SearchOpenHelper.COLUMN_ENDTIME, Event.getDateFormat().format(item.getEndDate()));
		values.put(SearchOpenHelper.COLUMN_TAGS, TextUtils.join("", item.getTags()));
		values.put(SearchOpenHelper.COLUMN_PHOTOS, TextUtils.join(",", item.getPhotos()));
		
        return values;
    }
	
	protected static final class SearchOpenHelper extends SQLiteOpenHelper {
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_EVENT_ID = "eventid";
        public static final String COLUMN_EVENT_NAME = "eventname";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_STARTTIME = "startdate";
        public static final String COLUMN_ENDTIME = "enddate";
        public static final String COLUMN_TAGS = "tags";
        public static final String COLUMN_PHOTOS = "photos";
        
		private static final int DATABASE_VERSION = 1;
		private static final String DATABASE_TABLE_NAME = "searchitems";
		private static final String DATABASE_TABLE_CREATE =
				"CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
						COLUMN_ID + " integer primary key autoincrement, " +
						COLUMN_EVENT_ID + " text not null, " +
						COLUMN_EVENT_NAME + " text not null, " +
						COLUMN_LOCATION + " text not null, " +
						COLUMN_STARTTIME + " text not null, " + 
						COLUMN_ENDTIME + " text not null, " +
						COLUMN_TAGS + " text not null, " +
						COLUMN_PHOTOS + " text not null);";
		
		public SearchOpenHelper(Context context) {
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