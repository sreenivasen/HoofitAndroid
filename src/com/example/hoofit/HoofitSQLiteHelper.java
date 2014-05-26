package com.example.hoofit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HoofitSQLiteHelper extends SQLiteOpenHelper {

	private static HoofitSQLiteHelper sInstance;
	
	public static final String TABLE_HOOFIT = "hoofit";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_STEPCOUNT = "step_count";
	public static final String COLUMN_DATE = "step_date";

	public static final String DB_NAME = "hoofit.db";
	public static final int DB_VERSION = 1;

	// create database string
	public static final String CREATE_TABLE_HOOFIT = "create table "
			+ TABLE_HOOFIT + "( " + COLUMN_ID
			+ " integer primary key autoincrement,  " + COLUMN_STEPCOUNT
			+ " integer, " + COLUMN_DATE + " datetime );";

	public static HoofitSQLiteHelper getInstance(Context context) {

	    // Use the application context, which will ensure that you 
	    // don't accidentally leak an Activity's context.
	    // See this article for more information: http://bit.ly/6LRzfx
	    if (sInstance == null) {
	      sInstance = new HoofitSQLiteHelper(context.getApplicationContext());
	    }
	    return sInstance;
	  }
	
	public HoofitSQLiteHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d("SQLite", "CREATING TABLE HOOFIT");
		db.execSQL(CREATE_TABLE_HOOFIT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// do nothing now - 24-APR-2014

	}

}
