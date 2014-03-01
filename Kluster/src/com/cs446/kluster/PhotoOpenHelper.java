package com.cs446.kluster;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Sample JSON from request to Kluster API
//{
// "photo": {
//     "photoId": 13249871032,
//     "location": {
//         "lat": 32.423,
//         "long": -127.234
//     },
//     "time": 204239108478,
//     "userId": 2,
//     "url": "http: //photos.kluster.com?id=13249871032",
//     “tags”: “foo,bar”
// }
//}


public class PhotoOpenHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TABLE_NAME = "photoitems";
	private static final String DATABASE_TABLE_CREATE =
			"CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
					"_id integer primary key autoincrement, " +
					"photoid integer not null, " +
					"location text not null, " +
					"date text not null, " + 
					"userid integer not null, " +
					"url text, " +
					"tags text, " +
					"localurl text, " +
					"thumburl text, " +
					"uploaded integer not null);";
	
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