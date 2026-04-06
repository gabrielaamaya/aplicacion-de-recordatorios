package com.example.taskplanner;

public class Tarea {

    public String titulo;
    public String descripcion;
    public String fecha;
    public String hora;
    public String repeticion;
    public boolean realizada = false;

    public Tarea(String titulo, String descripcion, String fecha, String hora, String repeticion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.repeticion = repeticion;
        this.realizada = false;
    }
}