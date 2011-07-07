package no.uka.findmyapp.android.rest.library;

import no.uka.findmyapp.android.rest.demo.AndroidRestClientDemoActivity;
import android.content.Context;
import android.content.Intent;

/**
 * The rest service helper class, a singleton 
 * which exposes a simple asynchronous
 * API to be used by the user interface.
 * 
 * Responsibility
 * 	Prepare and send the Service request:
 * 		- Check if the method is already running
 * 		- Create the requested Intent
 * 		- Add the operation type and a unique request id
 * 		- Add the method specific parameters
 * 		- Add the binder callback
 * 		- Call {@link RestService#startService()}
 * 		- Return the request id
 *  Handle the callback from the service
 *  	- Dispatch callbacks to the user interface listeners
 */
public class RestServiceHelper {
	
	/**
	 * The singleton RestServiceHelper instance
	 */
	private static RestServiceHelper INSTANCE; 

	
	
	private RestServiceHelper() {
	}
	
	public static RestServiceHelper getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new RestServiceHelper();
			return INSTANCE; 
		}
		return INSTANCE;
	}
	
	public void startServiceTest(Context context) {
		context.startService(new Intent(context, RestService.class));
	}
}
