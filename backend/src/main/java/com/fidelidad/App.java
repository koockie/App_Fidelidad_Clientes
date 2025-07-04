package com.fidelidad;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Compra;
import com.fidelidad.service.ClienteService;
import com.fidelidad.service.CompraService;
import com.fidelidad.service.PersistenciaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ClienteService clienteService = new ClienteService();
        CompraService compraService = new CompraService(clienteService);
        PersistenciaService persistencia = new PersistenciaService(clienteService);

        // Cargar datos desde los archivos correctos
        persistencia.cargarClientes("backend/data/clientes.txt");
        persistencia.cargarCompras("backend/data/compras.txt", compraService);

        while (true) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Ver todos los clientes");
            System.out.println("3. Eliminar cliente");
            System.out.println("4. Registrar compra");
            System.out.println("5. Ver historial de compras");
            System.out.println("6. Guardar y salir\n");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Rut: ");
                    String id = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Correo: ");
                    String correo = scanner.nextLine();

                    try {
                        clienteService.agregarCliente(id, nombre, correo);
                        System.out.println("Cliente agregado correctamente.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "2":
                    for (Cliente c : clienteService.listarClientes()) {
                        System.out.printf("Rut: %s | Nombre: %s | Correo: %s | Puntos: %d | Nivel: %s\n",
                                c.getId(), c.getNombre(), c.getCorreo(), c.getPuntos(), c.getNivel());
                    }
                    break;

                case "3":
                    System.out.print("\nRut del cliente a eliminar: ");
                    String idEliminar = scanner.nextLine();

                    if (clienteService.obtenerCliente(idEliminar) != null) {
                        clienteService.eliminarCliente(idEliminar);
                        System.out.println("Cliente eliminado.");
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case "4":
                    System.out.print("\nRut cliente: ");
                    String idCompra = scanner.nextLine();

                    if (clienteService.obtenerCliente(idCompra) == null) {
                        System.out.println("Cliente no encontrado.");
                        break;
                    }

                    System.out.print("Monto de la compra: ");
                    String montoTexto = scanner.nextLine();
                    try {
                        double monto = Double.parseDouble(montoTexto);
                        compraService.registrarCompra(idCompra, monto, LocalDate.now());
                        System.out.println("Compra registrada.");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: El monto ingresado no es válido.");
                    }
                    break;

                case "5":
                    System.out.print("Rut cliente: ");
                    String idHistorial = scanner.nextLine();

                    if (clienteService.obtenerCliente(idHistorial) == null) {
                        System.out.println("Cliente no encontrado.");
                        break;
                    }

                    List<Compra> compras = compraService.obtenerComprasPorCliente(idHistorial);
                    if (compras.isEmpty()) {
                        System.out.println("Este cliente no tiene compras registradas.");
                    } else {
                        for (Compra compra : compras) {
                            System.out.printf("Fecha: %s | Monto: %.2f\n", compra.getFecha(), compra.getMonto());
                        }
                    }
                    break;

                case "6":
                    persistencia.guardarClientes("backend/data/clientes.txt");
                    persistencia.guardarCompras("backend/data/compras.txt", compraService);
                    System.out.println("\nDatos guardados correctamente. Saliendo...");
                    return;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}
