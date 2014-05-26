package com.example.hoofit;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.sreenivasen.hoofit.R;

/**
 * 
 *
 * 
 */

public class BoundService extends Service implements
SensorEventListener {

    /*the interface object that receives interactions from clients
    it's given to the client to access the Service's public methods*/
    private final IBinder myBinder = new MyLocalBinder();
    private int NOTIFICATION_ID = 102;
	Date date;
	private String mReading;
	private HoofitSQLiteDataSource mDatasource;

	private SensorManager mSensorMgr;

    private String TAG = "BOUND SERVICE";
	private int REQUEST_CODE = 101;
	// we use the compatibility library
	private static NotificationCompat.Builder sbuilder;

    /**
     * A client is binding to the service with bindService()
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind called...");
        return myBinder;
    }

    /**
     * Called when the service is being created.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mDatasource = new HoofitSQLiteDataSource(getApplicationContext());
        mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		Sensor countSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
		if (countSensor != null) {
			mSensorMgr.registerListener(this, countSensor,
					SensorManager.SENSOR_DELAY_UI);
		} else {
			Log.d(TAG, "Counter sensor not available");
		}
		sendNotification(getNotificationBuilderInstance());
    }

//    called when the service starts from a call to startService()
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started by startService()");
        return START_STICKY;
    }

    private NotificationCompat.Builder getNotificationBuilderInstance(){
    	if (sbuilder == null) {
    		sbuilder = new NotificationCompat.Builder(this);
    	}
    	return sbuilder;
    }
    
    
	/*
	 * sends an ongoing notification notifying that service is running. it's
	 * only dismissed when the service is destroyed
	 */
	private void sendNotification(NotificationCompat.Builder builder) {
		builder.setSmallIcon(R.drawable.hoofit_heart_beat)
				.setContentTitle("Hoofit")
				.setTicker("HOOFIT")
				.setWhen(System.currentTimeMillis())
				.setOngoing(true);
		Intent startIntent = new Intent(this, HomeActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this,
				REQUEST_CODE, startIntent, 0);
		builder.setContentIntent(contentIntent);
		Notification notification = builder.build();
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, notification);
	}
	
	private void updateNotification(int stepCount) {
		String notificationContentText = "Steps walked: " + stepCount;

		Intent startIntent = new Intent(this, HomeActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this,
				REQUEST_CODE, startIntent, 0);
		getNotificationBuilderInstance().setContentText(notificationContentText)
		.setContentIntent(contentIntent)
		.setWhen(System.currentTimeMillis());
		Notification notification = getNotificationBuilderInstance().build();
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, notification);
	}

    /***********the public methods******************>>>>>>>*/


    /*<<<<<<<<<*********the public methods*******************/


//    the class used for the client Binder
    public class MyLocalBinder extends Binder {
        BoundService getService() {
           /* return this instance of the BoundService
            so the client can access the public methods*/
            return BoundService.this;
        }
    }

    /**
     * Called when The service is no longer used and is being destroyed
     */
    @Override
    public void onDestroy() {
//        release player and thread
        Log.d(TAG, "Destroying Service");
        Log.d(TAG, "Unregistering sensor listener");
        mSensorMgr.unregisterListener(this);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d(TAG, "Cancelling notification");
        notificationManager.cancel(NOTIFICATION_ID);
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss",java.util.Locale.getDefault());
		Log.d(TAG, " sensor changed");

		mReading = String.valueOf(event.values[0]);

        double d = Double.parseDouble(mReading);
        int stepCount = (int) d;
                
        updateNotification(stepCount);
        
        
        mDatasource.openDatabaseToWrite();
        mDatasource.insertIntoHoofit(stepCount, formatter.format(date));
        mDatasource.closeDatabaseConnection();
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
}
