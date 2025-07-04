package com.fidelidad.service;

import com.fidelidad.model.Compra;
import com.fidelidad.model.Cliente;

import java.time.LocalDate;
import java.util.*;

public class CompraService {
    private final Map<String, List<Compra>> comprasPorCliente = new HashMap<>();
    private final ClienteService clienteService;

    public CompraService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public Compra registrarCompra(String idCliente, double monto, LocalDate fecha) {
    Cliente cliente = clienteService.obtenerCliente(idCliente);
    if (cliente == null) {
        throw new IllegalArgumentException("Cliente no encontrado");
    }

    Compra compra = new Compra(idCliente, monto, fecha);
    comprasPorCliente.computeIfAbsent(idCliente, k -> new ArrayList<>()).add(compra);

    // Puntos base: 1 punto por cada 100 pesos
    int puntosBase = (int) (monto / 100);

    // Obtener bono según nivel actual del cliente
    double bono = 0.0;
    switch (cliente.getNivel()) { 
        case "Plata":
            bono = 0.20;
            break;
        case "Oro":
            bono = 0.50;
            break;
        case "Platino":
            bono = 1.00;
            break;
        default:
            bono = 0.0;//bronce no tiene bono
    }


    //calcular puntos totales con bono
    int puntosTotales = (int) (puntosBase * (1 + bono));

    //actualizar puntos 
    clienteService.agregarPuntos(idCliente, puntosTotales);
    clienteService.actualizarStreak(idCliente, fecha);

    return compra;
}

    public List<Compra> obtenerComprasPorCliente(String idCliente) {
        return comprasPorCliente.getOrDefault(idCliente, new ArrayList<>());
    }
    public Set<String> obtenerTodosLosClientesConCompras() {
    return comprasPorCliente.keySet();
    }

}

