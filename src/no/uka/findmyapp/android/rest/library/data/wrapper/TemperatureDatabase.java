package no.uka.findmyapp.android.rest.library.data.wrapper;

import java.sql.Date;

import no.uka.findmyapp.android.rest.library.data.model.Temperature;
import no.uka.findmyapp.android.rest.library.data.model.TemperatureMetaData;

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
		
		contentValues.put(TemperatureMetaData.TemperatureTable.LOCATION_ID, tSample.getLocationId());
		contentValues.put(TemperatureMetaData.TemperatureTable.VALUE, tSample.getValue());
		contentValues.put(TemperatureMetaData.TemperatureTable.DATE, tSample.getDate().toLocaleString());
		
		contentResolver.insert(TemperatureMetaData.CONTENT_PROVIDER_URI, contentValues);
	}
	
	public boolean isTemperatureInDB(ContentResolver contentResolver, int locationId, Date date) {
		boolean retVal = false; 
		
		Cursor cursor = contentResolver.query(TemperatureMetaData.CONTENT_PROVIDER_URI, null, 
			TemperatureMetaData.TemperatureTable.DATE + "='" + date.toLocaleString() + 
			"' AND " + TemperatureMetaData.TemperatureTable.LOCATION_ID + "='" + locationId + "'", 
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
		long delete = contentResolver.delete(TemperatureMetaData.CONTENT_PROVIDER_URI, null, null);
		Log.i("Cache refreshed", delete + " records deleted from " + TemperatureMetaData.CONTENT_PROVIDER_URI);
	}
}
