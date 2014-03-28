package com.cs446.kluster.net;

import com.cs446.kluster.ConfigManager;
import com.cs446.kluster.KlusterApplication;
import com.cs446.kluster.net.http.AuthRequest;
import com.google.android.gms.maps.model.LatLng;

public class EventRequest extends AuthRequest {

    final private static String ENDPOINT_EVENTS = "/events";

    public EventRequest(String url) {
        super(Method.GET, url);
    }

    public static EventRequest create(LatLng ne, LatLng sw) {
    	String param = String.format("%f,%f|%f,%f", ne.latitude, ne.longitude, sw.latitude, sw.longitude);
    	
        ConfigManager config = KlusterApplication.getInstance().getConfigManager();
        String url = config.getProperty(ConfigManager.PROP_URL) + ENDPOINT_EVENTS;
        
        EventRequest request = new EventRequest(url);
        request.param("bounds", param);
        return request;
    }
    
    public static EventRequest create(LatLng brng, double radius) {
    	String param = String.format("%f,%f,%f", brng.latitude, brng.longitude, radius);
    	
        ConfigManager config = KlusterApplication.getInstance().getConfigManager();
        String url = config.getProperty(ConfigManager.PROP_URL) + ENDPOINT_EVENTS;
        
        EventRequest request = new EventRequest(url);
        request.param("ll", param);
        return request;
    }
}