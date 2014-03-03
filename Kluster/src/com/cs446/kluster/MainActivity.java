package com.cs446.kluster;

import java.math.BigInteger;
import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cs446.kluster.accountadapter.AccountAdapter;

public class MainActivity extends Activity implements PhotoTilesFragment.ThumbnailClickListener {    
	PhotoFactory mFactory;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
        //** TODO: Move user creation to main? */
        AccountAdapter.setCurrentUser(new User(new BigInteger("531238e5f330ede5deafbc4e", 16), AccountAdapter.CreateAccount(this)));
        
        /*
         * Create a content observer object.
         * Its code does not mutate the provider, so set
         * selfChange to "false"
         */
        TableObserver observer = new TableObserver(AccountAdapter.getCurrentUser().getAccount(), this);
        /*
         * Register the observer for the data table. The table's path
         * and any of its subpaths trigger the observer.
         */
        getContentResolver().registerContentObserver(PhotoProvider.CONTENT_URI, true, observer);

        
        PhotoTilesFragment photoTilesFragmentOrganize=
        		(PhotoTilesFragment)
        		getFragmentManager().findFragmentById(R.id.pictureTilesFragmentOrganize);
        
        PhotoTilesFragment photoTilesFragmentShare=
        		(PhotoTilesFragment)
        		getFragmentManager().findFragmentById(R.id.pictureTilesFragmentShare);
        
        PhotoTilesFragment photoTilesFragmentDiscover=
        		(PhotoTilesFragment)
        		getFragmentManager().findFragmentById(R.id.pictureTilesFragmentDiscover);
        
        ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
        
        
        Bitmap bitmap=(BitmapFactory.decodeResource(getResources(), R.drawable.sample_a));
        bitmaps.add(Bitmap.createScaledBitmap(bitmap, 200, 100, false));
        bitmap=(BitmapFactory.decodeResource(getResources(), R.drawable.sample_b));
        bitmaps.add(Bitmap.createScaledBitmap(bitmap, 200, 100, false));
        bitmap=(BitmapFactory.decodeResource(getResources(), R.drawable.sample_c));
        bitmaps.add(Bitmap.createScaledBitmap(bitmap, 200, 100, false));
        bitmap=(BitmapFactory.decodeResource(getResources(), R.drawable.sample_d));
        bitmaps.add(Bitmap.createScaledBitmap(bitmap, 200, 100, false));
        bitmap=(BitmapFactory.decodeResource(getResources(), R.drawable.sample_e));
        bitmaps.add(Bitmap.createScaledBitmap(bitmap, 200, 100, false));
       photoTilesFragmentOrganize.setType("Organize");
       photoTilesFragmentOrganize.setClickableThumbnailText("Organize ");
       photoTilesFragmentOrganize.setThumbnailImages(bitmaps);
       photoTilesFragmentOrganize.setClickableThumbnailIcon(getResources().getDrawable(R.drawable.ic_action_collection));
       photoTilesFragmentShare.setType("Share");
       photoTilesFragmentShare.setClickableThumbnailText("Share ");
       photoTilesFragmentShare.setThumbnailImages(bitmaps);
       photoTilesFragmentShare.setClickableThumbnailIcon(getResources().getDrawable(R.drawable.ic_action_share));
       photoTilesFragmentDiscover.setType("Discover");
       photoTilesFragmentDiscover.setClickableThumbnailText("Discover ");
       photoTilesFragmentDiscover.setThumbnailImages(bitmaps);
       photoTilesFragmentDiscover.setClickableThumbnailIcon(getResources().getDrawable(R.drawable.ic_action_location_searching));
        
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu_options, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
    	switch (item.getItemId()) {
    	case android.R.id.home:
    		onBackPressed();
    		return true;
    		
    	case R.id.action_camera:
			Intent intent = new Intent(getBaseContext(), PhotoFactory.class);
			startActivity(intent);
    		return true;
    	case R.id.action_mapview:
			PhotoMapFragment firstFragment = new PhotoMapFragment();
            // Add the fragment to the 'main_activity'
			getFragmentManager().beginTransaction().add(R.id.main_container, firstFragment).addToBackStack(firstFragment.toString()).commit();
    		return true;	
    	} 	
    	return false;
    }

    @Override
	public void onBackPressed() {
		FragmentManager fm = getFragmentManager();

		if(fm.getBackStackEntryCount() > 0) {
			fm.popBackStack();
		}
	}

	@Override
	public void onThumbnailClick() {
		Log.d("onClick", "thumbnailClicked");
		// TODO Auto-generated method stub
//		PhotoAlbumFragment photoAlbumFragment = new PhotoAlbumFragment();
//		getFragmentManager().beginTransaction().add(R.id.main_container, photoAlbumFragment).commit();
		
	}
}
