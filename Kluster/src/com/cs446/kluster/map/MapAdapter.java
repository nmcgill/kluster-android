package com.cs446.kluster.map;

import com.google.android.gms.maps.model.LatLng;

public class MapAdapter {

	public MapAdapter() {
	}
	
	public static LatLng StringToLatLng(String str) {
		double lat = Double.parseDouble(str.substring(0, str.indexOf(',')));
		double lng = Double.parseDouble(str.substring(str.indexOf(',')+1));
				
		return new LatLng(lat, lng);
	}
	
	public static String LatLngToString(LatLng latlng) {
		return Double.toString(latlng.latitude) + "," + Double.toString(latlng.longitude);
	}

}
