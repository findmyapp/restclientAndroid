
package no.uka.findmyapp.android.rest.demo.activities;

import no.uka.findmyapp.android.rest.R;
import no.uka.findmyapp.android.rest.demo.activities.TemperatureDemo.StringsContentObserver;
import no.uka.findmyapp.android.rest.library.RestMethod;
import no.uka.findmyapp.android.rest.library.RestServiceHelper;
import no.uka.findmyapp.android.rest.library.ServiceReferenceFactory;
import no.uka.findmyapp.android.rest.library.data.model.ServiceModel;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RestDebugTool extends Activity implements OnClickListener{
	
	private static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	private StringsContentObserver stringsObserver = null;
	private Handler handler = new Handler();
	private ServiceModel serviceModel; 
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_tool);

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
	   
	    Button testTrigger = (Button) this.findViewById(R.id.button);
	    testTrigger.setOnClickListener(this);
    }

	public void onClick(View button) 
	{ 	
		EditText urlForm = (EditText) findViewById(R.id.editurl);
		EditText parameterForm = (EditText) findViewById(R.id.editparams);
		Spinner chosenDataFormat = (Spinner) findViewById(R.id.chooseFormat);
		Spinner chosenMethod = (Spinner) findViewById(R.id.chooseAction);
		
//		serviceModel = new ServiceModel();
		String url = urlForm.getText().toString(); 
		String parameters = parameterForm.getText().toString(); 
		String expectedDataType = (String) chosenDataFormat.getSelectedItem();
		String requestMethod = (String) chosenMethod.getSelectedItem(); 
	
		this.executeRestClient(url, parameters, expectedDataType, requestMethod);
	}
	
	 private void executeRestClient(String url, String parameters, String expectedDataType, String requestMethod) {
/*
		 serviceHelper.startServiceTest(this, ServiceReferenceFactory.getService(ServiceReferenceFactory.Services.TEMP)); 
		 
		 String info = "URL: " + url + parameters + "\nDataFormat: " + expectedDataType + "\nRequestMethod: " + requestMethod + "\n\n";
		 TextView tv = (TextView) findViewById(R.id.textview);
		        
		 try {
			 tv.setText(info + rm.get(parameters, expectedDataType));
		 } catch (Exception e) {
			 // TODO Auto-generated catch block
			 tv.setText(info + e.getMessage());
		 }
*/
	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener 
	{
	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	Toast.makeText(parent.getContext(), "Selected " +
	    	  parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	    }
	
	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
}

