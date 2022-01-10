package com.uas.bengkel.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uas.bengkel.R;
import com.uas.bengkel.helper.DataHelper;

public class DetailBengkelActivity extends AppCompatActivity {

    protected Cursor cursor;
    String sMerk, sHarga, sGambar;
    DataHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bengkel);

        Bundle terima = getIntent().getExtras();

        dbHelper = new DataHelper(this);
        Intent intent = getIntent();

        String merk = terima.getString("merk");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from mobil where merk = '" + merk + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            sMerk = cursor.getString(0);
            sHarga = cursor.getString(1);
        }

        // memasukan nama gambar sesuai merek yang dipilih untuk ditampilkan nantinya
        if (sMerk.equals("Bengkel Mandiri")) {
            sGambar = "mandiri";
        } else if (sMerk.equals("Bengkel Setia")) {
            sGambar = "setia";
        } else if (sMerk.equals("Bengkel Sejati")) {
            sGambar = "sejati";
        } else if (sMerk.equals("Bengkel Sentosa")) {
            sGambar = "sentosa";
        }

        // mencari ImageView dan TextView yang ada dalam file xml
        ImageView ivGambar = findViewById(R.id.ivMobil);
        TextView tvMerk = findViewById(R.id.JMobil);
        TextView tvHarga = findViewById(R.id.JHarga);

        // membuat text dari textview merek menjadi merek yang dipilih
        tvMerk.setText(sMerk);
        // membuat imageview untuk mendapatkan gambar dari folder drawable sesuai nama yang telah dipilih
        ivGambar.setImageResource(getResources().getIdentifier(sGambar, "drawable", getPackageName()));
        // men set nilai dari harga berdasarkan harga yang telah ditetapkan kedalam textview harga
        tvHarga.setText("Rp. " + sHarga);

        // memanggil method setupToolbar
        setupToolbar();

    }

    // menyiapkan toolbar
    private void setupToolbar() {
        // mencari toolbar dalam file xml
        Toolbar toolbar = findViewById(R.id.tbDetailMbl);
        // menset title
        toolbar.setTitle("Detail Bengkel");
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
