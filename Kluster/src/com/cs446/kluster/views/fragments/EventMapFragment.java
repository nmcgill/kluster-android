package com.cs446.kluster.views.fragments;

import java.util.HashMap;
import java.util.Map;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.cs446.kluster.KlusterApplication;
import com.cs446.kluster.data.PhotoProvider;
import com.cs446.kluster.views.map.MapAdapter;
import com.cs446.kluster.views.map.PhotoInfoWindowAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class EventMapFragment extends MapFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // Identifies a particular Loader being used in this component
    private static final int URL_LOADER = 0;
    
    Map<Marker, ImageView> mMarkerList = new HashMap<Marker, ImageView>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);

		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

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

			getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(MapAdapter.StringToLatLng(cursor.getString(locIndex)), 13));
	        
			Marker marker = getMap().addMarker(new MarkerOptions()
			.position(MapAdapter.StringToLatLng(cursor.getString(locIndex))));

			String remoteurl = cursor.getString(cursor.getColumnIndex("remoteurl"));
	        
	        ImageView imgView = new ImageView(getActivity());
	        mMarkerList.put(marker, imgView);

	        KlusterApplication.getInstance().getCache().loadBitmap(remoteurl, imgView, getActivity());
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}
}
