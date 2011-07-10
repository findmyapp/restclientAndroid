package no.uka.findmyapp.android.rest.library;

import no.uka.findmyapp.android.rest.library.data.model.ServiceModel;
import no.uka.findmyapp.android.rest.library.data.model.Temperature;
import no.uka.findmyapp.android.rest.library.data.wrapper.TemperatureDatabase;
import android.content.ContentResolver;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 */
public class RestProcessor {
	private RestMethod rm;
	private Gson gson;
	public RestProcessor() {
		rm = new RestMethod();
		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();
	}
	
	public void callRest(ServiceModel serviceModel, ContentResolver contentResolver) {
		rm.setUrl(serviceModel.getUri().toString());
		try {
			String resp = "";
			Object temp;
			switch(serviceModel.getHttpType()) {
				case GET :
					 resp = rm.get("", ServiceDataFormat.JSON);
					 temp = (Object) gson.fromJson(resp, serviceModel.getReturnType());
					 Log.v("INFO", resp.toString());
					 Log.v("INFO", temp.toString());

					 TemperatureDatabase td = TemperatureDatabase.getInstance();
					 td.addNewTemperatureSample(contentResolver, (Temperature)temp);
					 
					 td.getLatestTemperature(contentResolver, 1);
					 
				break;
				case POST :
					//TODO
				break;
				case DELETE :
					//TODO
				break;
				case PUT :
					//TODO
				break;
			}
			
			Log.v("INFO", resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
