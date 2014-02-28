package com.cs446.kluster;

import android.os.Bundle;

import com.google.android.gms.maps.MapFragment;



public class PhotoMapFragment extends MapFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}
}
