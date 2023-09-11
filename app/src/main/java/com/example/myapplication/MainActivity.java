package com.example.myapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MiDBHelper dbHelper;
    private AlumnoListAdapter alumnoListAdapter;

    private Spinner spinnerCarreras;
    private List<Alumno> listaAlumnos;
    private List<Carrera> listaCarreras;
    DAOemer dao = new DAOemer(this);
    private int alumnoIdEliminar = -1;
//    Spinner spinnerCarreras = findViewById(R.id.spinnerCarrera);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new DAOemer(this);
        dao.open();
        dbHelper = new MiDBHelper(this);
        listaCarreras = new ArrayList<>();
        listaAlumnos = new ArrayList<>();
        List<String> nombresCarreras = dao.obtenerNombresCarreras();

        Log.d("test", "onCreate: "+listaCarreras+listaAlumnos);

        Spinner spinnerCarreras = findViewById(R.id.spinnerCarrera);

        Button btnAgregar = findViewById(R.id.btnAgregar);
        Button btnListar = findViewById(R.id.btnListar);



        ListView listViewAlumnos = findViewById(R.id.listViewAlumnos);


        alumnoListAdapter = new AlumnoListAdapter(this, listaAlumnos);
        listViewAlumnos.setAdapter(alumnoListAdapter);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresCarreras);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCarreras.setAdapter(adapter);

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
