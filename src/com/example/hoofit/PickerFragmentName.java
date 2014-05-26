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
import android.widget.EditText;
import android.widget.TextView;
import com.sreenivasen.hoofit.R;

public class PickerFragmentName extends DialogFragment {

	private EditText userName;
	SharedPreferences pref;
	private String enteredName;
	private TextView done;
	Context context;

	public PickerFragmentName() {
		// Empty constructor required dialog fragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_name, container);

		done = (TextView) view.findViewById(R.id.doneText);
		userName = (EditText) view.findViewById(R.id.userName);
		
		pref = getActivity().getSharedPreferences("APPLICATION_PREFERENCES", 0);
		enteredName = pref.getString("NAME", "-1");
		if (!enteredName.equals("-1"))
			userName.setText(enteredName);
		
		getDialog().setTitle("Enter your name");
		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editor editor = pref.edit();
				editor.putString("NAME", userName.getText() + "");
				editor.commit();
				ProfileActivity profile = (ProfileActivity) getActivity();
				profile.onNameEntered(userName.getText() + "");
				getDialog().dismiss();

			}

		});

		return view;
	}


}
