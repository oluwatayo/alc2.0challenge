package com.oluwatayo.apps.medmanager.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 4/16/18.
 */

public class MedDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Medications.db";
    public static final int DATABASE_VERSION = 1;

    public static final String SQL_CREATE = "";

    public MedDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+DATABASE_NAME);
        sqLiteDatabase.execSQL(SQL_CREATE);
    }
}
