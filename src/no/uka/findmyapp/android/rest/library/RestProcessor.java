package no.uka.findmyapp.android.rest.library;

import android.util.Log;
import android.widget.Toast;
import no.uka.findmyapp.android.rest.R;
import no.uka.findmyapp.android.rest.model.BaseModel;

/**
 * 
 */
public class RestProcessor {
	private RestMethod rm;
	public RestProcessor() {
		rm = new RestMethod();
	}
	
	public void callRest(ServiceRequestWrapper srw) {
		rm.setUrl(srw.getUri().toString());
		try {
			String resp = "";
			switch(srw.getHttpType()) {
				case GET :
					 resp = rm.get("", ServiceDataFormat.JSON);
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
