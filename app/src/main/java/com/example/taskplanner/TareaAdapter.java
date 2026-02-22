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
        TextView fechaHora = convertView.findViewById(R.id.txtFechaHora);
        CheckBox check = convertView.findViewById(R.id.checkRealizada);

        titulo.setText(tarea.titulo);
        descripcion.setText(tarea.descripcion);
        fechaHora.setText(tarea.fecha);

        check.setChecked(tarea.realizada);

        check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tarea.realizada = isChecked;
        });

        return convertView;
    }
}