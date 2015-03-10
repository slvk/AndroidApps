/**
 * Created by VIanoshchuk on 05.03.2015.
 */


package com.mycompany.listviewdemo;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class DatabaseAdapter {
    private static final String TAG = "DBAdapter";

    // DB Fields
    public static final String KEY_ROWID = "subject_id";
    public static final int COL_ROWID = 0;
    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    public static final String KEY_NAME = "name";

    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_NAME = 1;

    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME};

    public static final String DATABASE_NAME = "MyDb";
    public static final String DATABASE_TABLE = "Subjects";
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE + "(" + KEY_ROWID + " integer  primary key autoincrement, " + KEY_NAME + " text not null);";
    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DatabaseAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DatabaseAdapter open() {
        //db = myDBHelper.getWritableDatabase();
        db = myDBHelper.getReadableDatabase();
        String name = myDBHelper.getDatabaseName();
        Log.v("MainActivity", "getWritableDatabase() "+name);
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
        Log.v("MainActivity", "myDBHelper.close()");
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public long insertRow(String name) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }


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
            Log.v("MainActivity", "OnCreate helper");
            _db.execSQL(DATABASE_CREATE_SQL);
            //_db.execSQL("insert into Subjects('name') values ('Biology')");
            Log.v("MainActivity", "OnCreate helper finished");
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}
