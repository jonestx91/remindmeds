package com.kokaza118.remindmeds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PrescriptionDB {

	//stores the id of the entry
	public static final String KEY_ROWID = "_id";
	public static final String KEY_MEDICINE = "medicine_name";
	public static final String KEY_PILLCOUNT = "pill_count";
	public static final String KEY_REFILLDATE = "refill_date";
	public static final String KEY_DOCTOR = "doctor_name";
	public static final String KEY_NICKNAME = "_nickname";
	public static final String KEY_DELETED = "_deleted";
	public static final String KEY_SENT = "_sent";
	public static final String KEY_DOSETIME = "dosage_time";
	public static final String KEY_DOSAGE = "_dosage";
	public static final String KEY_LOWAMOUNT = "_low";
	
	private static final String DATABASE_NAME = "PrescriptionDB";
	private static final String DATABASE_TABLE = "medicationTable";
	private static final int DATABASE_VERSION = 7;
	
	public static String getDatabaseName() {
		return DATABASE_NAME;
	}

	public static String getDatabaseTable() {
		return DATABASE_TABLE;
	}

	public static int getDatabaseVersion() {
		return DATABASE_VERSION;
	}

	//variables neeeded for the DBHelper class
	private DBHelper helper;
	private final Context dbContext;
	private SQLiteDatabase db;
	
	//this class is used to manipulate the DB
	private static class DBHelper extends SQLiteOpenHelper{
	
		//create the DB when it is called
		public DBHelper(Context context){
			
			super(context, DATABASE_NAME,null,DATABASE_VERSION);
		}
		
		@Override//create the tables
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			String sql = "CREATE TABLE " + DATABASE_TABLE +" (" +
			KEY_ROWID + " INTEGER AUTO_INCREMENT, " +
			KEY_MEDICINE + " TEXT NOT NULL, "+
			KEY_DOCTOR + " TEXT NOT NULL, "+
			KEY_PILLCOUNT + " INTEGER, "+
			KEY_DELETED + " INTEGER, "+
			KEY_DOSAGE + " INTEGER, "+
			KEY_LOWAMOUNT + " INTEGER, "+
			KEY_NICKNAME + " TEXT, "+
			KEY_SENT + " INTEGER, "+
			KEY_DOSETIME+ " TIME, "+
			KEY_REFILLDATE + " DATE " +
			" )";
			db.execSQL(sql);
//			ContentValues cv1 = new ContentValues();	//This is like a bundle or package to pass data
//			
//			cv1.put(KEY_ROWID, 1);
//			cv1.put(KEY_DOCTOR, "Snoop Dog");		//put the sql value with the key and value
//			cv1.put(KEY_MEDICINE, "advil");
//			cv1.put(KEY_PILLCOUNT, 50);
//			cv1.put(KEY_REFILLDATE, "2013-07-04");
//			cv1.put(KEY_NICKNAME, "blue pill");
//			cv1.put(KEY_DOSETIME, "10:05");
//			cv1.put(KEY_DELETED, 0);
//			cv1.put(KEY_DOSAGE, 2);
//			cv1.put(KEY_LOWAMOUNT, 10);
//			cv1.put(KEY_SENT, 0);
//			db.insert(DATABASE_TABLE, null, cv1);
			
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
		
		
	}
	
	public PrescriptionDB(Context c){
		
		dbContext = c;
	}
	
	public PrescriptionDB open() throws SQLException{
		helper = new DBHelper(dbContext);
		db = helper.getWritableDatabase();
		return null;
	}
	
	public void close(){
		helper.close();
	}

	//this is the column for entering data into a row in the table
	public long createEntry(String medicine, String nickname, String doctor, String dosage, String pillCount, String lowAmount, String minutes, String hours, String days, String months, String years) {
		// TODO Auto-generated method stub
		
		ContentValues cv = new ContentValues();	//This is like a bundle or package to pass data
	
		cv.put(KEY_DOCTOR, doctor);		//put the sql value with the key and value
		cv.put(KEY_MEDICINE, medicine);
		cv.put(KEY_PILLCOUNT, Integer.parseInt(pillCount));
		String date = years+"-"+days+"-"+months;
		cv.put(KEY_REFILLDATE, date);
		String time = hours+":"+minutes;
		cv.put(KEY_NICKNAME, nickname);
		cv.put(KEY_DOSETIME, time);
		cv.put(KEY_DELETED, 0);
		cv.put(KEY_DOSAGE, Integer.parseInt(dosage));
		cv.put(KEY_LOWAMOUNT, Integer.parseInt(lowAmount));
		cv.put(KEY_SENT, 0);
		
		//insert into db in prescription table
		return db.insert(DATABASE_TABLE, null, cv);
		
	}

	public Entry[] getData(Context context) {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_ROWID, KEY_MEDICINE, KEY_DOCTOR, KEY_PILLCOUNT, KEY_REFILLDATE,KEY_NICKNAME,KEY_DELETED
				,KEY_SENT, KEY_DOSAGE, KEY_DOSETIME,KEY_LOWAMOUNT};
		//use a cursor to read in data
		Cursor cursor = db.query(DATABASE_TABLE, columns, null, null, null, null, null);
		int count = cursor.getCount();
		Entry[] entries = new Entry[count];
		//get the data for each index
		int indexRow = cursor.getColumnIndex(KEY_ROWID);
		int indexMedicine = cursor.getColumnIndex(KEY_MEDICINE);
		int indexPillCount = cursor.getColumnIndex(KEY_PILLCOUNT);
		int indexRefillDate = cursor.getColumnIndex(KEY_REFILLDATE);
		int indexDoctor = cursor.getColumnIndex(KEY_DOCTOR);
		int indexNickname = cursor.getColumnIndex(KEY_NICKNAME);
		int indexDeleted = cursor.getColumnIndex(KEY_DELETED);
		int indexSent = cursor.getColumnIndex(KEY_SENT);
		int indexDoseTime = cursor.getColumnIndex(KEY_DOSETIME);
		int indexDosage = cursor.getColumnIndex(KEY_DOSAGE);
		int indexLowAmount = cursor.getColumnIndex(KEY_LOWAMOUNT);
		
		int i =0;
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			//this traverses the cursor to get the data
			 int rowID = cursor.getInt(indexRow);
			 String medicine = cursor.getString(indexMedicine);
			 int pillCount = cursor.getInt(indexPillCount);
			 String refillDate = cursor.getString(indexRefillDate);
			 String doctor = cursor.getString(indexDoctor);
			 String nickname = cursor.getString(indexNickname);
			 int deleted = cursor.getInt(indexDeleted);
			 int sent = cursor.getInt(indexSent);
			 String dosageTime = cursor.getString(indexDoseTime);
			 int dosage = cursor.getInt(indexDosage);
			 int lowAmount = cursor.getInt(indexLowAmount);
			 
			 Entry entry = new Entry(rowID, medicine, pillCount, refillDate, doctor, nickname, deleted, sent, dosageTime, dosage, lowAmount, context);
			 entries[i] = entry;
			 i++;
		}
		return entries;
	}
	
	public String getString() {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_ROWID, KEY_MEDICINE, KEY_DOCTOR, KEY_PILLCOUNT, KEY_REFILLDATE};
		//use a cursor to read in data
		Cursor c = db.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String result = "";
		//get the data for each index
		int indexRow = c.getColumnIndex(KEY_ROWID);
		int indexMedicine = c.getColumnIndex(KEY_MEDICINE);
		int indexPillCount = c.getColumnIndex(KEY_PILLCOUNT);
		int indexRefillDate = c.getColumnIndex(KEY_REFILLDATE);
		int indexDoctor = c.getColumnIndex(KEY_DOCTOR);
		int indexNickname = c.getColumnIndex(KEY_NICKNAME);
		int indexDeleted = c.getColumnIndex(KEY_DELETED);
		int indexSent = c.getColumnIndex(KEY_SENT);
		int indexDoseTime = c.getColumnIndex(KEY_DOSETIME);
		int indexDosage = c.getColumnIndex(KEY_DOSAGE);
		int indexLowAmount = c.getColumnIndex(KEY_LOWAMOUNT);
		
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			//this traverses the cursor to get the data
			result = result + c.getInt(indexRow)+"\n" +
			" "+ c.getString(indexMedicine)+
					 "\n" +" "+ c.getString(indexDoctor)+
					 "\n"+ " "+ c.getString(indexPillCount)+
					 "\n" +" "+ c.getString(indexRefillDate) +
					 "\n" +" "+ c.getString(indexNickname) +
					 "\n" +" "+ c.getInt(indexDeleted) +
					 "\n" +" "+ c.getInt(indexSent) +
					 "\n" +" "+ c.getString(indexDoseTime) +
					 "\n" +" "+ c.getInt(indexDosage) +
					 "\n" +" "+ c.getInt(indexLowAmount)+
					 " \n *************************************** \n";
		}
		return result;
	}
	
	public long updatePillCount(int count, int rowID){
		
		ContentValues args = new ContentValues();
		args.put(KEY_PILLCOUNT, count);
		String id = KEY_ROWID+"="+rowID;
		//try{
		//return db.execSQL("UPDATE "+ DATABASE_TABLE +" SET "+KEY_PILLCOUNT+"="+count+" WHERE "+KEY_ROWID+"="+rowID);
		return db.update(DATABASE_TABLE, args, id, null);
			
		//}catch(SQLException e){
		//	e.printStackTrace();
		//}
	}
	
}
