package com.fidelidad.service;

import com.fidelidad.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceTest {

    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService();
    }

    //Test1 para agregar un cliente válido 
    @Test
    void testAgregarClienteValido() {
        Cliente cliente = clienteService.agregarCliente("1", "Marcelo", "marcelo@email.com");
        assertEquals("Marcelo", cliente.getNombre());
        assertEquals("Bronce", cliente.getNivel());
        assertEquals(0, cliente.getPuntos());
        assertEquals(0, cliente.getStreakDias());
    }
    //Test2 para agregar un cliente con correo inválido, QUE USE @
    @Test
    void testAgregarClienteCorreoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            clienteService.agregarCliente("2", "Juan", "correoInvalido");
        });
    }
    //test3 para obtener todos los clientes que hay
    @Test 
    void testListarClientes() {
        clienteService.agregarCliente("1", "Marcelo", "marcelo@email.com");
        List<Cliente> lista = clienteService.listarClientes();
        assertEquals(1, lista.size());
    }
    //test4 para actualizar un cliente por su id, se espera que se actualice el nombre y correo
    @Test
    void testActualizarCliente() {
        clienteService.agregarCliente("1", "Marcelo", "marcelo@email.com");
        clienteService.actualizarCliente("1", "Marcelo F", "nuevo@email.com");
        Cliente actualizado = clienteService.obtenerCliente("1");
        assertEquals("Marcelo F", actualizado.getNombre());
        assertEquals("nuevo@email.com", actualizado.getCorreo());
    }
    //test5 para eliminar un cliente por su id, se espera un null al consultar el cliente eliminado
    @Test
    void testEliminarCliente() {
        clienteService.agregarCliente("1", "Marcelo", "marcelo@email.com");
        clienteService.eliminarCliente("1");
        assertNull(clienteService.obtenerCliente("1"));
    }
}
