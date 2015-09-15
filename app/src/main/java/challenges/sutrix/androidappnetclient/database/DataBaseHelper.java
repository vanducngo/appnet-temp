package challenges.sutrix.androidappnetclient.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by root on 22/07/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    String TAG = "DataBaseHelper";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_PATH = "/data/data/challenges.sutrix.androidappnetclient/databases/";
    // Database Name
    private static final String DATABASE_NAME = "appnet_webservice";

//    public static final String TABLE_BRAND = "Brand";
//    private static final String COLUMN_ID = "Id";
//    private static final String COLUMN_NAME = "Name";
//
//    private String[] allColumns = { COLUMN_ID, COLUMN_NAME};

    Context ctx;
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }


//    // Getting single contact
//    public Contact Get_ContactDetails(String name) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_CONTACT, new String[] { KEY_ID,
//                        KEY_NAME, KEY_EMAILID, KEY_MOBILENO }, KEY_NAME + "=?",
//                new String[] { name }, null, null, null, null);
//        if (cursor != null && cursor.moveToFirst()){
//            Contact cont = new Contact(cursor.getString(1), cursor.getString(2), cursor.getString(3));
//            // return contact
//            cursor.close();
//            db.close();
//
//            return cont;
//
//        }
//        return null;
//    }

    public void CopyDataBaseFromAsset() throws IOException{
        InputStream in  = ctx.getAssets().open("appnet_webservice.sqlite");
        Log.e(TAG, "Starting copying" );
        String outputFileName = DATABASE_PATH+DATABASE_NAME;
        File databaseFile = new File(DATABASE_PATH);
        // check if databases folder exists, if not create one and its subfolders
        if (!databaseFile.exists()){
            databaseFile.mkdir();
        }

        OutputStream out = new FileOutputStream(outputFileName);

        byte[] buffer = new byte[1024];
        int length;


        while ((length = in.read(buffer))>0){
            out.write(buffer,0,length);
        }
        Log.e(TAG, "Completed");
        out.flush();
        out.close();
        in.close();

    }


    public void openDataBase () throws SQLException{
        String path = DATABASE_PATH+DATABASE_NAME;
        SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "onCreate");
        try {
            CopyDataBaseFromAsset();
        } catch (IOException e) {
            Log.d(TAG, "Excepction copy " +e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

//    public ArrayList<DataModel> searchProduct(String product) {
//        ArrayList<DataModel> rListModel = new ArrayList<DataModel>();
//
//        SQLiteDatabase database = this.getReadableDatabase();
//
//        String query = "SELECT a.Name, i.Url, p.Id FROM AliasProduct as a join Product as p on a.ProductId = p.Id join Image as i on a.ProductId = i.Id join Brand as b on p.BrandId = b.Id where a.IsMain = 'True' and p.IsActive = 'True' and a.Name like '%"+ product+"%'";
//        Cursor cursor = database.rawQuery(query,null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            DataModel tVocabulary = cursorToModel(cursor);
//            rListModel.add(tVocabulary);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
//        database.close();
//        return rListModel;
//    }
//
//
//    public ArrayList<String> getComments(long id) {
//        ArrayList<String> rListModel = new ArrayList<String>();
//
//        SQLiteDatabase database = this.getReadableDatabase();
//
//        String query = "SELECT Content FROM Comment where ProductId = " + id;
//        Cursor cursor = database.rawQuery(query,null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            String tVocabulary = cursor.getString(0);
//            rListModel.add(tVocabulary);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
//        database.close();
//        return rListModel;
//    }
//
//    public String getDescription(long id) {
//
//        SQLiteDatabase database = this.getReadableDatabase();
//        String query = "SELECT Description FROM Product where Id = " + id;
//        Cursor cursor = database.rawQuery(query, null);
//
//        cursor.moveToFirst();
//        String tVocabulary = cursor.getString(0);
//        // make sure to close the cursor
//        cursor.close();
//        database.close();
//        return tVocabulary;
//    }
//
//    private DataModel cursorToModel(Cursor cursor) {
//        DataModel rVocabulary = new DataModel();
//        rVocabulary.setName(cursor.getString(0));
//        rVocabulary.setImageUrl(cursor.getString(1));
//        rVocabulary.setId(cursor.getLong(2));
//        return rVocabulary;
//    }
}