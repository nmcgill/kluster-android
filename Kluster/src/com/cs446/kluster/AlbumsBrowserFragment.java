package com.cs446.kluster;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class AlbumsBrowserFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, OnItemClickListener{

	
	AlbumsBrowserAdapter adapter;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.albumsbrowser_fragment,
				container, false);
		
		String[] cols = new String[] { "location" };
		int[]   views = new int[]   { R.id.gridTextCaption };
		
		adapter = new AlbumsBrowserAdapter(getActivity(), R.layout.photoalbumgrid_layout, null, cols, views, 0);
		
		GridView gridView=(GridView)view.findViewById(R.id.genericGridView);
		gridView.setAdapter(adapter);
        
        /* Start loader */  
        getLoaderManager().initLoader(0, null, this);   
		return view;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
		return new CursorLoader(getActivity(), PhotoProvider.CONTENT_URI, null, null, null, null);
	}
		
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.changeCursor(cursor);
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.changeCursor(null);
	}
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
    }
    
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return getActivity().onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}	
}
