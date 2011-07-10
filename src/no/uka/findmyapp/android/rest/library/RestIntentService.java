package no.uka.findmyapp.android.rest.library;

import java.util.Date;

import no.uka.findmyapp.android.rest.R;
import no.uka.findmyapp.android.rest.library.data.model.ServiceModel;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * The RestService is able to recieve Intents sent
 * from the {@link RestServiceHelper} and starts
 * the corresponding {@link RestMethod}
 * 
 * Responsibility: 
 * 	- Ensure that the restmethods are called
 *	- Handle the {@link RestProcessor} callback, and 
 *	  invoke {@link RestServiceHelper} binder callback.
 *	- Implement queue of request tasks.
 */

public class RestIntentService extends IntentService {

    public static final String TAG = "SimpleIntentService";
    private RestProcessor _restProcessor;

    public RestIntentService() {
        super("SimpleIntentService");
        _restProcessor = new RestProcessor();
    }

	@Override
	protected void onHandleIntent(Intent intent) {
    	Log.v("Debug","HandleIntent");
        Log.v(TAG, "" + new Date() + ", In onHandleIntent for thread id = " + Thread.currentThread().getId());

        Bundle bundle = intent.getExtras();
        ServiceModel serviceModel = (ServiceModel) bundle.get("ServiceModel");
		
		Log.v(TAG, "STARTING" + " URI: " + serviceModel);
		
		//ServiceRequestWrapper srw = new ServiceRequestWrapper();
		//srw.setHttpType(serviceModel.getHttpType());
		//srw.setUri(serviceModel.getUri());
		
		_restProcessor.callRest(serviceModel, this.getContentResolver());
		
		Log.v(TAG, "DONE HandleIntent");
        Log.v(TAG, "" + new Date() + ", This thread is waked up.");
	}
	@Override
    public void onCreate() {
    	super.onCreate();
    	Log.v("Debug:","Service started");
    }
	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	super.onStartCommand(intent, flags, startId);
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
    	super.onDestroy();

        // Tell the user we stopped.
        Toast.makeText(this, R.string.rest_service_stopped, Toast.LENGTH_SHORT).show();
    }
}
