package com.example.iamsafe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PoliceDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PoliceDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_POLICE = "police";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_MOBILE_NUMBER = "mobile_number";

    public PoliceDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_POLICE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_MOBILE_NUMBER + " TEXT);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POLICE);
        onCreate(db);
    }

    public boolean insertData(String username, String password, String mobileNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_MOBILE_NUMBER, mobileNumber);

        long result = db.insert(TABLE_POLICE, null, values);
        return result != -1;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_POLICE +
                " WHERE " + COLUMN_USERNAME + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_POLICE +
                " WHERE " + COLUMN_USERNAME + " =? AND " + COLUMN_PASSWORD + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean loginSuccessful = cursor.getCount() > 0;
        cursor.close();
        return loginSuccessful;
    }
}
