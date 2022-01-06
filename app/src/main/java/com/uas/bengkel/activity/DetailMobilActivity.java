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

public class DetailMobilActivity extends AppCompatActivity {

    protected Cursor cursor;
    String sMerk, sHarga, sGambar;
    DataHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mobil);

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

        if (sMerk.equals("Bengkel ABC")) {
            sGambar = "alphard";
        } else if (sMerk.equals("Bengkel Mantap")) {
            sGambar = "bengkel";
        } else if (sMerk.equals("Bengkel Hebat")) {
            sGambar = "bengkel";
        }

        ImageView ivGambar = findViewById(R.id.ivMobil);
        TextView tvMerk = findViewById(R.id.JMobil);
        TextView tvHarga = findViewById(R.id.JHarga);

        tvMerk.setText(sMerk);
        ivGambar.setImageResource(getResources().getIdentifier(sGambar, "drawable", getPackageName()));
        tvHarga.setText("Rp. " + sHarga);

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbDetailMbl);
        toolbar.setTitle("Detail Mobil");
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
