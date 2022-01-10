package com.uas.bengkel.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uas.bengkel.R;
import com.uas.bengkel.helper.DataHelper;

import java.util.List;

public class DetailBengkelAdmin extends AppCompatActivity {
    // seperti biasanya pertama kami mendeklarasi beberapa variabel untuk dapat digunakan nantinya
    EditText nama,harga2;
    Button selesai;

    String sNama,sHarga2;
    DataHelper dbHelper; // ini juga variabelnya nanti digunakan untuk menghubungkannya ke code untuk mengakses data didalam SQLiteDatabase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_bengkel_baru);

        // membuat instance dari datahelper
        dbHelper = new DataHelper(this);

        // mencari setiap component yang dibutuhkan
        nama = findViewById(R.id.NNama1);
        harga2 = findViewById(R.id.JHarga2);
        selesai = findViewById(R.id.selesaiHitung1);

        // mejalankan methojd loadSpinnerData

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mendapatkan text dan mengubahnay menjadi string
                sNama = nama.getText().toString();
                sHarga2 = harga2.getText().toString();
                // jika salah satu kosong, maka akan memberi tahu bahwa nilainya tidak boleh kosong
                if (sNama.isEmpty() || sHarga2.isEmpty()) {
                    Toast.makeText(DetailBengkelAdmin.this, "(*) tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                // membuat writeabledatabase dan memasukan data kedalam database
                SQLiteDatabase dbH = dbHelper.getWritableDatabase();
                dbH.execSQL("INSERT INTO mobil VALUES ('" +
                        sNama + "','" +
                        sHarga2 + "');");
                // refresh list
                DaftarBengkelActivity.m.RefreshList();
                finish();

            }
        });

        setupToolbar();

    }
    // menyiapkan toolbar
    private void setupToolbar() {
        // mencari toolbar dalam file xml
        Toolbar toolbar = findViewById(R.id.tbDetailMbl);
        // menset title
        toolbar.setTitle("Tambah Bengkel");
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

}