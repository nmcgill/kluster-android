package com.cs446.kluster;

import android.app.GridFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class PhotoAlbumFragment extends GridFragment implements LoaderManager.LoaderCallbacks<Cursor>, OnItemClickListener {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		String[] cols = new String[] { "title" };
		int[]   views = new int[]   { R.id.PhotoAlbumThumbnailText };
		
		PhotoAlbumAdapter adapter = new PhotoAlbumAdapter(getActivity(), R.layout.photoalbumthumbnail_fragment, null, cols, views, 0);
        
        setGridAdapter(adapter);
        getGridView().setOnItemClickListener(this);
        
        /* Start loader */  
        getLoaderManager().initLoader(0, null, this);   
    }
    
	@Override
	public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
		return new CursorLoader(getActivity(), PhotoProvider.CONTENT_URI, null, "_id in (select _id from newsitems where position = 0 and category = 0 order by pubdate desc LIMIT 1) or _id in (select _id from newsitems where position = 0 and category = 1 order by pubdate desc LIMIT 1) or _id in (select _id from newsitems where position = 0 and category = 2 order by pubdate desc LIMIT 1)", null, null);
	}
		
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		((PhotoAlbumAdapter)getListAdapter()).changeCursor(cursor);
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		((PhotoAlbumAdapter)getListAdapter()).changeCursor(null);
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}	
}
