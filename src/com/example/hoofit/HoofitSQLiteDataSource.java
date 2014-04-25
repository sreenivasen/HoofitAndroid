package com.example.hoofit;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HoofitSQLiteDataSource {
	
	private SQLiteDatabase database;
	private HoofitSQLiteHelper dbhelper;
	private AssetManager assetManager = null;
	
	public HoofitSQLiteDataSource(Context context){
		dbhelper = new HoofitSQLiteHelper(context);
		assetManager = context.getAssets();
	}
	
	public void open() throws SQLException{
		database = dbhelper.getWritableDatabase();
	}
	
	public void close(){
		dbhelper.close();
	}
	
	public boolean insertIntoHoofit(int count, String date){
		Log.d("SQLite INSERT", "count: " + count + "date: " + date);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String selectQuery = "SELECT * FROM hoofit ORDER BY step_date DESC";
		Cursor cursor = db.rawQuery(selectQuery, null);
		String recentDate = "";
		int recentStepCount = 0;
		int id = 0;
		if(cursor.moveToFirst()){
			if (cursor.getString(cursor.getColumnIndex("step_date")) == null){
				Log.d("SQLite INSERT", "NULL VALUE FOUND for last session");
				recentDate = "";
				recentStepCount = 0;
			}
			else{
				Log.d("SQLite INSERT", "NOT NULL VALUE FOUND for last session");
				recentDate = cursor.getString(cursor.getColumnIndex("step_date"));
				recentStepCount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("step_count")));
				id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
			}
			cursor.close();
			
		}
		else {
			recentDate = "";
			recentStepCount = 0;
			id = 0;
		}
		
		Log.d("RECENT SQLite Values: ", "date: " + recentDate + " step count: " + recentStepCount);
		
		ContentValues values = new ContentValues();
		
		if (date.equals(recentDate)){
			values.put(HoofitSQLiteHelper.COLUMN_DATE, date);
			values.put(HoofitSQLiteHelper.COLUMN_STEPCOUNT, count);
			db.update(HoofitSQLiteHelper.TABLE_HOOFIT, values, HoofitSQLiteHelper.COLUMN_ID + "=" + id, null);
		}
		else {
			values.put(HoofitSQLiteHelper.COLUMN_DATE, date);
			values.put(HoofitSQLiteHelper.COLUMN_STEPCOUNT, count - recentStepCount);
			long insertId = database.insert(HoofitSQLiteHelper.TABLE_HOOFIT,null, values);
		}
		
		return true;
	}
	
	public int getStepCountToday(String date){
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String selectQuery = "SELECT step_count FROM hoofit WHERE step_date = " + "'" + date + "'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		int stepCount = 0;
		if(cursor.moveToFirst()){
			if (cursor.getString(cursor.getColumnIndex("step_count")) == null){
				Log.d("SQLite DATA SOURCE", "NULL VALUE FOUND for last session");
				stepCount = 0;
			}
			else{
				Log.d("SQLite DATA SOURCE", "NOT NULL VALUE FOUND for last session");
				stepCount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("step_count")));
			}
			cursor.close();
			
		}
		else {
			stepCount = 0;
		}	
		return stepCount;
	}

}
