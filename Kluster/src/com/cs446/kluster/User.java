package com.cs446.kluster;

import java.math.BigInteger;

import android.accounts.Account;


public class User {
	private BigInteger mUserID;
	private Account mAccount;
	/**TODO: Add as needed */
	
	public BigInteger getID() {
		return mUserID;
	}
	
	public Account getAccount() {
		return mAccount;
	}
	
	public User(BigInteger id, Account account) {
		mUserID = id;
		mAccount = account;
	}
}
