package android.com.androidcode.database;

import android.com.androidcode.application.TitleApplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import jsonmodels.JSONDataStore;

/**
 * Created by jiffler on 23/12/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "databasedb";
    private static final String TABLE_USER_DATA = "userdata";
    private static final String KEY_ID = "id";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_JSON_DATA = "json_data";
    private SQLiteDatabase db;
    DatabaseHelper mInstance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper() {
        super(TitleApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USER_DATA + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_TIMESTAMP + " TEXT,"
                + KEY_JSON_DATA + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    public void addJsonData(JSONDataStore dataStore) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, dataStore.getKey());
        values.put(KEY_TIMESTAMP, dataStore.getTimeStamp());
        values.put(KEY_JSON_DATA, dataStore.getJsonData());
        db.insert(TABLE_USER_DATA, null, values);
        db.close();
    }

    public JSONDataStore getJsonData(String key) {
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER_DATA, new String[]{KEY_ID,
                        KEY_TIMESTAMP, KEY_JSON_DATA}, KEY_ID + "=?",
                new String[]{key}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        JSONDataStore dataStore = new JSONDataStore(cursor.getString(0),
                cursor.getString(1), cursor.getString(2));
        cursor.close();
        db.close();
        return dataStore;
    }

    public List<JSONDataStore> getAllJsonData() {
        List<JSONDataStore> jsonDataList = new ArrayList<JSONDataStore>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER_DATA;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                JSONDataStore dataStore = new JSONDataStore(
                        cursor.getString(0), cursor.getString(1),
                        cursor.getString(2));
                jsonDataList.add(dataStore);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return jsonDataList;
    }

    public int updateJsonData(JSONDataStore dataStore) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIMESTAMP, dataStore.getTimeStamp());
        values.put(KEY_JSON_DATA, dataStore.getJsonData());
        int result = db.update(TABLE_USER_DATA, values, KEY_ID + " = ?",
                new String[]{
                        String.valueOf(dataStore.getKey())
                });
        db.close();
        return result;
    }

    public boolean containsKey(String key) {
        boolean isContainsKey = false;
        db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER_DATA;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(0).equals(key)
                        || key == cursor.getString(0)) {
                    isContainsKey = true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return isContainsKey;
    }


    public void deleteJsonData(JSONDataStore dataStore) {
        db = this.getWritableDatabase();

        db.delete(TABLE_USER_DATA, KEY_ID + " = ?",
                new String[]{String.valueOf(dataStore.getKey())});
        db.close();
    }

    public void deleteTables() {
        db = this.getWritableDatabase();
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DATA);
        db.execSQL("delete from " + TABLE_USER_DATA);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DATA);
        onCreate(db);
    }

    @Override
    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
    }
}