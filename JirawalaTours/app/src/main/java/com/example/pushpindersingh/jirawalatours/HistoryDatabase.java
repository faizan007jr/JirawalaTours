package com.example.pushpindersingh.jirawalatours;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bharat on 04/12/2018.
 */

public class HistoryDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "t&t_history_db";
    public static final String TABLE_NAME = "history_tbl";
    public static final String COL_1 = "location";
    public static final String COL_2 = "hotel";
    public static final String COL_3 = "room";
    public static final String COL_4 = "uname";
    public static final String COL_5 = "cmail";
    public static final String COL_6 = "date";

    public HistoryDatabase(Context ctx) {
        super(ctx, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(location TEXT, hotel TEXT, room TEXT, uname TEXT, cmail TEXT, date TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void wipeTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public Cursor getAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return res;
    }

    public boolean insertData(String c1, String c2, String c3, String c4, String c5, String c6) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,c1);
        contentValues.put(COL_2,c2);
        contentValues.put(COL_3,c3);
        contentValues.put(COL_4,c4);
        contentValues.put(COL_5,c5);
        contentValues.put(COL_6,c6);
        Long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
