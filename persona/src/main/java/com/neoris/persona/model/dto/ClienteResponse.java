package com.neoris.persona.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponse {
    Long idCliente;
    String nombre;
    String genero;
    int edad;
    String identificacion;
    String direccion;
    String telefono;
    String contrasenia;
    boolean estado;
}
