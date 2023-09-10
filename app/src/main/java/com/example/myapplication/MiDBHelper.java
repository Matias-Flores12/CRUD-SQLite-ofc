package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MiDBHelper extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DATOS = "mi_base_de_datos.db";
    private static final int VERSION_BASE_DATOS = 1;

    // Constructor
    public MiDBHelper(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION_BASE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE carrera (" +
                "id INTEGER PRIMARY KEY," +
                "nombre TEXT NOT NULL," +
                "estado TEXT);");

        db.execSQL("CREATE TABLE alumno (" +
                "id INTEGER PRIMARY KEY," +
                "nombres TEXT NOT NULL," +
                "apellidos TEXT NOT NULL," +
                "correo TEXT," +
                "carrera_id INTEGER," +
                "FOREIGN KEY (carrera_id) REFERENCES carrera(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
