package com.cs446.kluster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cs446.kluster.accountadapter.AccountAdapter;

public class MainActivity extends Activity implements PhotoTilesFragment.ThumbnailClickListener {    
	PhotoFactory mFactory;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
        //** TODO: Move user creation to main? */
        AccountAdapter.setCurrentUser(new User(0, AccountAdapter.CreateAccount(this)));
        
        /*
         * Create a content observer object.
         * Its code does not mutate the provider, so set
         * selfChange to "false"
         */
        TableObserver observer = new TableObserver(AccountAdapter.getCurrentUser().getAccount(), this);
        /*
         * Register the observer for the data table. The table's path
         * and any of its subpaths trigger the observer.
         */
        getContentResolver().registerContentObserver(PhotoProvider.CONTENT_URI, true, observer);

        PhotoTilesFragment photoTilesFragmentOrganize=
        		(PhotoTilesFragment)
        		getFragmentManager().findFragmentById(R.id.pictureTilesFragmentOrganize);
        photoTilesFragmentOrganize.setClickableThumbnailText("Yayy");
        
        
        Button mTakePictureButton = (Button)findViewById(R.id.CameraButton);
        mTakePictureButton.setOnClickListener( new OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), PhotoFactory.class);
				startActivity(intent);
		    }
		});
        
        Button mViewMapButton = (Button)findViewById(R.id.MapViewButton);
        mViewMapButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				PhotoMapFragment firstFragment = new PhotoMapFragment();
	            
	            // Add the fragment to the 'main_activity'
				getFragmentManager().beginTransaction().add(R.id.main_container, firstFragment).commit();
			}
		});
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
//	@Override
//	public void onBackPressed() {
//		FragmentManager fm = getFragmentManager();
//
//		if(fm.getBackStackEntryCount() > 0) {
//			fm.popBackStack();
//		}
//		else {
//			super.onBackPressed();
//		}
//	}
	
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
    	switch (item.getItemId()) {
    	case android.R.id.home:
    		onBackPressed();
    		return true;
    	}
    	
    	return false;
    }


	@Override
	public void onThumbnailClick() {
		// TODO Auto-generated method stub
		
	}
}
