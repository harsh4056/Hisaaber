package own.app.hisaaber;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class HisaabSheetActivtiy extends Activity {
String databaseName;
ArrayList<String> nameColumns;
MySheetDbHelper myDb;
Boolean whichContextSeAya;
DialogforHisaabSheet dfh;
Integer id;
TableLayout tableLayout;
HashMap<String, Integer> nameAmountPair;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hisaabsheet);
		tableLayout=(TableLayout) findViewById(R.id.main_table);
		id=0;
		Intent i= getIntent();
		whichContextSeAya=i.getBooleanExtra("dialogSeAya", false);
		databaseName= i.getStringExtra("DataBaseKaNaam");
		
		nameColumns=i.getStringArrayListExtra("nameColumnArray");
		
	
		
		databaseName=databaseName.replaceAll("-", "_");
		databaseName=databaseName.replaceAll(":", "_");
		openDB();
		
		
		Cursor cursor = myDb.getAllRows();
		displayRecordSet(cursor);
		
}
	
	
	
	
 public void displayRecordSet(Cursor cursor) {
	 nameAmountPair=new HashMap<String, Integer>();
	 
	 
	 String[] columnNames=	MySheetDbHelper.ALL_KEYS;
	for(int j=0;j<nameColumns.size();j++){
			
			nameAmountPair.put(nameColumns.get(j),0);
			
		}
	 int count = tableLayout.getChildCount();
	 for (int i = 0; i < count; i++) {
	     View child = tableLayout.getChildAt(i);
	     if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
	 }
	 
	 if (cursor.moveToFirst()) {
			do {
				// Process the data:
			
				LayoutParams params = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
			
			TableRow rowLoc= new TableRow(tableLayout.getContext());
			rowLoc.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			TextView payCause = new TextView(this);
			payCause.setHorizontalScrollBarEnabled(true);
	        payCause.setId(id);
	        payCause.setGravity(Gravity.CENTER);
	        payCause.setLayoutParams(params);
	        payCause.setBackgroundResource(R.drawable.cell_shape);
	        payCause.setText(cursor.getString(columnNames.length-1));
	        payCause. setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
	        payCause.setTextColor(Color.BLACK);
	        payCause.setPadding(0, 0, 0, 0);
	        rowLoc.addView(payCause);
	        tableLayout.addView(rowLoc);
	        for(int i=1;i<columnNames.length-1;i++){
	         nameAmountPair.put(cursor.getColumnName(i),nameAmountPair.get(cursor.getColumnName(i))+cursor.getInt(i));
	        	TableRow rowAmounts= new TableRow(tableLayout.getContext());
				TextView name = new TextView(this);
				LayoutParams params1 = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.5f);
		        name.setId(id);
		        name.setGravity(Gravity.CENTER);
		        name.setLayoutParams(params1);
		        name.setBackgroundResource(R.drawable.cell_shape);
		        name.setText(cursor.getColumnName(i));
		        name. setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
		        name.setTextColor(Color.BLACK);
		        name.setPadding(0, 0, 0, 0);
		    	TextView amount = new TextView(this);
		        amount.setId(id);
		        amount.setGravity(Gravity.CENTER);
		       amount.setBackgroundResource(R.drawable.cell_shape);
		        amount.setText(cursor.getString(i));
		        amount.setLayoutParams(params1);
		        amount. setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
		        amount.setTextColor(Color.BLACK);
		        amount.setPadding(0, 0, 0, 0);
		        rowAmounts.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		        rowAmounts.addView(name);
		        rowAmounts.addView(amount);
		        tableLayout.addView(rowAmounts);
	        	
	        }
			
			
				
				
			
				
			
			} while(cursor.moveToNext());
			
			
			
	 }
		cursor.close();	

	
		
	}




public void payKiya(View v){
	 
	 dfh = new DialogforHisaabSheet(HisaabSheetActivtiy.this,myDb,nameColumns);
	 
 }
	
	
	
	private void openDB() {
		myDb = new MySheetDbHelper(this, nameColumns, databaseName,whichContextSeAya);
		myDb.open();
	}
	
	
	@Override
    public void onBackPressed()
    {	 Intent intent = new Intent(HisaabSheetActivtiy.this, MainActivity.class);
   	     startActivity(intent);
   	     finish();
   		 
   		 
   	 
    }

	
	
	public void  hisaabResultDialog(View v) {
		
	Dialog	dialogHisResult = new Dialog(HisaabSheetActivtiy.this);
	dialogHisResult.requestWindowFeature(Window.FEATURE_NO_TITLE);
	dialogHisResult.setContentView(R.layout.hisaabresults);
	Integer sum,mean,count;
	sum=0;
	count=nameAmountPair.size();
	String hissabSummary="Total payments per Individual ";
	String perIndividualShare="\nPer person kharcha is : Rs ";
	String giveGEt="\n";
	for(Entry<String, Integer> m:nameAmountPair.entrySet()){
		hissabSummary= hissabSummary+"\n"+m.getKey() +" : Rs " +m.getValue();
		sum=sum+m.getValue();
		
	}
	mean=(sum/count);
	perIndividualShare= perIndividualShare +mean;
	for(Entry<String, Integer> m:nameAmountPair.entrySet()){
		if(m.getValue()-mean<0)
			giveGEt= giveGEt +m.getKey()+" should give : Rs "+(mean-m.getValue())+"\n";
		else	if(m.getValue()-mean>0)
			giveGEt= giveGEt +m.getKey()+" should get : Rs "+(m.getValue()-mean)+"\n";
		else{
			giveGEt= giveGEt +m.getKey()+" should neither get anything nor give anything \n";
		} 
		
	}
	
	
	
	String message=hissabSummary+perIndividualShare+giveGEt;
	
	
	TextView textView= (TextView) dialogHisResult.findViewById(R.id.textView1);
	textView.setTextColor(Color.BLACK);
	textView.setText(message);
	dialogHisResult.show();
		
		
		
	}
	
	
}
