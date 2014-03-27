package com.cs446.kluster;

import android.app.Fragment;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PhotoViewerFragment extends Fragment{
	
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.photoviewer_fragment, container,
				false);
		getArguments().getString("imageUrl");
		/*Is given a list images and this view just open all of them*/
		return view;
    }
    
  



}
