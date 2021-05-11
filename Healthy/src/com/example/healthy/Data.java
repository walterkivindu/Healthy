package com.example.healthy;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Data extends SQLiteOpenHelper {
    private static final String LOGCAT = null;

    public Data(Context applicationcontext) {
        super(applicationcontext, "healthy.db", null, 1);
        Log.d(LOGCAT, "Created");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "create table IF NOT EXISTS recipe( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "rname text, cat text,ingre text,instr text)";
        database.execSQL(query);
        Log.d(LOGCAT, "Tables Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS recipe";
        database.execSQL(query);
        onCreate(database);
    }

    public void AddRecord(String recipe, String cat, String ingre, String instr) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("instr", instr);
        values.put("cat", cat);
        values.put("rname", recipe);
        values.put("ingre", ingre);
        database.insert("recipe", null, values);
        database.close();
    }
}

