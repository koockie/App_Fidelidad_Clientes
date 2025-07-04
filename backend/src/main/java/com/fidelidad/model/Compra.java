package com.fidelidad.model;

import java.time.LocalDate;

public class Compra {
    private String idCliente;
    private double monto;
    private LocalDate fecha;

    public Compra(String idCliente, double monto, LocalDate fecha) {
        this.idCliente = idCliente;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
