package biz.bokhorst.xprivacy.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import biz.bokhorst.xprivacy.PKDConstants;
import biz.bokhorst.xprivacy.PUsage;

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

	/**
	 * Database creation constructor
	 * @param context
	 */
	public PrajitDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	
	/**
	 * method to insert into application table the application
	 * @param db
	 * @param anAppInfo
	 * @return
	 */
	public int addUsage(SQLiteDatabase db, PUsage aPUsage) {
		ContentValues values = new ContentValues();
		values.put(COL_USAGE_TAB_UID, aPUsage.getUid());
		values.put(COL_USAGE_TAB_RESTRICTION, aPUsage.getRestrictionName());
		values.put(COL_USAGE_TAB_METHOD, aPUsage.getMethodName());
		values.put(COL_USAGE_TAB_EXTRA, aPUsage.getExtra());
		values.put(COL_USAGE_TAB_RESTRICTED, aPUsage.isRestricted());
		values.put(COL_USAGE_TAB_TIME, aPUsage.getTime());
		values.put(COL_USAGE_TAB_VALUE, aPUsage.getValue());
		try{
			db.insert(USAGE_TABLE_NAME, null, values);
		} catch (SQLException e) {
			Log.e(PKDConstants.getDebugTag(), "Error inserting " + values, e);
			return -1;
		}
		return 1;
	}
	
	/**
	 * method to delete a row from a table based on the identifier 
	 * @param db
	 * @param anAppInfo
	 */
	public void deleteUsage(SQLiteDatabase db, PUsage aPUsage) {
		db.delete(USAGE_TABLE_NAME, COL_USAGE_TAB_UID + " = ?",
				new String[] { String.valueOf(aPUsage.getUid()) });
	}

	/**
	 * Getting all policies
	 * @param db
	 * @return
	 */
	public ArrayList<PUsage> findAllUsages(SQLiteDatabase db) {
		ArrayList<PUsage> pUsages = new ArrayList<PUsage>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + USAGE_TABLE_NAME + ";";
		try{
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					PUsage pUsage = new PUsage();
					pUsage.setUid(Integer.parseInt(cursor.getString(0)));
					pUsage.setRestrictionName(cursor.getString(1));
					pUsage.setMethodName(cursor.getString(2));
					pUsage.setExtra(cursor.getString(3));
					pUsage.setRestricted(Boolean.parseBoolean(cursor.getString(4)));
					pUsage.setTime(Long.parseLong(cursor.getString(5)));
					pUsage.setValue(cursor.getString(6));
					
					// Adding policies to list
					pUsages.add(pUsage);
				} while (cursor.moveToNext());
			}
		} catch(SQLException e) {
	        throw new SQLException("Could not find " + e);
		}
		// return policy rules list
		return pUsages;
	}
	
	/**
	 * table creation happens in onCreate this method also loads the default policies
	 */
	@Override
	public void onCreate(SQLiteDatabase db) { //On create for database
		db.execSQL(CREATE_RESTRICTIONS_TABLE);
		db.execSQL(CREATE_USAGE_TABLE);
		db.execSQL(CREATE_SETTINGS_TABLE);
		Log.v(PKDConstants.getDebugTag(), "I came to PrajitDBHelper.onCreate()");								
	}
	
	/**
	 * method to update single application
	 * @param appName
	 */
	public int updateUsage(SQLiteDatabase db, PUsage aPUsage) {
		ContentValues values = new ContentValues();
		values.put(COL_USAGE_TAB_UID, aPUsage.getUid());
		values.put(COL_USAGE_TAB_RESTRICTION, aPUsage.getRestrictionName());
		values.put(COL_USAGE_TAB_METHOD, aPUsage.getMethodName());
		values.put(COL_USAGE_TAB_EXTRA, aPUsage.getExtra());
		values.put(COL_USAGE_TAB_RESTRICTED, aPUsage.isRestricted());
		values.put(COL_USAGE_TAB_TIME, aPUsage.getTime());
		values.put(COL_USAGE_TAB_VALUE, aPUsage.getValue());
		return db.update(USAGE_TABLE_NAME, values, COL_USAGE_TAB_UID + " = ?", 
				new String[] { String.valueOf(aPUsage.getUid()) });
	}

	/**
	 * Method to delete all data from the database; Very dangerous
	 * @param db
	 */
	public void deleteAllData(SQLiteDatabase db) {
		dropDBObjects(db);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(PrajitDBHelper.class.getName(), 
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ". Old data will be destroyed");
		dropDBObjects(db);
	}

	/**
	 * Point to note!! You cannot drop an index before you drop a table. If it seems a bit counter intuitive then you are thinking is wrong.
	 * @param db
	 */
	private void dropDBObjects(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " +  CREATE_RESTRICTIONS_TABLE);
		db.execSQL("DROP INDEX IF EXISTS " + CREATE_USAGE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " +  CREATE_SETTINGS_TABLE);
		onCreate(db);
	}
}