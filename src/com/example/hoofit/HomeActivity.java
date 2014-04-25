package com.example.hoofit;



import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.*;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity implements SensorEventListener {

	protected String[] drawerListViewItems;
	protected DrawerLayout drawerLayout;
	protected ListView drawerListView;
	protected ActionBarDrawerToggle actionBarDrawerToggle;
	protected ActionBar actionBar;
	final HomeActivity home = this;
	private SensorManager sensorManager;
	boolean activityRunning;
	TextView historyText;
	
	MyReceiver myReceiver = null;
	Intent i;
	
	private HoofitSQLiteDataSource datasource;
	Date date;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.navigation_drawer);
		setNavigationDrawer();
		
    	datasource = new HoofitSQLiteDataSource(this);
		
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		historyText = (TextView) findViewById(R.id.historyText);
		
		i= new Intent(this, ServiceSensorMonitor.class);
	    Log.d( "HOME ACTIVITY", "onCreate/startService" );
	}

	@Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
        
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();      
        intentFilter.addAction(ServiceSensorMonitor.MY_ACTION);
        startService(i);  
        registerReceiver(myReceiver, intentFilter);

    }
	
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
				
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 1:
				Intent intent1 = new Intent(home, ProfileActivity.class);
				startActivity(intent1);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 2:
				Intent intent3 = new Intent(home, SettingsActivity.class);
				startActivity(intent3);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 3:
				Intent intent4 = new Intent(home, AboutActivity.class);
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				startActivity(intent4);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 4:
				Intent intent2 = new Intent(home, ContactActivity.class);
				startActivity(intent2);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 5:
				Intent intent5 = new Intent(home, MainActivity.class);
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
	
	@Override
    protected void onPause() {
        super.onPause();
//        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
//        sensorManager.unregisterListener(this); 
    }

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		Log.d( "HOME ACTIVITY", " sensor event change detected" );
		datasource.open();
    	date = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (activityRunning) {
            historyText.setText(String.valueOf(event.values[0]) + " Steps Walked" + "\n SQLite: " + datasource.getStepCountToday(formatter.format(date)));
            Log.d("HOME ACTIVITY", "fetching value from SQLite: " + datasource.getStepCountToday(formatter.format(date)));
        }
		
	}
	


}


