package com.neoris.cuenta.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movimiento")
@Builder
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMovimiento;
    private Long numeroCuenta;
    @CreationTimestamp
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private Double Valor;
    private Double Saldo;

}
