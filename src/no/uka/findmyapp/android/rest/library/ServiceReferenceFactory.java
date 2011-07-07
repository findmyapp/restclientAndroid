package no.uka.findmyapp.android.rest.library;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.reflect.TypeToken;

import no.uka.findmyapp.android.rest.library.data.model.Temperature;
import no.uka.findmyapp.android.rest.model.ServiceModel;

public class ServiceReferenceFactory {
	public enum Services {TEMP, XX}
	public static ServiceModel getService(Services service) {
		switch(service) {
			case TEMP :
				try {
					Type typeToken = new TypeToken<Temperature>(){}.getType();

					return new ServiceModel(new URI(String.format(ServicesConstants.SERVICE1_URI, "1")), HttpType.GET, typeToken);
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
