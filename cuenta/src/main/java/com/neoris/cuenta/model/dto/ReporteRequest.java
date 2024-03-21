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
public class ReporteRequest {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String Identificacion;
}
