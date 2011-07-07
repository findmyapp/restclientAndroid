package no.uka.findmyapp.android.rest.library;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import no.uka.findmyapp.android.rest.R;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SimpleIntentService extends IntentService {

    public static final String TAG = "SimpleIntentService";
    private RestProcessor _restProcessor;

    public SimpleIntentService() {
        super("SimpleIntentService");
        _restProcessor = new RestProcessor();
    }

	@Override
	protected void onHandleIntent(Intent intent) {
    	Log.v("Debug","HandleIntent");
        Log.v(TAG, "" + new Date() + ", In onHandleIntent for thread id = " + Thread.currentThread().getId());

        Bundle bundle = intent.getExtras();
		URI uri = (URI) bundle.get("URI");
		HttpType httpType = (HttpType) bundle.get("HttpType");
		
		Log.v(TAG, "STARTING" + " URI: " + uri + " HttpType: " + httpType);
		
		ServiceRequestWrapper srw = new ServiceRequestWrapper();
		srw.setHttpType(httpType);
		srw.setUri(uri);
		
		_restProcessor.callRest(srw);
		
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
