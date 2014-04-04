package com.example.hoofit;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {
	protected String[] drawerListViewItems;
	protected DrawerLayout drawerLayout;
	protected ListView drawerListView;
	protected ActionBarDrawerToggle actionBarDrawerToggle;
	protected ActionBar actionBar;
	final ProfileActivity profile = this;
	TextView dateOfBirth, height, weight;
//	final NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberpicker);
//	final NumberPicker fieldPicker = (NumberPicker) findViewById(R.id.fieldpicker);
//	final NumberPicker unitPicker = (NumberPicker) findViewById(R.id.unitpicker);

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_profile);
		setNavigationDrawer();

		dateOfBirth = (TextView) findViewById(R.id.ageCalculator);
		
		NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberpicker);
		NumberPicker fieldPicker = (NumberPicker) findViewById(R.id.fieldpicker);
		NumberPicker unitPicker = (NumberPicker) findViewById(R.id.unitpicker);
		
		unitPicker.setMinValue(0);
		unitPicker.setMaxValue(2);
		unitPicker.setDisplayedValues(new String[]{"Yrs", "cms","kg"});
		
		
		fieldPicker.setMinValue(0);
		fieldPicker.setMaxValue(2);
		fieldPicker.setWrapSelectorWheel(true);
		fieldPicker.setDisplayedValues(new String[]{"Age", "Height", "Weight"});
		fieldPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				// TODO Auto-generated method stub
				Log.d("fieldPicker", "old value: " + oldVal + "new value: " + newVal);
				
			}
			
		});
		
		numberPicker.setMaxValue(80);
		numberPicker.setMinValue(18);
		numberPicker.setWrapSelectorWheel(true);
		numberPicker
				.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
					@Override
					public void onValueChange(NumberPicker picker, int oldVal,
							int newVal) {
						dateOfBirth.setText(newVal + " Yrs");
						dateOfBirth
								.setTextAppearance(profile, R.style.boldText);
					}
				});
		height = (TextView) findViewById(R.id.height);
		weight = (TextView) findViewById(R.id.weight);
		height.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ProfileActivity.this, "Still working on this",
						Toast.LENGTH_LONG).show();

			}

		});

		weight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ProfileActivity.this, "Still working on this",
						Toast.LENGTH_LONG).show();

			}

		});

		dateOfBirth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePicker();
			}
		});

	}

	private void showDatePicker() {
		DateOfBirthPickerFragment date = new DateOfBirthPickerFragment();
		/**
		 * Set Up Current Date Into dialog
		 */
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
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

			// Toast.makeText(
			// ProfileActivity.this,
			// String.valueOf(year) + "-" + String.valueOf(monthOfYear)
			// + "-" + String.valueOf(dayOfMonth),
			// Toast.LENGTH_LONG).show();
			dateOfBirth.setText(String.valueOf(year) + "-"
					+ String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(dayOfMonth));
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
