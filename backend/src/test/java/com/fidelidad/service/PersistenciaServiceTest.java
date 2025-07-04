package com.fidelidad.service;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Compra;

import org.junit.jupiter.api.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenciaServiceTest {

    private ClienteService clienteService;
    private PersistenciaService persistencia;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService();
        persistencia = new PersistenciaService(clienteService);
    }

    @Test
    void testGuardarYCargarClientes() {
        clienteService.agregarCliente("1", "Marcelo", "marcelo@email.com");
        clienteService.agregarCliente("2", "Karla", "karla@email.com");

        persistencia.guardarClientes("clientes_test.txt");

        clienteService = new ClienteService(); // limpiamos
        persistencia = new PersistenciaService(clienteService);

        persistencia.cargarClientes("clientes_test.txt");
        List<Cliente> lista = clienteService.listarClientes();
        assertEquals(2, lista.size());

        // Limpieza del archivo de prueba
        new File("clientes_test.txt").delete();
    }
    @Test
void testGuardarYCargarCompras() {
    Cliente cliente = clienteService.agregarCliente("1", "Marcelo", "marcelo@email.com");
    CompraService compraService = new CompraService(clienteService);
    PersistenciaService persistencia = new PersistenciaService(clienteService);

    compraService.registrarCompra("1", 1000, LocalDate.now());
    compraService.registrarCompra("1", 500, LocalDate.now());

    persistencia.guardarCompras("compras_test.txt", compraService);

    // Crear nuevo entorno limpio
    clienteService = new ClienteService();
    clienteService.agregarCliente("1", "Marcelo", "marcelo@email.com");
    compraService = new CompraService(clienteService);
    persistencia = new PersistenciaService(clienteService);

    persistencia.cargarCompras("compras_test.txt", compraService);

    List<Compra> compras = compraService.obtenerComprasPorCliente("1");
    assertEquals(2, compras.size());

    new File("compras_test.txt").delete();
    }

}
