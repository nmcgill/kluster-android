package com.cs446.kluster.models;

import android.content.Context;


/**TODO: Replace this */

public class Users {
	public static User mCurrentUser;

	public static User getUser() {
		return mCurrentUser;
	}
	
	public static void CreateUser(Context c, String userid) { 
		mCurrentUser = new User(c, userid);
	}
}
