package biz.bokhorst.xprivacy;

public class PKDConstants {
	private static final String DebugTag = new String("XPrivacyPrajit");

	public static String getDebugTag() {
		return DebugTag;
	}

	/**
	 * Database Constants Starts
	 */
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

	public static final String DATABASE_NAME = "prajit";
	public static final int DATABASE_VERSION = 1;
	
	public static final String RESTRICTIONS_TABLE_NAME = "restriction";
	public static final String USAGE_TABLE_NAME = "usage";
	public static final String SETTINGS_TABLE_NAME = "settings";

	public static final String CREATE_RESTRICTIONS_TABLE = "CREATE TABLE " + RESTRICTIONS_TABLE_NAME + "("
			+ COL_RESTRCITION_TAB_UID +" INTEGER NOT NULL,"
			+ COL_RESTRCITION_TAB_RESTRICTION + " TEXT NOT NULL,"
			+ COL_RESTRCITION_TAB_METHOD + " TEXT NOT NULL,"
			+ COL_RESTRCITION_TAB_RESTRICTED + " INTEGER NOT NULL);"; 
	public static final String CREATE_USAGE_TABLE = "CREATE TABLE " + USAGE_TABLE_NAME + "("
			+ COL_USAGE_TAB_UID + " INTEGER NOT NULL,"
			+ COL_USAGE_TAB_RESTRICTION + " TEXT NOT NULL,"
			+ COL_USAGE_TAB_METHOD + " TEXT NOT NULL,"
			+ COL_USAGE_TAB_EXTRA + " TEXT NOT NULL,"
			+ COL_USAGE_TAB_RESTRICTED + " INTEGER NOT NULL,"
			+ COL_USAGE_TAB_TIME + " INTEGER NOT NULL,"
			+ COL_USAGE_TAB_VALUE + " TEXT);";
	public static final String CREATE_SETTINGS_TABLE = "CREATE TABLE " + SETTINGS_TABLE_NAME + "("
			+ COL_SETTINGS_TAB_UID + " INTEGER NOT NULL,"
			+ COL_SETTINGS_TAB_NAME + " TEXT NOT NULL,"
			+ COL_SETTINGS_TAB_VALUE + " TEXT,"
			+ COL_SETTINGS_TAB_TYPE + " TEXT);";
	public static final String CREATE_INDEX_RESTRICTIONS_TABLE = "CREATE UNIQUE INDEX idx_pkd_restriction ON " 
			+ RESTRICTIONS_TABLE_NAME + "("
			+ COL_RESTRCITION_TAB_UID + ", "
			+ COL_RESTRCITION_TAB_RESTRICTION + ", "
			+ COL_RESTRCITION_TAB_METHOD + ");";
	public static final String CREATE_INDEX_SETTINGS_TABLE = "CREATE UNIQUE INDEX idx_pkd_setting ON " 
			+ SETTINGS_TABLE_NAME + "("
			+ COL_SETTINGS_TAB_UID + ", "
			+ COL_SETTINGS_TAB_NAME + ");";
	public static final String CREATE_INDEX_USAGE_TABLE = "CREATE UNIQUE INDEX idx_pkd_usage ON " 
			+ USAGE_TABLE_NAME + "("
			+ COL_USAGE_TAB_UID + ", "
			+ COL_USAGE_TAB_RESTRICTION + ", "
			+ COL_USAGE_TAB_METHOD + ", "
			+ COL_USAGE_TAB_TIME + ", "
			+ COL_USAGE_TAB_EXTRA + ");";
	/**
	 * Database Constants Ends
	 */
}