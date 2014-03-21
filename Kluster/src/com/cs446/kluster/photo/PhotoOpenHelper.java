package com.cs446.kluster.photo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*	private BigInteger mPhotoId;
	private BigInteger mUserId;
	private BigInteger mEventId;
	private LatLng mLocation;
	private Date mDate;
	private String mUrl;
	private ArrayList<String> mTags;
	private Boolean mUploaded;
	private Uri mLocalUrl;
	private String mThumbnailUrl;*/

public class PhotoOpenHelper extends SQLiteOpenHelper {
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
					"remoteurl text, " +
					"tags text, " +
					"uploaded integer not null," +
					"localurl text, " +
					"thumburl text);";
	
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