package com.example.taskplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    FloatingActionButton fabAgregar;
    Button btnPendientes, btnRealizadas;

    ArrayList<Tarea> lista;
    TareaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listRecordatorios);
        fabAgregar = findViewById(R.id.fabAgregar);
        btnPendientes = findViewById(R.id.btnPendientes);
        btnRealizadas = findViewById(R.id.btnRealizadas);

        lista = new ArrayList<>();
        adapter = new TareaAdapter(this, lista);
        listView.setAdapter(adapter);

        // 🔔 Permiso notificaciones Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // ➕ Abrir AgregarActivity
        fabAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AgregarActivity.class);
            startActivityForResult(intent, 1);
        });

        // 🔎 Mostrar Pendientes
        btnPendientes.setOnClickListener(v -> {
            ArrayList<Tarea> pendientes = new ArrayList<>();
            for (Tarea t : lista) {
                if (!t.realizada) {
                    pendientes.add(t);
                }
            }
            adapter = new TareaAdapter(this, pendientes);
            listView.setAdapter(adapter);
        });

        // 🔎 Mostrar Realizadas
        btnRealizadas.setOnClickListener(v -> {
            ArrayList<Tarea> hechas = new ArrayList<>();
            for (Tarea t : lista) {
                if (t.realizada) {
                    hechas.add(t);
                }
            }
            adapter = new TareaAdapter(this, hechas);
            listView.setAdapter(adapter);
        });
    }

    // 📥 Recibir nueva tarea
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            String titulo = data.getStringExtra("titulo");
            String fecha = data.getStringExtra("fecha");
            String descripcion = data.getStringExtra("descripcion");

            Tarea nueva = new Tarea(titulo, descripcion, fecha);
            lista.add(nueva);
            adapter.notifyDataSetChanged();
        }
    }
}