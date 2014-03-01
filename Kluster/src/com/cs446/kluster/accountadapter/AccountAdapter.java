package com.cs446.kluster.accountadapter;

import android.content.Context;

public class AccountAdapter {

	public AccountAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public static void CreateAccount(Context c) { 
		/**TODO: Add google account? Twitter? FB? */
		KlusterAccount.CreateSyncAccount(c);
	}
}
