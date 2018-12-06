package com.example.david.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    // adatbázis neve
    private static final String DATABASE_NAME = "GamfTODO";
    // tasks tábla neve
    private static final String TABLE_TASKS = "tasks";
    // tasks tábla oszlopai
    private static final String KEY_ID = "id";
    private static final String KEY_TASKNAME = "taskName";
    private static final String KEY_TASKDATE = "taskDate";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // amennyiben nem létezik a tábla, létrehozza azt úgy, hogy az id automatikusan növekedni fog beszúráskor
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TASKNAME + " TEXT, "
                + KEY_TASKDATE + " INTEGER)";
        db.execSQL(sql);
    }

    // Törli a tábla régi verzióját és létrehozza újra
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public void addTask(Task task) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASKNAME, task.getTaskName());
        values.put(KEY_TASKDATE, convertDateToMillisecs(task.getDate()));

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    public Date convertMillisecsToDate(long milli){
        Date d = new Date(milli);

        return d;
    }

    public long convertDateToMillisecs(Date d){
        long milli = d.getTime();

        return milli;
    }

    // egy listával tér vissza, melyben az összes feladat megtalálható
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();

        String selectQuery = "SELECT * FROM "
                + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(0));
                task.setTaskName(cursor.getString(1));
                task.setDate(convertMillisecsToDate(cursor.getLong(2)));
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        return taskList;
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASKNAME, task.getTaskName());
        db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public void deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
