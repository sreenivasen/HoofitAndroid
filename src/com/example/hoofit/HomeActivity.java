package com.example.hoofit;

import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.sreenivasen.hoofit.R;

public class HomeActivity extends Activity{

	protected String[] drawerListViewItems;
	protected DrawerLayout drawerLayout;
	protected ListView drawerListView;
	protected ActionBarDrawerToggle actionBarDrawerToggle;
	protected ActionBar actionBar;
	final HomeActivity home = this;
	boolean activityRunning;
	TextView historyText;

	private BoundService serviceReference;
	private boolean isBound;

	Date date;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.navigation_drawer);
		setNavigationDrawer();

		Log.d("HOME ACTIVITY", "creating service intent");
		Intent serviceIntent = new Intent(this, BoundService.class);
		startService(serviceIntent);
//		sendNotification();
		Log.d("HOME ACTIVITY", "onCreate - startService");
		
		
		ImageView historyIcon = (ImageView) findViewById(R.id.historyImage);
		ImageView settingsIcon = (ImageView) findViewById(R.id.settingsImage);
		ImageView infoIcon = (ImageView) findViewById(R.id.infoImage);
		
//		stepsCountText = (TextView) findViewById(R.id.stepsCountText);
		
		historyIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("HOME", "history icon selected");
				Intent intent = new Intent(home, BarChartActivity.class);
				startActivity(intent);

			}

		});
		
		settingsIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("HOME", "settings icon selected");
				Intent intent = new Intent(home, SettingsActivity.class);
				startActivity(intent);

			}

		});
		
		infoIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("HOME", "history icon selected");
				Intent intent = new Intent(home, AboutActivity.class);
				startActivity(intent);

			}

		});

	}

	// interface for monitoring the state of the service
	private ServiceConnection myConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// called when the connection with the service has been
			// established. gives us the service object to use so we can
			// interact with the service.we have bound to a explicit
			// service that we know is running in our own process, so we can
			// cast its IBinder to a concrete class and directly access it.
			Log.d("HOME ACTIVITY", "Bound service connected");
			serviceReference = ((BoundService.MyLocalBinder) service)
					.getService();
			isBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// called when the connection with the service has been
			// unexpectedly disconnected -- its process crashed.
			// Because it is running in our same process, we should never
			// see this happen.
			Log.d("HOME ACTIVITY", "Problem: bound service disconnected");
			serviceReference = null;
			isBound = false;
		}
	};

	// unbind from the service
	private void doUnbindService() {
		Log.d("HOME ACTIVITY", "Service Unbinding");
		unbindService(myConnection);
		isBound = false;
	}

	// bind to the service
	private void doBindToService() {
		Log.d("HOME ACTIVITY", "Service Binding");
		if (!isBound) {
			Intent bindIntent = new Intent(this, BoundService.class);
			isBound = bindService(bindIntent, myConnection,
					Context.BIND_AUTO_CREATE);
		}
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
			case 6:
				Intent intent6 = new Intent(home, BarChartActivity.class);
				startActivity(intent6);
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

	// activity starting
	@Override
	protected void onStart() {
		super.onStart();
		Log.d("HOME ACTIVITY", "MainActivity - onStart");
		// bind to the service
		doBindToService();
	}

	// activity stopping
	@Override
	protected void onStop() {
		Log.d("HOME ACTIVITY", "MainActivity - onStop");
		super.onStop();
		// unbind from the service
		doUnbindService();
	}

	@Override
	protected void onPause() {
		Log.d("HOME ACTIVITY", "MainActivity - onPause");
		super.onPause();
		// activityRunning = false;
		// if you unregister the last listener, the hardware will stop detecting
		// step events
		// sensorManager.unregisterListener(this);
	}
	
	@Override
	protected void onResume() {
		Log.d("HOME ACTIVITY", "MainActivity - onResume");
		super.onResume();
		// activityRunning = false;
		// if you unregister the last listener, the hardware will stop detecting
		// step events
		// sensorManager.unregisterListener(this);
	}
	
	@Override
	protected void onRestart() {
		Log.d("HOME ACTIVITY", "MainActivity - onRestart");
		super.onRestart();
		// activityRunning = false;
		// if you unregister the last listener, the hardware will stop detecting
		// step events
		// sensorManager.unregisterListener(this);
	}
	
//  the activity is being destroyed
  @Override
  protected void onDestroy() {
      super.onDestroy();
      Log.d("HOME ACTIVITY", "Destroying activity...");
     /* it's not just being destroyed to rebuild due to orientation
      change but genuinely being destroyed...for ever*/
      if (isFinishing()) {
          Log.d("HOME ACTIVITY", "activity is finishing");
//          stop service as activity being destroyed and we won't use it any more
          Intent intentStopService = new Intent(this, BoundService.class);
          stopService(intentStopService);
      }
  }


}
