package no.uka.findmyapp.android.rest.library;

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
		rm.get("", expectedDataFormat)
	}
}
