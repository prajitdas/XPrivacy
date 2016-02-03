package biz.bokhorst.xprivacy.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

	private Context context;

	/**
	 * Database creation constructor
	 * @param context
	 */
	public PrajitDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.setContext(context); 
	}
	
	public String getDatabaseName() {
		return DATABASE_NAME;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	public static int getDatabaseVersion() {
		return DATABASE_VERSION;
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
	public int addUsage(SQLiteDatabase db, AppInfo anAppInfo) {
		ContentValues values = new ContentValues();
		values.put(APPLABEL, anAppInfo.getLabel());
		values.put(APPPACK, anAppInfo.getPackageName());
		values.put(APPPERM, anAppInfo.getPermissions());
		try{
			db.insert(APPLICATION_TABLE_NAME, null, values);
		} catch (SQLException e) {
            Log.e(COMMANDApplication.getDebugTag(), "Error inserting " + values, e);
            return -1;
		}
		return 1;
	}
	
	/**
	 * method to insert into policy table the policy
	 * @param db
	 * @param aPolicyRule
	 * @return
	 */
	public int addPolicy(SQLiteDatabase db, PolicyInfo aPolicyRule) {
		ContentValues values = new ContentValues();
		values.put(POLAPPID, aPolicyRule.getAppId());
		values.put(POLPROVID, aPolicyRule.getProvId());
		values.put(CONTEXTLOC, aPolicyRule.getUserContext().getLocation());
		values.put(CONTEXTACT, aPolicyRule.getUserContext().getActivity());
		values.put(CONTEXTTIME, aPolicyRule.getUserContext().getTime());
		values.put(CONTEXTID, aPolicyRule.getUserContext().getIdentity());
		if(aPolicyRule.isRule())
			values.put(POLICY, 1);
		else
			values.put(POLICY, 0);
		values.put(POLACCLVL, aPolicyRule.getAccessLevel());
		try {
			db.insert(POLICY_TABLE_NAME, null, values);
		} catch (SQLException e) {
	        Log.e(COMMANDApplication.getDebugTag(), "Error inserting " + values, e);
	        return -1;
		}
		return 1;
	}

	/**
	 * method to insert into provider table the provider
	 * @param db
	 * @param aProvider
	 * @return
	 */
	public int addProvider(SQLiteDatabase db, ProvInfo aProvider) {
		ContentValues values = new ContentValues();
		values.put(PROVLABEL, aProvider.getLabel());
		values.put(PROVPRO, aProvider.getProviderName());
		values.put(PROVAUTH, aProvider.getAuthority());
		values.put(PROVREADPERM, aProvider.getReadPermission());
		values.put(PROVWRITEPERM, aProvider.getWritePermission());
		try {
			db.insert(PROVIDER_TABLE_NAME, null, values);
		} catch (SQLException e) {
	        Log.e(COMMANDApplication.getDebugTag(), "Error inserting " + values, e);
	        return -1;
		}
		return 1;
	}


	/**
	 * method to insert into service table the service
	 * @param db
	 * @param aService
	 * @return
	 */
	public int addService(SQLiteDatabase db, ServInfo aService) {
		ContentValues values = new ContentValues();
		values.put(SERVLABEL, aService.getServiceLabel());
		values.put(SERVNAME, aService.getServiceName());
		values.put(SERVENABLED, aService.isEnabled());
		values.put(SERVEXPORTED, aService.isExported());
		values.put(SERVPROCESS, aService.getProcess());
		values.put(SERVPERM, aService.getPermission());
		try {
			db.insert(SERVICE_TABLE_NAME, null, values);
		} catch (SQLException e) {
	        Log.e(COMMANDApplication.getDebugTag(), "Error inserting " + values, e);
	        return -1;
		}
		return 1;
	}
	
	/**
	 * method to delete a row from a table based on the identifier 
	 * @param db
	 * @param anAppInfo
	 */
	public void deleteApplication(SQLiteDatabase db, AppInfo anAppInfo) {
		db.delete(APPLICATION_TABLE_NAME, APPID + " = ?",
				new String[] { String.valueOf(anAppInfo.getId()) });
	}

	/**
	 * method to delete a row from a table based on the identifier
	 * @param db
	 * @param aPolicyRule
	 */
	public void deletePolicy(SQLiteDatabase db, PolicyInfo aPolicyRule) {
		db.delete(POLICY_TABLE_NAME, POLID + " = ?",
				new String[] { String.valueOf(aPolicyRule.getId()) });
	}

	/**
	 * method to delete a row from a table based on the identifier
	 * @param db
	 * @param aProvider
	 */
	public void deleteProvider(SQLiteDatabase db, ProvInfo aProvider) {
		db.delete(PROVIDER_TABLE_NAME, PROVID + " = ?",
				new String[] { String.valueOf(aProvider.getId()) });
	}

	/**
	 * method to delete a row from a table based on the identifier
	 * @param db
	 * @param aService
	 */
	public void deleteProvider(SQLiteDatabase db, ServInfo aService) {
		db.delete(SERVICE_TABLE_NAME, SERVID + " = ?",
				new String[] { String.valueOf(aService.getId()) });
	}

	/**
	 * Finds a policy based on the application and the provider being accessed
	 * @param db
	 * @param appPack
	 * @param provAuth
	 * @return
	 */
	public PolicyInfo findPolicyByAppProv(SQLiteDatabase db, String appPack, String provAuth) {
		// Select Policy Query
		String selectQuery = "SELECT "+
					POLICY_TABLE_NAME + "." + POLID + "," +
					APPLICATION_TABLE_NAME + "." + APPID + "," +
					APPLICATION_TABLE_NAME + "." + APPLABEL + "," +
					PROVIDER_TABLE_NAME + "." + PROVID + "," +
					PROVIDER_TABLE_NAME + "." + PROVLABEL + "," +
					POLICY_TABLE_NAME + "." + CONTEXTLOC + "," +
					POLICY_TABLE_NAME + "." + CONTEXTACT + "," +
					POLICY_TABLE_NAME + "." + CONTEXTTIME + "," +
					POLICY_TABLE_NAME + "." + CONTEXTID + "," +
					POLICY_TABLE_NAME + "." + POLICY + "," +
					POLICY_TABLE_NAME + "." + POLACCLVL + 
					" FROM " + 
					POLICY_TABLE_NAME +
					" LEFT JOIN " + APPLICATION_TABLE_NAME + 
					" ON " + POLICY_TABLE_NAME + "." + POLAPPID + 
					" = " +  APPLICATION_TABLE_NAME + "." + APPID +
					" LEFT JOIN " + PROVIDER_TABLE_NAME + 
					" ON " + POLICY_TABLE_NAME + "." + POLPROVID + 
					" = " +  PROVIDER_TABLE_NAME + "." + PROVID + 
					" WHERE "  +  
					APPLICATION_TABLE_NAME + "." + APPPACK + " = '" + appPack + "' AND " +
					PROVIDER_TABLE_NAME + "." + PROVAUTH + " = '" + provAuth + 
					"';";

		PolicyInfo policyInfo = new PolicyInfo();
		
		try{
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				policyInfo.setId(Integer.parseInt(cursor.getString(0)));
				policyInfo.setAppId(Integer.parseInt(cursor.getString(1)));
				policyInfo.setAppLabel(cursor.getString(2));
				policyInfo.setProvId(Integer.parseInt(cursor.getString(3)));
				policyInfo.setProvLabel(cursor.getString(4));
				
				UserContext contextCondition = new UserContext(cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
				policyInfo.setUserContext(contextCondition);
				
				if(Integer.parseInt(cursor.getString(9)) == 1)
					policyInfo.setRule(true);
				else
					policyInfo.setRule(false);
				policyInfo.setAccessLevel(Integer.parseInt(cursor.getString(10)));
			}
		} catch(SQLException e) {
            throw new SQLException("Could not find " + e);
		}
		return policyInfo;
	}

	/**
	 * Finds a policy based on the policy id
	 * @param db
	 * @param id
	 * @return
	 */
	public PolicyInfo findPolicyByID(SQLiteDatabase db, int id) {
		// Select Policy Query
		String selectQuery = "SELECT "+
					POLICY_TABLE_NAME + "." + POLID + "," +
					APPLICATION_TABLE_NAME + "." + APPID + "," +
					APPLICATION_TABLE_NAME + "." + APPLABEL + "," +
					PROVIDER_TABLE_NAME + "." + PROVID + "," +
					PROVIDER_TABLE_NAME + "." + PROVLABEL + "," +
					POLICY_TABLE_NAME + "." + CONTEXTLOC + "," +
					POLICY_TABLE_NAME + "." + CONTEXTACT + "," +
					POLICY_TABLE_NAME + "." + CONTEXTTIME + "," +
					POLICY_TABLE_NAME + "." + CONTEXTID + "," +
					POLICY_TABLE_NAME + "." + POLICY + "," +
					POLICY_TABLE_NAME + "." + POLACCLVL + 
					" FROM " + 
					POLICY_TABLE_NAME +
					" LEFT JOIN " + APPLICATION_TABLE_NAME + 
					" ON " + POLICY_TABLE_NAME + "." + POLAPPID + 
					" = " +  APPLICATION_TABLE_NAME + "." + APPID +
					" LEFT JOIN " + PROVIDER_TABLE_NAME + 
					" ON " + POLICY_TABLE_NAME + "." + POLPROVID + 
					" = " +  PROVIDER_TABLE_NAME + "." + PROVID + 
					" WHERE "  +  
					POLICY_TABLE_NAME + "." + POLID + " = " + id + ";";

		PolicyInfo policyInfo = new PolicyInfo();
		
		try{
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				policyInfo.setId(Integer.parseInt(cursor.getString(0)));
				policyInfo.setAppId(Integer.parseInt(cursor.getString(1)));
				policyInfo.setAppLabel(cursor.getString(2));
				policyInfo.setProvId(Integer.parseInt(cursor.getString(3)));
				policyInfo.setProvLabel(cursor.getString(4));
				
				UserContext contextCondition = new UserContext(cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
				policyInfo.setUserContext(contextCondition);
				
				if(Integer.parseInt(cursor.getString(9)) == 1)
					policyInfo.setRule(true);
				else
					policyInfo.setRule(false);
				policyInfo.setAccessLevel(Integer.parseInt(cursor.getString(10)));
			}
		} catch(SQLException e) {
	        throw new SQLException("Could not find " + e);
		}
		return policyInfo;
	}

	/**
	 * Getting all policies
	 * @param db
	 * @return
	 */
	public ArrayList<PolicyInfo> findAllPolicies(SQLiteDatabase db) {
		ArrayList<PolicyInfo> policyInfos = new ArrayList<PolicyInfo>();
		// Select All Query
		String selectQuery = "SELECT "+
					POLICY_TABLE_NAME + "." + POLID + "," +
					APPLICATION_TABLE_NAME + "." + APPID + "," +
					APPLICATION_TABLE_NAME + "." + APPLABEL + "," +
					PROVIDER_TABLE_NAME + "." + PROVID + "," +
					PROVIDER_TABLE_NAME + "." + PROVLABEL + "," +
					POLICY_TABLE_NAME + "." + CONTEXTLOC + "," +
					POLICY_TABLE_NAME + "." + CONTEXTACT + "," +
					POLICY_TABLE_NAME + "." + CONTEXTTIME + "," +
					POLICY_TABLE_NAME + "." + CONTEXTID + "," +
					POLICY_TABLE_NAME + "." + POLICY + "," +
					POLICY_TABLE_NAME + "." + POLACCLVL + 
					" FROM " + 
					POLICY_TABLE_NAME +
					" LEFT JOIN " + APPLICATION_TABLE_NAME + 
					" ON " + POLICY_TABLE_NAME + "." + POLAPPID + 
					" = " +  APPLICATION_TABLE_NAME + "." + APPID +
					" LEFT JOIN " + PROVIDER_TABLE_NAME + 
					" ON " + POLICY_TABLE_NAME + "." + POLPROVID + 
					" = " +  PROVIDER_TABLE_NAME + "." + PROVID + ";";

		try{
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					PolicyInfo policyInfo = new PolicyInfo();
					policyInfo.setId(Integer.parseInt(cursor.getString(0)));
					policyInfo.setAppId(Integer.parseInt(cursor.getString(1)));
					policyInfo.setAppLabel(cursor.getString(2));
					policyInfo.setProvId(Integer.parseInt(cursor.getString(3)));
					policyInfo.setProvLabel(cursor.getString(4));
					
					UserContext contextCondition = new UserContext(cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
					policyInfo.setUserContext(contextCondition);
					
					if(Integer.parseInt(cursor.getString(9)) == 1)
						policyInfo.setRule(true);
					else
						policyInfo.setRule(false);
					policyInfo.setAccessLevel(Integer.parseInt(cursor.getString(10)));

					// Adding policies to list
					policyInfos.add(policyInfo);
				} while (cursor.moveToNext());
			}
		} catch(SQLException e) {
	        throw new SQLException("Could not find " + e);
		}
		// return policy rules list
		return policyInfos;
	}
	
	/**
	 * Getting all providers
	 * @param db
	 * @return
	 */
	public ArrayList<ProvInfo> findAllProviders(SQLiteDatabase db) {
		ArrayList<ProvInfo> provInfos = new ArrayList<ProvInfo>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + PROVIDER_TABLE_NAME + ";";

		try{
			Cursor cursor = db.rawQuery(selectQuery, null);
	
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					ProvInfo provInfo = new ProvInfo(
							Integer.parseInt(cursor.getString(0)),
							cursor.getString(1),
							cursor.getString(2),
							cursor.getString(3),
							cursor.getString(4),
							cursor.getString(5));
					Log.v(COMMANDApplication.getDebugTag(), provInfo.toString());
					// Adding providers to list
					provInfos.add(provInfo);
				} while (cursor.moveToNext());
			}
		} catch(SQLException e) {
	        throw new SQLException("Could not find " + e);
		}
		// return policy rules list
		return provInfos;
	}

	/**
	 * Getting all services
	 * @param db
	 * @return
	 */
	public ArrayList<ServInfo> findAllServices(SQLiteDatabase db) {
		ArrayList<ServInfo> services = new ArrayList<ServInfo>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + SERVICE_TABLE_NAME + ";";

		try{
			Cursor cursor = db.rawQuery(selectQuery, null);
	
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					ServInfo service = new ServInfo(
							Integer.parseInt(cursor.getString(0)),
							cursor.getString(1),
							cursor.getString(2),
							((Integer.parseInt(cursor.getString(3)) == 1) ? true : false),
							((Integer.parseInt(cursor.getString(4)) == 1) ? true : false),
							cursor.getString(5),
							cursor.getString(6));
					Log.v(COMMANDApplication.getDebugTag(), service.toString());
					// Adding providers to list
					services.add(service);
				} while (cursor.moveToNext());
			}
		} catch(SQLException e) {
	        throw new SQLException("Could not find " + e);
		}
		// return policy rules list
		return services;
	}
	
	/**
	 * Getting all applications
	 * @param db
	 * @return
	 */
	public ArrayList<AppInfo> findAllApplications(SQLiteDatabase db) {
		ArrayList<AppInfo> apps = new ArrayList<AppInfo>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + APPLICATION_TABLE_NAME + ";";

		try{
			Cursor cursor = db.rawQuery(selectQuery, null);
	
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					AppInfo app = new AppInfo(
							Integer.parseInt(cursor.getString(0)),
							cursor.getString(1),
							cursor.getString(2),
							cursor.getString(3));
					// Adding applications to list
					apps.add(app);
				} while (cursor.moveToNext());
			}
		} catch(SQLException e) {
	        throw new SQLException("Could not find " + e);
		}
		// return applications list
		return apps;
	}

	/**
	 * table creation happens in onCreate this method also loads the default policies
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_APPLICATION_TABLE);
		db.execSQL(CREATE_APPLICATIONS_INDEX);
		db.execSQL(CREATE_PROVIDER_TABLE);
		db.execSQL(CREATE_PROVIDERS_INDEX);
		db.execSQL(CREATE_SERVICE_TABLE);
		db.execSQL(CREATE_SERVICES_INDEX);
		db.execSQL(CREATE_POLICY_TABLE);
		//The following method loads the database with the default data on creation of the database
		loadDefaultPoliciesIntoDB(db);
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
		Log.w(PolicyDBHelper.class.getName(), 
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ". Old data will be destroyed");
		dropDBObjects(db);
	}

	/**
	 * Point to note!! You cannot drop an index before you drop a table. If it seems a bit counter intuitive then you are thinking is wrong.
	 * @param db
	 */
	private void dropDBObjects(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " +  APPLICATION_TABLE_NAME);
		db.execSQL("DROP INDEX IF EXISTS " + APPLICATION_TABLE_INDEX);
		db.execSQL("DROP TABLE IF EXISTS " +  PROVIDER_TABLE_NAME);
		db.execSQL("DROP INDEX IF EXISTS " + PROVIDER_TABLE_INDEX);
		db.execSQL("DROP TABLE IF EXISTS " + SERVICE_TABLE_NAME);
		db.execSQL("DROP INDEX IF EXISTS " + SERVICE_TABLE_INDEX);
		db.execSQL("DROP TABLE IF EXISTS " +  POLICY_TABLE_NAME);
		onCreate(db);
	}
	/**
	 * method to update single application
	 * @param appName
	 */
	public int updateApplication(SQLiteDatabase db, AppInfo anAppInfo) {
		ContentValues values = new ContentValues();
		values.put(APPLABEL, anAppInfo.getLabel());
		values.put(APPPACK, anAppInfo.getPackageName());
		values.put(APPPERM, anAppInfo.getPermissions());
		return db.update(APPLICATION_TABLE_NAME, values, APPID + " = ?", 
				new String[] { String.valueOf(anAppInfo.getId()) });
	}
	
	/**
	 * method to update single policy
	 * @param aPolicyRule update the policy value
	 */
	public int updatePolicyRule(SQLiteDatabase db, PolicyInfo aPolicyRule) {
		ContentValues values = new ContentValues();
		values.put(POLAPPID, aPolicyRule.getAppId());
		values.put(POLPROVID, aPolicyRule.getProvId());
		values.put(CONTEXTLOC, aPolicyRule.getUserContext().getLocation());
		values.put(CONTEXTACT, aPolicyRule.getUserContext().getActivity());
		values.put(CONTEXTTIME, aPolicyRule.getUserContext().getTime());
		values.put(CONTEXTID, aPolicyRule.getUserContext().getIdentity());
		if(aPolicyRule.isRule())
			values.put(POLICY, 1);
		else
			values.put(POLICY, 0);
		values.put(POLACCLVL, aPolicyRule.getAccessLevel());
		return db.update(POLICY_TABLE_NAME, values, POLID + " = ?", 
				new String[] { String.valueOf(aPolicyRule.getId()) });
	}

	/**
	 * method to update single provider
	 * @param db
	 * @param aProvider
	 * @return
	 */
	public int updateProvider(SQLiteDatabase db, ProvInfo aProvider) {
		ContentValues values = new ContentValues();
		values.put(PROVLABEL, aProvider.getLabel());
		values.put(PROVPRO, aProvider.getProviderName());
		values.put(PROVAUTH, aProvider.getAuthority());
		values.put(PROVREADPERM, aProvider.getReadPermission());
		values.put(PROVWRITEPERM, aProvider.getWritePermission());
		return db.update(PROVIDER_TABLE_NAME, values, PROVID + " = ?", 
				new String[] { String.valueOf(aProvider.getId()) });
	}

	/**
	 * method to update single service
	 * @param db
	 * @param aProvider
	 * @return
	 */
	public int updateService(SQLiteDatabase db, ServInfo aService) {
		ContentValues values = new ContentValues();
		values.put(SERVLABEL, aService.getServiceLabel());
		values.put(SERVNAME, aService.getServiceName());
		values.put(SERVENABLED, aService.isEnabled());
		values.put(SERVEXPORTED, aService.isExported());
		values.put(SERVPROCESS, aService.getProcess());
		values.put(SERVPERM, aService.getPermission());
		return db.update(SERVICE_TABLE_NAME, values, SERVID + " = ?", 
				new String[] { String.valueOf(aService.getId()) });
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