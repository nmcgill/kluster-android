package com.cs446.kluster;

import java.math.BigInteger;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cs446.kluster.fragments.PhotoMapFragment;
import com.cs446.kluster.photo.PhotoProvider;
import com.cs446.kluster.user.Users;

public class MainActivity extends Activity {    
	PhotoFactory mFactory;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
        //** TODO: Move user creation to main? */
        Users.CreateUser(this, new BigInteger("531238e5f330ede5deafbc4e", 16));
        
        /*
         * Create a content observer object.
         * Its code does not mutate the provider, so set
         * selfChange to "false"
         */
        //TableObserver observer = new TableObserver(AccountAdapter.getCurrentUser().getAccount(), this);
        /*
         * Register the observer for the data table. The table's path
         * and any of its subpaths trigger the observer.
         */
        //getContentResolver().registerContentObserver(PhotoProvider.CONTENT_URI, true, observer);        
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu_options, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
    	switch (item.getItemId()) {
    	case android.R.id.home:
    		onBackPressed();
    		return true;
    		
    	case R.id.action_camera:
			Intent intent = new Intent(getBaseContext(), PhotoFactory.class);
			startActivity(intent);
    		return true;
    	case R.id.action_mapview:
			PhotoMapFragment firstFragment = new PhotoMapFragment();
            // Add the fragment to the 'main_activity'
			getFragmentManager().beginTransaction().add(R.id.main_container, firstFragment).addToBackStack(firstFragment.toString()).commit();
    		return true;
    	case R.id.action_refresh: 
    	{
            ContentResolver.setSyncAutomatically(Users.getUser().getAccount(), PhotoProvider.PROVIDER_NAME, true);
            
            Bundle settingsBundle = new Bundle();
            settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);

            ContentResolver.requestSync(Users.getUser().getAccount(), PhotoProvider.PROVIDER_NAME, settingsBundle);  
            return true;
    	}
    	}
    	return false;
    }

    @Override
	public void onBackPressed() {
		FragmentManager fm = getFragmentManager();

		if(fm.getBackStackEntryCount() > 0) {
			fm.popBackStack();
		}
		
		if (fm.getBackStackEntryCount() == 1) {
			getActionBar().setDisplayHomeAsUpEnabled(false);
		}
	}
}
