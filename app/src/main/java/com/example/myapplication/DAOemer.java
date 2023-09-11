package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.nio.file.ClosedFileSystemException;
import java.util.ArrayList;
import java.util.List;

public class DAOemer {

    private MiDBHelper dbHelper;
    private SQLiteDatabase database;

    public DAOemer(Context context) {
        dbHelper = new MiDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean agregarAlumno(String nombre) {
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);

        long resultado = database.insert("alumno", null, values);

        return resultado != -1;
    }

    public boolean eliminarAlumno(int alumnoId) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(alumnoId)};

        int filasEliminadas = database.delete("alumno", whereClause, whereArgs);


        return filasEliminadas > 0;
    }

    public Cursor obtenerAlumnos() {
        String[] columnas = {"id", "nombre",};

        return database.query("alumno", columnas, null, null, null, null, null);
    }



    public List<AlumnoCarrera> obtenerAlumnosCarrera() {
        List<AlumnoCarrera> listaAlumnosCarrera = null;
//
//        String[] columnas = {"id", "nombres","apellidos", "nombre_carrera"};

        Cursor cursor = database.rawQuery("SELECT a.id, a.nombres, a.apellidos, c.nombre_carrera FROM alumno a, carrera c WHERE a.carrera_id = c.id", null);

        if (cursor.moveToFirst()) {
            listaAlumnosCarrera = new ArrayList<>();

            do {
                int id = cursor.getInt(0);
                String nombres = cursor.getString(1);
                String apellidos = cursor.getString(2);
                String nombre_carrera = cursor.getString(3);

                AlumnoCarrera alumnoCarrera = new AlumnoCarrera(id, nombres, apellidos, nombre_carrera);
                listaAlumnosCarrera.add(alumnoCarrera);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listaAlumnosCarrera;
    }
    public List<String> obtenerNombresCarreras() {
        List<String> nombresCarreras = new ArrayList<>();

        Cursor cursor = database.query("carrera", new String[]{"nombre_carrera"}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String nombreCarrera = cursor.getString(cursor.getColumnIndex("nombre_carrera"));
                nombresCarreras.add(nombreCarrera);
            }
            cursor.close();
        }

        return nombresCarreras;
    }
    public List<Carrera> obtenerCarrera(){

        List<Carrera> listaCarrera = new ArrayList<>();


        Cursor cursor = database.query("carrera",null,null,null,null,null,null);


        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nombre_carrera = cursor.getString(cursor.getColumnIndex("nombre_carrera"));
            Log.d("sadsdasdasdaasdsda", "obtenerCarrera: "+listaCarrera);
            Carrera carrera = new Carrera(id, nombre_carrera);
            listaCarrera.add(carrera);
        }
//        if (cursor.moveToFirst()) {
//
//            do {
//                int id = cursor.getInt(0);
//                String nombre_carrera = cursor.getString(1);
//                Log.d("sadsdasdasdaasdsda", "obtenerCarrera: "+listaCarrera);
//                Carrera carrera = new Carrera(id, nombre_carrera);
//                listaCarrera.add(carrera);
//            } while (cursor.moveToNext());
//
//        }

        cursor.close();

        return listaCarrera;
    }









}
