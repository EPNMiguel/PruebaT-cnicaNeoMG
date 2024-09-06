package com.neoris.cuenta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteResponse {
    private Long idMovimiento;
    private LocalDate fechaMovimiento;
    private String identificacionCliente;
    private String nombreCliente;
    private Long numeroCuenta;
    private String tipoCuenta;
    private Double saldo;
    private Boolean estado;
    private String tipoMovimiento;
    private Double valor;
}
