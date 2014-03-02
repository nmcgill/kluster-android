package com.cs446.kluster;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

public class PhotoAlbumBrowserActivity extends Activity implements PhotoAlbumThumbnailFragment.ThumbnailClickListener {

	@Override
	public void onThumbnailClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoalbumbrowser_activity);
//		
//	    GridView gridview = (GridView) findViewById(R.id.AlbumGrid);
//	    PhotoAlbumThumbnailFragment photoAlbumThumbnailFragment=new PhotoAlbumThumbnailFragment();
//	    gridview.addView((View)photoAlbumThumbnailFragment); //	    gridview.setAdapter(new ThumbnailAdapter(this));
//
//	    gridview.setOnItemClickListener(new OnItemClickListener() {
//	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//	            Toast.makeText(HelloGridView.this, "" + position, Toast.LENGTH_SHORT).show();
//	        }
//	    });
		
	}
	
	

}
