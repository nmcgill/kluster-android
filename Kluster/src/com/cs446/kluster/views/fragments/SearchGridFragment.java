package com.cs446.kluster.views.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.cs446.kluster.R;
import com.cs446.kluster.data.SearchProvider;
import com.cs446.kluster.data.SearchStorageAdapter;
import com.cs446.kluster.models.Event;
import com.cs446.kluster.net.AuthKlusterRestAdapter;
import com.cs446.kluster.net.KlusterService;
import com.cs446.kluster.views.fragments.FilterDialogFragment.FilterListener;

public class SearchGridFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, FilterListener {
	private EventGridAdapter mAdapter;
	private Map<String, String> mFilters;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.searchgrid_layout, container, false);
		
		String[] cols = new String[] { "location" };
		int[]   views = new int[]   { R.id.eventgrid_txtTitle };
		
		mAdapter = new EventGridAdapter(getActivity(), R.layout.eventgridcell_layout, null, cols, views, 0);
		
		GridView gridView=(GridView)view.findViewById(android.R.id.list);
		gridView.setEmptyView(view.findViewById(android.R.id.empty));
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
		
		Bundle args = getArguments();
		Integer radius = 55000;
		
		RestAdapter restAdapter = new AuthKlusterRestAdapter()
		.build();
		
		KlusterService service = restAdapter.create(KlusterService.class);
		service.getEvents(args.getString("location"), Integer.toString(radius), new Callback<List<Event>>() {		
			@Override
			public void success(List<Event> events, Response response) {
				SearchStorageAdapter storage = new SearchStorageAdapter(getActivity().getContentResolver());
				for (Event item : events) {
					storage.insert(item);
				}
			}
			@Override
			public void failure(RetrofitError error) {
				//Log.e("Search", error.getResponse().getReason());
			}
		});
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
		if (mFilters == null) {
			mFilters = new HashMap<String, String>();	
		}
		
		Set<String> keySet = mFilters.keySet();
		keySet.remove("location");
		List<String> selectionArgs = new ArrayList<String>();
		String selection = TextUtils.join(" = ? and ", keySet);
		
		for (String str : keySet) {
			selectionArgs.add(mFilters.get(str));
		}
		
		String[] argsArray = selectionArgs.toArray(new String[selectionArgs.size()]);
		
		return new CursorLoader(getActivity(), SearchProvider.CONTENT_URI, null, selection, argsArray, null);
	}
		
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.changeCursor(cursor);
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.changeCursor(null);
	}

	@Override
	public void userSetFilter(String filter, String value) {
		if (mFilters == null) {
			mFilters = new HashMap<String, String>();	
		}
		
		mFilters.put(filter, value);
	}

	@Override
	public void userReturned() {
		
	}
}
