package com.cs446.kluster.views.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.cs446.kluster.R;

public class FilterDialogFragment extends DialogFragment {
    
    public static interface FilterListener
    {
        public void userSetFilter(String filter, String value);
        public void userReturned();
    }
    
    private static FilterListener mListener;
    
    public void setFilterListener(FilterListener listener) {
    	mListener = listener;
    }
    
    public FilterListener getFilterListener() {
    	return mListener;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.filter_layout, container, false);
		
		getDialog().setTitle("Filter Search Results");
		
		Button btnDate = (Button)view.findViewById(R.id.filter_btnDate);
		Button btnTime = (Button)view.findViewById(R.id.filter_btnTime);
		Button btnApply = (Button)view.findViewById(R.id.filter_btnApply);
		
		EditText txtContributor = (EditText)view.findViewById(R.id.filter_user);
		EditText txtTags = (EditText)view.findViewById(R.id.filter_tags);
		
		btnDate.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				DialogFragment fragment = new DatePickerFragment();
				
				fragment.show(getFragmentManager(), "timePicker");	
			}
		});
		
		btnTime.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				DialogFragment fragment = new TimePickerFragment();
				
				fragment.show(getFragmentManager(), "timePicker");	
			}
		});
		
		btnApply.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				mListener.userReturned();
				getDialog().dismiss();
			}
		});
		
		txtContributor.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) { }
			
			@Override
			public void afterTextChanged(Editable s) {
				mListener.userSetFilter("username", s.toString());
			}
		});
		
		txtTags.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) { }
			
			@Override
			public void afterTextChanged(Editable s) {
				mListener.userSetFilter("tags", s.toString());
			}
		});
		
		return view;
	}
	
	public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
		}
		
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mListener.userSetFilter("time", String.format("%s:%s", hourOfDay, minute));
		}
	}
	
	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {	
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		public void onDateSet(DatePicker view, int year, int month, int day) {
			mListener.userSetFilter("date", String.format("%d/%d/%d", month, day, year));
		}
	}
	
}
