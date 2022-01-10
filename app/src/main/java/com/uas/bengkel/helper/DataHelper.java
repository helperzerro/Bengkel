package com.uas.bengkel.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {
    // disini kami mendekalrasi beberapa sebagai kunci dasar membuat database, terdiri dari nama databasenya serta versi-nya
    private static final String DATABASE_NAME = "bengkel.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); //create database

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // membuat tabel beberapa tabel didalam database, seperti tabel penyewa, mobil, sewa
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("create table penyewa (" +
                "nama text," +
                "alamat text," +
                "no_hp text," +
                "primary key(nama)" +
                ");" +
                "");
        db.execSQL("create table mobil(" +
                "merk text," +
                "harga int," +
                "primary key(merk)" +
                ");" +
                "");
        db.execSQL("create table sewa(" +
                "merk text," +
                "nama text," +
                "promo int," +
                "lama int," +
                "total double," +
                "foreign key(merk) references mobil (merk), " +
                "foreign key(nama) references penyewa (nama) " +
                ");" +
                "");

        // disini langsung di inisialisasi beberapa data ke tabel mobil
        db.execSQL("insert into mobil values (" +
                "'Bengkel Mandiri'," +
                "50000" +
                ");" +
                "");
        db.execSQL("insert into mobil values (" +
                "'Bengkel Setia'," +
                "35000" +
                ");" +
                "");
        db.execSQL("insert into mobil values (" +
                "'Bengkel Sejati'," +
                "40000" +
                ");" +
                "");
        db.execSQL("insert into mobil values (" +
                "'Bengkel Sentosa'," +
                "30000" +
                ");" +
                "");
    }

    // function ini digunakan untuk mengambil data mobil
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<String>(); // menbuat variabel array dulu
        String selectQuery = "select * from mobil"; // membuat Querynya
        SQLiteDatabase db = this.getReadableDatabase(); // menghubungkannya ke data
        Cursor cursor = db.rawQuery(selectQuery, null);  // mengambil index item, ketika item di tekan

        if (cursor.moveToFirst()) {
            do { //
                categories.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close(); // menonaktifkan cursor
        db.close();  // menonaktifkan database

        return categories; // mengembalikan data
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

}