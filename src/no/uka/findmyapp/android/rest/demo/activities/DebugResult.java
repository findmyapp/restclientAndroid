package no.uka.findmyapp.android.rest.demo.activities;

import no.uka.findmyapp.android.rest.R;
import no.uka.findmyapp.android.rest.demo.activities.TemperatureDemo.StringsContentObserver;
import no.uka.findmyapp.android.rest.library.RestServiceHelper;
import no.uka.findmyapp.android.rest.library.data.model.ServiceModel;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class DebugResult extends Activity{
	private static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	private StringsContentObserver stringsObserver = null;
	private Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug_result);
		
		ServiceModel serviceModel = (ServiceModel) getIntent().getSerializableExtra("request");
		
        this.execute(serviceModel);
	}
	
	private void execute(ServiceModel sm) {
		setHeadText(sm);
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
}
