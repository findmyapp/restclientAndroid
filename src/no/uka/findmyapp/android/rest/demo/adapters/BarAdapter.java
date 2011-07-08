package no.uka.findmyapp.android.rest.demo.adapters;

import java.util.List;

import no.uka.findmyapp.android.rest.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BarAdapter extends ArrayAdapter<Integer> {
	private int textViewResourceId;
	public BarAdapter(Context context, int textViewResourceId, List<Integer> items) {	
		super(context, textViewResourceId, items);
		this.textViewResourceId = textViewResourceId;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		LinearLayout eventView = null;
		int val = getItem(position);

		if(convertView == null){
			eventView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
			vi.inflate(textViewResourceId, eventView, true);
		}
		else
		{
			eventView = (LinearLayout) convertView;
		}

		ProgressBar bar = (ProgressBar) eventView.findViewById(R.id.progressBar1);
		TextView value = (TextView) eventView.findViewById(R.id.textview);
		
		bar.setProgress(val);
		value.setText(val + " ");

		return eventView;
	}
}
