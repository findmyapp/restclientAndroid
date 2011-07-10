package no.uka.findmyapp.android.rest.demo.activities;

import java.util.ArrayList;

import no.uka.findmyapp.android.rest.R;
import no.uka.findmyapp.android.rest.demo.adapters.BarAdapter;
import no.uka.findmyapp.android.rest.demo.listeners.ShakeEventListener;
import no.uka.findmyapp.android.rest.library.RestServiceHelper;
import no.uka.findmyapp.android.rest.library.ServiceReferenceFactory;
import no.uka.findmyapp.android.rest.library.data.model.TemperatureMetaData;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TemperatureDemo extends Activity implements OnClickListener {

	private static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	private StringsContentObserver stringsObserver = null;
	private Handler handler = new Handler();
	
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;

	BarAdapter barAdapter;
	ArrayList<Integer> barArrayList;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature_demo);

        Button refreshButton = (Button) this.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(this);
        
        ContentResolver cr = getContentResolver();
        stringsObserver = new StringsContentObserver( handler );
        cr.registerContentObserver(TemperatureMetaData.CONTENT_PROVIDER_URI, true,
				stringsObserver );
        
        mSensorListener = new ShakeEventListener();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener,
            mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI);

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
          public void onShake() {
            Toast.makeText(TemperatureDemo.this, "Shake!", Toast.LENGTH_SHORT).show();
            initializeService();
          }
        });
        barArrayList = new ArrayList<Integer>();
   	 	ListView listView = (ListView) findViewById(R.id.listView1);
		barAdapter = new BarAdapter(this, R.layout.list_bar, barArrayList);
        listView.setAdapter(barAdapter);
    }
    
    public void onClick(View button) { 
    	initializeService();
    }
    
    private void initializeService() {
    	// ServiceReferenceFactory returns a ServiceModelObject
    	serviceHelper.startServiceTest(this, ServiceReferenceFactory.getService(ServiceReferenceFactory.Services.TEMP)); 
    }
    
    private void showBar() {
    	Cursor cursor = getContentResolver().query(TemperatureMetaData.CONTENT_PROVIDER_URI,
    			new String[] { "value" }, null, null, "_id DESC");
    	
    	if (cursor != null && cursor.moveToFirst()) {
    	     String[] calNames = new String[cursor.getCount()];
    	     int[] calIds = new int[cursor.getCount()];
    	     for (int i = 0; i < 1; i++) {
    	          // retrieve the calendar names and ids
    	          // at this stage you can print out the display names to get an idea of what calendars the user has
    	          //calIds[i] = cursor.getInt(0);
    	          //calNames[i] = cursor.getString(1);
    	    	 
    	    	 if(barArrayList.size() > 10) {
    	    		 barArrayList.clear();
    	    	 }
    	    	 Float value =  cursor.getFloat(0);
    	    	 Log.v("SHOWTEXT", value + " value");
    	    	 int iv = (int)(value + 0.5f);
    	    	 //barArrayList.add(iv);
    	    	 barArrayList.add(0, iv);
    	    	 barAdapter.notifyDataSetChanged();
    	    	 
    	    	 //TextView textView = (TextView) findViewById(R.id.textview);
    	         //textView.setText(cursor.getFloat(0) + " value");
    	    	 //ListView listView = (ListView) findViewById(R.id.listView1);

    	         cursor.moveToNext();
    	      }
    	      cursor.close();
    	      if (calIds.length > 0) {
    	           // we're safe here to do any further work
    	      }
    	}
    }
    
    @Override
    protected void onResume() {
      super.onResume();
      mSensorManager.registerListener(mSensorListener,
          mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
          SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
      mSensorManager.unregisterListener(mSensorListener);
      super.onStop();
    }
    
    public class StringsContentObserver extends ContentObserver {
  	  public StringsContentObserver( Handler h ) {
  		super( h );
  	  }

  	  public void onChange(boolean selfChange) {
  		Log.d( "Activity", "StringsContentObserver.onChange( "+selfChange+")" );
  		showBar();
  	  }
  	}
}
