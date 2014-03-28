package com.cs446.kluster;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

public class LocationRequest implements LocationListener {
	LocationManager mLocationManager;
	
	protected LocationRequest() {
	}
	
	public LatLng getLastLocation(Context c) {
		LocationManager mLocationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
		
		//String locationProvider = LocationManager.NETWORK_PROVIDER;
		// Or, use GPS location data:
		String locationProvider = LocationManager.GPS_PROVIDER;

		mLocationManager.requestLocationUpdates(locationProvider, 0, 0, this);
		
		return null;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
