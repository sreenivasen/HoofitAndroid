package com.example.hoofit;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.sreenivasen.hoofit.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class HistoryActivity extends Activity {

	private HoofitSQLiteDataSource datasource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		datasource = new HoofitSQLiteDataSource(this);
		datasource.openDatabaseToWrite();
	
		// draw sin curve
//		int num = 150;
		int num = datasource.getTotalNumberOfEntries();
		Log.d("HISTORY", "total number of entries: " + num);
		String stepCounts = datasource.getAllStepCounts();
		String[] steps = stepCounts.split(" ");
		GraphViewData[] data = new GraphViewData[num];
		for (int i=0; i<num; i++) {
			Log.d("HISTORY", i + " : " + steps[i]);
		  data[i] = new GraphViewData(i, Double.parseDouble(steps[i]));
		}
		GraphView graphView = new BarGraphView(
		    this
		    , ""
		);
		// add data
		graphView.addSeries(new GraphViewSeries(data));
		// set view port, start=0, size=5
		graphView.setViewPort(0, 10);
		graphView.setScrollable(true);
		// optional - activate scaling / zooming
		graphView.setScalable(true);
    

    LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
    layout.addView(graphView);


	}

}
