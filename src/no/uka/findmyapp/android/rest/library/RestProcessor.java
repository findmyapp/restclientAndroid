package no.uka.findmyapp.android.rest.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.util.Log;
import android.widget.Toast;
import no.uka.findmyapp.android.rest.R;
import no.uka.findmyapp.android.rest.library.data.model.Temperature;
import no.uka.findmyapp.android.rest.model.BaseModel;
import no.uka.findmyapp.android.rest.model.ServiceModel;

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
	
	public void callRest(ServiceModel serviceModel) {
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
