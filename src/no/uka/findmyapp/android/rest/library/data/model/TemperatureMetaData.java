package no.uka.findmyapp.android.rest.library.data.model;

import java.sql.Date;
import android.net.Uri;
import android.provider.BaseColumns;

public class TemperatureMetaData 
{
	private TemperatureMetaData() {}

	public static final String AUTHORITY = "no.uka.findmyapp.android.rest.library.data.providers.TemperatureProvider"; 
	public static final Uri CONTENT_PROVIDER_URI = Uri.parse("content://" + AUTHORITY + "temperatures");
	public static final String DATABASE_NAME = "temperature.db";
	public static final int DATABASE_VERSION = 1; 

	//TODO implement article MIME
	//content type for one item: vnd.android.cursor.item/vnd.your-domain.your-item-name
	public static final String CONTENT_TYPE_TEMPERATURE_ITEM = "vnd.android.cursor.item/vnd.uka.temperature"; 
	
	public class TemperatureTable implements BaseColumns {
		private TemperatureTable() {}
		
		// Table name
		public static final String TABLE_NAME = "temperature_table"; 
		
		// Databasefields
		public static final String ID = "_id";
		public static final String LOCATION_ID = "location_id"; 
		public static final String VALUE = "value";
		public static final String DATE = "date";
	}
}
