package com.neoris.cuenta.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuenta")
@Builder
public class Cuenta {

    @Id
    private Long numeroCuenta;
    private String tipoCuenta;
    private Double saldo;
    private Boolean estado;
    private String identificacion;

}
