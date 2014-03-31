package com.cs446.kluster.views.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.cs446.kluster.R;
import com.cs446.kluster.data.SearchProvider;
import com.cs446.kluster.tests.TestData;
import com.cs446.kluster.views.fragments.DiscoverFragment;
import com.cs446.kluster.views.fragments.EventDialogFragment;
import com.cs446.kluster.views.fragments.SearchFragment;

public class MainActivity extends Activity {    
	private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mMenuTitles;
    private CharSequence mTitle;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity); 
        
        SharedPreferences pref = getSharedPreferences("User", Context.MODE_PRIVATE);
        
        String name = pref.getString("name", "No name");
        
        View header = (View)getLayoutInflater().inflate(R.layout.drawerheader_layout, null);
        TextView txtUserName = (TextView) header.findViewById(R.id.drawerheader_txtName);
        txtUserName.setText(name);
        
        mTitle = "Discover";
        
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.addHeaderView(header);
        
        mMenuTitles = getResources().getStringArray(R.array.menu_list);

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
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //Add Testing Data
        TestData.CreateTestData(this);
        
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
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Get the SearchView and set the searchable configuration
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
            	
    			Bundle bundle = new Bundle();
    			bundle.putString("query", query);

    			Fragment fragment = new SearchFragment();
    			fragment.setArguments(bundle);
    			FragmentManager fragmentManager = getFragmentManager();
    			fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();
    			setTitle("Search");
    			invalidateOptionsMenu();
    			return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        
        // Assumes current activity is the searchable activity
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
                
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
    	
        	 ContentResolver resolver = getContentResolver();
        	 ContentProviderClient client = resolver.acquireContentProviderClient("com.cs446.kluster.Search");
        	 SearchProvider provider = (SearchProvider) client.getLocalContentProvider();
        	 provider.resetDatabase();
        	 client.release();
        	
    		 mTitle = "Search";
    		 invalidateOptionsMenu();
	       	 Fragment fragment = new SearchFragment();
	         FragmentManager fragmentManager = getFragmentManager();
	         fragmentManager.beginTransaction()
	                        .replace(R.id.main_container, fragment)
	                        .commit();
			return true;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        Fragment fragment = null;
        
        switch (position) {
        case 1:
        {
        	new EventDialogFragment().show(getFragmentManager(), "eventDialog");
        	return;
        }
        case 2:
        	fragment = new SearchFragment(); break;
        case 3:
        	fragment = new DiscoverFragment(); break;
        default:
        	return;
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.main_container, fragment)
                       .commit();

        // Highlight the seleted item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuTitles[position-1]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
    	mTitle = title;
    	super.setTitle(title);
    }
}
