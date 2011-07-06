package no.uka.findmyapp.android.restclient.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UkaService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// code to execute when the service is first created
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// code to execute when the service is shutting down
	}

	@Override
	public void onStart(Intent intent, int startid) {
		// code to execute when the service is starting up
	}

}
