package com.example.amrepanda.movies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    static final String DATABASE_NAME="Favorits";
    static final String TABLE_NAME="Movies";
    static final String COLUMN_ID="Movie_ID";
    static final String COLUMN_NAME="Original_Title";
    static final String COLUMN_POSTER="Movie_Poaster";
    static final String COLUMN_OVERVIEW="Over_View";
    static final String COULMN_Date="Release_Date";
    static final String COULMN_VOTE="Avg_Vote";
    static final int Version=1;
    public Database(Context context) { super(context, DATABASE_NAME, null, Version); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_Creat= "CREATE TABLE " + TABLE_NAME + "( "+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_POSTER +  " TEXT, " +
                COLUMN_NAME + " TEXT, "  +
                COLUMN_OVERVIEW + " TEXT, " +
                COULMN_Date + " TEXT,"  +
                COULMN_VOTE + " TEXT )";
        db.execSQL(SQL_Creat);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
