package com.cs446.kluster;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	PhotoFactory mFactory = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   

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
