package challenges.sutrix.androidappnetclient.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by root on 16/07/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    // private static final String ENCODING = "UTF-8";

    private static final String DATABASE_NAME = "appnet.db";
    private static final int DATABASE_VERSION = 1;
    // Database creation sql statement


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(VocabularyDataSource.DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DataBaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + VocabularyDataSource.TABLE_VOCABULARY);
        onCreate(db);
    }

}