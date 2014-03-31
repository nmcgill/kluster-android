package com.cs446.kluster.map;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class MapUtils {
	
	public MapUtils() {
	}
	
	public static LatLng stringToLatLng(String str) {
		double lat = Double.parseDouble(str.substring(0, str.indexOf(',')));
		double lng = Double.parseDouble(str.substring(str.indexOf(',')+1));
				
		return new LatLng(lat, lng);
	}
	
	public static String latLngToString(LatLng latlng) {
		return Double.toString(latlng.latitude) + "," + Double.toString(latlng.longitude);
	}
	
	public static LatLng locationToLatLng(Location loc) {
		return new LatLng(loc.getLatitude(), loc.getLongitude());
	}
	
	public static String locationToString(Location loc) {
		return latLngToString(locationToLatLng(loc));
	}

}
