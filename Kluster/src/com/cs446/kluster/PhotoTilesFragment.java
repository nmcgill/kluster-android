package com.cs446.kluster;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoTilesFragment extends Fragment{
	
	public interface ThumbnailClickListener{
		public void onThumbnailClick();
	}
	
	ThumbnailClickListener activityCallback;
	TextView clickableThumbnailText;
	List<ImageView> thumbnailPictures;
	
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
          thumbnailPictures=new ArrayList<ImageView>();
          thumbnailPictures.add((ImageView)view.findViewById(R.id.Thumbnail_0_0));
          thumbnailPictures.add((ImageView)view.findViewById(R.id.Thumbnail_0_1));
          thumbnailPictures.add((ImageView)view.findViewById(R.id.Thumbnail_1_0));
          thumbnailPictures.add((ImageView)view.findViewById(R.id.Thumbnail_1_1));
          thumbnailPictures.add((ImageView)view.findViewById(R.id.Thumbnail_1_2));   
        return view;
    }
    
	   public void thumbnailClicked (View view) {
		   activityCallback.onThumbnailClick();
	   }
	   
	   public void setClickableThumbnailText(String text){
		   clickableThumbnailText.setText(text);
	   }
	   
	   public void setClickableThumbnailImages(List<Bitmap> bitmaps){
		   
		   for(int i=0; i < 5; i++)
		   {
			   if(bitmaps.size() <= i)
			   {
				   thumbnailPictures.get(i).setImageBitmap(bitmaps.get(i));
			   }
		   }
	   }
	   
	   
}
