package no.uka.findmyapp.android.rest.library.data.wrapper;

import java.sql.Date;

import no.uka.findmyapp.android.rest.library.data.model.Temperature;
import no.uka.findmyapp.android.rest.library.data.providers.SensorProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class TemperatureDatabase {
	
	private static TemperatureDatabase INSTANCE; 

	private TemperatureDatabase() {}
	
	public static TemperatureDatabase getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new TemperatureDatabase();
			return INSTANCE; 
		}
		return INSTANCE;
	}
	
	public void addNewTemperatureSample(ContentResolver contentResolver, Temperature tSample) {
		ContentValues contentValues = new ContentValues(); 
		//DateFormat df = new SimpleDateFormat();
		contentValues.put(SensorProvider.TemperatureTable.LOCATION_ID, tSample.getLocationId());
		contentValues.put(SensorProvider.TemperatureTable.VALUE, tSample.getValue());
		//contentValues.put(TemperatureMetaData.TemperatureTable.DATE, "2011-10-10 13:13:13.213");
		
		contentResolver.insert(SensorProvider.CONTENT_PROVIDER_URI, contentValues);
	}
	
	public void getLatestTemperature(ContentResolver contentResolver, int locationId) {
		ContentValues contentValues = new ContentValues(); 
		Cursor cursor = contentResolver.query(SensorProvider.CONTENT_PROVIDER_URI, null, 
				SensorProvider.TemperatureTable.LOCATION_ID + "='" + locationId + "'", 
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			cursor.getLong(cursor.getColumnIndex(SensorProvider.TemperatureTable.VALUE));
			Log.v("INFO", cursor.getFloat(cursor.getColumnIndex(SensorProvider.TemperatureTable.VALUE)) + "");
        }
	}
	
	public boolean isTemperatureInDB(ContentResolver contentResolver, int locationId, Date date) {
		boolean retVal = false; 
		
		Cursor cursor = contentResolver.query(SensorProvider.CONTENT_PROVIDER_URI, null, 
			SensorProvider.TemperatureTable.DATE + "='" + date.toLocaleString() + 
			"' AND " + SensorProvider.TemperatureTable.LOCATION_ID + "='" + locationId + "'", 
			null, null);
		
		if(null != cursor && cursor.moveToNext()) {
			retVal = true; 
		}
		cursor.close(); 
		
		return retVal; 
	}
	
	/** 
	 * Cleans the database, deletes all entries.
	 * 
	 * @param contentResolver
	 */
	public void refreshCache(ContentResolver contentResolver) {
		long delete = contentResolver.delete(SensorProvider.CONTENT_PROVIDER_URI, null, null);
		Log.i("Cache refreshed", delete + " records deleted from " + SensorProvider.CONTENT_PROVIDER_URI);
	}
}
