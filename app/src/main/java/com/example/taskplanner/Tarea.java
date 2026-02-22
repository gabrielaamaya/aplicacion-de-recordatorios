package com.example.taskplanner;

public class Tarea {

    String titulo;
    String descripcion;
    String fecha;
    String hora;
    String repeticion;
    boolean realizada;

    public Tarea(String titulo, String descripcion, String fecha, String hora, String repeticion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.repeticion = repeticion;
        this.realizada = false;
    }
}