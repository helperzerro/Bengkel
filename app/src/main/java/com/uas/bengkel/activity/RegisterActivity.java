package com.uas.bengkel.activity;

import androidx.appcompat.app.AppCompatActivity;

// ini beberapa content yang harus di import agar dapat digunakan variabelnya
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uas.bengkel.helper.DBHelper;
import com.uas.bengkel.R;

public class RegisterActivity extends AppCompatActivity {
    // disini saya mendekalrasi beberapa variabel dari layout activity_register untuk dapat menginisialisasi data inputan user
    EditText username, password, repassword;
    TextView signin;
    Button signup;

    // dekalrasi ini untuk diarahkan ke file DBHelper yang dibuat
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // disini kami menginisialisasi data inputan user ke suatu variabel
        username = (EditText) findViewById(R.id.Rusername);
        password = (EditText) findViewById(R.id.Rpassword);
        repassword = (EditText) findViewById(R.id.Rconfirmpassword);
        signup = (Button) findViewById(R.id.buttonRegister);
        signin = (TextView) findViewById(R.id.textLogin);

        // mendeklarasikan objek
        DB = new DBHelper(this);

        // kondisi ketika user menekan button Register
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // disini saya mendeklarasikan variabel baru, dimana inputan data dari user akan diubah ke String
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                // kondisi jika user tidak menginput data apapun
                if(user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    // kondisi jika password dan confimasi password sama
                    if(pass.equals(repass)){
                        // dia cek dulu username di database SQLite sudah pernah dibuat belum
                        Boolean checkuser = DB.checkusername(user);
                        // kondisi jika belum pernah dibuat
                        if(checkuser==false){
                            // maka data akan di masukan kedalam database SQLite
                            Boolean insert = DB.insertData(user, pass);
                            // kondisi jika data berhasil dimasukan ke database
                            if(insert==true){
                                // arahkan ke Form Login, dengan memanfaatkan intent
                                Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                // data gagal register, karena insert data ke dalam SQLite tidak berhasil
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        // kondisi gagal karena username sudah pernah dibuat
                        else{
                            Toast.makeText(RegisterActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                        // kondisi gagal kerena password dan confirm password tidak sama
                    }else{
                        Toast.makeText(RegisterActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                } }
        });

        // kondisi ketika user menekan text Login
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // melakukan perpindahan ke Form Login
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}