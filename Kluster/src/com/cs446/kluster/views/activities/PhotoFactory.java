package com.cs446.kluster.views.activities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.cs446.kluster.models.Photo;
import com.cs446.kluster.net.UploadService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;

/** Handles switching to Camera, saving data, and recording GPS information */
public class PhotoFactory extends Activity implements GooglePlayServicesClient.ConnectionCallbacks, 
													  GooglePlayServicesClient.OnConnectionFailedListener {
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static Uri fileUri;
    private static LocationClient mLocationClient = null;
    
    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {
		// Global field to contain the error dialog
		private Dialog mDialog;
		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
	    	super();
	    	mDialog = null;
		}
		// Set the dialog to display
		public void setDialog(Dialog dialog) {
	    	mDialog = dialog;
		}
		
		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
       /* Create a new location client, using the enclosing class to
        * handle callbacks. */
        mLocationClient = new LocationClient(this, this, this);
    
		TakePhoto();
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            	Date timeStamp = new Date();

            	sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, fileUri));
            	
            	SharedPreferences pref = getSharedPreferences("User", Context.MODE_PRIVATE);
            	String userid = pref.getString("id", null);
            	
                // Image captured and saved to fileUri specified in the Intent
                //Toast.makeText(this, "Image saved to"+fileUri.toString(), Toast.LENGTH_LONG).show();
                Location lastloc = mLocationClient.getLastLocation();
                
                Geocoder geoCoder = new Geocoder(this);
                List<Address> addr = null;
                List<String> tags = new ArrayList<String>();
                
                try {
					addr = geoCoder.getFromLocation(lastloc.getLatitude(), lastloc.getLongitude(), 1);
				} catch (IOException e) {
					Log.e("Photofactory", "Could not geocode: " + e.toString());
				}
                
                if( addr != null && addr.size() > 0) {
                	tags.add(addr.get(0).getAddressLine(0));
                	tags.add(addr.get(0).getAddressLine(1));
                	tags.add(addr.get(0).getAddressLine(2));
                	tags.add(addr.get(0).getLocality());
                }
                
                Photo photo = new Photo("531238e5f330ede5deafbc3b",
                						userid,
                						"531238e5f330ede5deafbc3a",
                						new LatLng(lastloc.getLatitude(),lastloc.getLongitude()),
                						timeStamp,
                						new String[] {fileUri.getPath(), fileUri.getPath(), fileUri.getPath()},
                						tags,
                						new String[] {"0", "0"});
            	
                //Start upload service
                Intent intent = new Intent(this, UploadService.class);
                intent.putExtra("com.cs446.kluster.Photo", photo);
                startService(intent);
                
                // Disconnecting the client invalidates it.
                Log.w("gps", mLocationClient.getLastLocation().toString());
                mLocationClient.disconnect();
            } 
            else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            }
            else {
                // Image capture failed, advise user
            }
        }
        
        //Go back / destroy PhotoFactory
        onBackPressed();
    }
	
	public void TakePhoto() {
		// create Intent to take a picture and return control to the calling application
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	    fileUri = PhotoFactory.getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // Connect the location client.
        mLocationClient.connect();
        
	    // start the image capture Intent
	    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
    /** Create a file Uri for saving an image or video */
    public static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }
    
    /** Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

    	if (!isExternalStorageWritable()) {
    		Log.d("Kluster", "External not writable");
    		return null;
    	}
    	
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "Kluster");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("Kluster", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            // Get the error code
            int errorCode = connectionResult.getErrorCode();
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    errorCode,
                    this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment =
                        new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                errorFragment.show(getFragmentManager(),
                        "Location Updates");
            }
        }
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
        //Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();		
	}
}
