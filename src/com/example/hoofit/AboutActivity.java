package com.example.hoofit;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
import com.sreenivasen.hoofit.R;


public class AboutActivity extends Activity {
	protected String[] drawerListViewItems;
	protected DrawerLayout drawerLayout;
	protected ListView drawerListView;
	protected ActionBarDrawerToggle actionBarDrawerToggle;
	protected ActionBar actionBar;
	final AboutActivity about = this;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.app_about);
		setNavigationDrawer();
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

		// 2.3 enable and show "up" arrow
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		actionBarDrawerToggle.syncState();
		
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
				Intent intent1 = new Intent(about, HomeActivity.class);
				startActivity(intent1);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 1:
				Intent intent2 = new Intent(about, ProfileActivity.class);
				startActivity(intent2);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 2:
				Intent intent3 = new Intent(about, SettingsActivity.class);
				startActivity(intent3);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 3:
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 4:
				Intent intent4 = new Intent(about, ContactActivity.class);
				startActivity(intent4);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 5:
				Intent intent5 = new Intent(about, MainActivity.class);
				startActivity(intent5);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 6:
				Intent intent6 = new Intent(about, BarChartActivity.class);
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
}

