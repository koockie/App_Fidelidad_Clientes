package com.fidelidad.model;

public class Cliente {
    private String id;
    private String nombre;
    private String correo;
    private int puntos;
    private String nivel;
    private int streakDias;

    public Cliente(String id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos = 0;
        this.nivel = "Bronce";
        this.streakDias = 0;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getNivel() {
        return nivel;
    }

    public int getStreakDias() {
        return streakDias;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    //manejo de puntos y compras
    public void setPuntos(int puntos) {
    this.puntos = puntos;
    }

    public void setStreakDias(int streakDias) {
        this.streakDias = streakDias;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}