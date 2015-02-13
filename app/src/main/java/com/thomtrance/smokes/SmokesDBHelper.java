package com.thomtrance.smokes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thom on 1/26/2015.
 */
public class SmokesDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="smokes.db";
    public static final int DATABASE_VERSION=3;

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SmokeEntry.TABLE_NAME + " (" +
                    SmokeEntry._ID + " INTEGER PRIMARY KEY," +
                    SmokeEntry.COLUMN_NAME_SMOKE_TIME + TEXT_TYPE + COMMA_SEP +
                    SmokeEntry.COLUMN_NAME_SMOKE_DATE + TEXT_TYPE + COMMA_SEP +
                    SmokeEntry.COLUMN_NAME_SMOKE_LOCATION+ TEXT_TYPE +" )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SmokeEntry.TABLE_NAME;


    public SmokesDBHelper(Context c){

        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    };

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
