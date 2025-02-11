package com.example.sebaproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import androidx.annotation.Nullable;

public class dbConnect extends SQLiteOpenHelper {
    private static String dbName = "seba";
    private static String dbTable = "users";
    private static int dbVersion = 1;
    private static String ID = "id";
    private static String Fname = "Fname";
    private static String Lname = "Lname";
    private static String Username = "Username";
    private static String email = "email";
    private static String Password = "password";


    public dbConnect(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase dbsqLite) {
        String query = "create table " + dbTable + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ Fname + " TEXT, "
                + Lname + " TEXT, "+ Username + " TEXT, "+ email + " TEXT, "+ Password + " TEXT)";
        dbsqLite.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase dbsqLite, int i, int i1) {
        dbsqLite.execSQL("DROP TABLE IF EXISTS " + dbTable);
        onCreate(dbsqLite);

    }
    public void addUser(Users user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Fname, user.getFname());
        values.put(Lname, user.getLname());
        values.put(email, user.getEmail());
        values.put(Username, user.getUsername());
        values.put(Password, user.getPassword());
        db.insert(dbTable,null,values);
        db.close();
    }
    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return isValid;
    }

}
