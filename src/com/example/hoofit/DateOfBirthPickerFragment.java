package com.example.hoofit;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;



public class DateOfBirthPickerFragment extends DialogFragment {
	 OnDateSetListener ondateSet;

	 public void DatePickerFragment() {
	 }

	 public void setCallBack(OnDateSetListener ondate) {
	  ondateSet = ondate;
	 }

	 private int year, month, day;

	 @Override
	 public void setArguments(Bundle args) {
	  super.setArguments(args);
	  year = args.getInt("year");
	  month = args.getInt("month");
	  day = args.getInt("day");
	 }

	 @Override
	 public Dialog onCreateDialog(Bundle savedInstanceState) {
	  return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
	 }
	}
