package com.example.hoofit;

import java.text.SimpleDateFormat;
import java.util.Date;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver{
	
	private HoofitSQLiteDataSource datasource; 
	Date date;
	
    @Override
    public void onReceive(Context context, Intent arg1){
    	datasource = new HoofitSQLiteDataSource(context);
    	datasource.open();
    	date = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String measurement = arg1.getStringExtra("measurement");      
        Log.d( "SIMPLE RECEIVER ", measurement );
        double d = Double.parseDouble(measurement);
        int stepCount = (int) d;
        Log.d("SIMPLE RECEIVER", "after update: " + stepCount);
        datasource.insertIntoHoofit(stepCount, formatter.format(date));
        datasource.close();
    }

}