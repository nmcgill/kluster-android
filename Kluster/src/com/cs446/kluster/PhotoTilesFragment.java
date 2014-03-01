package com.cs446.kluster;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class PhotoTilesFragment extends Fragment{
	
	public interface ThumbnailClickListener{
		public void onThumbnailClick();
	}
	
	ThumbnailClickListener activityCallback;
	TextView clickableThumbnailText;
	
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
          
          clickableThumbnailText=(TextView)view.findViewById(R.id.Thumbnail_Text);
        return view;
    }
    
	   public void thumbnailClicked (View view) {
		   activityCallback.onThumbnailClick();
	   }
	   
	   public void setClickableThumbnailText(String text){
		   clickableThumbnailText.setText(text);
	   }
	   
	   public void setClickableThumbnailImages(ArrayList<Bitmap> bitmaps){
		   
	   }
	   
	   
}
