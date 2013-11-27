package com.kokaza118.remindmeds;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class CallActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String number = preferences.getString("number", "");
		Intent intent;
		if(number.equals("")){
			//showToast("No Number");
		}else{
			//showToast(number);
			String dial = "tel:".concat(number);
			intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(Uri.parse(dial));
			startActivity(intent);
			finish();
		}
	}
	
	

}
