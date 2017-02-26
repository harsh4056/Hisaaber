package own.app.hisaaber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;




public class MainActivity extends Activity {

int year,month, day;
DialogEntry d;
Calendar calendar;
MyDbHelper myDb;
ArrayList<String> data,dbName,names;
ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		lv= (ListView) findViewById(R.id.listView1);
		getNameFromMain();
		dbName=new ArrayList<String>();
		data= new ArrayList<String>();
		names=new ArrayList<String>();
		openDB();
		Cursor cursor = myDb.getAllRows();
		displayRecordSet(cursor);
		 
	}
	private void displayRecordSet(Cursor cursor) {
		if (cursor.moveToFirst()) {
			do {
				// Process the data:
				
				String event = cursor.getString(MyDbHelper.COL_Event);
				String date = cursor.getString(MyDbHelper.COL_date);
				String time = cursor.getString(MyDbHelper.COL_time);
				String nameS = cursor.getString(MyDbHelper.COL_names);
				// Append data to the message:
			String	message = 
						    "Name : " + event
						   +" \r\nDate : " + date
						   +" \r\nTime : " + time
						  
						
						   ;
			    dbName.add((event+date+time).replaceAll(" ", "_"));
				data.add(message);
				names.add(nameS);
			} while(cursor.moveToNext());
			
			
			
			
		cursor.close();	

			
			
			
		}
		ArrayAdapter<String> adapterForListView = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);
		lv.setAdapter(adapterForListView);
	}
	private void openDB() {
		myDb = new MyDbHelper(this);
		myDb.open();
	}

	private void closeDB() {
		
	
		myDb.close();
	}

	public void newEvent(View v){
		 d= new DialogEntry( MainActivity.this,myDb);
		 calendar = Calendar.getInstance();
	      year = calendar.get(Calendar.YEAR);
	      
	      month = calendar.get(Calendar.MONTH);
	      day = calendar.get(Calendar.DAY_OF_MONTH);
	      showDate0(year, appending0(month+1), appending0(day));
	}
	//---------date------------
	
	protected Dialog onCreateDialog(int id) {
	      // TODO Auto-generated method stub
	      if (id == 999) {
	         return new DatePickerDialog( this, myDateListener, year, month, day);
	      }
	      return null;
	   }

	   private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
	      @Override
	      public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
	    	  
	    	  arg2=arg2+1;
	         // TODO Auto-generated method stub
	         // arg1 = year
	         // arg2 = month
	         // arg3 = day
	    	  String strm="";
	    	  String strd="";

     strm =appending0(arg2);
     strd  =appending0(arg3);  	  
	         showDate0(arg1, strm, strd);
	    
	    	  /*else  if (flag==2)
	 	         showDate2(arg1, strm, strd);
	    	  else if (flag==3)
	 	         showDate3(arg1, strm, strd);*/
	      }
	   };

private void showDate0(int year, String month, String day) {
		   d.date.setText(new StringBuilder().append(year).append("-")
	      .append(month).append("-").append(day));
	   }
	   
	      public String appending0(int argument)
	      {String s;
	    	  
	    	  if (argument<10){
	    		  StringBuilder sb = new StringBuilder();
	    	  sb.append("0");
	    	  sb.append(argument);
	    	  s = sb.toString();
	    	  }
	    	  
	    	  else{
	    		  StringBuilder sb = new StringBuilder();
		    	 
		    	  sb.append(argument);
		    	  s = sb.toString();
	    	  }
	    	  
	    	  
	    	  return s;
	      }
	

	      //------Date--------
	      
	      
	      private void getNameFromMain(){
	    	  
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
			//	String itemValue=lv.getItemAtPosition(position).toString();
		
				Intent i = new Intent(MainActivity.this,HisaabSheetActivtiy.class);
				i.putExtra("DataBaseKaNaam", dbName.get(position));
				String [] parts = names.get(position).split(",");
				ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(parts));
				i.putStringArrayListExtra("nameColumnArray", stringList );
				startActivity(i);
				closeDB();
				finish();
				
			}
		});
	    	  
	      }
	      
	
}
