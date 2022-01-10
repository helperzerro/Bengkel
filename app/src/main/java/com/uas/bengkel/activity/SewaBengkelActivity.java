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

public class SewaBengkelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // seperti biasanya pertama kami mendeklarasi beberapa variabel untuk dapat digunakan nantinya
    EditText nama, alamat, no_hp, lama;
    RadioGroup promo;
    RadioButton weekday, weekend;
    Button selesai;

    String sNama, sAlamat, sNo, sMerk, sLama;
    double dPromo;
    int iLama, iPromo, iHarga;
    double dTotal;

    private Spinner spinner;
    DataHelper dbHelper; // ini juga variabelnya nanti digunakan untuk menghubungkannya ke code untuk mengakses data didalam SQLiteDatabase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa);

        // membuat instance dari datahelper
        dbHelper = new DataHelper(this);

        // mencari setiap component yang dibutuhkan
        spinner = findViewById(R.id.spinner);
        selesai = findViewById(R.id.selesaiHitung);
        nama = findViewById(R.id.eTNama);
        alamat = findViewById(R.id.eTAlamat);
        no_hp = findViewById(R.id.eTHP);
        promo = findViewById(R.id.promoGroup);
        weekday = findViewById(R.id.rbWeekDay);
        weekend = findViewById(R.id.rbWeekEnd);
        lama = findViewById(R.id.eTLamaSewa);

        spinner.setOnItemSelectedListener(this);

        // mejalankan methojd loadSpinnerData
        loadSpinnerData();

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mendapatkan text dan mengubahnay menjadi string
                sNama = nama.getText().toString();
                sAlamat = alamat.getText().toString();
                sNo = no_hp.getText().toString();
                sLama = lama.getText().toString();
                // jika salah satu kosong, maka akan memberi tahu bahwa nilainya tidak boleh kosong
                if (sNama.isEmpty() || sAlamat.isEmpty() || sNo.isEmpty() || sLama.isEmpty()) {
                    Toast.makeText(SewaBengkelActivity.this, "(*) tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                // mengatur nilai promo
                if (weekday.isChecked()) {
                    // promo 10%
                    dPromo = 0.1;
                } else if (weekend.isChecked()) {
                    // promo 25%
                    dPromo = 0.25;
                }

                // mengambil nilai lama dan mengubahnya menjadi integer
                iLama = Integer.parseInt(sLama);
                // menghitung promo dalam bentuk integer
                iPromo = (int) (dPromo * 100);
                // menghitung total harga
                dTotal = (iHarga * iLama) - (iHarga * iLama * dPromo);

                // membuat writeabledatabase dan memasukan data kedalam database
                SQLiteDatabase dbH = dbHelper.getWritableDatabase();
                dbH.execSQL("INSERT INTO penyewa (nama, alamat, no_hp) VALUES ('" +
                        sNama + "','" +
                        sAlamat + "','" +
                        sNo + "');");
                dbH.execSQL("INSERT INTO sewa (merk, nama, promo, lama, total) VALUES ('" +
                        sMerk + "','" +
                        sNama + "','" +
                        iPromo + "','" +
                        iLama + "','" +
                        dTotal + "');");
                // refresh list
                PenyewaActivity.m.RefreshList();
                finish();

            }
        });

        setupToolbar();

    }
    // menyiapkan toolbar
    private void setupToolbar() {
        // mencari toolbar dalam file xml
        Toolbar toolbar = findViewById(R.id.tbSewaMobl);
        // menset title
        toolbar.setTitle("Sewa Bengkel");
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

    private void loadSpinnerData() {
        // membuat instance dari datahelper dan mendapatkan semau kategori
        DataHelper db = new DataHelper(getApplicationContext());
        List<String> categories = db.getAllCategories();

        // mengatur array adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sMerk = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}