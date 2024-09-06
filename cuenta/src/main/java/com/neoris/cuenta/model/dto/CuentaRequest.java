package com.neoris.cuenta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuentaRequest {
    private Long numeroCuenta;
    private String tipoCuenta;
    private Double saldo;
    private Boolean estado;
    private String identificacion;
}
