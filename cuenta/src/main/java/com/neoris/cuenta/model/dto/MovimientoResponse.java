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
public class MovimientoResponse {
    private Long idMovimiento;
    private Long numeroCuenta;
    private LocalDateTime fecha;
    private String TipoMovimiento;
    private Double Valor;
    private Double Saldo;
}
