package no.uka.findmyapp.android.rest.library.data.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import no.uka.findmyapp.android.rest.library.data.model.TemperatureMetaData;

public class TemperatureDbHelper extends SQLiteOpenHelper{

	private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TemperatureMetaData.TemperatureTable.TABLE_NAME + " (" 
													+ TemperatureMetaData.TemperatureTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
													+ TemperatureMetaData.TemperatureTable.LOCATION_ID + " INTEGER, " 
													+ TemperatureMetaData.TemperatureTable.VALUE + " DECIMAL,"
													+ TemperatureMetaData.TemperatureTable.DATE + " DATETIME);";
	
	private static final String DROP_TABLE_QUERY = "DROP TABLE " + TemperatureMetaData.TemperatureTable.TABLE_NAME + ";";

	
	public TemperatureDbHelper(Context context) {
		super(context, TemperatureMetaData.DATABASE_NAME, null, TemperatureMetaData.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TemperatureDbHelper.class.getName(), "Database upgrade from version " + oldVersion + " to " + newVersion); 
		db.execSQL(DROP_TABLE_QUERY);
		onCreate(db);
	}	
}