package com.cs446.kluster;

import android.accounts.Account;


public class User {
	private int mUserID;
	private Account mAccount;
	/**TODO: Add as needed */
	
	public int getID() {
		return mUserID;
	}
	
	public Account getAccount() {
		return mAccount;
	}
	
	public User(int id, Account account) {
		mUserID = id;
		mAccount = account;
	}
}
