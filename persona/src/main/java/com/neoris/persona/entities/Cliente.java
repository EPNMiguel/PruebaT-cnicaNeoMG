package com.neoris.persona.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@ToString
public class Cliente extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    private String contrasenia;
    private Boolean estado;

    @Builder
    public Cliente(Long idCliente, String nombre, String genero, int edad, String identificacion, String direccion, String telefono, String contrasenia, boolean estado) {
        super(nombre, genero, edad, identificacion, direccion, telefono);
        this.idCliente = idCliente;
        this.contrasenia = contrasenia;
        this.estado = estado;

    }

}
