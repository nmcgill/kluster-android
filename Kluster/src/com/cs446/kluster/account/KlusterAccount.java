package com.cs446.kluster.account;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;

import com.cs446.kluster.photo.PhotoProvider;

public class KlusterAccount extends Account {
	// An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "kluster.com";
    // The account name
    public static final String ACCOUNT = "dummyaccount";
    
    private BigInteger mUserId;
    
    public KlusterAccount(String name, String type, BigInteger userid) {
		super(name, type);
		
		mUserId = userid;
	}
    
    public BigInteger getUserId() {
    	return mUserId;
    }
	
	/**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static KlusterAccount CreateSyncAccount(Context context, BigInteger userid) {
        // Create the account type and default account
    	KlusterAccount newAccount = new KlusterAccount(
                ACCOUNT, ACCOUNT_TYPE, userid);
        // Get an instance of the Android account manager
        
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                		Activity.ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        	ContentResolver.setIsSyncable(newAccount, PhotoProvider.PROVIDER_NAME, 1);

        	return newAccount;
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        	ArrayList<Account> accounts = new ArrayList<Account>(Arrays.asList(accountManager.getAccountsByType(ACCOUNT_TYPE)));
        	
        	return (KlusterAccount)accounts.get(accounts.indexOf(newAccount));
        }
    }
}
