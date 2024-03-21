package com.neoris.cuenta.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

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
    @ManyToOne
    @JoinColumn(name = "numero_cuenta", referencedColumnName = "numeroCuenta")
    private Cuenta cuenta;
    @CreationTimestamp
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private Double Valor;
    private Double Saldo;

}
