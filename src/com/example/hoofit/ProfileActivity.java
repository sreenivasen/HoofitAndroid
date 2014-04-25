package com.example.hoofit;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {
	protected String[] drawerListViewItems;
	protected DrawerLayout drawerLayout;
	protected ListView drawerListView;
	protected ActionBarDrawerToggle actionBarDrawerToggle;
	protected ActionBar actionBar;
	final ProfileActivity profile = this;
	TextView dateOfBirth, height, weight, gender, userName;
	String strGender, strAge, strHeight, strWeight, strBirthYear, strBirthDay, strBirthMonth, strName;
	Context context;
	private SharedPreferences pref;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.app_profile);
		setNavigationDrawer();
		
		pref = getApplicationContext().getSharedPreferences("APPLICATION_PREFERENCES", MODE_PRIVATE);

		dateOfBirth = (TextView) findViewById(R.id.ageCalculator);	
		userName = (TextView) findViewById(R.id.userName);
		height = (TextView) findViewById(R.id.height);
		gender = (TextView) findViewById(R.id.gender);
		weight = (TextView) findViewById(R.id.weight);
		
		strGender = pref.getString("GENDER", "-1");
		strAge = pref.getString("AGE", "-1");
		strHeight = pref.getString("HEIGHT", "-1");
		strWeight = pref.getString("WEIGHT", "-1");
		strName = pref.getString("NAME", "-1");

		//check if gender is set
		if (strGender.equals("-1")){
			gender.setText("enter your gender");
		}
		else{
			gender.setText(strGender);
			gender.setTextAppearance(profile, R.style.boldText);
		}
		
		//check if age is set
		if (strAge.equals("-1")){
			dateOfBirth.setText("enter your DOB");
		}
		else{
			dateOfBirth.setText(strAge);
			dateOfBirth.setTextAppearance(profile, R.style.boldText);
		}
		
		//check if height is set
		if (strHeight.equals("-1")){
			height.setText("enter your height");
		}
		else{
			height.setText(strHeight + " cm");
			height.setTextAppearance(profile, R.style.boldText);
		}
		
		//check if weight is set
		if (strWeight.equals("-1")){
			weight.setText("enter your weight");
		}
		else{
			weight.setText(strWeight + " lb");
			weight.setTextAppearance(profile, R.style.boldText);
		}
		
		//check if name is set
		if (strName.equals("-1")){
			userName.setText("enter your name");
		}
		else{
			userName.setText(strName + "");
			userName.setTextAppearance(profile, R.style.boldText);
		}
		
		userName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showNamePicker();

			}

		});
		
		height.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showHeightPicker();

			}

		});

		weight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showWeightPicker();

			}

		});
		
		gender.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showGenderPicker();

			}

		});

		dateOfBirth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePicker();
			}
		});

	}
	
	private void showHeightPicker(){

        PickerFragmentHeight editGenderDialog = new PickerFragmentHeight();
        editGenderDialog.show(getFragmentManager(), "Height Picker");
        
	}
	
	private void showWeightPicker(){
		
        PickerFragmentWeight editWeightDialog = new PickerFragmentWeight();
        editWeightDialog.show(getFragmentManager(), "Weight Picker");
		
	}
	
	private void showGenderPicker(){
		
        PickerFragmentGender editGenderDialog = new PickerFragmentGender();
        editGenderDialog.show(getFragmentManager(), "Gender Picker");
	}
	
	private void showNamePicker(){
        PickerFragmentName editNameDialog = new PickerFragmentName();
        editNameDialog.show(getFragmentManager(), "Name Picker");
	}
	
	public void onHeightSelected(int ht){
		height.setText(ht + " cm");
		height.setTextAppearance(profile, R.style.boldText);
	}
	
	public void onWeightSelected(int wt){
		weight.setText(wt + " lb");
		weight.setTextAppearance(profile, R.style.boldText);
	}
	
	public void onGenderSelected(String genderString){
		gender.setText(genderString);
		gender.setTextAppearance(profile, R.style.boldText);
		
	}
	
	public void onNameEntered(String usr){
		userName.setText(usr);
		userName.setTextAppearance(profile, R.style.boldText);
	}
	


	private void showDatePicker() {
		DateOfBirthPickerFragment date = new DateOfBirthPickerFragment();
		/**
		 * Set Up Current Date Into dialog
		 */
		strBirthYear = pref.getString("BIRTH_YEAR", "-1");
		strBirthMonth = pref.getString("BIRTH_MONTH", "-1");
		strBirthDay = pref.getString("BIRTH_DAY", "-1");
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		if (strBirthYear.equals("-1")){
		args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		}
		else{
			args.putInt("year", Integer.parseInt(strBirthYear));
			args.putInt("month", Integer.parseInt(strBirthMonth));
			args.putInt("day", Integer.parseInt(strBirthDay));
		}
		date.setArguments(args);
		/**
		 * Set Call back to capture selected date
		 */
		date.setCallBack(ondate);
		date.show(getFragmentManager(), "Date Picker");
	}

	OnDateSetListener ondate = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.set(cal1.get(Calendar.YEAR), cal1.MONTH, cal1.DAY_OF_MONTH);
			cal2.set(year, monthOfYear, dayOfMonth);
			long mills1 = cal1.getTimeInMillis();
			long mills2 = cal2.getTimeInMillis();

			long difference = mills1 - mills2;
			long differenceYears = (difference / (24 * 60 * 60 * 1000)) / 365;

			dateOfBirth.setText(String.valueOf(year) + "-"
					+ String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(dayOfMonth));
			Editor editor = pref.edit();
			editor.putString("AGE", differenceYears + " Yrs");
			editor.putString("BIRTH_YEAR", year + "");
			editor.putString("BIRTH_MONTH", monthOfYear + "");
			editor.putString("BIRTH_DAY", dayOfMonth + "");
			editor.commit();
			dateOfBirth.setText(differenceYears + " Yrs");
			dateOfBirth.setTextAppearance(profile, R.style.boldText);
		}
	};

	private void setNavigationDrawer() {
		// get list items from strings.xml
		drawerListViewItems = getResources().getStringArray(R.array.items);
		// get ListView defined in activity_main.xml
		drawerListView = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		drawerListView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_listview_item, drawerListViewItems));

		// 2. App Icon
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// 2.1 create ActionBarDrawerToggle
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, /*
																 * host Activity
																 */
		drawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		);

		// 2.2 Set actionBarDrawerToggle as the DrawerListener
		drawerLayout.setDrawerListener(actionBarDrawerToggle);

		actionBarDrawerToggle.syncState();
		
		// 2.3 enable and show "up" arrow
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// just styling option
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		drawerListView.setOnItemClickListener(new DrawerItemClickListener());
	}

	public class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			Log.d("MAIN", "Drawer Item selection check");
			switch (position) {
			case 0:
				Intent intent1 = new Intent(profile, HomeActivity.class);
				startActivity(intent1);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 1:
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 2:
				Intent intent3 = new Intent(profile, SettingsActivity.class);
				startActivity(intent3);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 3:
				Intent intent4 = new Intent(profile, AboutActivity.class);
				startActivity(intent4);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 4:
				Intent intent2 = new Intent(profile, ContactActivity.class);
				startActivity(intent2);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 5:
				Intent intent5 = new Intent(profile, MainActivity.class);
				startActivity(intent5);
				drawerLayout.closeDrawer(drawerListView);
				break;
			default:
				drawerLayout.closeDrawer(drawerListView);

			}

		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			Log.d("MAIN", "Home Item selected in the menu");
			if (drawerLayout.isDrawerOpen(drawerListView)) {
				drawerLayout.closeDrawer(drawerListView);
				Log.d("MAIN", "Drawer closed now");
			} else {
				drawerLayout.openDrawer(drawerListView);
				Log.d("MAIN", "Drawer opened now");
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
