package com.uas.bengkel.activity;

import android.R.layout;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uas.bengkel.R;
import com.uas.bengkel.helper.DataHelper;

public class DaftarBengkelAdmin extends AppCompatActivity {
    // mendeklarasi beberapa variabel untuk mengakses activity_Bengkel_admin
    String[] daftar;
    ListView ListView11;
    Menu menu;
    Button TambahBengkel;
    protected Cursor cursor;
    DataHelper dbcenter;
    public static DaftarBengkelAdmin m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bengkel_admin);
        // mendeklarasikan class ini untuk menjadi context untuk DataHelper
        m = this;
        dbcenter = new DataHelper(this);

        // memanggil method refreshList dan setupToolbar
        RefreshList();
        setupToolbar(); // menampilkan text di
    }

    // menyiapkan toolbar
    private void setupToolbar() {
        // mencari toolbar dalam file xml
        Toolbar toolbar = findViewById(R.id.tbInfoMbl1);
        // menset title
        toolbar.setTitle("Informasi Daftar Bengkel");
        // menampilkan toolbar ke getSupportActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void RefreshList() {
        // mendapatkan readable database
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        // melakukan query
        cursor = db.rawQuery("SELECT * FROM mobil", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            daftar[i] = cursor.getString(0);
        }

        // mencari listview dan menambahkan adapter
        ListView11 = findViewById(R.id.listView11);
        ListView11.setAdapter(new ArrayAdapter(this, layout.simple_list_item_1, daftar));
        ListView11.setSelected(true);
        // mengatur setiap list jika ditekan
        ListView11.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                // melakukan perpindahan activity menggunakna intent serta memberikan extra bernilai merk
                Intent i = new Intent(DaftarBengkelAdmin.this, DetailBengkelActivity.class);
                i.putExtra("merk", selection);
                startActivity(i);
            }
        });

        // ketika button Tambah di klik maka akan terjadi perubahan halaman menuju halaman DetailBengkelAdmin
        TambahBengkel=findViewById(R.id.tambahPenyewa1);
        TambahBengkel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DaftarBengkelAdmin.this, DetailBengkelAdmin.class);
                startActivity(i);
            }
        });

        ((ArrayAdapter) ListView11.getAdapter()).notifyDataSetInvalidated();

    }


}