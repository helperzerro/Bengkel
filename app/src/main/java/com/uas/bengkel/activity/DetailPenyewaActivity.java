package com.uas.bengkel.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uas.bengkel.R;
import com.uas.bengkel.helper.DataHelper;

public class DetailPenyewaActivity extends AppCompatActivity {

    String sNama, sAlamat, sHP, sMerk, sHarga;
    int iLama, iPromo, iTotal;
    double dTotal;

    protected Cursor cursor;
    DataHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyewa);

        // membuat instance dari datahelper
        dbHelper = new DataHelper(this);

        // mendapatkan readabledatabase
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // melakukan query
        cursor = db.rawQuery("select * from penyewa, mobil, sewa where penyewa.nama = sewa.nama AND mobil.merk = sewa.merk AND penyewa.nama = '" + getIntent().getStringExtra("nama") + "'", null);
        // memindahkan cursor ke hasil pertama
        cursor.moveToFirst();
        // menerima dan mamsukan data kedalam variabel yang sudah disediakan jika cursor berisi nilai
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            sNama = cursor.getString(0);
            sAlamat = cursor.getString(1);
            sHP = cursor.getString(2);
            sMerk = cursor.getString(3);
            sHarga = cursor.getString(4);
            iPromo = cursor.getInt(7);
            iLama = cursor.getInt(8);
            dTotal = cursor.getDouble(9);
        }

        // mencari textview nama, alamat, hp
        TextView tvNama = findViewById(R.id.HNama);
        TextView tvAlamat = findViewById(R.id.HAlamat);
        TextView tvHP = findViewById(R.id.HTelp);

        // mencari textview merk, harga
        TextView tvMerk = findViewById(R.id.HMerk);
        TextView tvHarga = findViewById(R.id.HHarga);

        // mencari textview lama, promo, total
        TextView tvLama = findViewById(R.id.HLamaSewa);
        TextView tvPromo = findViewById(R.id.HPromo);
        TextView tvTotal = findViewById(R.id.HTotal);

        // mengatur text nama, alamat, hp
        tvNama.setText("     " + sNama);
        tvAlamat.setText("     " + sAlamat);
        tvHP.setText("     " + sHP);

        // mengatur text merk, harga
        tvMerk.setText("     " + sMerk);
        tvHarga.setText("     Rp. " + sHarga);

        // mengatur text lama hari, promo
        tvLama.setText("     " + iLama + " hari");
        tvPromo.setText("     " + iPromo + "%");

        // mengatur harga total setelah dihitung sesuai lama dan promo yang ada
        iTotal = Integer.parseInt(sHarga) - (Integer.parseInt(sHarga) * iLama ) * iPromo / 100;
        // menampilkan harga total yang telah dihitung
        tvTotal.setText("     Rp. " + iTotal);
        setupToolbar();
    }

    private void setupToolbar() {
        // mencari toolbar dari xml
        Toolbar toolbar = findViewById(R.id.tbDetailPenyewa);
        // mengatur title dari toolbar
        toolbar.setTitle("Detail PenyewaActivity");
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