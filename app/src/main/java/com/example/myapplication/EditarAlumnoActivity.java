package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditarAlumnoActivity extends AppCompatActivity {

    private MiDBHelper dbHelper;
    private int alumnoId; //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_alumno);

        dbHelper = new MiDBHelper(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            alumnoId = extras.getInt("alumno_id");
        }


        EditText editTextNombres = findViewById(R.id.editTextNombres);
        EditText editTextApellidos = findViewById(R.id.editTextApellidos);
        EditText editTextCorreo = findViewById(R.id.editTextCorreo);

        Button btnGuardar = findViewById(R.id.btnGuardar);


        cargarDetallesAlumno(alumnoId, editTextNombres, editTextApellidos, editTextCorreo);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nuevosNombres = editTextNombres.getText().toString();
                String nuevosApellidos = editTextApellidos.getText().toString();
                String nuevoCorreo = editTextCorreo.getText().toString();


                actualizarAlumno(alumnoId, nuevosNombres, nuevosApellidos, nuevoCorreo);


                finish();
            }
        });
    }

    private void cargarDetallesAlumno(int id, EditText editTextNombres, EditText editTextApellidos, EditText editTextCorreo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "nombres",
                "apellidos",
                "correo"
        };

        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = db.query(
                "alumno",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String nombres = cursor.getString(cursor.getColumnIndexOrThrow("nombres"));
            String apellidos = cursor.getString(cursor.getColumnIndexOrThrow("apellidos"));
            String correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"));

            editTextNombres.setText(nombres);
            editTextApellidos.setText(apellidos);
            editTextCorreo.setText(correo);
        }

        cursor.close();
    }

    private void actualizarAlumno(int id, String nuevosNombres, String nuevosApellidos, String nuevoCorreo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombres", nuevosNombres);
        values.put("apellidos", nuevosApellidos);
        values.put("correo", nuevoCorreo);

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};


        db.update("alumno", values, whereClause, whereArgs);


        db.close();
    }
}

