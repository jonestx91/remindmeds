package com.kokaza118.remindmeds;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

//This is the activity for the add screen
//Handles everything should occur during the add process other than the database commands
//The activity opens an instance of the PrescriptionDB in order to insert data
public class AddActivity extends Activity implements OnClickListener {

	EditText etMedicine, etNickname, etDoctor;
	EditText etDosage, etPillCount, etLowAmount;
	Button bTime, bRefillDate;
	Button bSubmit;
	
	//The variables to store in the DB
	String medicine, nickname, doctor, dosage, pillCount, lowAmount = "";
	String hours, minutes, days, months, years;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_screen);
		
		//assign the edit texts on the screen
		etMedicine = (EditText) findViewById(R.id.etMedicine);
		etNickname = (EditText) findViewById(R.id.etNickname);
		etDoctor = (EditText) findViewById(R.id.etDoctor);
		etDosage = (EditText) findViewById(R.id.etDosage);
		etPillCount = (EditText) findViewById(R.id.etPillCount);
		etLowAmount = (EditText) findViewById(R.id.etLowAmount);
		
		//assign the buttons
		bTime = (Button) findViewById(R.id.bTime);
		bRefillDate = (Button)findViewById(R.id.bRefillDate);
		bSubmit = (Button) findViewById(R.id.bSubmit);
		
		//set the click listeners on the buttons
		bTime.setOnClickListener(this);
		bRefillDate.setOnClickListener(this);
		bSubmit.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
			case R.id.bSubmit:
				boolean noError = true;
				
				try{
				//this is the data that needs to be sanitized and checked 
				//create a method that will be called here to do the following and 
				//also to do the checks
				//this method should stay in this class
				medicine = etMedicine.getText().toString();
				nickname = etNickname.getText().toString();
				doctor = etDoctor.getText().toString();
				dosage =etDosage.getText().toString();
				pillCount =etPillCount.getText().toString();
				lowAmount =etLowAmount.getText().toString();
				
				
				
				//create a new row in the table to place the data
				PrescriptionDB entry = new PrescriptionDB(AddActivity.this);
				entry.open();
				
				entry.createEntry(medicine,nickname,doctor,dosage,pillCount,lowAmount
						,minutes, hours,days, months,years);
				entry.close();
				finish();
				}catch (Exception e) {
					// TODO: handle exception
					noError = false;
				}finally{
					if(!noError){
						Dialog d = new Dialog(this);
						d.setTitle("Entry successful");
						TextView tv = new TextView(this);
						tv.setText("Medicine entered");
						d.setContentView(tv);
						d.show();
					}
				}			
				finish();
				break;
				
			case R.id.bTime:
					
				Calendar mcurrentTime = Calendar.getInstance();
	            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
	            int minute = mcurrentTime.get(Calendar.MINUTE);
	            TimePickerDialog mTimePicker;
	            mTimePicker = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
	                @Override
	                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
	                    bTime.setText( selectedHour + ":" + selectedMinute);
	                    
	                    hours = String.valueOf(selectedHour);
	                    minutes = String.valueOf(selectedMinute);
	                }
	            }, hour, minute, true);//Yes 24 hour time
	            mTimePicker.setTitle("Select Time");
	            mTimePicker.show();	
				
				break;
				
			case R.id.bRefillDate:
				
				Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(AddActivity.this, new OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        bRefillDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                        days = String.valueOf(selectedday);
                        months = String.valueOf(selectedmonth);
                        years = String.valueOf(selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            
				
				break;
		
		}
		
	}
	
	
	
}
