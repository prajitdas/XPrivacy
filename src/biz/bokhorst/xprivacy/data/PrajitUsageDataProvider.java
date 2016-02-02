package biz.bokhorst.xprivacy.data;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import edu.umbc.cs.ebiquity.mithril.command.COMMANDApplication;

public class PrajitUsageDataProvider extends ContentProvider {
	public static final String AUTHORITY = "biz.bokhorst.xprivacy.data.provider";
	public static final String PREFIX = "content://";
	public static final String SLASH = "/"; 

	//URIs
	public static final String PATH_RESTRICTION = "restriction";
	public static final String PATH_USAGE = "usage";
	public static final String PATH_SETTINGS = "settings";

	//URIs for access
	public static final Uri URI_RESTRICTION = Uri.parse(PREFIX + AUTHORITY + SLASH + PATH_RESTRICTION);
	public static final Uri URI_USAGE = Uri.parse(PREFIX + AUTHORITY + SLASH + PATH_USAGE);
	public static final Uri URI_SETTING = Uri.parse(PREFIX + AUTHORITY + SLASH + PATH_SETTINGS);

	//Uri matchers
	private static final UriMatcher uriMatcher;
	private static final int URI_TYPE_RESTRICTION = 1;
	private static final int URI_TYPE_USAGE = 2;
	private static final int URI_TYPE_SETTING = 3;
//	private static final int URI_TYPE_USAGE_UID = 4;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, PATH_RESTRICTION, URI_TYPE_RESTRICTION);
		uriMatcher.addURI(AUTHORITY, PATH_USAGE, URI_TYPE_USAGE);
		uriMatcher.addURI(AUTHORITY, PATH_SETTINGS, URI_TYPE_SETTING);
//		uriMatcher.addURI(AUTHORITY, PATH_USAGE+"/#", URI_TYPE_USAGE_UID);
	}
	
	private static HashMap<String, String> USAGE_PROJECTION_MAP;
	
	/**
	* Database specific constant declarations
	*/
	private SQLiteDatabase db;

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

	@Override
	public boolean onCreate() { //On create for content provider
		Context context = getContext();
		PrajitDBHelper dbHelper = new PrajitDBHelper(context);
		/**
		* Create a write able database which will trigger its 
		* creation if it doesn't already exist.
		*/
		db = dbHelper.getWritableDatabase();
		return (db == null)? false:true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(USAGE_TABLE_NAME);
		
		switch (uriMatcher.match(uri)) {
//			case URI_TYPE_RESTRICTION:
//				qb.setProjectionMap(USAGE_PROJECTION_MAP);
//				break;
			case URI_TYPE_USAGE:
				qb.setProjectionMap(USAGE_PROJECTION_MAP);
				break;
//			case URI_TYPE_SETTING:
//				qb.setProjectionMap(USAGE_PROJECTION_MAP);
//				break;
//			case URI_TYPE_USAGE_UID:
//				qb.appendWhere( COL_USAGE_TAB_UID + "=" + uri.getPathSegments().get(1));
//				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}

		if (sortOrder == null || sortOrder == ""){
			/** 
			* By default sort on uid
			*/
			sortOrder = COL_RESTRCITION_TAB_UID;
		}

		Cursor c = qb.query(db,	projection,	selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)){
			/**
			* Get all usage records 
			*/
			case URI_TYPE_USAGE:
			return String.format("vnd.android.cursor.dir/%s.%s", AUTHORITY, PATH_USAGE);
	
	//		/** 
	//		* Get a particular usage record
	//		*/
	//		case URI_TYPE_USAGE_UID:
	//		return "vnd.android.cursor.item/vnd.example.students";
	
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowid;
		try{
			/**
			* Add a new record
			*/
			long rowID = db.insert(USAGE_TABLE_NAME, null, values);
		} catch (SQLException e) {
            Log.e(COMMANDApplication.getDebugTag(), "Error inserting " + values, e);
            return -1;
		}
		return 1;

		/** 
		* If record is added successfully
		*/

		if (rowID > 0)
		{
			Uri _uri = ContentUris.withAppendedId(URI_USAGE, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		}
		throw new SQLException("Failed to add a record into " + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;

		switch (uriMatcher.match(uri)){
			case URI_TYPE_USAGE:
				count = db.delete(USAGE_TABLE_NAME, selection, selectionArgs);
				break;
//			case URI_TYPE_USAGE_UID:
//				String id = uri.getPathSegments().get(1);
//				count = db.delete(USAGE_TABLE_NAME, COL_USAGE_TAB_UID +  " = " + id + 
//						(!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
//				break;
			default: 
				throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count = 0;

		switch (uriMatcher.match(uri)){
			case URI_TYPE_USAGE:
				count = db.update(USAGE_TABLE_NAME, values, selection, selectionArgs);
				break;
//			case URI_TYPE_USAGE_UID:
//				count = db.update(USAGE_TABLE_NAME, values, COL_USAGE_TAB_UID + " = " + uri.getPathSegments().get(1) + 
//				(!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
//				break;
			default: 
				throw new IllegalArgumentException("Unknown URI " + uri );
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}