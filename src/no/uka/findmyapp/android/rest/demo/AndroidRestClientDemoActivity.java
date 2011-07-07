package no.uka.findmyapp.android.rest.demo;

import no.uka.findmyapp.android.rest.R;
import no.uka.findmyapp.android.rest.library.RestMethod;
import no.uka.findmyapp.android.rest.library.RestService;
import no.uka.findmyapp.android.rest.library.RestServiceHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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


public class AndroidRestClientDemoActivity extends Activity implements OnClickListener{ 

	private static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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

    }
    
    public void onClick(View button) { 
    	EditText urlForm = (EditText) findViewById(R.id.editurl);
    	EditText parameterForm = (EditText) findViewById(R.id.editparams);
    	Spinner chosenDataFormat = (Spinner) findViewById(R.id.chooseFormat);
    	Spinner chosenMethod = (Spinner) findViewById(R.id.chooseAction);
 
    	String url = urlForm.getText().toString(); 
    	String parameters = parameterForm.getText().toString(); 
    	String expectedDataType = (String) chosenDataFormat.getSelectedItem();
    	String requestMethod = (String) chosenMethod.getSelectedItem(); 
    	
    	this.executeRestClient(url, parameters, expectedDataType, requestMethod);
    }
    
    private void executeRestClient(String url, String parameters, String expectedDataType, String requestMethod) {
    	this.executeDemoMethods();
    	
    	
    	/*
        RestMethod rm = new RestMethod(url);
        
        String info = "URL: " + url + parameters + "\nDataFormat: " + expectedDataType + "\nRequestMethod: " + requestMethod + "\n\n"; 
        TextView tv = (TextView) findViewById(R.id.textview);
        
        try {
			tv.setText(info + rm.get(parameters, expectedDataType));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			tv.setText(info + e.getMessage());
    	} */
    	
    }
    
    private void executeDemoMethods() {
    	initializeService(); 
    }
    
    private void initializeService() {
    	serviceHelper.startServiceTest(this); 
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
}