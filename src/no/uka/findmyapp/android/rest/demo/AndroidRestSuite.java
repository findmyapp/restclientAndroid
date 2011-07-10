package no.uka.findmyapp.android.rest.demo;

import no.uka.findmyapp.android.rest.R;
import no.uka.findmyapp.android.rest.demo.activities.RestDebugTool;
import no.uka.findmyapp.android.rest.demo.activities.TemperatureDemo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AndroidRestSuite extends Activity implements OnClickListener { 

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button debug = (Button) findViewById(R.id.debugMenuEntry);
        debug.setOnClickListener(this);
        
        Button temperatureDemo = (Button) findViewById(R.id.temperatureDemoEntry);
        temperatureDemo.setOnClickListener(this);
    }

	public void onClick(View button) {
		Intent i; 
		switch (button.getId()) {
			case R.id.debugMenuEntry:
				i = new Intent(this, RestDebugTool.class);
				startActivity(i);
				break;
				
			case R.id.temperatureDemoEntry:
				i = new Intent(this, TemperatureDemo.class);
				startActivity(i);
				break; 
				
			default:
				break;
		}
	}
}