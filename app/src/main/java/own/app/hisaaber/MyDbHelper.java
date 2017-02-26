package own.app.hisaaber;



// TODO: Change the package to match your project.


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class MyDbHelper {

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";
	
	// DB Fields
	
	public static final int COL_ROWID = 0;
	public static final int COL_Event = 1;
	public static final int COL_date = 2;
	public static final int COL_time = 3;
	public static final int COL_names = 4;
	/*
	 * CHANGE 1:
	 */
	// TODO: Setup your fields here:
	public static final String KEY_ROWID = "_id";
	public static final String KEY_event = "event";
	public static final String KEY_date = "date";
	public static final String KEY_time = "time";
	public static final String KEY_names = "names";

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)



	
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, 
		KEY_event, 
		KEY_date, 
		KEY_time,KEY_names};
	
	// DB info: it's name, and the table we are using (just one).
	public static final String DATABASE_NAME = "Hisaaber";
	public static final String DATABASE_TABLE = "mainTable";
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 1;	
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE 
			+ " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			
			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			//	- Key is the column name you created above.
			//	- {type} is one of: text, integer, real, blob
			//		(http://www.sqlite.org/datatype3.html)
			//  - "not null" means it is a required field (must be given a value).
			// NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
			+ KEY_event + " text not null, "
			+ KEY_date + " string not null, "
			+ KEY_time + " string not null, "
			+ KEY_names + " string not null "
			
			// Rest  of creation:
			+ ");";
	
	// Context of application who uses us.
	private final Context context;
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public MyDbHelper(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public MyDbHelper open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}

	
	// Add a new set of values to the database.
	public long insertRow(String event,String date,String time,String names
		
		    ) {
		/*
		 * CHANGE 3:
		 */		
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_event, event);
		initialValues.put(KEY_date, date);
		initialValues.put(KEY_time, time);
		initialValues.put(KEY_names, names);

		
		
		
		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}
	
	public void deleteAll() {
	db.delete(DATABASE_TABLE, null, null);
	}
	
	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where , null, null, null, KEY_ROWID +" DESC", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	public Cursor getSelectedRows(String name,String batch,String centre,String person_res,int date_plow,int date_phigh,int date_llow,int date_lhigh,int flag) {
		
		Cursor c;
if (flag==0){
			 c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, "name=?", new String[] { name}
							, null, null, null, null);}
else if (flag==2){
	 c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, "Batch=?", new String[] { batch}
					, null, null, null, null);}
	
			
else if (flag==3){
	 c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, "Centre=?", new String[] { centre}
					, null, null, null, null);}

else if (flag==1){
	 c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, "per_res=?", new String[] { person_res}
					, null, null, null, null);}
	
			
else if (flag==4){
	c =	db.rawQuery("SELECT * FROM mainTable WHERE Date_log BETWEEN "+date_llow+" AND "+ date_lhigh , null);
}
			
else if (flag==5){
	 c =	db.rawQuery("SELECT * FROM mainTable WHERE date_pend BETWEEN "+date_plow+" AND "+ date_phigh , null);
}
else c=null;
			
	
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	


	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Change an existing row to be equal to new data.
/*	public boolean updateRow(long rowId,
			String name, 
			String handled,
			String Parent,
			String Batch,
			String MODE,
			String NATURE,
			String code_nature,
			String description_nature,
			String remarks_nature,
			String DETAIL,
			String REMARKS,
			String ACTION_TAKEN_FLAG,
			String ACTION_TAKEN,
			String ACTION_TAKEN_CODE,
			String ACTION_TAKEN_DESCRIPTION,
			String ACTION_TAKEN_REMARKS,
			String DATE,
			String PERSON_RESPONSIBLE,
			String Time,
			String Date_LOG
			
			
			
			
			
			) {
		String where = KEY_ROWID + "=" + rowId;

		
		 * CHANGE 4:
		 
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_NAME, name);
		newValues.put(KEY_handled, handled);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		
		
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	*/
	
	
	/////////////////////////////////////////////////////////////////////
	//	Private Helper Classes:
	/////////////////////////////////////////////////////////////////////
	
	/**
	 * Private class which handles database creation and upgrading.
	 * Used to handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			
			
			// Recreate new database:
			onCreate(_db);
		}
	}

}
