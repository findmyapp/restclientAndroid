package no.uka.findmyapp.android.rest.demo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import no.uka.findmyapp.android.rest.R;
import no.uka.findmyapp.android.rest.demo.adapters.BarAdapter;
import no.uka.findmyapp.android.rest.demo.listeners.ShakeEventListener;
import no.uka.findmyapp.android.rest.library.HttpType;
import no.uka.findmyapp.android.rest.library.RestMethod;
import no.uka.findmyapp.android.rest.library.RestService;
import no.uka.findmyapp.android.rest.library.RestServiceHelper;
import no.uka.findmyapp.android.rest.library.ServiceReferenceFactory;
import no.uka.findmyapp.android.rest.library.ServicesConstants;
import no.uka.findmyapp.android.rest.library.data.model.TemperatureMetaData;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class AndroidRestClientDemoActivity extends Activity implements OnClickListener{ 

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
        setContentView(R.layout.temp);
        /*
        Button testTrigger = (Button) this.findViewById(R.id.button);
        testTrigger.setOnClickListener(this);
        
        Spinner chooseFormatSpinner = (Spinner) findViewById(R.id.chooseFormat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.expectedDataFormat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseFormatSpinner.setAdapter(adapter);
        
        Spinner chooseActionSpinner = (Spinner) findViewById(R.id.chooseAction);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.action, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseActionSpinner.setAdapter(adapter2);
        */

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
            Toast.makeText(AndroidRestClientDemoActivity.this, "Shake!", Toast.LENGTH_SHORT).show();
            executeDemoMethods();
          }
        });
        barArrayList = new ArrayList<Integer>();
   	 	ListView listView = (ListView) findViewById(R.id.listView1);
		barAdapter = new BarAdapter(this, R.layout.list_bar, barArrayList);
        listView.setAdapter(barAdapter);
    }
    

	
    
    public void onClick(View button) { 
    	/*
    	EditText urlForm = (EditText) findViewById(R.id.editurl);
    	EditText parameterForm = (EditText) findViewById(R.id.editparams);
    	Spinner chosenDataFormat = (Spinner) findViewById(R.id.chooseFormat);
    	Spinner chosenMethod = (Spinner) findViewById(R.id.chooseAction);
 
    	String url = urlForm.getText().toString(); 
    	String parameters = parameterForm.getText().toString(); 
    	String expectedDataType = (String) chosenDataFormat.getSelectedItem();
    	String requestMethod = (String) chosenMethod.getSelectedItem(); 
    	
    	this.executeRestClient(url, parameters, expectedDataType, requestMethod);
    	*/
    	executeDemoMethods();
    }
    
    private void executeRestClient(String url, String parameters, String expectedDataType, String requestMethod) {
    	this.executeDemoMethods();
    }
    private void executeDemoMethods() {
    	initializeService(); 
    }
    
    private void initializeService() {
    	
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
    	    	// ListView listView = (ListView) findViewById(R.id.listView1);

    	    	 
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

    
    public class MyOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
        	Toast.makeText(parent.getContext(), "Selected " +
        	  parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
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