package com.example.taskplanner;

import android.content.Context;
import android.view.*;
import android.widget.*;

import java.util.List;

public class TareaAdapter extends ArrayAdapter<Tarea> {

    public TareaAdapter(Context context, List<Tarea> tareas) {
        super(context, 0, tareas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_tarea, parent, false);
        }

        Tarea tarea = getItem(position);

        TextView titulo = convertView.findViewById(R.id.txtTitulo);
        TextView descripcion = convertView.findViewById(R.id.txtDescripcion);
        TextView fecha = convertView.findViewById(R.id.txtFecha);
        TextView hora = convertView.findViewById(R.id.txtHora);
        TextView mensajeHora = convertView.findViewById(R.id.txtMensajeHora);
        CheckBox check = convertView.findViewById(R.id.checkRealizada);

        // 🔹 Asignar datos
        titulo.setText(tarea.titulo);
        descripcion.setText(tarea.descripcion);

        fecha.setText("📅 " + tarea.fecha);
        hora.setText("⏰ " + tarea.hora);
        mensajeHora.setText("Se debe realizar a las " + tarea.hora);

        // 🔹 Estado del Check
        check.setOnCheckedChangeListener(null);
        check.setChecked(tarea.realizada);

        check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tarea.realizada = isChecked;
        });

        return convertView;
    }
}