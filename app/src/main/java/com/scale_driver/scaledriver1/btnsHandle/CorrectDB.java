package com.scale_driver.scaledriver1.btnsHandle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CorrectDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "correctDB";
    public static final String TABLE_BTNS = "btns";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_VALUE1 = "value1";
    public static final String KEY_VALUE2 = "value2";



    public CorrectDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_BTNS + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text,"
                + KEY_VALUE1 + " integer," + KEY_VALUE2 + " integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
