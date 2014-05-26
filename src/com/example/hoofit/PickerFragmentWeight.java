package com.example.hoofit;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.sreenivasen.hoofit.R;

public class PickerFragmentWeight extends DialogFragment {

	private NumberPicker genderPicker;
	SharedPreferences pref;
	private TextView done;
	Context context;
	String weight;

	public PickerFragmentWeight() {
		// Empty constructor required dialog fragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_height, container);
		genderPicker = (NumberPicker) view.findViewById(R.id.heightpicker);
		pref = getActivity().getSharedPreferences("APPLICATION_PREFERENCES", 0);
		weight = pref.getString("WEIGHT", "170");
		done = (TextView) view.findViewById(R.id.doneText);
		genderPicker.setMaxValue(210);
		genderPicker.setMinValue(130);
		genderPicker.setValue(Integer.parseInt(weight));
		genderPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		genderPicker.setWrapSelectorWheel(true);
		genderPicker
				.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
					@Override
					public void onValueChange(NumberPicker picker, int oldVal,
							int newVal) {
						getDialog().setTitle("Weight: " + newVal + " lb");
					}
				});
		getDialog().setTitle("Weight: 170 lb");
		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ProfileActivity profile = (ProfileActivity) getActivity();
				Editor editor = pref.edit();
				editor.putString("WEIGHT", genderPicker.getValue() + "");
				editor.commit();
				
				profile.onWeightSelected(genderPicker.getValue());
				getDialog().dismiss();

			}

		});

		return view;
	}


}
