package com.kokaza118.remindmeds;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.telephony.SmsManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ViewActivity extends Activity{
	private ScrollView scroll;
	private LinearLayout mainLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.view_screen);
		
		//WE HAVE TO HAVE A LOOP TO GET ENTRIES AND SET UP THE UI HERE
		Context activityContext = this.getBaseContext();
		
		scroll = new ScrollView(this);
		
		mainLayout = new LinearLayout(this);
		mainLayout.setBackgroundResource(R.drawable.background_blueglow);
		mainLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.setPadding(10, 10, 10, 10);
		
		
		TextView tv = new TextView(this);
		PrescriptionDB db = new PrescriptionDB(this);
		db.open();
		//String data = db.getString();
		Entry entries[] = db.getData(activityContext);
		db.close();
		
		for(int i=0;i<entries.length;i++){
			mainLayout.addView(entries[i].getUI());
			mainLayout.addView(new TextView(this));
		}
		
		scroll.addView(mainLayout);
		setContentView(scroll);
		
		
		
		//tv.setText(data);
		
	}

	//Use this method to text the caretaker if the pill count goes below low amount
	public void textCareTaker(){
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String number = preferences.getString("number", "");
		String message ="Construct the medicine refill text here";
		SmsManager sms = SmsManager.getDefault();
	    sms.sendTextMessage(number, null, message, null, null);
	}
}
