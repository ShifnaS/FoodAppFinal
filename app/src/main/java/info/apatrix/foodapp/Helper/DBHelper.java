package info.apatrix.foodapp.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;
    public Context context;
    // Database Name
    private static final String DATABASE_NAME = "foodapp.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String q="CREATE TABLE tbl_order(ID INTEGER PRIMARY KEY AUTOINCREMENT,pid INTEGER, name TEXT,price INTEGER,quantity INTEGER)";
        db.execSQL(q);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS tbl_notification");
        // Create tables again
        onCreate(db);

    }
}