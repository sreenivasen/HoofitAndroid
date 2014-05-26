package com.example.hoofit;


import android.app.ActionBar;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import com.sreenivasen.hoofit.R;


public class SettingsActivity extends PreferenceActivity {
	protected String[] drawerListViewItems;
	protected DrawerLayout drawerLayout;
	protected ListView drawerListView;
	protected ActionBarDrawerToggle actionBarDrawerToggle;
	protected ActionBar actionBar;
	final SettingsActivity settings = this;

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		addPreferencesFromResource(R.xml.activity_settings);
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			Log.d("MAIN", "Home Item selected in the menu");
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
}

