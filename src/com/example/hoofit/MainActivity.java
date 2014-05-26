package com.example.hoofit;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.ExpandableListView;
import com.sreenivasen.hoofit.R;

public class MainActivity extends ExpandableListActivity {

	private ArrayList<String> parentItems = new ArrayList<String>();
	private ArrayList<Object> childItems = new ArrayList<Object>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SensorManager mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		List<Sensor> sensors = mgr.getSensorList(Sensor.TYPE_ALL);
		for (Sensor sensor : sensors) {
			parentItems.add(sensor.getName().toString());
			setChildData(sensor);
		}

		ExpandableListView expandableList = getExpandableListView();

		expandableList.setDividerHeight(25);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);

		ExpandableAdapter adapter = new ExpandableAdapter(parentItems,childItems);

		adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),this);
		expandableList.setAdapter(adapter);
		expandableList.setOnChildClickListener(this);
		
		ActionBar bar = getActionBar();
		//bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFA201")));
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFACD341")));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void setChildData(Sensor sensor) {

		// Details of sensors
		StringBuilder sensorDetails = new StringBuilder();
		ArrayList<String> child = new ArrayList<String>();
		
		sensorDetails.append("\nVendor: " + sensor.getVendor().toString() + "\n\n");
		sensorDetails.append("Power used (mA): " + sensor.getPower() + "\n\n");
		sensorDetails.append("Version: " + sensor.getVersion() + "\n\n");
		sensorDetails.append("Type: " + sensor.getType() + "\n\n");
		sensorDetails.append("Max Range: " + sensor.getMaximumRange() + "\n\n");
		sensorDetails.append("Resolution: " + sensor.getResolution() + "\n");
		
		child.add(sensorDetails.toString());
		
		childItems.add(child);

	}

}
