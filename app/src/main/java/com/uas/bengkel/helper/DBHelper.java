package com.uas.bengkel.helper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    // membuat nama databasenya
    public static final String DBNAME = "Login.db";
    // create database
    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)"); // membuat tabel users
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");  // query menghapus tabel
    }

    // disini ketika user melakukan registrasi, maka data-nya akan diinsert kedalam tabel users
    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username); // menginisialisasi username
        contentValues.put("password", password); // menginisialisasi password
        long result = MyDB.insert("users", null, contentValues); //data di masukan ke tebel users
        if(result==-1) return false; // kondisi ketika data tidak berhasil di masukan
        else
            return true; // kondisi ketika data berhasil dimasukan
    }

    // function mengecek username sudaj pernah dibuat / belum
    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        // ini menjalankan query untuk melakukan pencarian
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0) // ketika user name ada / sudah terbuat
            return true;
        else
            return false; // mengembalikan nilai false, jika belum pernah membuatnya
    }

    // function mengecek username dan password
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        // menjalankan query untuk mengecek apakah username dan password ada tidak
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0) // mengelbalikan nilai true jika ada
            return true;
        else
            return false; // mengembalikan nilai false jika belum ada
    }
}