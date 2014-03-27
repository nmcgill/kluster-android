package com.cs446.kluster.model;

import java.math.BigInteger;

import android.accounts.Account;
import android.content.Context;


/**TODO: Replace this */

public class Users {
	public static User mCurrentUser;

	public static User getUser() {
		return mCurrentUser;
	}
	
	public static void CreateUser(Context c, BigInteger userid) { 
		mCurrentUser = new User(c, userid);
	}
}
