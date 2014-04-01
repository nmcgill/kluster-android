package com.cs446.kluster.views.fragments;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cs446.kluster.R;
import com.cs446.kluster.data.EventProvider;
import com.cs446.kluster.data.EventProvider.EventOpenHelper;


public class EventMapFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // Identifies a particular Loader being used in this component
    private static final int URL_LOADER = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);

    	LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mapwebview_layout, container, false);
		
		getLoaderManager().initLoader(URL_LOADER, null, this);
		
	    WebView webView = (WebView) view.findViewById(R.id.map_webview);
	    webView.getSettings().setJavaScriptEnabled(true);
	    
	    webView.setWebViewClient(new MyWebViewClient());
	    webView.getSettings().supportZoom();
	    webView.loadUrl("https://www.google.com/maps/place/43%C2%B028'18.0%22N+80%C2%B032'33.6%22W/@43.471665,-80.542671,15z/data=!3m1!4b1!4m2!3m1!1s0x0:0x0");
	    
	    return view;
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
	        
			/*try {
				getMap().addMarker(new MarkerOptions()
				.position(MapUtils.stringToLatLng(loc))
				.snippet(df.format(Event.getDateFormat().parse(eventDate)))
				.title(eventName));
			} catch (ParseException e) {
			}*/

		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}
	
	private class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	    
	    @Override
	    public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {


	    	 handler.proceed() ;


	    	 }
	}
}
