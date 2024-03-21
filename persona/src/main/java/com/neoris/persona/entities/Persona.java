package com.neoris.persona.entities;

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
    String identificacion;
    String direccion;
    String telefono;
}
