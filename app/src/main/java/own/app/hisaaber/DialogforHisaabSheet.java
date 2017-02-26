package own.app.hisaaber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;


@SuppressLint("NewApi")
public class DialogforHisaabSheet {
 Dialog addHisaab;
 Context context;
 HashMap<String,String> hm;
 ArrayList<String> names;
 LinearLayout linearLayout;
 Integer id;
 Button b1;
 Boolean textChecker=false;
 EditText paymaentPlace;
	public DialogforHisaabSheet(final Context context,final MySheetDbHelper mySheetDb,final ArrayList<String> names) {
		id=0;
		 hm=new HashMap<String,String>(); 
		 this.names=names;
		this.context=context;
		addHisaab= new Dialog(context);
		addHisaab.requestWindowFeature(Window.FEATURE_NO_TITLE);
		addHisaab.setContentView(R.layout.addhisaab);
		paymaentPlace=(EditText) addHisaab.findViewById(R.id.editText1);
		linearLayout= (LinearLayout) addHisaab.findViewById(R.id.forNameSet);
		setTextViews(this.names, linearLayout);
		addHisaab.show();
	b1= (Button) addHisaab.findViewById(R.id.addRow);
	b1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(hm.size()==names.size()){
			if(hashTableCHeckAllZeros(hm)){
			if(!paymaentPlace.getText().toString().equals("")){
			mySheetDb.insertRow(hm, paymaentPlace.getText().toString());
			Cursor cursor = mySheetDb.getAllRows();
				  addHisaab.dismiss();
				  ((HisaabSheetActivtiy) context).displayRecordSet(cursor);}
			else Toast.makeText(context, "The where did you pay field is empty", Toast.LENGTH_SHORT).show();}
			else Toast.makeText(context, "What was the point in keeping all values 0?\n You might wanna go back and start again", Toast.LENGTH_LONG).show();
			
			}else Toast.makeText(context, "Dude I am a machine!!\nPress enter on all the fields will you", Toast.LENGTH_LONG).show();
				
			}
	});
		
		
	}
	
	
	
	
	
	
	protected void setTextViews (ArrayList<String> names,LinearLayout linearLayout){
		
		for(int i=0;i<names.size();i++){
			
		final EditText etName = new EditText(linearLayout.getContext());
	    	etName.setId(id+1);
	    	etName.setText("0");
	    	etName.setInputType(InputType.TYPE_CLASS_NUMBER);
	    	etName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
	    
	        etName.setSingleLine();
	    	etName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	    	etName. setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
	    	etName.setTextColor(Color.BLACK);
	    	etName.setPadding(0, 0, 0, 0);
	    	etName.setGravity(Gravity.CENTER);
			
			
	    	final TextView tvName = new TextView(linearLayout.getContext());
			tvName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			tvName.setText(names.get(i));
	        tvName.setId(id+2);
	        tvName. setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
	        tvName.setTextColor(Color.BLACK);
	        tvName.setPadding(0, 0, 0, 0);
	        tvName.setGravity(Gravity.CENTER);
	        setEnterKey(etName,tvName);
	        linearLayout.addView(tvName);
	        linearLayout.addView(etName);
	        	
		}
	}


private boolean hashTableCHeckAllZeros(HashMap<String, String> hashMap){
	
	for(Entry<String, String> m:hashMap.entrySet()){ 
		if(Integer.parseInt(m.getValue())>0)
			textChecker=true;
		
	}
	
	return textChecker;
		
		
	}



	private void setEnterKey(final EditText etName,final TextView tvName) {
		
		etName.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
				//	Toast.makeText(context, et.getText().toString(), Toast.LENGTH_SHORT).show();
					
					hm.put(tvName.getText().toString(),etName.getText().toString());  
					etName.setEnabled(false);
			    	
					
					return true;
				}
				return false;
			}
		});
		
		
	}
	
	

}
