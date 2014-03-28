package com.cs446.kluster.views.fragments;

import java.util.HashMap;
import java.util.Map;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.cs446.kluster.KlusterApplication;
import com.cs446.kluster.data.EventStorageAdapter;
import com.cs446.kluster.data.PhotoProvider;
import com.cs446.kluster.data.serialize.EventSerializer;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.net.EventRequest;
import com.cs446.kluster.net.http.task.HttpContentRequestTask;
import com.cs446.kluster.views.map.MapUtils;
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
    
    Map<Marker, ImageView> mMarkerList = new HashMap<Marker, ImageView>();

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
		
		getMap().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				getMap().setInfoWindowAdapter(new PhotoInfoWindowAdapter(getActivity(), mMarkerList.get(marker)));
				marker.showInfoWindow();
				return true;
			}
		});
		
		getMap().setOnCameraChangeListener(new OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                LatLngBounds bounds = getMap().getProjection().getVisibleRegion().latLngBounds;
                
        		EventRequest request = EventRequest.create(bounds.northeast, bounds.southwest);
        		HttpContentRequestTask<Event> task = new HttpContentRequestTask<Event>(new EventSerializer(), new EventStorageAdapter(getActivity().getContentResolver()));
        	
        		task.executeAsync(request);
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
                            PhotoProvider.CONTENT_URI,        // Table to query
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
		int locIndex;

		while (cursor != null && cursor.moveToNext()) {
			locIndex = cursor.getColumnIndex("location");
	        
			Marker marker = getMap().addMarker(new MarkerOptions()
			.position(MapUtils.stringToLatLng(cursor.getString(locIndex))));

			String url = cursor.getString(cursor.getColumnIndex("url"));
	        
	        ImageView imgView = new ImageView(getActivity());
	        mMarkerList.put(marker, imgView);

	        KlusterApplication.getInstance().getCache().loadBitmap(url, imgView, getActivity());
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}
}
