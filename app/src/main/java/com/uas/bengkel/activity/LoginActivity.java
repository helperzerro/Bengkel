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

public class LoginActivity extends AppCompatActivity {
    // disini saya mendekalrasi beberapa variabel dari layout activity_login untuk dapat menginisialisasi data inputan user
    EditText username, password;
    TextView signup;
    Button btnlogin;

    // dekalrasi ini untuk diarahkan ke file DBHelper yang dibuat
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // disini kami menginisialisasi data inputan user ke suatu variabel
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.buttonLogin);
        signup = (TextView) findViewById(R.id.textRegister);

        // mendeklarasikan objek
        DB = new DBHelper(this);

        // kondisi ketika user menekan button Login
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // disini saya mendeklarasikan variabel baru, dimana inputan data dari user akan diubah ke String
                String user = username.getText().toString();
                String pass = password.getText().toString();

                // kondisi jika user tidak menginput data apapun
                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    //  dia cek dulu apakah username dan password di database SQLite sudah sesuai dengan yang di input
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    // kondisi jika sesuai
                    if(checkuserpass==true){
                        // maka akan dilakukan perpindahan ke From Login dengan memanfaatkan intent
                        // yang diserta dengan String dari nama dari user yang login
                        Toast.makeText(LoginActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        String name = username.getText().toString();
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }else{
                        // Kondisi jika username dan password salah
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // kondisi ketika user menekan text Register
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
