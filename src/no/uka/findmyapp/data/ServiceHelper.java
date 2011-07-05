package no.uka.findmyapp.data;

import android.content.Intent;

public class ServiceHelper {
	/**
	 * A handle to the unique Singleton instance.
	 */
	static private ServiceHelper _instance = null;

	/**
	 * @return The unique instance of this class.
	 */
	static public ServiceHelper instance() {
		if (null == _instance) {
			_instance = new ServiceHelper();
		}
		return _instance;
	}
	
	public void testService() {
		/*
		Intent myIntent = new Intent(getApplicationContext(), ServiceTemplate.class);
		myIntent.putExtra("extraData", "somedata");
		startService(myIntent);
		*/
	}
}
