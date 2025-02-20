package com.example.sebaproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sebaproject.Model.ToDomodel;

import java.util.ArrayList;
import java.util.List;

public class dbConnect extends SQLiteOpenHelper {
    private static final String dbName = "seba";
    private static final String dbTable = "users";
    private static final int dbVersion = 1;
    private static final String ID = "id";
    private static final String Fname = "Fname";
    private static final String Lname = "Lname";
    private static final String Username = "Username";
    private static final String email = "email";
    private static final String Password = "password";
    private static final String TODO_TABLE = "TODO";
    private static final String TODO_ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String DESCRIPTION = "description";
    private static final String TIME = "time";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + " ("
            + TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK + " TEXT, "
            + DESCRIPTION + " TEXT, "
            + TIME + " TEXT, "
            + STATUS + " INTEGER)";

    public dbConnect(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + dbTable + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Fname + " TEXT, "
                + Lname + " TEXT, "
                + Username + " TEXT, "
                + email + " TEXT, "
                + Password + " TEXT)");
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + dbTable);
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(db);
    }

    public void addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Fname, user.getFname());
        values.put(Lname, user.getLname());
        values.put(email, user.getEmail());
        values.put(Username, user.getUsername());
        values.put(Password, user.getPassword());
        db.insert(dbTable, null, values);
        db.close();
    }

    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + dbTable + " WHERE email=? AND password=?", new String[]{email, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

    public boolean emailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + dbTable + " WHERE email=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public void openDatabase() {
        this.getWritableDatabase();
    }

    public void addTask(ToDomodel task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(DESCRIPTION, task.getDescription());
        cv.put(TIME, task.getTime());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null, cv);
        db.close();
    }

    @SuppressLint("Range")
    public List<ToDomodel> getAllTasks() {
        List<ToDomodel> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TODO_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ToDomodel task = new ToDomodel();
                task.setId(cursor.getInt(cursor.getColumnIndex(TODO_ID)));
                task.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                task.setTime(cursor.getString(cursor.getColumnIndex(TIME)));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return taskList;
    }

    public void updateTaskStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, TODO_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateTask(int id, String task,String description,String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        cv.put(DESCRIPTION, description);
        cv.put(TIME, time);
        db.update(TODO_TABLE, cv, TODO_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODO_TABLE, TODO_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}