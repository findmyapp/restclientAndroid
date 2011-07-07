package no.uka.findmyapp.android.rest.library;

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
