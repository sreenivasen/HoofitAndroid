package com.example.hoofit;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceSensorMonitor extends Service implements SensorEventListener {

	final static String MY_ACTION = "MY_ACTION";
	   private String reading;
	   private SensorManager mgr;
	   static final String LOG_TAG = "SIMPLE SERVICE";
	   Intent intent = new Intent("com.example.ServiceSensorMonitor.MY_ACTION");

	   @Override
	   //public void onStartCommand() {
	   public void onCreate() {
	      Log.d( LOG_TAG, "onStartCommand" );
	      mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
	      Sensor countSensor = mgr.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
	        if (countSensor != null) {
	            mgr.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
	        } else {
	            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
	        }
	   }

	   @Override
	   public void onDestroy() {
	        Log.d( LOG_TAG, "onDestroy" );
	        mgr.unregisterListener(this);       
	        super.onDestroy();
	   }

	@Override
	public IBinder onBind(Intent arg0) {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    // TODO Auto-generated method stub      
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
	      Log.d( LOG_TAG, " sensor changed" );
	 
	      reading= String.valueOf(event.values[0]);

	      //Send back reading to Activity
	      intent.putExtra("measurement", reading);
	      sendBroadcast(intent);        
	}
	
}
