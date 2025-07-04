package com.fidelidad.service;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Compra;

import java.io.*;
import java.time.LocalDate;

public class PersistenciaService {

    private final ClienteService clienteService;

    public PersistenciaService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void guardarClientes(String archivo) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
        for (Cliente cliente : clienteService.listarClientes()) {
            writer.write(String.format("%s;%s;%s;%d;%s;%d\n",
                cliente.getId(),
                cliente.getNombre(),
                cliente.getCorreo(),
                cliente.getPuntos(),
                cliente.getNivel(),
                cliente.getStreakDias()
            ));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    public void guardarCompras(String archivo, CompraService compraService) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
        for (String idCliente : compraService.obtenerTodosLosClientesConCompras()) {
            for (Compra compra : compraService.obtenerComprasPorCliente(idCliente)) {
                writer.write(String.format("%s;%f;%s\n",
                    compra.getIdCliente(),
                    compra.getMonto(),
                    compra.getFecha()
                ));
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public void cargarClientes(String archivo) {
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split(";");
            if (partes.length == 6) {
                String id = partes[0];
                String nombre = partes[1];
                String correo = partes[2];
                int puntos = Integer.parseInt(partes[3]);
                String nivel = partes[4];
                int streakDias = Integer.parseInt(partes[5]);

                Cliente cliente = new Cliente(id, nombre, correo);
                cliente.setPuntos(puntos);
                cliente.setNivel(nivel);
                cliente.setStreakDias(streakDias);
                clienteService.agregarCliente(id, cliente); // método personalizado
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public void cargarCompras(String archivo, CompraService compraService) {
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split(";");
            if (partes.length == 3) {
                String idCliente = partes[0];
                double monto = Double.parseDouble(partes[1]);
                LocalDate fecha = LocalDate.parse(partes[2]);
                compraService.registrarCompra(idCliente, monto, fecha);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


}
