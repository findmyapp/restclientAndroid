package no.uka.findmyapp.android.rest.demo.activities;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.Gson;

import no.uka.findmyapp.android.rest.R;
import no.uka.findmyapp.android.rest.demo.activities.TemperatureDemo.StringsContentObserver;
import no.uka.findmyapp.android.rest.library.HttpType;
import no.uka.findmyapp.android.rest.library.RestServiceHelper;
import no.uka.findmyapp.android.rest.library.data.model.ServiceModel;
import no.uka.findmyapp.android.rest.library.data.model.Temperature;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class DebugResult extends Activity {
	
	private static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	private StringsContentObserver stringsObserver = null;
	private Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug_result);
	
		ServiceModel serviceModel = null;
		
		try {
			Log.d("BroadcastIntentDebug", "DebugResult activity started, inflating serviceModel");
			serviceModel = (ServiceModel) getIntent().getSerializableExtra("request");
			Log.d("BroadcastIntentDebug", serviceModel.toString());
		} catch (Exception e) {
			Log.w("BroadcastIntentDebug", e.getMessage());
		}
		
		Log.d("BroadcastIntentDebug", "executing serviceModel ");
        this.execute(serviceModel);
	}
	
	private void execute(ServiceModel sm) {
		
		setHeadText(sm);
		ReciveIntent intentReceiver = new ReciveIntent();
		IntentFilter intentFilter = new IntentFilter(RestDebugTool.BROADCAST_INTENT_TOKEN);

		registerReceiver(intentReceiver, intentFilter); 
		serviceHelper.startServiceTest(this, sm); 
	}
	
	private void setHeadText(ServiceModel sm) {
		String text = "URI: " + sm.getUri() + "\n"; 
		text += "Method: " + sm.getHttpType() + "\n";
		text += "Class: " + sm.getReturnType(); 

		TextView tv = (TextView) findViewById(R.id.debugHead); 
		tv.setText(text);
	}
	
	private void setResultText(String result) {
		TextView tv = (TextView) findViewById(R.id.debugResult);
		tv.setText(result);
	}
	
	public class ReciveIntent extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			Log.w("BroadcastIntentDebug", "---------");
			TextView tv = (TextView) findViewById(R.id.debugResult);
			Gson gson = new Gson(); 
			if (intent.getAction().equals(RestDebugTool.BROADCAST_INTENT_TOKEN)) {
				Serializable obj = intent.getSerializableExtra("return");
				//gson.toJson(obj);
				Temperature t = (Temperature) obj;
				
				tv.setText(t.toString()); 
			}
		}
	}
}
