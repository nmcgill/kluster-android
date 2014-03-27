package com.cs446.kluster.views.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.cs446.kluster.R;

public class FilterDialogFragment extends DialogFragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.filter_layout, container, false);
		
		getDialog().setTitle("Filter Search Results");
		
		Button btnDate = (Button)view.findViewById(R.id.filter_btnDate);
		Button btnTime = (Button)view.findViewById(R.id.filter_btnTime);
		Button btnApply = (Button)view.findViewById(R.id.filter_btnApply);
		
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
				getDialog().dismiss();
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
			// Do something with the time chosen by the user
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
			// Do something with the date chosen by the user
		}
	}
	
}
