package com.cs446.kluster;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.MapFragment;


public class PhotoMapFragment extends MapFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}
	
	public void onBackPressed() {
		FragmentManager fm = getFragmentManager();

		if(fm.getBackStackEntryCount() > 0) {
			fm.popBackStack();
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
