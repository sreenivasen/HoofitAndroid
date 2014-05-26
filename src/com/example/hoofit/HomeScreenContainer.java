package com.example.hoofit;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.sreenivasen.hoofit.R;

public class HomeScreenContainer extends FragmentActivity {

	static ViewPager hoofitViewPager;
	TabsAdapter hoofitTabAdapters;
	android.app.ActionBar bar;
	protected String[] drawerListViewItems;
	protected DrawerLayout drawerLayout;
	protected ListView drawerListView;
	protected ActionBarDrawerToggle actionBarDrawerToggle;
	protected ActionBar actionBar;
	final HomeScreenContainer homescreen = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_important);
		setNavigationDrawer();
		 hoofitViewPager = new ViewPager(this);
		 hoofitViewPager.setId(R.id.hoofitPager);
		
		 bar = getActionBar();
		 bar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);
		 hoofitTabAdapters = new TabsAdapter(this, hoofitViewPager);
		
		 hoofitTabAdapters.addTab(bar.newTab().setText("Home"),
		 FragmentTabHome.class, null);
		 hoofitTabAdapters.addTab(bar.newTab().setText("History"),
		 FragmentTabHistory.class, null);

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
		
		actionBarDrawerToggle.syncState();

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

	public class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			Log.d("MAIN", "Drawer Item selection check");
			switch (position) {
			case 0:
				Intent intent1 = new Intent(homescreen, HomeActivity.class);
				startActivity(intent1);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 1:
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 2:
				Intent intent3 = new Intent(homescreen, SettingsActivity.class);
				startActivity(intent3);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 3:
				Intent intent4 = new Intent(homescreen, AboutActivity.class);
				startActivity(intent4);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 4:
				Intent intent2 = new Intent(homescreen, ContactActivity.class);
				startActivity(intent2);
				drawerLayout.closeDrawer(drawerListView);
				break;
			case 5:
				Intent intent5 = new Intent(homescreen, MainActivity.class);
				startActivity(intent5);
				drawerLayout.closeDrawer(drawerListView);
				break;
			default:
				drawerLayout.closeDrawer(drawerListView);

			}

		}
	}

}
