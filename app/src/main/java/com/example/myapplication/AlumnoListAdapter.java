package com.example.myapplication;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapplication.Alumno;

import java.util.List;

public class AlumnoListAdapter extends BaseAdapter {
    private Context context;
    private List<Alumno> listaAlumnos;
    private OnEditarClickListener editarClickListener;
    private OnEliminarClickListener eliminarClickListener;

    public AlumnoListAdapter(Context context, List<Alumno> listaAlumnos) {
        this.context = context;
        this.listaAlumnos = listaAlumnos;
    }

    public void setOnEditarClickListener(OnEditarClickListener listener) {
        editarClickListener = listener;
    }

    public void setOnEliminarClickListener(OnEliminarClickListener listener) {
        eliminarClickListener = listener;
    }

    @Override
    public int getCount() {
        return listaAlumnos.size();
    }

    @Override
    public Alumno getItem(int position) {
        return listaAlumnos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_alumno, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textViewNombre = convertView.findViewById(R.id.textViewNombre);
            viewHolder.textViewApellidos = convertView.findViewById(R.id.textViewApellidos);
            viewHolder.textViewCorreo = convertView.findViewById(R.id.textViewCorreo);
            viewHolder.btnEditar = convertView.findViewById(R.id.btnEditar);
            viewHolder.btnEliminar = convertView.findViewById(R.id.btnEliminar);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Alumno alumno = getItem(position);

        viewHolder.textViewNombre.setText(alumno.getNombres());
        viewHolder.textViewApellidos.setText(alumno.getApellidos());
        viewHolder.textViewCorreo.setText(alumno.getCorreo());


        viewHolder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editarClickListener != null) {
                    editarClickListener.onEditarClick(alumno);
                }
            }
        });

        viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eliminarClickListener != null) {
                    eliminarClickListener.onEliminarClick(alumno);
                }
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewNombre;
        TextView textViewApellidos;
        TextView textViewCorreo;
        Button btnEditar;
        Button btnEliminar;
    }

    public interface OnEditarClickListener {
        void onEditarClick(Alumno alumno);
    }

    public interface OnEliminarClickListener {
        void onEliminarClick(Alumno alumno);
    }
}
