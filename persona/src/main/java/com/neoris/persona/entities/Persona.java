package com.neoris.persona.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Persona {
    String nombre;
    String genero;
    int edad;
    @Column(unique = true , nullable = false)
    String identificacion;
    String direccion;
    String telefono;
}
