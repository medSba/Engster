package com.example.engster;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDataBase extends SQLiteOpenHelper {

    public MyDataBase(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
}
    private static final String DATABASE_NAME = "word_expression.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_WORD_EXPRESSION = "word_expression";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_WORDEXAMPLE = "wordexample";
    private static final String COLUMN_EXPRESSION = "expression";
    private static final String COLUMN_TYPE = "type";

    // Image table
    private static final String TABLE_IMAGE = "image";
    private static final String COLUMN_IMAGE_URI = "image_uri";
    private static final String COLUMN_WORD_EXPRESSION_ID = "word_expression_id";



    @Override
    public

    void onCreate(SQLiteDatabase db) {
        // Create Word and Expression table
        String createWordExpressionTableQuery = "CREATE TABLE " + TABLE_WORD_EXPRESSION + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_WORDEXAMPLE + " TEXT NOT NULL, "
                + COLUMN_EXPRESSION + " TEXT NOT NULL, "
                + COLUMN_TYPE + " TEXT NOT NULL"
                + ")";
        db.execSQL(createWordExpressionTableQuery);
        db.close();

        // Create Image table
        String createImageTableQuery = "CREATE TABLE " + TABLE_IMAGE + "("
                + COLUMN_IMAGE_URI + " TEXT NOT NULL, "
                + COLUMN_WORD_EXPRESSION_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + COLUMN_WORD_EXPRESSION_ID + ") REFERENCES " + TABLE_WORD_EXPRESSION + "(" + COLUMN_ID + "), "
                + "PRIMARY KEY (" + COLUMN_IMAGE_URI + ", " + COLUMN_WORD_EXPRESSION_ID + ")"
                + ")";
        db.execSQL(createImageTableQuery);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD_EXPRESSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
        // Recreate tables
        onCreate(db);

    }

    // Insert data into Word and Expression table
    public long insertWordExpression(Notes notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORDEXAMPLE, notes.getWordexample());
        values.put(COLUMN_EXPRESSION, notes.getExpression());
        values.put(COLUMN_TYPE, notes.getType());
        long id = db.insert(TABLE_WORD_EXPRESSION, null, values);
        return id;
    }

    // Insert data into image table
    public long insertImages(Images images) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_URI, images.getImageData());
        values.put(COLUMN_WORD_EXPRESSION_ID, images.getWordExpressionId());
        long id = db.insert(TABLE_IMAGE, null, values);
        return id;
    }

    @SuppressLint("Range")
    public ArrayList<Notes> getAll() {
        ArrayList<Notes> wordExpressions = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_WORD_EXPRESSION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Notes notes = new Notes();
                notes.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                notes.setWordexample(cursor.getString(cursor.getColumnIndex(COLUMN_WORDEXAMPLE)));
                notes.setExpression(cursor.getString(cursor.getColumnIndex(COLUMN_EXPRESSION)));
                notes.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
                wordExpressions.add(notes);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return wordExpressions;
    }

    public void updateWordExpression(Notes notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORDEXAMPLE, notes.getWordexample());
        values.put(COLUMN_EXPRESSION, notes.getExpression());
        values.put(COLUMN_TYPE, notes.getType());

        // Update the row with matching id
        db.update(TABLE_WORD_EXPRESSION, values, COLUMN_ID + "=?", new String[]{String.valueOf(notes.getId())});
    }

    public void deleteWordExpression(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the row with matching id
        db.delete(TABLE_WORD_EXPRESSION, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        // Delete the rows with matching wordExpressionId
        db.delete(TABLE_IMAGE, COLUMN_WORD_EXPRESSION_ID + "=?", new String[]{String.valueOf(id)});
    }


}

