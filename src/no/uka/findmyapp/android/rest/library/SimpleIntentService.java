package no.uka.findmyapp.android.rest.library;

import java.util.Date;

import no.uka.findmyapp.android.rest.demo.R;
import android.app.IntentService;
import android.content.Intent;
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
		// TODO Auto-generated method stub
    	Log.v("Debug","HandleIntent");
    	
    	//_restProcessor.callRest(model);
    	
    	
    	String userAction = intent.getStringExtra("userAction");

        Log.v(TAG, "" + new Date() + ", In onHandleIntent for userAction = " + userAction + ", thread id = " + Thread.currentThread().getId());

        //if ("1".equals(userAction)) {
            try {
            	Log.v("INFO", "STARTING: " + userAction);
                Thread.sleep(Integer.parseInt(userAction) * 15000);
                Log.v("INFO", "DONE: " + userAction);
            } catch (InterruptedException e) {
                Log.e(TAG, "error", e);
            }

            Log.v(TAG, "" + new Date() + ", This thread is waked up.");
        //}

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
