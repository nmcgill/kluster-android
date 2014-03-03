package com.cs446.kluster;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.PorterDuff;

public class PhotoTilesFragment extends Fragment {
	
	int ORGANIZE_COLOR=0xFFF71111;
	int SHARE_COLOR=0XFF19C9FF;
	int DISCOVER_COLOR=0XFFF0D000;
	
	private String type;

	public interface ThumbnailClickListener {
		public void onThumbnailClick();
	}

	ThumbnailClickListener activityCallback;
	TextView clickableThumbnailText;
	ArrayList<ImageView> thumbnailPictures;
	LinearLayout fragmentLayout;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			activityCallback = (ThumbnailClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ThumbnailClickListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.phototiles_fragment, container,
				false);

		final FrameLayout clickableThumbnail = (FrameLayout) view
				.findViewById(R.id.clickable_thumbnail);
		clickableThumbnail.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d("onClick", "thumbnailClicked");
				PhotoAlbumFragment photoAlbumFragment = new PhotoAlbumFragment();
				getFragmentManager().beginTransaction().add(R.id.main_container, photoAlbumFragment).commit();
			}
		});
		
        clickableThumbnail.setOnTouchListener(new OnTouchListener() {
			
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                	formatClickableThumbnailOnActionDown();
                    break;
                }
                case MotionEvent.ACTION_UP:{
                	formatClickableThumbnailOnActionUp();
    				AlbumsBrowserFragment albumsBrowserFragment = new AlbumsBrowserFragment();
    				getFragmentManager().beginTransaction().add(R.id.main_container, albumsBrowserFragment).addToBackStack(albumsBrowserFragment.toString()).commit();
                    break;
                }
                }
                    return true;
            }
		});
        fragmentLayout=(LinearLayout)view.findViewById(R.id.Phototiles_Fragment);


		clickableThumbnailText = (TextView) view
				.findViewById(R.id.Thumbnail_Text);
		thumbnailPictures = new ArrayList<ImageView>();
		thumbnailPictures.add((ImageView) view.findViewById(R.id.Thumbnail_00));
		thumbnailPictures.add((ImageView) view.findViewById(R.id.Thumbnail_01));
		thumbnailPictures.add((ImageView) view.findViewById(R.id.Thumbnail_10));
		thumbnailPictures.add((ImageView) view.findViewById(R.id.Thumbnail_11));
		thumbnailPictures.add((ImageView) view.findViewById(R.id.Thumbnail_12));
		return view;
	}

	public void thumbnailClicked(View view) {
		activityCallback.onThumbnailClick();
	}

	public void setClickableThumbnailText(String text) {
		clickableThumbnailText.setText(text);
	}
	
	public void setClickableThumbnailColor(String text) {
		clickableThumbnailText.setText(text);
	}

	public void setThumbnailImages(ArrayList<Bitmap> bitmaps) {

		for (int i = 0; i < 5; i++) {
			if (bitmaps.size() >= i) {
				thumbnailPictures.get(i).setImageBitmap(bitmaps.get(i));
			}
			if (i == 1) {
				formatClickableThumbnailOnActionUp();
			}
		}
	}

	public void formatClickableThumbnailOnActionUp(){
		if(type=="Organize")
		{
		   thumbnailPictures.get(1).setColorFilter(ORGANIZE_COLOR);
		   thumbnailPictures.get(1).setBackgroundColor(ORGANIZE_COLOR);
		   fragmentLayout.setBackgroundColor(ORGANIZE_COLOR);
		}
		else if(type=="Share")
		{
		   thumbnailPictures.get(1).setColorFilter(SHARE_COLOR);
		   thumbnailPictures.get(1).setBackgroundColor(SHARE_COLOR);
		   fragmentLayout.setBackgroundColor(SHARE_COLOR);
		}
		else //Discover just acts as a fall through case
		{
		   thumbnailPictures.get(1).setColorFilter(DISCOVER_COLOR);
		   thumbnailPictures.get(1).setBackgroundColor(DISCOVER_COLOR);
		   fragmentLayout.setBackgroundColor(DISCOVER_COLOR);
		}
	}
	
	public void formatClickableThumbnailOnActionDown(){
		   thumbnailPictures.get(1).setColorFilter(0xff787478,
				        PorterDuff.Mode.MULTIPLY);
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
