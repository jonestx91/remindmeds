package com.kokaza118.remindmeds;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Entry {

	private LinearLayout mainContainer;	//this iss the outside linear layout
	private LinearLayout dataContainer;	//This is the linear layout holding the data
	
	private Button bDecrement;
	
	private TextView tNickname;
	private TextView tMedicine;
	private TextView tPillCount;
	
	private int rowID;
	private String medicine;
	private int pillCount;
	private String refillDate;
	private String doctor;
	private String nickname;
	private boolean deleted;
	private boolean sent;
	private String dosageTime;
	private int dosage;
	private int lowAmount;
	
	private Context context;
	
	public Entry(int rowID, String medicine, int pillCount, String refillDate
			,String doctor, String nickname, int deleted, int sent, String dosageTime,
			int dosage, int lowAmount, Context c){
		
		this.rowID = rowID;
		this.medicine = medicine;
		this.pillCount = pillCount;
		this.refillDate = refillDate;
		this.doctor = doctor;
		this.nickname = nickname;
		//since the deleted value should be 0 because of the query
		if(deleted ==0)
			this.deleted = false;
		else
			this.deleted = true;
		//sent may or may not be true but we are not gonna use it yet
		this.sent = false;
		this.dosageTime = dosageTime;
		this.dosage = dosage;
		this.lowAmount = lowAmount;
		
		//now set up the components;
		this.context = c;
		mainContainer = new LinearLayout(context);
		dataContainer = new LinearLayout(context);
		bDecrement = new Button(context);
		tMedicine = new TextView(context);
		tNickname = new TextView(context);	
		tPillCount = new TextView(context);
		
		//set up all of the components
		
		//set up the main container
		LayoutParams mainParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		mainContainer.setLayoutParams(mainParams);
		mainContainer.setOrientation(LinearLayout.HORIZONTAL);
		mainContainer.setBackgroundColor(Color.LTGRAY);
		mainContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_END);
		
		//set up datacontainer
		LayoutParams dataParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		dataContainer.setLayoutParams(dataParams);
		dataContainer.setOrientation(LinearLayout.VERTICAL);
		//dataContainer.setOnLongClickListener(new MyLongClick(rowID,context));
//		dataContainer.setOnLongClickListener(new OnLongClickListener() {
//			
//			@Override
//			public boolean onLongClick(View v) {
//				// TODO Auto-generated method stub
//				//showMenu(rowID);
//				return false;
//			}
//		});//this needs to be set up
		dataContainer.setId(rowID);
		
		//set up textViews
		LayoutParams textParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		tMedicine.setLayoutParams(textParams);
		tMedicine.setTextAppearance(context, android.R.attr.textAppearanceSmall);
		tMedicine.setText(medicine);
		tMedicine.setTextColor(Color.BLACK);
		tMedicine.setWidth(600);
		
		tNickname.setLayoutParams(textParams);
		tNickname.setTextAppearance(context, android.R.attr.textAppearanceMedium);
		tNickname.setText(nickname);
		tNickname.setTextColor(Color.BLACK);
		tNickname.setWidth(600);
		
		tPillCount.setLayoutParams(textParams);
		tPillCount.setTextAppearance(context, android.R.attr.textAppearanceSmall);
		tPillCount.setText(String.valueOf(pillCount));
		tPillCount.setTextColor(Color.BLACK);
		tPillCount.setWidth(600);
		
		//add the textviews to the data container
		dataContainer.addView(tNickname);
		dataContainer.addView(tMedicine);
		dataContainer.addView(tPillCount);
		
		//set up the decrement button
		//LayoutParams buttonParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//bDecrement.setLayoutParams(buttonParams);
		bDecrement.setText("-");
		bDecrement.setWidth(400);
		bDecrement.setHeight(150);
		bDecrement.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int count = getPillCount();
				count-= getDosage();
				setPillCount(count);
				tPillCount.setText(String.valueOf(count));
				
				//update the pillcount in the database
				updatePillCount(count);
			}
		});
		
		mainContainer.addView(dataContainer);
		mainContainer.addView(bDecrement);
		
	
	}
	
	
	public LinearLayout getUI(){
		
		return this.mainContainer;
	}

	public int getPillCount(){
		return this.pillCount;
	}
	
	public void setPillCount(int count){
		this.pillCount = count;
	}
	
	public int getDosage(){
		return this.dosage;
	}
	
	public void updatePillCount(int count){
		
		
		try{
			PrescriptionDB db = new PrescriptionDB(context);
			db.open();
			//db.execSQL("UPDATE "+ DATABASE_TABLE +" SET "+KEY_PILLCOUNT+"="+count+" WHERE "+KEY_ROWID+"="+rowID);
			db.updatePillCount(count, rowID);

			db.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		
	}
	
	private class MyLongClick implements OnLongClickListener{
		
	
		private int rowID;
		private Context context;
		
		public MyLongClick(int rowID,Context context) {
		       this.rowID = rowID;
		       this.context = context;
		    }
		@Override
		public boolean onLongClick(View arg0) {
			// TODO Auto-generated method stub
			showMenu(rowID,context);
			return false;
		}
		
		public int getRowID(){
			return rowID;
		}
		
		public void showMenu(int rowID, Context context){
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setItems(R.menu.dialog_menu, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					switch(which){
					
						case 0: //showDetailed(rowID);
						
							break;
						
						case 1: //deleteItem(rowID);
						
							break;
					}
				}
			});
			dialog.show();
		}
	}

}

