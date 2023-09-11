package com.example.myapplication;

public class Carrera {
    int id_carrera;
    String nombre_carrera;

    public Carrera() {
    }

    public Carrera(int id_carrera, String nombre_carrera) {
        this.id_carrera = id_carrera;
        this.nombre_carrera = nombre_carrera;
    }

    public int getId_carrera() {
        return id_carrera;
    }

    public void setId_carrera(int id_carrera) {
        this.id_carrera = id_carrera;
    }

    public String getNombre_carrera() {
        return nombre_carrera;
    }

    public void setNombre_carrera(String nombre_carrera) {
        this.nombre_carrera = nombre_carrera;
    }
}
