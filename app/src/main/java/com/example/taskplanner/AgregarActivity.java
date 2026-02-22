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
    Spinner spinnerRepeticion;
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
        spinnerRepeticion = findViewById(R.id.spinnerRepeticion);
        btnGuardar = findViewById(R.id.btnGuardar);

        // 🔥 Opciones del Spinner
        String[] opciones = {"No repetir", "Diariamente", "Semanalmente", "Mensualmente"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                opciones
        );
        spinnerRepeticion.setAdapter(adapter);

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

        // ⏰ Seleccionar Hora
        edtHora.setOnClickListener(v -> {
            new TimePickerDialog(this,
                    (view, hourOfDay, minute) -> {

                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);

                        String amPm = (hourOfDay >= 12) ? "PM" : "AM";

                        int hora12 = hourOfDay % 12;
                        if (hora12 == 0) hora12 = 12;

                        String horaFinal = String.format("%02d:%02d %s",
                                hora12, minute, amPm);

                        edtHora.setText(horaFinal);

                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
            ).show();
        });

        btnGuardar.setOnClickListener(v -> {

            String titulo = edtTitulo.getText().toString();
            String descripcion = edtDescripcion.getText().toString();
            String fecha = edtFecha.getText().toString();
            String hora = edtHora.getText().toString();
            String repeticion = spinnerRepeticion.getSelectedItem().toString();

            if(!titulo.isEmpty() && !descripcion.isEmpty()
                    && !fecha.isEmpty()
                    && !hora.isEmpty()) {

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

                long tiempo = calendar.getTimeInMillis();

                switch (repeticion) {

                    case "Diariamente":
                        alarmManager.setRepeating(
                                AlarmManager.RTC_WAKEUP,
                                tiempo,
                                AlarmManager.INTERVAL_DAY,
                                pendingIntent
                        );
                        break;

                    case "Semanalmente":
                        alarmManager.setRepeating(
                                AlarmManager.RTC_WAKEUP,
                                tiempo,
                                AlarmManager.INTERVAL_DAY * 7,
                                pendingIntent
                        );
                        break;

                    case "Mensualmente":
                        alarmManager.setRepeating(
                                AlarmManager.RTC_WAKEUP,
                                tiempo,
                                AlarmManager.INTERVAL_DAY * 30,
                                pendingIntent
                        );
                        break;

                    default:
                        alarmManager.setExact(
                                AlarmManager.RTC_WAKEUP,
                                tiempo,
                                pendingIntent
                        );
                        break;
                }

                // 📤 Enviar datos a MainActivity
                Intent intent = new Intent();
                intent.putExtra("titulo", titulo);
                intent.putExtra("descripcion", descripcion);
                intent.putExtra("fecha", fecha);
                intent.putExtra("hora", hora);
                intent.putExtra("repeticion", repeticion);

                setResult(RESULT_OK, intent);
                finish();

            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}