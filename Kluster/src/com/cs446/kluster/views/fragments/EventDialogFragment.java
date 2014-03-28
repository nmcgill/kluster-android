package com.cs446.kluster.views.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs446.kluster.R;

public class EventDialogFragment extends DialogFragment {
    private String mEventName;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mEventName = "";
		
		View view = inflater.inflate(R.layout.eventdialog_layout, container, false);
		
		getDialog().setTitle("Create a New Event");
		
		Button btnApply = (Button)view.findViewById(R.id.eventdialog_btnCreate);
		
		EditText txtCreate = (EditText)view.findViewById(R.id.eventdialog_txtName);

		btnApply.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				
				if (mEventName.length() > 4) {
					Toast.makeText(getActivity(), "Event name too short.", Toast.LENGTH_SHORT).show();
				}
				else {
					//POST REQUEST?
				}
				
				getDialog().dismiss();
			}
		});
		
		txtCreate.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) { }
			
			@Override
			public void afterTextChanged(Editable s) {
				mEventName = s.toString();
			}
		});
		
		return view;
	}	
}
