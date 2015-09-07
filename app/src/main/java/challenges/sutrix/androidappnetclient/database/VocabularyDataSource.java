//package challenges.sutrix.androidappnetclient.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//
//import java.util.ArrayList;
//
//import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyModel;
//
///**
// * Created by root on 16/07/2015.
// */
//public class VocabularyDataSource {
//    // Database fields
//    private SQLiteDatabase database;
//
//    public static final String TABLE_VOCABULARY = "vocabulary";
//    private static final String COLUMN_ID = "_id";
//    private static final String COLUMN_WORD = "word";
//    private static final String COLUMN_TYPE = "type";
//    private static final String COLUMN_CATEGORY = "category";
//    private static final String COLUMN_MEANING_VI = "mean_vi";
//    private static final String COLUMN_MEANING_EN = "mean_en";
//    private static final String COLUMN_EXAMPLE = "example";
//    private static final String COLUMN_REMEMBER = "remember";
//    private static final String COLUMN_IMAGE = "image";
//
//    public static final String DATABASE_CREATE = "create table "
//            + TABLE_VOCABULARY + "("
//            + COLUMN_ID + " integer primary key autoincrement, "
//            + COLUMN_WORD + " text not null,"
//            + COLUMN_TYPE + " text not null,"
//            + COLUMN_CATEGORY + " text not null);"
//            + COLUMN_MEANING_VI + " text,"
//            + COLUMN_MEANING_EN + " text,"
//            + COLUMN_EXAMPLE + " text,"
//            + COLUMN_REMEMBER + " integer not null,"
//            + COLUMN_IMAGE + " BLOB,";
//
//    private DataBaseHelper dbHelper;
//    private String[] allColumns = {COLUMN_ID, COLUMN_WORD,
//            COLUMN_TYPE, COLUMN_CATEGORY,
//            COLUMN_MEANING_VI, COLUMN_MEANING_EN,
//            COLUMN_EXAMPLE, COLUMN_REMEMBER, COLUMN_IMAGE};
//
//    public VocabularyDataSource(Context context) {
//        dbHelper = new DataBaseHelper(context);
//    }
//
//    public void open() throws SQLException {
//        database = dbHelper.getWritableDatabase();
//    }
//
//    public void close() {
//        dbHelper.close();
//    }
//
//    public long insertVocabulary(String sWord, String sType,
//                                 String sCategory, String sMeanVi, String sMeanEn,
//                                 String sExample, boolean isRemembered, byte[] sImage) {
//        ContentValues values = new ContentValues();
//
//        values.put(COLUMN_WORD, sWord);
//        values.put(COLUMN_TYPE, sType);
//        values.put(COLUMN_CATEGORY, sCategory);
//        values.put(COLUMN_MEANING_VI, sMeanVi);
//        values.put(COLUMN_MEANING_EN, sMeanEn);
//        values.put(COLUMN_EXAMPLE, sExample);
//        values.put(COLUMN_REMEMBER, isRemembered);
//        values.put(COLUMN_IMAGE, sImage);
//        long insertId = database.insert(TABLE_VOCABULARY,
//                null, values);
//
//        return insertId;
//    }
//
//    public void deleteVocabulary(VocabularyModel sVocabulary) {
//        long id = sVocabulary.getId();
//        System.out.println("Bai hat da duoc xoa voi id: " + id);
//        database.delete(TABLE_VOCABULARY,
//                COLUMN_ID + " = " + id, null);
//    }
//
//    public void updateVocabulary(int sID, boolean isRemember) {
//
//        ContentValues cv = new ContentValues();
//        if (isRemember) {
//            cv.put(COLUMN_REMEMBER, 1);
//        } else {
//            cv.put(COLUMN_REMEMBER, 0);
//        }
//
//        database.update(TABLE_VOCABULARY, cv,
//                COLUMN_ID + " = " + sID, null);
//    }
//
//    public ArrayList<VocabularyModel> getAllVocabulary() {
//        ArrayList<VocabularyModel> rListVocabulary = new ArrayList<VocabularyModel>();
//
//        Cursor cursor = database.query(TABLE_VOCABULARY,
//                allColumns, null, null, null, null, null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            VocabularyModel tVocabulary = cursorToVocabulary(cursor);
//            rListVocabulary.add(tVocabulary);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
//        return rListVocabulary;
//    }
//
//    private VocabularyModel cursorToVocabulary(Cursor cursor) {
//        VocabularyModel rVocabulary = new VocabularyModel();
//        rVocabulary.setId(cursor.getLong(0));
//        rVocabulary.setWord(cursor.getString(1));
//        rVocabulary.setType(cursor.getString(2));
//        rVocabulary.setCategory(cursor.getString(3));
//        rVocabulary.setMeanVietnamese(cursor.getString(4));
//        rVocabulary.setMeanEnglish(cursor.getString(5));
//        rVocabulary.setExample(cursor.getString(6));
//
//        int isRemember = cursor.getInt(7);
//        if (isRemember == 1) {
//            rVocabulary.setRemember(true);
//        } else {
//            rVocabulary.setRemember(false);
//        }
//        rVocabulary.setImage(cursor.getBlob(8));
//        return rVocabulary;
//    }
//
//    public VocabularyModel getVocabulary(String sWord) {
//        Cursor cursor = database.query(TABLE_VOCABULARY,
//                allColumns, COLUMN_WORD + " = '" + sWord
//                        + "'", null, null, null, null);
//        if (!cursor.moveToFirst()) {
//            return null;
//        }
//        VocabularyModel newVocabulary = cursorToVocabulary(cursor);
//        cursor.close();
//        return newVocabulary;
//    }
//
//    public VocabularyModel getVocabularyById(int id) {
//        Cursor cursor = database.query(TABLE_VOCABULARY,
//                allColumns, COLUMN_ID + " = " + id, null, null,
//                null, null);
//        if (!cursor.moveToFirst()) {
//            return null;
//        }
//        VocabularyModel rVocabulary = cursorToVocabulary(cursor);
//        cursor.close();
//        return rVocabulary;
//    }
//}