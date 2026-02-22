package com.example.taskplanner;

public class Tarea {

    String titulo;
    String descripcion;
    String fecha;
    boolean realizada;

    public Tarea(String titulo, String descripcion, String fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.realizada = false;
    }
}