package com.example.iamsafe;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GuardianDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "guardian.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_GUARDIANS = "guardians";
    private static final String COLUMN_GUARDIAN_ID = "id";
    private static final String COLUMN_GUARDIAN_NAME = "name";
    private static final String COLUMN_GUARDIAN_MOBILE_NUMBER = "mobile_number";

    public GuardianDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_GUARDIANS + " (" +
                COLUMN_GUARDIAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GUARDIAN_NAME + " TEXT, " +
                COLUMN_GUARDIAN_MOBILE_NUMBER + " TEXT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableStatement = "DROP TABLE IF EXISTS " + TABLE_GUARDIANS;
        db.execSQL(dropTableStatement);
        onCreate(db);
    }

    public void insertGuardian(Guardian guardian) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GUARDIAN_NAME, guardian.getName());
        values.put(COLUMN_GUARDIAN_MOBILE_NUMBER, guardian.getMobileNumber());
        db.insert(TABLE_GUARDIANS, null, values);
        db.close();
    }

    public List<Guardian> getAllGuardians() {
        List<Guardian> guardians = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GUARDIANS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_GUARDIAN_ID));
                @SuppressLint("Range")                 String name = cursor.getString(cursor.getColumnIndex(COLUMN_GUARDIAN_NAME));
                @SuppressLint("Range") String mobileNumber = cursor.getString(cursor.getColumnIndex(COLUMN_GUARDIAN_MOBILE_NUMBER));

                Guardian guardian = new Guardian(id, name, mobileNumber);
                guardians.add(guardian);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return guardians;
    }
}
