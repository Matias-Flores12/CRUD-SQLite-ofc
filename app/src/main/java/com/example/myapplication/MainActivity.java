package com.example.myapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MiDBHelper dbHelper;
    private AlumnoListAdapter alumnoListAdapter;
    private List<Alumno> listaAlumnos;
    private int alumnoIdEliminar = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MiDBHelper(this);
        listaAlumnos = new ArrayList<>();


        Button btnAgregar = findViewById(R.id.btnAgregar);
        Button btnListar = findViewById(R.id.btnListar);
//        Button btnEditar = findViewById(R.id.btnEditarAlumno);
//        Button btnEliminar = findViewById(R.id.btnEliminarAlumno);


        ListView listViewAlumnos = findViewById(R.id.listViewAlumnos);


        alumnoListAdapter = new AlumnoListAdapter(this, listaAlumnos);
        listViewAlumnos.setAdapter(alumnoListAdapter);


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTextNombre = findViewById(R.id.editTextNombres);
                String nombre = editTextNombre.getText().toString();
                EditText editTextApellido = findViewById(R.id.editTextApellidos);
                String apellido = editTextApellido.getText().toString();
                EditText editTextCorreo = findViewById(R.id.editTextCorreo);
                String correo = editTextCorreo.getText().toString();


                Log.d("MainActivity", "Haciendo clic en el botón Agregar");
                Log.d("MainActivity", "Agregando un alumno: Nombres: " + nombre + ", Apellidos: Apellido, Correo: correo@example.com, Carrera ID: 1");
                agregarAlumno(nombre, apellido, correo, 1);
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listaAlumnos.clear();
                listaAlumnos.addAll(obtenerAlumnos());
                alumnoListAdapter.notifyDataSetChanged();
                Log.d("MainActivity", "Haciendo clic en el botón Listar");
            }
        });

//        btnEditar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                int posicionAlumnoParaEditar = listViewAlumnos.getCheckedItemPosition();
//
//                if (posicionAlumnoParaEditar != AdapterView.INVALID_POSITION) {
//
//                    Alumno alumnoParaEditar = listaAlumnos.get(posicionAlumnoParaEditar);
//
//
//                    Intent intent = new Intent(MainActivity.this, EditarAlumnoActivity.class);
//
//
//                    intent.putExtra("alumno_id", alumnoParaEditar.getId());
//
//
//                    startActivity(intent);
//                    Log.d("MainActivity", "Haciendo clic en el botón Editar");
//                } else {
//
//                    Toast.makeText(MainActivity.this, "Seleccione un alumno para editar", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        btnEliminar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int posicionAlumnoParaEliminar = listViewAlumnos.getCheckedItemPosition();
//
//                if (posicionAlumnoParaEliminar != AdapterView.INVALID_POSITION) {
//
//                    Alumno alumnoParaEliminar = listaAlumnos.get(posicionAlumnoParaEliminar);
//
//
//                    alumnoIdEliminar = alumnoParaEliminar.getId();
//
//
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setMessage("¿Estás seguro de que deseas eliminar a este alumno?");
//                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            eliminarAlumno(alumnoIdEliminar);
//
//
//                            listaAlumnos.remove(posicionAlumnoParaEliminar);
//                            alumnoListAdapter.notifyDataSetChanged();
//
//                            Toast.makeText(MainActivity.this, "Alumno eliminado con éxito", Toast.LENGTH_SHORT).show();
//                            Log.d("MainActivity", "Haciendo clic en el botón Eliminar");
//                        }
//                    });
//                    builder.setNegativeButton("No", null); // No realizar ninguna acción si el usuario hace clic en "No"
//                    builder.show();
//                } else {
//
//                    Toast.makeText(MainActivity.this, "Seleccione un alumno para eliminar", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
   }

    private void agregarAlumno(String nombres, String apellidos, String correo, int carreraId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombres", nombres);
        values.put("apellidos", apellidos);
        values.put("correo", correo);
        values.put("carrera_id", carreraId);

        long newRowId = db.insert("alumno", null, values);
        db.close();

        if (newRowId != -1) {

            Toast.makeText(this, "Alumno agregado con éxito", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Error al agregar el alumno", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Alumno> obtenerAlumnos() {
        List<Alumno> listaAlumnos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Log.d("sexxxxxxxxxxxxxxx", "eliminarAlumno: "+db);

        Cursor cursor = db.query("alumno", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nombres = cursor.getString(cursor.getColumnIndex("nombres"));
            String apellidos = cursor.getString(cursor.getColumnIndex("apellidos"));
            String correo = cursor.getString(cursor.getColumnIndex("correo"));
            int carreraId = cursor.getInt(cursor.getColumnIndex("carrera_id"));

            Alumno alumno = new Alumno(id, nombres, apellidos, correo, carreraId);
            listaAlumnos.add(alumno);
        }

        cursor.close();
        db.close();

        return listaAlumnos;
    }

//    private void eliminarAlumno(int alumnoId) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String whereClause = "id=?";
//        String[] whereArgs = {String.valueOf(alumnoId)};
//        db.delete("alumno", whereClause, whereArgs);
//        db.close();
//    }
}
