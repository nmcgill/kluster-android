package com.cs446.kluster.views.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.cs446.kluster.R;
import com.cs446.kluster.data.EventProvider;
import com.cs446.kluster.data.EventStorageAdapter;
import com.cs446.kluster.data.serialize.EventSerializer;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.net.EventRequest;
import com.cs446.kluster.net.http.task.HttpCollectionRequestTask;
import com.cs446.kluster.views.map.MapUtils;

public class EventGridFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
	EventGridAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
    	LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

		String current = MapUtils.locationToString(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
		
		EventRequest request = EventRequest.create(current, 30000);
		HttpCollectionRequestTask<Event> task = new HttpCollectionRequestTask<Event>(new EventSerializer(), new EventStorageAdapter(getActivity().getContentResolver()));
	
		task.executeAsync(request);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.eventgrid_layout, container, false);
		
		String[] cols = new String[] { "location" };
		int[]   views = new int[]   { R.id.eventgrid_txtTitle };
		
		mAdapter = new EventGridAdapter(getActivity(), R.layout.eventgridcell_layout, null, cols, views, 0);
		
		GridView gridView=(GridView)view.findViewById(R.id.eventGrid);
		gridView.setAdapter(mAdapter);

        /* Start loader */  
        getLoaderManager().initLoader(0, null, this);   
        
        setHasOptionsMenu(true);
        getActivity().getActionBar().setTitle("Discover");
        
		return view;
	}
		
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if (getActivity().getFragmentManager().getBackStackEntryCount() > 0) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
		return new CursorLoader(getActivity(), EventProvider.CONTENT_URI, null, null, null, null);
	}
		
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.changeCursor(cursor);
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.changeCursor(null);
	}
}
