package com.uas.bengkel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.uas.bengkel.R;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    // mendeklarasi beberapa variabel untuk mengakses activity_main
    TextView tvToday, tvMainSalam;
    String hariIni;
    Animation animTv;
    String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mencari button informasi, sewa dan logout
        Button informasi = findViewById(R.id.btn_info_mobil);
        Button sewa = findViewById(R.id.btn_sewa);
        Button logout = findViewById(R.id.buttonLogout);

        // menerima extra dari intent yang dilakukan dari login activity
        nama = getIntent().getExtras().getString("name");

        informasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // memindahkan activity dari MainAcitivity ke DaftarBengkelAcivity
                if(nama.equals("admin")){
                    Intent i = new Intent(MainActivity.this, DaftarBengkelAdmin.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(MainActivity.this, DaftarBengkelActivity.class);
                    startActivity(i);
                }
            }
        });

        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // memindahkan activity dari MainAcitivity ke PenyewaAcitivity
                Intent p = new Intent(MainActivity.this, PenyewaActivity.class);
                startActivity(p);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  bertujuan untuk kembali ke halaman login jika user menekan tombol logout
                Intent p = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(p);
                // membuat user menjadi tidak dapat kembali ke halaman sebelumnya (MainActivity)
                finish();
            }
        });

        tvToday = findViewById(R.id.tvDate);
        tvMainSalam = findViewById(R.id.tvMainSalam);
        animTv = AnimationUtils.loadAnimation(this, R.anim.anim_tv);
        tvMainSalam.startAnimation(animTv);

        // mendapatkan tanggal pada saat program dijalankan
        Date dateNow = Calendar.getInstance().getTime();
        hariIni = (String) DateFormat.format("EEEE", dateNow);
        // mengetahui hari apa saat program dijalankan
        if (hariIni.equalsIgnoreCase("sunday")) {
            hariIni = "Minggu";
        } else if (hariIni.equalsIgnoreCase("monday")) {
            hariIni = "Senin";
        } else if (hariIni.equalsIgnoreCase("tuesday")) {
            hariIni = "Selasa";
        } else if (hariIni.equalsIgnoreCase("wednesday")) {
            hariIni = "Rabu";
        } else if (hariIni.equalsIgnoreCase("thursday")) {
            hariIni = "Kamis";
        } else if (hariIni.equalsIgnoreCase("friday")) {
            hariIni = "Jumat";
        } else if (hariIni.equalsIgnoreCase("saturday")) {
            hariIni = "Sabtu";
        }

        // memanggil method getToday dan setSalam
        getToday();
        setSalam();
    }

    private void setSalam() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        // menyambut user dan memberikan salam sesuai waktu saat program dijalankan
        if (timeOfDay >= 0 && timeOfDay < 12) {
            tvMainSalam.setText("Selamat Pagi" + " " + nama);
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            tvMainSalam.setText("Selamat Siang" + " " + nama);
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            tvMainSalam.setText("Selamat Sore" + " " + nama);
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            tvMainSalam.setText("Selamat Malam" + " " + nama);
        }
    }

    private void getToday() {
        // mendapatkan tanggal saat program dijalankan
        Date date = Calendar.getInstance().getTime();
        // memasukan tanggal, bulan dan tahun dari tanggal yang sudah didapatkan
        String tanggal = (String) DateFormat.format("d", date);
        String monthNumber = (String) DateFormat.format("M", date);
        String year = (String) DateFormat.format("yyyy", date);

        // melakukan konversi string ke bentuk integer
        int month = Integer.parseInt(monthNumber);
        // membuat bulan menjadi null terlebih dahulu
        String bulan = null;

        // mmasukan nama bulan ke dalam variabel bulan sesuai urut bulan
        if (month == 1) {
            bulan = "Januari";
        } else if (month == 2) {
            bulan = "Februari";
        } else if (month == 3) {
            bulan = "Maret";
        } else if (month == 4) {
            bulan = "April";
        } else if (month == 5) {
            bulan = "Mei";
        } else if (month == 6) {
            bulan = "Juni";
        } else if (month == 7) {
            bulan = "Juli";
        } else if (month == 8) {
            bulan = "Agustus";
        } else if (month == 9) {
            bulan = "September";
        } else if (month == 10) {
            bulan = "Oktober";
        } else if (month == 11) {
            bulan = "November";
        } else if (month == 12) {
            bulan = "Desember";
        }

        // memformat hari, tanggal, bulan dan tahun ke dalam satu string
        String formatFix = hariIni + ", " + tanggal + " " + bulan + " " + year;
        // menampilan string dari format yang sudah dibuat
        tvToday.setText(formatFix);
    }

}
