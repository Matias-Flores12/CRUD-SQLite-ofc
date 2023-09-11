package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.nio.file.ClosedFileSystemException;

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
        Log.d("xxxxxxxxxxxxxxxxxxxx", "eliminarAlumno: "+database);

        return filasEliminadas > 0;
    }

//    public Cursor obtenerAlumnos() {
//        String[] columnas = {"id", "nombre"};
//
//        return database.query("alumno", columnas, null, null, null, null, null);
//    }
}
