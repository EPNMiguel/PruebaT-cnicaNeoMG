package com.neoris.cuenta.model.dto;


import com.neoris.cuenta.entities.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimientoRequest {
    private Cuenta cuenta;
    private LocalDateTime fecha;
    private String TipoMovimiento;
    private Double Valor;
    private Double Saldo;
}
