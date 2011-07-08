package no.uka.findmyapp.android.rest.library.data.providers;

import java.sql.SQLException;
import java.util.HashMap;

import no.uka.findmyapp.android.rest.library.data.helpers.TemperatureDbHelper;
import no.uka.findmyapp.android.rest.library.data.model.TemperatureMetaData;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class TemperatureProvider extends ContentProvider{
	private TemperatureDbHelper dbHelper; 
	private static final int TEMPERATURE = 1; 
	
	private static final UriMatcher uriMatcher; 
	
	private static final HashMap<String, String> temperatureProjectionMap; 
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(TemperatureMetaData.AUTHORITY, TemperatureMetaData.TemperatureTable.TABLE_NAME, TEMPERATURE);
		
		temperatureProjectionMap = new HashMap<String, String>(); 
		temperatureProjectionMap.put(
			TemperatureMetaData.TemperatureTable.ID, TemperatureMetaData.TemperatureTable.ID
		);
		temperatureProjectionMap.put(
			TemperatureMetaData.TemperatureTable.LOCATION_ID, TemperatureMetaData.TemperatureTable.LOCATION_ID
		);
		temperatureProjectionMap.put(
			TemperatureMetaData.TemperatureTable.VALUE, TemperatureMetaData.TemperatureTable.VALUE
		);
		temperatureProjectionMap.put(
			TemperatureMetaData.TemperatureTable.DATE, TemperatureMetaData.TemperatureTable.DATE
		);
		
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(); 
		
		int count = 0; 
		switch (uriMatcher.match(uri)) {
		case TEMPERATURE:
			count = db.delete(TemperatureMetaData.TemperatureTable.TABLE_NAME, selection, selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri); 
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		Log.v("DEBUG", "URI " + uri);
		switch (uriMatcher.match(uri)) {
		case TEMPERATURE:
			Log.v("DEBUG", "INSIDE");
			return TemperatureMetaData.CONTENT_TYPE_TEMPERATURE_ITEM;
		default:
			Log.v("DEBUG", "DEFAULT");
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initValues) {
		Log.v("DEBUG", "URI - INSERT " + uri);
		if(uriMatcher.match(uri) != TEMPERATURE) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		Log.v("DEBUG", "URI - INSERT AFTER" + uri);
		
		ContentValues values; 
		if(initValues != null) {
			values = new ContentValues(initValues); 
		}
		else {
			values = new ContentValues(); 
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase(); 
        Log.v("INFO", "DB: " +db.toString());
		
		
		/* The second insert() parameter is a nullColumnHack, 
		 * somewhat a crappy solution that is used to avoid 
		 * that queries like "INSERT INTO tablename;" isn't 
		 * declared illegal. Instead it automatically creates
		 * a statement like "INSERT INTO temperature_table
		 * (location_id) VALUES (NULL)" in this case.
		 */
		long rowId = db.insert(TemperatureMetaData.TemperatureTable.TABLE_NAME, TemperatureMetaData.TemperatureTable.LOCATION_ID, values);
		if(rowId > 0) {
			Uri temperatureUri = ContentUris.withAppendedId(TemperatureMetaData.CONTENT_PROVIDER_URI, rowId);
			getContext().getContentResolver().notifyChange(uri, null);
			return temperatureUri;
		}
		throw new IllegalArgumentException("InsertUnknown URI: " + uri);
	}

	@Override
	public boolean onCreate() {
		Log.v("TP", "CREATED");
		dbHelper = new TemperatureDbHelper(getContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		Log.v("TP", "CREATED" + db);
		boolean b = (dbHelper == null) ? false : true;

		Log.v("TP", "CREATED" + b);
		return b;
	}
	
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case TEMPERATURE:
                qb.setTables(TemperatureMetaData.TemperatureTable.TABLE_NAME);
                qb.setProjectionMap(temperatureProjectionMap);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Log.v("INFO", "DB: " +db.toString());
        Log.v("INFO", "URI: " +uri);
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (uriMatcher.match(uri)) {
            case TEMPERATURE:
                count = db.update(TemperatureMetaData.TemperatureTable.TABLE_NAME, values, where, whereArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}