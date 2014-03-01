package com.cs446.kluster.accountadapter;

import android.accounts.Account;
import android.content.Context;

import com.cs446.kluster.User;

public class AccountAdapter {
	public static User mCurrentUser;

	public static User getCurrentUser() {
		return mCurrentUser;
	}
	
	public static void setCurrentUser(User user) {
		mCurrentUser = user;
	}
	
	public static Account CreateAccount(Context c) { 
		/**TODO: Add google account? Twitter? FB? */
		return KlusterAccount.CreateSyncAccount(c);
	}
}
