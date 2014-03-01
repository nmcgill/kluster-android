package com.cs446.kluster;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class PhotoTilesFragment extends Fragment{
	
	public interface ThumbnailClickListener{
		public void onThumbnailClick();
	}
	
	ThumbnailClickListener activityCallback;
	
	@Override
	  public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        try {
	            activityCallback = (ThumbnailClickListener)activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement ThumbnailClickListener");
	        }
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
          View view = inflater.inflate(R.layout.phototiles_fragment, 
              container, false);
          
          final FrameLayout clickableThumbnail=(FrameLayout)view.findViewById(R.id.clickable_thumbnail);
          clickableThumbnail.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                thumbnailClicked(v);
	            }
	        });
        return view;
    }
    
	   public void thumbnailClicked (View view) {
		   activityCallback.onThumbnailClick();
	   }
}
