package com.example.hoofit;


import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PickerFragmentGender extends DialogFragment {

	private TextView done;
	private String gender = "Male";
	private SharedPreferences pref;
	Context context;

	public PickerFragmentGender() {
		// Empty constructor required dialog fragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_gender, container);
		pref = getActivity().getSharedPreferences("APPLICATION_PREFERENCES", 0);
		gender = pref.getString("GENDER", "Male");
		done = (TextView) view.findViewById(R.id.doneText);
		RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.optionRadioGroup);
		RadioButton radio1 = (RadioButton) view.findViewById(R.id.radio1);
		RadioButton radio2 = (RadioButton) view.findViewById(R.id.radio2);
		
		if (gender.equals("Male")){
			radio1.setChecked(true);
		}
		else{
			radio2.setChecked(true);
		}
		
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group,
							int checkedId) {
						Log.d("RADIO VALUE: ", checkedId + " integer value");
						RadioButton checkedRadioButton = (RadioButton) view.findViewById(checkedId);
						gender = checkedRadioButton.getText().toString();
					}
				});
		getDialog().setTitle("Gender");
		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor editor = pref.edit();
				editor.putString("GENDER", gender);
				editor.commit();
				ProfileActivity profile = (ProfileActivity) getActivity();
				profile.onGenderSelected(gender);
				getDialog().dismiss();

			}

		});

		return view;
	}


}
