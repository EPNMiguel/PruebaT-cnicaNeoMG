package com.neoris.persona.services;

import com.neoris.persona.entities.Cliente;
import com.neoris.persona.model.dto.ClienteRequest;
import com.neoris.persona.model.dto.ClienteResponse;
import com.neoris.persona.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public void addCliente(ClienteRequest clienteRequest) {
        var cliente = Cliente.builder()
                .nombre(clienteRequest.getNombre())
                .genero(clienteRequest.getGenero())
                .edad(clienteRequest.getEdad())
                .identificacion(clienteRequest.getIdentificacion())
                .direccion(clienteRequest.getDireccion())
                .telefono(clienteRequest.getTelefono())
                .contrasenia(clienteRequest.getContrasenia())
                .estado(clienteRequest.getEstado())
                .build();

        clienteRepository.save(cliente);
        log.info("Cliente agregado " + cliente);
    }

    public List<ClienteResponse> getAllClientes() {
        var clientes = clienteRepository.findAll();
        return clientes.stream().map(this::mapToClientesResponse).toList();
    }

    private ClienteResponse mapToClientesResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .genero((cliente.getGenero()))
                .edad((cliente.getEdad()))
                .identificacion(cliente.getIdentificacion())
                .telefono(cliente.getTelefono())
                .contrasenia(cliente.getContrasenia())
                .estado(cliente.getEstado())
                .build();
    }
}
