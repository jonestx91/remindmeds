package com.kokaza118.remindmeds;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	Button bAdd, bView, bSettings, bCall;
	private SharedPreferences preferences;
	private String number;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Link the buttons from the XML to the Java
        bCall = (Button) findViewById(R.id.buttonCall);
        bAdd = (Button) findViewById(R.id.buttonAdd);
        bSettings = (Button) findViewById(R.id.buttonSettings);
        bView = (Button) findViewById(R.id.buttonView);
        
        //Set the Click Listeners
        bCall.setOnClickListener(this);
        bAdd.setOnClickListener(this);
        bSettings.setOnClickListener(this);
        bView.setOnClickListener(this);
        
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		number = preferences.getString("number", "");
    }


    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
    	//Choose what to do based on which button is clicked
    	Class c = null;
    	Intent intent;
    	switch(v.getId()){
    		
    		//if the call button do the following
    		case R.id.buttonCall:
    			
    			//showToast("Call");
    			//This will Dial the Caretaker number
    			//***For now there is a hardcoded number in there"
    			//***ALSO WE CAN CHANGE FROM DIAL TO CALL IF WE WANT
    			//preferences.registerOnSharedPreferenceChangeListener(listener)
    			/*if(number.equals("")){
    				showToast("No Number");
    			}else{
    				showToast(number);
    				String dial = "tel:".concat(number);
    				intent = new Intent(Intent.ACTION_DIAL);
    				intent.setData(Uri.parse(dial));
    				startActivity(intent);
    			}*/

    			try {
    				c = Class.forName("com.kokaza118.remindmeds.CallActivity");
    			} catch (ClassNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			intent = new Intent(MainActivity.this, c);
    			startActivity(intent);
    			break;
    		//if the new button is clicked then do the following
    		case R.id.buttonAdd:
    			
    			showToast("Add");
    			//Set up intent and launch Activity for the Add Screen
    			
    			try {
    				c = Class.forName("com.kokaza118.remindmeds.AddActivity");
    			} catch (ClassNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			intent = new Intent(MainActivity.this, c);
    			startActivity(intent);
    			break;
    			
    		//if the settings button is clicked then do the following
    		case R.id.buttonSettings:
    			
    			showToast("Settings");
    			//Set up intent and launch Activity for the Settings Screen
   
    			Intent intentS = new Intent("com.kokaza118.remindmeds.PREFSACTIVITY");
    			startActivity(intentS);
    			break;
    			
    		//if the view button is clicked then do the following
    		case R.id.buttonView:
    			
    			showToast("View");
    			//Set up intent and launch Activity for the View Screen
    			
    			
    			try {
    				c = Class.forName("com.kokaza118.remindmeds.ViewActivity");
    			} catch (ClassNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			intent = new Intent(MainActivity.this, c);
    			startActivity(intent);
    			break;
    	}
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	//method for testing break points
	public void showToast(String name){
		
		(Toast.makeText(this,name,Toast.LENGTH_SHORT)).show();
	}



	
	
    
}
