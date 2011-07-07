package no.uka.findmyapp.android.rest.library;

import no.uka.findmyapp.android.rest.library.data.helpers.TemperatureDbHelper;
import no.uka.findmyapp.android.rest.library.data.model.Temperature;
import android.content.ContentProvider;
import android.database.SQLException;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

/**
 * 
 */
public class RestProcessor {
	private String url; 
	private Object obj; 
	private ContentProvider contentProvider; 
	
	public RestProcessor(String url, Object object, ContentProvider contentProvider) {
		this.url = url; 
		this.obj = object; 
		this.contentProvider = contentProvider;
	}
	
	private void startProcessing() {
	}
	
	private void setFlags(int temperature)throws SQLException, java.sql.SQLException {

	}

	
	
}
