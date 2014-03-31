package com.cs446.kluster.views.fragments;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.cs446.kluster.R;
import com.cs446.kluster.views.fragments.FilterDialogFragment.FilterListener;

public class SearchFragment extends Fragment implements ActionBar.TabListener {
    private ViewPager mPager;	
    private Bundle mFilters;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		mFilters = new Bundle();
		
		if (getArguments() != null) {
			mFilters.putString("location", getLocationString(getArguments().getString("query")));
		}
		else {
			mFilters.putString("location", "0,0");
		}
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_layout, container, false);

        mPager = (ViewPager) view.findViewById(R.id.viewpager_noswipe);
        mPager.setAdapter(new DemoCollectionPagerAdapter(getChildFragmentManager()));  
        
        Button btnFilter = (Button) view.findViewById(R.id.search_btnfilter);
        
        btnFilter.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				FilterDialogFragment fragment = new FilterDialogFragment();

				fragment.setFilterListener(new FilterListener() {
					@Override
					public void userSetFilter(String filter, String value) {
						mFilters.putString(filter, value);
					}

					@Override
					public void userReturned() {
					}
				});
				
				fragment.show(getFragmentManager(), "filterDialog");	
			}
		});
        
		return view;
	}
		
	public String getLocationString(String name) {

		Geocoder geocoder = new Geocoder(getActivity());
		List<Address> addr = null;
		try {
			addr = geocoder.getFromLocationName(name, 1);
		}
		catch (IOException e) {
		}
		
		if (addr != null) {
			return String.format(Locale.US, "%f,%f", addr.get(0).getLatitude(), addr.get(0).getLongitude());
		}
		else {
			return "";
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
	
	 public class DemoCollectionPagerAdapter extends FragmentPagerAdapter {
	     public DemoCollectionPagerAdapter(FragmentManager fm) {
	         super(fm);
	     }
	
	     @Override
	     public Fragment getItem(int position) {

            if (position == 0) {
            	Fragment fragment = new SearchGridFragment();
            	fragment.setArguments(mFilters);
	         	return fragment;
	         }
	         else {
	         	return new SearchMapFragment();	     		
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
}
