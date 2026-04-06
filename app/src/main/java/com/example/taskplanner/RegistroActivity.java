package com.example.taskplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    EditText txtUsuario, txtPassword;
    Button btnRegistrar;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        preferences = getSharedPreferences("USUARIOS", MODE_PRIVATE);

        btnRegistrar.setOnClickListener(v -> {

            String user = txtUsuario.getText().toString();
            String pass = txtPassword.getText().toString();

            if(user.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("usuario", user);
            editor.putString("password", pass);
            editor.apply();

            Toast.makeText(this, "Cuenta creada", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}