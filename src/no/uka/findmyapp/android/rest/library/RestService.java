package no.uka.findmyapp.android.rest.library;

import no.uka.findmyapp.android.rest.demo.AndroidRestClientDemoActivity;
import no.uka.findmyapp.android.rest.demo.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
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

public class RestService extends Service {
	
    private NotificationManager mNM;

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.rest_service_started;

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        RestService getService() {
            return RestService.this;
        }
    }

    @Override
    public void onCreate() {
    	super.onCreate();
        //mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // Display a notification about us starting.  We put an icon in the status bar.
        //showNotification();
    	Log.v("Debug:","Service started");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

    	super.onDestroy();
        // Cancel the persistent notification.
        mNM.cancel(NOTIFICATION);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.rest_service_stopped, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        CharSequence text = getText(R.string.rest_service_started);
        Notification notification = new Notification(R.drawable.icon, text, System.currentTimeMillis());   

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }
}
