/*
* Copyright (c) 2011 Accenture
* Licensed under the MIT open source license	
* http://www.opensource.org/licenses/mit-license.php
*/

package no.uka.findmyapp.android.rest.library.data.helpers;

import no.uka.findmyapp.android.rest.library.data.providers.SensorProvider;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TemperatureDbHelper extends SQLiteOpenHelper{

	private static SQLiteDatabase db;
	private Context appContext;
	private SQLiteDatabase dbReadable;
	private SQLiteDatabase dbWriteable;


	private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + SensorProvider.TemperatureTable.TABLE_NAME + " (" 
													+ SensorProvider.TemperatureTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
													+ SensorProvider.TemperatureTable.LOCATION_ID + " INTEGER, " 
													+ SensorProvider.TemperatureTable.VALUE + " DECIMAL,"
													+ SensorProvider.TemperatureTable.DATE + " DATETIME);";
	
	private static final String DROP_TABLE_QUERY = "DROP TABLE " + SensorProvider.TemperatureTable.TABLE_NAME + ";";

	
	public TemperatureDbHelper(Context context) {
		super(context, SensorProvider.DATABASE_NAME, null, SensorProvider.DATABASE_VERSION);
		appContext = context;

		//dbReadable = this.getReadableDatabase();
		//dbWriteable = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("INFO", "CREATING TABLE " + db);
		Log.d("INFO", "CREATING TABLE " + db);
		Log.d("INFO", CREATE_TABLE_QUERY);
		db.execSQL(CREATE_TABLE_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TemperatureDbHelper.class.getName(), "Database upgrade from version " + oldVersion + " to " + newVersion); 
		db.execSQL(DROP_TABLE_QUERY);
		onCreate(db);
	}	
	
	public void closeDb() {
	    if (dbReadable != null) { dbReadable.close();}
	    if (dbWriteable != null) { dbWriteable.close();}
	}

	
}