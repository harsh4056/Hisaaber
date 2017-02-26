package own.app.hisaaber;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.widget.TextView.OnEditorActionListener;

public class DialogEntry {
	Dialog dialog;
	Context context;
	LinearLayout ll;
	Integer id;
	String namesSingleLine;
	ArrayList<String> names;
	EditText countPpl,event;
	Button cntSwitch,submit;
	TextView date,time;
	MyDbHelper myDb;
	public DialogEntry(Context ctx, MyDbHelper myDb) {
		names= new ArrayList<String>();
		namesSingleLine="";
		this.myDb=myDb;
		id=1;
		this.context=ctx;
		this.dialog =new Dialog(context);
		this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.dialog.setContentView(R.layout.entry);
		this.dialog.show();
		ll= (LinearLayout) dialog.findViewById(R.id.ll1);
	    countPpl= (EditText)dialog.findViewById(R.id.eventStrength);
	    
	    event=(EditText)dialog.findViewById(R.id.eventName);
	    submit=(Button)dialog.findViewById(R.id.submitevent);
	    submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Integer.parseInt(countPpl.getText().toString())==names.size())
				
			store();
				
				else Toast.makeText(context, "Enter all data,make sure you press enter key!!", Toast.LENGTH_SHORT).show();
			}
		});
	    cntSwitch=(Button)dialog.findViewById(R.id.submitppl);
	    cntSwitch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int cnt= Integer.parseInt(countPpl.getText().toString());
				
				for(int i=0;i<cnt;i++)
					viewSwitch(ll);	
				cntSwitch.setClickable(false);
				countPpl.setFocusable(false);
			
				
			}
		});

	 
	
	date= (TextView) dialog.findViewById(R.id.date);
	time= (TextView) dialog.findViewById(R.id.time);
	
	date.setOnClickListener(new OnClickListener() {
		
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
		((MainActivity) context).showDialog(999);
		}
	});
	
	time.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			  Calendar mcurrentTime = Calendar.getInstance();
              int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
              int minute = mcurrentTime.get(Calendar.MINUTE);
              TimePickerDialog mTimePicker;
              mTimePicker = new TimePickerDialog(time.getContext(), new TimePickerDialog.OnTimeSetListener() {
                  @Override
                  public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                	  if (selectedMinute<10){
                		  if(selectedHour<10)
                    		  time.setText( "0"+selectedHour + ":0" + selectedMinute);  
                    	  else  time.setText( selectedHour + ":0" + selectedMinute);
                		  
                	}
                	  else  {
                		  if(selectedHour<10)
                    		  time.setText( "0"+selectedHour + ":" + selectedMinute);  
                    	  else  time.setText( selectedHour + ":" + selectedMinute);
                	  }
                	
                  }
              }, hour, minute, true);//Yes 24 hour time
              mTimePicker.setTitle("Select Time");
              mTimePicker.show();
			
		}
	});
	
	}
	
	
	
	protected void store() {
		String event=this.event.getText().toString();
		String date=this.date.getText().toString();
		String time=this.time.getText().toString();
		
		if(event.equals(""))
			Toast.makeText(context, "Cant leave event empty", Toast.LENGTH_SHORT).show();
		else	if(time.equals("Click for time"))
			Toast.makeText(context, "Choose time", Toast.LENGTH_SHORT).show();
		
		else{	myDb.insertRow(event, date, time,namesSingleLine);
		
		
		
		dialog.dismiss();
		myDb.close();
		String dbName=event+date+time;
		dbName=dbName.replaceAll(" ", "_");
		 Intent i = new Intent(context, HisaabSheetActivtiy.class);
		 i.putExtra("DataBaseKaNaam",dbName );
		 i.putExtra("dialogSeAya", true);
		 i.putStringArrayListExtra("nameColumnArray", names);
		context.startActivity(i);
	((MainActivity) context).finish();
		
		
		}
	}



	protected void viewSwitch(LinearLayout relativeLayout){
		final ViewSwitcher switcher = new ViewSwitcher(ll.getContext());
		switcher.setId(id);
		switcher.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		//Toast.makeText(context, "Switcheradded", Toast.LENGTH_SHORT).show();
	
        
    	EditText etName = new EditText(switcher.getContext());
    	etName.setId(id+1);
    	etName.setText("");
    	etName.setImeOptions(EditorInfo.IME_ACTION_DONE);
    	setEnterKey(etName,switcher);
        etName.setSingleLine();
    	etName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
    	etName. setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
    	etName.setTextColor(Color.BLACK);
    	etName.setPadding(0, 0, 0, 0);
    	etName.setGravity(Gravity.CENTER);
	
        switcher.addView(etName);
       
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
lp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
       
        relativeLayout.addView(switcher,lp1);
        
        
        id=id+4;
	}
	

	protected void setEnterKey(final EditText editText,final ViewSwitcher switcher){
		
		editText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
				//	Toast.makeText(context, et.getText().toString(), Toast.LENGTH_SHORT).show();
			    	TextView tvName = new TextView(switcher.getContext());
					tvName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			        tvName.setId(id+2);
			        tvName.setText(editText.getText().toString());
			        tvName. setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
			        tvName.setTextColor(Color.BLACK);
			        tvName.setPadding(0, 0, 0, 0);
			        tvName.setGravity(Gravity.CENTER);
			        switcher.addView(tvName);
					names.add(editText.getText().toString());
					namesSingleLine=namesSingleLine+editText.getText().toString()+",";
					
					switcher.showNext();
					return true;
				}
				return false;
			}
		});
		
	}

	

		
		
	
	
}
