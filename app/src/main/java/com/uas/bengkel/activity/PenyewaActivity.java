package com.uas.bengkel.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uas.bengkel.R;
import com.uas.bengkel.helper.DataHelper;

import static android.R.layout.*;

public class PenyewaActivity extends AppCompatActivity {
    // mendeklarasi variabel
    String[] daftar;
    int[] id;
    ListView ListView1;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    public static PenyewaActivity m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyewa);

        // mencari tombol tambah
        Button tambah = findViewById(R.id.tambahPenyewa);

        // memulai activity SewaBengkel jika tombol tambah ditekan
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(PenyewaActivity.this, SewaBengkelActivity.class);
                startActivity(p);
            }
        });

        // mendeklarasikan class ini untuk mencajadi context dari DataHelper
        m = this;
        dbcenter = new DataHelper(this);

        RefreshList();
        setupToolbar();

    }

    // mensetup toolbar
    private void setupToolbar() {
        // mencari toolbar dalam file xml
        Toolbar toolbar = findViewById(R.id.tbPenyewa);
        // menset title
        toolbar.setTitle("Daftar Penyewa");
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
        cursor = db.rawQuery("SELECT * FROM penyewa", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            daftar[i] = cursor.getString(0);
        }

        // mencari listview dan menambahkan adapter
        ListView1 = findViewById(R.id.listView1);
        ListView1.setAdapter(new ArrayAdapter(this, simple_list_item_1, daftar));
        ListView1.setSelected(true);
        // mengatur setiap list jika ditekan
        ListView1.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                // list yang tersedia
                final CharSequence[] dialogitem = {"Lihat Data", "Hapus Data"};
                // membuat builder dair AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(PenyewaActivity.this);
                // mengatur title dair builder
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                // jika pilihan pertama dipilih maka akan melakukan intent ke DetailPenyewaActivity
                                Intent i = new Intent(PenyewaActivity.this, DetailPenyewaActivity.class);
                                i.putExtra("nama", selection);
                                startActivity(i);
                                break;
                            }
                            case 1: {
                                // jika pilihan kedua dipilih, maka akan membuat readabledatabase,
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                // kemudian mengahapus selection dari daftar yang telah dipilih
                                db.execSQL("DELETE FROM penyewa where nama = '" + selection + "'");
                                db.execSQL("DELETE FROM sewa where nama = '" + selection + "'");
                                // kemudian akan merefresh list
                                RefreshList();
                                break;
                            }
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) ListView1.getAdapter()).notifyDataSetInvalidated();

    }
}
