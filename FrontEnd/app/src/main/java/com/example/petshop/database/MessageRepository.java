package com.example.petshop.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.petshop.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MessageRepository {
    private DatabaseHelper dbHelper;

    public MessageRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void saveMessage(String username, String message, String chatRoom, String filePath) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Check if the username already exists
        Cursor cursor = db.query(DatabaseHelper.TABLE_MESSAGES,
                null,
                DatabaseHelper.COLUMN_USER + "=?",
                new String[]{username},
                null,
                null,
                null);

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER, username);
        values.put(DatabaseHelper.COLUMN_MESSAGE, message);
        values.put(DatabaseHelper.COLUMN_CHATROOM, chatRoom);
        values.put(DatabaseHelper.COLUMN_FILEPATH, filePath);

        if (cursor != null && cursor.moveToFirst()) {
            // Username exists, update the last message
            db.update(DatabaseHelper.TABLE_MESSAGES, values, DatabaseHelper.COLUMN_USER + "=?", new String[]{username});
        } else {
            // Username does not exist, insert a new message
            db.insert(DatabaseHelper.TABLE_MESSAGES, null, values);
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
    }

    @SuppressLint("Range")
    public List<Contact> getAllMessages() {
        List<Contact> messages = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_MESSAGES, null, null, null, null, null, DatabaseHelper.COLUMN_TIMESTAMP + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setUsername(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER)));
                contact.setContent(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE)));
                contact.setChatRoom(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHATROOM)));
                contact.setFile(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FILEPATH)));
                messages.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return messages;
    }

    // Add a method to clear the table for testing purposes
    public void clearMessages() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_MESSAGES, null, null);
        db.close();
    }
}
