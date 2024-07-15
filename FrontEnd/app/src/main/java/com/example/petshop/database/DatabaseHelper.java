package com.example.petshop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ChatDatabase";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_MESSAGES = "Messages";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_MESSAGE = "Message";
    public static final String COLUMN_CHATROOM = "ChatRoom";
    public static final String COLUMN_FILEPATH = "FilePath";
    public static final String COLUMN_TIMESTAMP = "Timestamp";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_MESSAGES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER + " TEXT, " +
                    COLUMN_MESSAGE + " TEXT, " +
                    COLUMN_CHATROOM + " TEXT, " +
                    COLUMN_FILEPATH + " TEXT, " +
                    COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }
}
