package com.cs446.kluster.user;

import java.math.BigInteger;

import android.accounts.Account;
import android.content.Context;

public class Users {
	public static User mCurrentUser;

	public static User getUser() {
		return mCurrentUser;
	}
	
	public static void CreateUser(Context c, BigInteger userid) { 
		mCurrentUser = new User(c, userid);
	}
}
