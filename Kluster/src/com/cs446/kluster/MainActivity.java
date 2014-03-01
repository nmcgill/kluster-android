package com.cs446.kluster;

import java.util.ArrayList;
import java.util.Arrays;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.cs446.kluster.Photos";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "kluster.com";
    // The account name
    public static final String ACCOUNT = "dummyaccount";
    // Instance fields
    Account mAccount;
    
	PhotoFactory mFactory = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
        // Create account for this device
        mAccount = CreateSyncAccount(this);
        
        /*
         * Create a content observer object.
         * Its code does not mutate the provider, so set
         * selfChange to "false"
         */
        TableObserver observer = new TableObserver(mAccount, this);
        /*
         * Register the observer for the data table. The table's path
         * and any of its subpaths trigger the observer.
         */
        getContentResolver().registerContentObserver(PhotoProvider.CONTENT_URI, true, observer);

        Button mTakePictureButton = (Button)findViewById(R.id.takePicture);
        mTakePictureButton.setOnClickListener( new OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), PhotoFactory.class);
				startActivity(intent);
		    }
		});
        
        Button mViewMapButton = (Button)findViewById(R.id.viewPictures);
        mViewMapButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				PhotoMapFragment firstFragment = new PhotoMapFragment();
	            
	            // Add the fragment to the 'main_activity'
				getFragmentManager().beginTransaction().add(R.id.main_container, firstFragment).commit();
			}
		});
    }
    
    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
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
        	return newAccount;
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        	ArrayList<Account> accounts = new ArrayList<Account>(Arrays.asList(accountManager.getAccounts()));
        	
        	return accounts.get(accounts.indexOf(newAccount));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	@Override
	public void onBackPressed() {
		FragmentManager fm = getFragmentManager();

		if(fm.getBackStackEntryCount() > 0) {
			fm.popBackStack();
		}
		else {
			super.onBackPressed();
		}
	}
	
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
    	switch (item.getItemId()) {
    	case android.R.id.home:
    		onBackPressed();
    		return true;
    	}
    	
    	return false;
    }
}
