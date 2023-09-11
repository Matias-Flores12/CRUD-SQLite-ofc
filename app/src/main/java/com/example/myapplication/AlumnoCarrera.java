package com.example.myapplication;

public class AlumnoCarrera {
    private int id;
    private String nombres;
    private String apellidos;
    private String nombre_carrera;

    public AlumnoCarrera() {
    }

    public AlumnoCarrera(int id, String nombres, String apellidos, String nombre_carrera) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombre_carrera = nombre_carrera;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre_carrera() {
        return nombre_carrera;
    }

    public void setNombre_carrera(String nombre_carrera) {
        this.nombre_carrera = nombre_carrera;
    }
}
