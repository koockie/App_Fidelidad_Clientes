package com.fidelidad.service;

import com.fidelidad.model.Cliente;

import java.util.*;

public class ClienteService {

    private final Map<String, Cliente> clientes = new HashMap<>();

    public Cliente agregarCliente(String id, String nombre, String correo) {
        if (!correo.contains("@")) {
            throw new IllegalArgumentException("Correo no válido");
        }
        Cliente cliente = new Cliente(id, nombre, correo);
        clientes.put(id, cliente);
        return cliente;
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
}
