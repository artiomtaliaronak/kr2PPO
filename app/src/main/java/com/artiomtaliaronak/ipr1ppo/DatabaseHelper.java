package com.artiomtaliaronak.ipr1ppo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "Users.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "_username";
    public static final String COLUMN_SCORE = "_score";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +  COLUMN_SCORE + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void addScore(String username, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_SCORE, score);
        db.insert(TABLE_NAME, null, cv);
    }

    public List<User> readData(){
        List<User> list = new ArrayList<>();
        Cursor c = this.getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_SCORE + " DESC;", null);
        while (c.moveToNext()){
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String username = c.getString(c.getColumnIndex(COLUMN_USERNAME));
            @SuppressLint("Range") int score = c.getInt(c.getColumnIndex(COLUMN_SCORE));
            User nextUser = new User(id, username, score);
            list.add(nextUser);
        }
        c.close();
        return list;
    }

    @SuppressLint("Range")
    public void updateDatabase(int id, String username, int score){
        Cursor c = this.getWritableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
        while (c.moveToNext()){
            @SuppressLint("Range") String tmpUsername = c.getString(c.getColumnIndex(COLUMN_USERNAME));
            if (username.equals(tmpUsername)) {
                id = c.getInt(c.getColumnIndex(COLUMN_ID));
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_SCORE, score);
                this.getWritableDatabase().update(TABLE_NAME, cv, COLUMN_ID + "=" + id, null);
                break;
            }
        }
        c.close();
        if (id == -1){
            addScore(username, score);
        }
    }

}
