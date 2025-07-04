package com.fidelidad.service;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Compra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompraServiceTest {

    private CompraService compraService;
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService();
        compraService = new CompraService(clienteService);
        clienteService.agregarCliente("1", "Marcelo", "marcelo@email.com");
    }

    // Test 1: registrar una compra válida
    @Test
    void testRegistrarCompraValida() {
        Compra compra = compraService.registrarCompra("1", 1000, LocalDate.now());
        assertNotNull(compra);
        assertEquals(1000, compra.getMonto());
    }

    // Test 2: registrar compra para cliente inexistente
    @Test
    void testRegistrarCompraClienteInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            compraService.registrarCompra("999", 1000, LocalDate.now());
        });
    }

    // Test 3: verificar que se acumulan los puntos correctamente
    @Test
    void testAcumulacionPuntos() {
        compraService.registrarCompra("1", 1000, LocalDate.now());  // 10 puntos
        Cliente cliente = clienteService.obtenerCliente("1");
        assertEquals(10, cliente.getPuntos());  // 1 punto cada 100 pesos
    }

    // Test 4: ver historial de compras
    @Test
    void testHistorialCompras() {
        compraService.registrarCompra("1", 1000, LocalDate.now());
        compraService.registrarCompra("1", 500, LocalDate.now());
        List<Compra> compras = compraService.obtenerComprasPorCliente("1");
        assertEquals(2, compras.size());
    }
}
