package com.neoris.persona.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.neoris.persona.entities.Cliente;
import com.neoris.persona.model.dto.ClienteRequest;
import com.neoris.persona.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddClienteNewCliente() {
        ClienteRequest clienteRequest = new ClienteRequest("Miguel Gaibor", "masculino", 30, "1722587829", "el inca", "0987053787", "password", true);
        when(clienteRepository.findByIdentificacion(clienteRequest.getIdentificacion())).thenReturn(null);
        ResponseEntity<Object> response = clienteService.addCliente(clienteRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testAddClienteExistingCliente() {
        ClienteRequest clienteRequest = new ClienteRequest("Miguel Gaibor", "masculino", 30, "1722587829", "el inca", "0987053787", "password", true);
        Cliente existingCliente = new Cliente();
        when(clienteRepository.findByIdentificacion(clienteRequest.getIdentificacion())).thenReturn(existingCliente);
        ResponseEntity<Object> response = clienteService.addCliente(clienteRequest);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void testAddClienteException() {
        ClienteRequest clienteRequest = new ClienteRequest("Miguel Gaibor", "masculino", 30, "1722587829", "el inca", "0987053787", "password", true);
        when(clienteRepository.findByIdentificacion(clienteRequest.getIdentificacion())).thenThrow(new RuntimeException("Database error"));
        ResponseEntity<Object> response = clienteService.addCliente(clienteRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}