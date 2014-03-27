package com.cs446.kluster.views.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cs446.kluster.R;
import com.cs446.kluster.R.drawable;
import com.cs446.kluster.R.id;
import com.cs446.kluster.R.layout;
import com.cs446.kluster.R.menu;
import com.cs446.kluster.R.string;
import com.cs446.kluster.models.Users;
import com.cs446.kluster.tests.TestData;
import com.cs446.kluster.views.fragments.DiscoverFragment;
import com.cs446.kluster.views.fragments.EventMapFragment;

public class MainActivity extends Activity {    
	private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mMenuTitles;
    private String mTitle;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity); 
        
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        //TODO: move these in to array.xml
        mMenuTitles = new String[]{"Search", "Discover"};

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mMenuTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
        	

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle("Test Drawer Title");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
                
        //** TODO: Move user creation to test cases? */
        Users.CreateUser(this, "531238e5f330ede5deafbc4e");
        
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
    	
    	if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
    	
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
    	
    	return super.onOptionsItemSelected(item);
    }

    @Override
	public void onBackPressed() {
		/*FragmentManager fm = getFragmentManager();

		if(fm.getBackStackEntryCount() > 1) {
			fm.popBackStack();
			
			if (fm.getBackStackEntryCount() <= 2){
				getActionBar().setDisplayHomeAsUpEnabled(false);
			}
		}
		else {
			super.onBackPressed();
		}*/
	}
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        Fragment fragment;
        if (position == 0) {
	       	 setTitle("Search");
	       	 fragment = new EventMapFragment();	 
         }
         else {
         	setTitle("Discover");
         	fragment = new DiscoverFragment();
         }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.main_container, fragment)
                       .commit();

        // Highlight the seleted item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
    	mTitle = title.toString();
    	getActionBar().setTitle(title);
    }
}
