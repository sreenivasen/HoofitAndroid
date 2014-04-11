package com.example.hoofit;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.NumberPicker;
import android.widget.TextView;

public class PickerFragmentHeight extends DialogFragment {

	private NumberPicker genderPicker;
	SharedPreferences pref;
	private String height;
	private TextView done;
	Context context;

	public PickerFragmentHeight() {
		// Empty constructor required dialog fragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_height, container);
		genderPicker = (NumberPicker) view.findViewById(R.id.heightpicker);
		done = (TextView) view.findViewById(R.id.doneText);
		genderPicker.setMaxValue(210);
		genderPicker.setMinValue(130);
		
		pref = getActivity().getSharedPreferences("APPLICATION_PREFERENCES", 0);
		height = pref.getString("HEIGHT", "170");
		genderPicker.setValue(Integer.parseInt(height));
		
		genderPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		genderPicker.setWrapSelectorWheel(true);
		genderPicker
				.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
					@Override
					public void onValueChange(NumberPicker picker, int oldVal,
							int newVal) {
						Log.d("GENDER PICKER", "New Val: " + newVal);
						getDialog().setTitle("Height: " + newVal + " cm");
					}
				});
		getDialog().setTitle("Height: 170 cm");
		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("GENDER PICKER", "value picked: "  + genderPicker.getValue());
				Editor editor = pref.edit();
				editor.putString("HEIGHT", genderPicker.getValue() + "");
				editor.commit();
				ProfileActivity profile = (ProfileActivity) getActivity();
				profile.onHeightSelected(genderPicker.getValue());
				getDialog().dismiss();

			}

		});

		return view;
	}

//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//
////		return new AlertDialog.Builder(getActivity())
////				.setNegativeButton("Done",
////						new DialogInterface.OnClickListener() {
////							public void onClick(DialogInterface dialog,
////									int whichButton) {
////								// DO SOMETHING
////							}
////						}).create();
//	}

}
