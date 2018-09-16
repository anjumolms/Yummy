package com.example.dell.yummy.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dell.yummy.Constants;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "UserDb";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE login_details " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " + "userid INTEGER, " +
                "token TEXT ) ";

        Log.d(TAG, "DB: OnCreateSqlite.......");
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS login_details";
        db.execSQL(sql);
        Log.d(TAG, "DB: OnUpgrade.......");
        onCreate(db);
    }

    public void insertLoginDetails(HashMap<String, String> queryValues) {
        Log.d(TAG, "DB: InsertLoginDetails.......");
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (queryValues.containsKey(Constants.COLOUM_USER_NAME)) {
            values.put(Constants.COLOUM_USER_NAME, queryValues.get(Constants.COLOUM_USER_NAME));
        }
        if (queryValues.containsKey(Constants.COLOUM_USER_TOKEN)) {
            values.put(Constants.COLOUM_USER_TOKEN, queryValues.get(Constants.COLOUM_USER_TOKEN));
        }
        database.insert(Constants.TABLE_NAME, null, values);
        database.close();
    }

    public String getToken(int id) {
        String token = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * " + " FROM " + Constants.TABLE_NAME +
                " LIMIT 1";

        Cursor c = db.rawQuery(query, null);
        if (c!= null && c.moveToFirst()) {
            c.moveToFirst();
            token = c.getString(3);
        }
        Log.d(TAG, "getToken: " + token);
        c.close();
        db.close();
        return token;
    }
}
