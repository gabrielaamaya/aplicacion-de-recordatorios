package com.example.taskplanner;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import java.util.Calendar;

public class AgregarActivity extends AppCompatActivity {

    EditText edtTitulo, edtFecha, edtHora, edtDescripcion;
    Button btnGuardar;

    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtFecha = findViewById(R.id.edtFecha);
        edtHora = findViewById(R.id.edtHora);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);

        // 📅 Seleccionar Fecha
        edtFecha.setOnClickListener(v -> {
            new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        edtFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        // ⏰ Seleccionar Hora (FORMATO 12 HORAS)
        edtHora.setOnClickListener(v -> {

            new TimePickerDialog(this,
                    (view, hourOfDay, minute) -> {

                        // Guardamos en formato 24 interno para la alarma
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);

                        // Convertimos a 12 horas para mostrar
                        String amPm;
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }

                        int hora12 = hourOfDay % 12;
                        if (hora12 == 0) {
                            hora12 = 12;
                        }

                        String horaFinal = String.format("%02d:%02d %s",
                                hora12, minute, amPm);

                        edtHora.setText(horaFinal);

                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false // 🔥 FALSE = quitar modo militar
            ).show();
        });

        btnGuardar.setOnClickListener(v -> {

            String titulo = edtTitulo.getText().toString();
            String descripcion = edtDescripcion.getText().toString();

            if(!titulo.isEmpty() && !descripcion.isEmpty()
                    && !edtFecha.getText().toString().isEmpty()
                    && !edtHora.getText().toString().isEmpty()) {

                // 🔔 Programar notificación
                Intent intentNoti = new Intent(this, NotificationReceiver.class);
                intentNoti.putExtra("titulo", titulo);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        this,
                        (int) System.currentTimeMillis(),
                        intentNoti,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

                AlarmManager alarmManager =
                        (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );

                // 📤 Regresar datos
                Intent intent = new Intent();
                intent.putExtra("titulo", titulo);
                intent.putExtra("fecha", edtFecha.getText().toString());
                intent.putExtra("descripcion", descripcion);
                setResult(RESULT_OK, intent);

                finish();

            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}