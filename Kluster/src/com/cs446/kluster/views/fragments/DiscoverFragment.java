package com.cs446.kluster.views.fragments;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs446.kluster.R;
import com.cs446.kluster.data.EventStorageAdapter;
import com.cs446.kluster.data.serialize.EventSerializer;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.net.EventRequest;
import com.cs446.kluster.net.http.HttpRequestListener;
import com.cs446.kluster.net.http.task.HttpContentRequestTask;
import com.google.android.gms.maps.model.LatLng;

public class DiscoverFragment extends Fragment implements ActionBar.TabListener, HttpRequestListener<Event> {
    private ViewPager mPager;	
   
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.discover_layout, container, false);
		
        mPager = (ViewPager) view.findViewById(R.id.viewpager_noswipe);
        mPager.setAdapter(new DemoCollectionPagerAdapter(getChildFragmentManager()));  
        
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

		String locationProvider = LocationManager.GPS_PROVIDER;

		Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
		
		EventRequest request = EventRequest.create(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 30000);
		HttpContentRequestTask<Event> task = new HttpContentRequestTask<Event>(new EventSerializer(), new EventStorageAdapter(getActivity().getContentResolver()));
	
		task.executeAsync(request);
	}

	 public class DemoCollectionPagerAdapter extends FragmentPagerAdapter {
	     public DemoCollectionPagerAdapter(FragmentManager fm) {
	         super(fm);
	     }
	
	     @Override
	     public Fragment getItem(int position) {

            if (position == 0) {
	         	return new EventGridFragment();
	         }
	         else {
	         	return new EventMapFragment();	     		
	         }
	     }
	
	     @Override
	     public int getCount() {
	         return 2;
	     }
	     
	     @Override
	    public CharSequence getPageTitle(int position) {
	            if (position == 0) {
		         	return "PHOTO";
		         }
		         else {
		         	return "MAP";	     		
		         }
	    }
	 }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
    	mPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Exception e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(Event result) {
		// TODO Auto-generated method stub
		
	}
}
