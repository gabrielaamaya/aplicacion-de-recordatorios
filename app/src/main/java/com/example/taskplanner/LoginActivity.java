package com.example.taskplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsuario, txtPassword;
    Button btnLogin, btnCrear;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCrear = findViewById(R.id.btnCrearCuenta);

        preferences = getSharedPreferences("USUARIOS", MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {

            String user = txtUsuario.getText().toString();
            String pass = txtPassword.getText().toString();

            String userGuardado = preferences.getString("usuario", "");
            String passGuardado = preferences.getString("password", "");

            if(user.equals(userGuardado) && pass.equals(passGuardado)){
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }else{
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }

        });

        btnCrear.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistroActivity.class));
        });
    }
}