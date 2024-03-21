package com.neoris.cuenta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteResponse {
    private LocalDateTime fechaMovimiento;
    private String identificacionCliente;
    private String nombreCliente;
    private Long numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    private String TipoMovimiento;
    private Double Valor;
    private Double Saldo;
}
