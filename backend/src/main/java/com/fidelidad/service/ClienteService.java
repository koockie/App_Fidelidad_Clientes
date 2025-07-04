package com.fidelidad.service;

import com.fidelidad.model.Cliente;

import java.time.LocalDate;
import java.util.*;

public class ClienteService {

    private final Map<String, Cliente> clientes = new HashMap<>();

    public Cliente agregarCliente(String id, String nombre, String correo) { //agregar clientes desde cli
        if (!correo.contains("@")) {
            throw new IllegalArgumentException("Correo no válido");
        }
        Cliente cliente = new Cliente(id, nombre, correo);
        clientes.put(id, cliente);
        return cliente;
    }
    // parecido al de arriba pero la uso internamente paara cargar clientes desde el txt
    public void agregarCliente(String id, Cliente cliente) {
        clientes.put(id, cliente);
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes.values());
    }

    public Cliente obtenerCliente(String id) {
        return clientes.get(id);
    }

    public void actualizarCliente(String id, String nuevoNombre, String nuevoCorreo) {
        Cliente cliente = clientes.get(id);
        if (cliente != null) {
            cliente.setNombre(nuevoNombre);
            cliente.setCorreo(nuevoCorreo);
        }
    }

    public void eliminarCliente(String id) {
        clientes.remove(id);
    }

    public void agregarPuntos(String idCliente, int puntos) {
    Cliente cliente = clientes.get(idCliente);
    if (cliente != null) {
        cliente.setPuntos(cliente.getPuntos() + puntos);
        actualizarNivel(cliente);
    }
}

    public void actualizarStreak(String idCliente, LocalDate nuevaFecha) {
        Cliente cliente = clientes.get(idCliente);
        if (cliente != null) {
            cliente.setStreakDias(cliente.getStreakDias() + 1);
        }
    }

    private void actualizarNivel(Cliente cliente) {
        int puntos = cliente.getPuntos();
        if (puntos <= 500  ) {
            cliente.setNivel("Oro");
        } else if (puntos >= 500 && puntos <= 1499) {
            cliente.setNivel("Plata");
        } else if (puntos >= 1500 && puntos <= 2999) {
            cliente.setNivel("oro");
        } else {
            cliente.setNivel("platino");
        }
    }
    public Map<String, Cliente> getClientes() {
    return clientes;
}

}
