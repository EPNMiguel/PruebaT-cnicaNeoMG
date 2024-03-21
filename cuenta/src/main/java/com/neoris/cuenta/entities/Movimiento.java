package com.neoris.cuenta.entities;

import ch.qos.logback.core.net.server.Client;
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
    @ManyToOne
    @JoinColumn(name = "numero_cuenta", referencedColumnName = "numeroCuenta")
    private Cuenta cuenta;
    @CreationTimestamp
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private Double Valor;
    private Double Saldo;

    @Builder
    public Movimiento(Long idMovimiento, Long numeroCuenta, LocalDateTime fecha, String tipoMovimiento, Double valor, double saldo){
        this.idMovimiento = idMovimiento;
        this.cuenta.setNumeroCuenta(numeroCuenta);
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.Valor = valor;
        this.Saldo = saldo;
    }
}
