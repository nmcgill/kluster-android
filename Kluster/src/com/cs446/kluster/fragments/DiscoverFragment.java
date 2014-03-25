package com.cs446.kluster.fragments;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs446.kluster.R;

public class DiscoverFragment extends Fragment implements ActionBar.TabListener {
    private ViewPager mPager;	
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.discover_layout, container, false);
		
        
        mPager = (ViewPager) view.findViewById(R.id.viewpager_noswipe);
        mPager.setAdapter(new DemoCollectionPagerAdapter(getChildFragmentManager()));  
        
        // Specify that tabs should be displayed in the action bar.
        ActionBar ab = getActivity().getActionBar();
        
        ab.setTitle("Discover");
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ab.removeAllTabs();
        ab.addTab(ab.newTab().setText("PHOTO").setTabListener(this));
        ab.addTab(ab.newTab().setText("MAP").setTabListener(this));
		
		return view;
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
}
