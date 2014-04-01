package com.cs446.kluster.views.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.cs446.kluster.data.SearchProvider;
import com.cs446.kluster.data.EventProvider.EventOpenHelper;
import com.cs446.kluster.map.MapUtils;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.views.fragments.FilterDialogFragment.FilterListener;
import com.cs446.kluster.views.map.PhotoInfoWindowAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class SearchMapFragment extends MapFragment implements LoaderManager.LoaderCallbacks<Cursor>, FilterListener {
    // Identifies a particular Loader being used in this component
    private static final int URL_LOADER = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);

		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

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
                            SearchProvider.CONTENT_URI,        // Table to query
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

	@Override
	public void userSetFilter(String filter, String value) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void userReturned() {
		// TODO Auto-generated method stub
	}
}
