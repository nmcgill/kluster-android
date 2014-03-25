package com.cs446.kluster;

import java.math.BigInteger;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.cs446.kluster.fragments.DiscoverFragment;
import com.cs446.kluster.fragments.EventGridFragment;
import com.cs446.kluster.fragments.EventMapFragment;
import com.cs446.kluster.tests.TestData;
import com.cs446.kluster.user.Users;

public class MainActivity extends Activity {    
	private PhotoFactory mFactory;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity); 
                
        //** TODO: Move user creation to test cases? */
        Users.CreateUser(this, new BigInteger("531238e5f330ede5deafbc4e", 16));
        
        //Add Testing Data
        TestData.CreateTestData(getContentResolver());
        	        
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
        
        Fragment firstFragment = new DiscoverFragment();
        getFragmentManager().beginTransaction().add(R.id.main_container, firstFragment).commit();
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    	case R.id.action_search:
			return true;
    	/*case R.id.action_refresh: 
    	{
            ContentResolver.setSyncAutomatically(Users.getUser().getAccount(), PhotoProvider.PROVIDER_NAME, true);
            
            Bundle settingsBundle = new Bundle();
            settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);

            ContentResolver.requestSync(Users.getUser().getAccount(), PhotoProvider.PROVIDER_NAME, settingsBundle);  
            return true;
    	}*/
    	}
    	return false;
    }

    @Override
	public void onBackPressed() {
		FragmentManager fm = getFragmentManager();

		if(fm.getBackStackEntryCount() > 0) {
			fm.popBackStack();
			
			if (fm.getBackStackEntryCount() <= 1){
				getActionBar().setDisplayHomeAsUpEnabled(false);
			}
		}
		else {
			super.onBackPressed();
		}
	}
}
