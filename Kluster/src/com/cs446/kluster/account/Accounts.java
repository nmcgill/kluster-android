package com.cs446.kluster.account;

import java.math.BigInteger;

import android.accounts.Account;
import android.content.Context;

public class Accounts {
	public static KlusterAccount mCurrentAccount;

	public static KlusterAccount getAccount() {
		return mCurrentAccount;
	}
	
	public static void CreateAccount(Context c, BigInteger userid) { 
		mCurrentAccount = KlusterAccount.CreateSyncAccount(c, userid);
	}
}
