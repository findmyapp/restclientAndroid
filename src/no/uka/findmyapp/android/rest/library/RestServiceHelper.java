package no.uka.findmyapp.android.rest.library;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;

import no.uka.findmyapp.android.rest.library.data.model.ServiceModel;
import no.uka.findmyapp.android.rest.library.data.model.Temperature;
import no.uka.findmyapp.android.rest.library.data.providers.TemperatureProvider;
import android.content.Context;
import android.content.Intent;

import com.google.gson.reflect.TypeToken;

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
	
	public static enum Services {TEMP, XX}
	
	private RestServiceHelper() {
	}
	
	public static RestServiceHelper getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new RestServiceHelper();
			return INSTANCE; 
		}
		return INSTANCE;
	}
	
	public void startServiceTest(Context context, ServiceModel serviceModel) {
		Intent selectIntent = new Intent(context, RestIntentService.class);
		selectIntent.putExtra("ServiceModel", serviceModel);
        context.startService(selectIntent);
	}
	
	public class ServiceReferenceFactory {
		public ServiceModel getService(Services service) {
			switch(service) {
				case TEMP :
					try {
						Type typeToken = new TypeToken<Temperature>(){}.getType();

						String uri = ServicesConstants.SERVICE1_URI; 
						
						return new ServiceModel(
								new URI(String.format(ServicesConstants.SERVICE1_URI, "1")), 
								HttpType.GET, 
								ServiceDataFormat.JSON,
								typeToken, 
								TemperatureProvider.CONTENT_PROVIDER_URI);
						
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case XX :
					break;
			}
			return null;
		}
	}

}
