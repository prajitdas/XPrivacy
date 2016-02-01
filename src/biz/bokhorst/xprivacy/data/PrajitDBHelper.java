package biz.bokhorst.xprivacy.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
* Helper class that actually creates and manages 
* the provider's underlying data repository.
*/
public class PrajitDBHelper extends SQLiteOpenHelper {
	//Restriction table column names
	public static final String COL_RESTRCITION_TAB_UID = "uid";
	public static final String COL_RESTRCITION_TAB_RESTRICTION = "restriction";
	public static final String COL_RESTRCITION_TAB_METHOD = "method";
	public static final String COL_RESTRCITION_TAB_RESTRICTED = "restricted";

	//Usage table column names
	public static final String COL_USAGE_TAB_UID = "uid";
	public static final String COL_USAGE_TAB_RESTRICTION = "restriction";
	public static final String COL_USAGE_TAB_METHOD = "method";
	public static final String COL_USAGE_TAB_EXTRA = "extra";
	public static final String COL_USAGE_TAB_RESTRICTED = "restricted";
	public static final String COL_USAGE_TAB_TIME = "time";
	public static final String COL_USAGE_TAB_VALUE = "value";

	//Settings table column names
	public static final String COL_SETTINGS_TAB_UID = "uid";
	public static final String COL_SETTINGS_TAB_NAME = "name";
	public static final String COL_SETTINGS_TAB_VALUE = "value";
	public static final String COL_SETTINGS_TAB_TYPE = "type";

	public static final String DATABASE_NAME = "XPrivacyDB";
	public static final int DATABASE_VERSION = 1;
	
	public static final String RESTRICTIONS_TABLE_NAME = "restriction";
	public static final String USAGE_TABLE_NAME = "usage";
	public static final String SETTINGS_TABLE_NAME = "settings";

	public static final String CREATE_RESTRICTIONS_TABLE = "CREATE TABLE restriction ("
			+ COL_RESTRCITION_TAB_UID +" INTEGER NOT NULL,"
			+ COL_RESTRCITION_TAB_RESTRICTION + " TEXT NOT NULL,"
			+ COL_RESTRCITION_TAB_METHOD + " TEXT NOT NULL,"
			+ COL_RESTRCITION_TAB_RESTRICTED + " INTEGER NOT NULL);"; 
	public static final String CREATE_USAGE_TABLE = "CREATE TABLE usage ("
			+ COL_USAGE_TAB_UID + " INTEGER NOT NULL,"
			+ COL_USAGE_TAB_RESTRICTION + " TEXT NOT NULL,"
			+ COL_USAGE_TAB_METHOD + " TEXT NOT NULL,"
			+ COL_USAGE_TAB_EXTRA + " TEXT NOT NULL,"
			+ COL_USAGE_TAB_RESTRICTED + " INTEGER NOT NULL,"
			+ COL_USAGE_TAB_TIME + " INTEGER NOT NULL,"
			+ COL_USAGE_TAB_VALUE + " TEXT);";
	public static final String CREATE_SETTINGS_TABLE = "CREATE TABLE settings ("
			+ COL_SETTINGS_TAB_UID + " INTEGER NOT NULL,"
			+ COL_SETTINGS_TAB_NAME + " TEXT NOT NULL,"
			+ COL_SETTINGS_TAB_VALUE + " TEXT,"
			+ COL_SETTINGS_TAB_TYPE + " TEXT);";

	public PrajitDBHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) { //On create for database
		db.execSQL(CREATE_RESTRICTIONS_TABLE);
		db.execSQL(CREATE_USAGE_TABLE);
		db.execSQL(CREATE_SETTINGS_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + RESTRICTIONS_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + USAGE_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + SETTINGS_TABLE_NAME);
		onCreate(db);
	}		
}