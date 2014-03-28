package com.cs446.kluster.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cs446.kluster.KlusterApplication;
import com.cs446.kluster.R;

public class PhotoViewerFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.photoview_layout, container, false);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		String url = getArguments().getString("url");
		
		ImageView imgMain =(ImageView)getView().findViewById(R.id.photoview_imgMain);
		
		KlusterApplication.getInstance().getCache().loadBitmap(url, imgMain, getActivity());		
	}
}