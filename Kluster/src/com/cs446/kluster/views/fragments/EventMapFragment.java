package com.cs446.kluster.views.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit.RestAdapter;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.cs446.kluster.data.EventProvider;
import com.cs446.kluster.data.EventProvider.EventOpenHelper;
import com.cs446.kluster.map.MapUtils;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.net.AuthKlusterRestAdapter;
import com.cs446.kluster.net.EventsCallback;
import com.cs446.kluster.net.KlusterService;
import com.cs446.kluster.views.map.PhotoInfoWindowAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class EventMapFragment extends MapFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // Identifies a particular Loader being used in this component
    private static final int URL_LOADER = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);

    	LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

		getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(MapUtils.locationToLatLng(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)), 13));
    }
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		getLoaderManager().initLoader(URL_LOADER, null, this);
		
		/*getMap().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				getMap().setInfoWindowAdapter(new PhotoInfoWindowAdapter(getActivity()));
				marker.showInfoWindow();
				return true;
			}
		});*/
		
		getMap().setOnCameraChangeListener(new OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                LatLngBounds bounds = getMap().getProjection().getVisibleRegion().latLngBounds;
                double swlong = bounds.southwest.longitude;
                double swlat = bounds.southwest.latitude;
                double nelong = bounds.northeast.longitude;
                double nelat = bounds.northeast.latitude;
        		RestAdapter restAdapter = new AuthKlusterRestAdapter()
        		.build();
        		KlusterService service = restAdapter.create(KlusterService.class);
        		
        		service.getEvents(null, null, String.format("%f,%f|%f,%f", swlong, swlat, nelong, nelat), new EventsCallback(getActivity()));
            }
        });
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return getActivity().onOptionsItemSelected(item);
	}

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle)
    {
        switch (loaderID) {
            case URL_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                            getActivity(),   // Parent activity context
                            EventProvider.CONTENT_URI,        // Table to query
                            null,     		 // Projection to return
                            null,            // No selection clause
                            null,            // No selection arguments
                            null             // Default sort order
            );
            default:
                return null;
        }
    }

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		String loc;
		String eventName;
		String eventDate;
		String eventTags;
		SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy hh:mmaa", Locale.US);

		while (cursor != null && cursor.moveToNext()) {
			loc = cursor.getString(cursor.getColumnIndex(EventOpenHelper.COLUMN_LOCATION));		
	        eventName = cursor.getString(cursor.getColumnIndex(EventOpenHelper.COLUMN_EVENT_NAME));
	        eventDate = cursor.getString(cursor.getColumnIndex(EventOpenHelper.COLUMN_STARTTIME));
	        eventTags = cursor.getString(cursor.getColumnIndex(EventOpenHelper.COLUMN_TAGS));
	        
			try {
				getMap().addMarker(new MarkerOptions()
				.position(MapUtils.stringToLatLng(loc))
				.snippet(df.format(Event.getDateFormat().parse(eventDate)))
				.title(eventName));
			} catch (ParseException e) {
			}

		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}
}
