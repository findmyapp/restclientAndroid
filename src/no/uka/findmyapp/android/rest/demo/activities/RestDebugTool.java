
package no.uka.findmyapp.android.rest.demo.activities;

import java.net.URI;
import java.net.URISyntaxException;

import no.uka.findmyapp.android.rest.R;
import no.uka.findmyapp.android.rest.library.HttpType;
import no.uka.findmyapp.android.rest.library.data.model.ServiceModel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

public class RestDebugTool extends Activity implements OnClickListener{
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
		//Spinner chosenDataFormat = (Spinner) findViewById(R.id.chooseFormat);
		Spinner chosenMethod = (Spinner) findViewById(R.id.chooseAction);
		
		try {
			serviceModel = new ServiceModel(
					new URI(urlForm.getText().toString() + parameterForm.getText().toString()), 
					this.getType((String) chosenMethod.getSelectedItem()), 
					new TypeToken<Object>(){}.getType());
			
			Intent i = new Intent(this, DebugResult.class);
			i.putExtra("request", serviceModel);
			startActivity(i);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			Log.e("URI-fault", e.getMessage());
		} 
	}
	
	private HttpType getType(String method) {
		Log.i("getType()", method); 
		if (method.equals("PUT")) { return HttpType.PUT; }
		else if (method.equals("POST")) { return HttpType.POST; }
		else if (method.equals("UPDATE")) { return HttpType.UPDATE; }
		else if (method.equals("DELETE")) { return HttpType.DELETE; }
		else return HttpType.GET;
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

