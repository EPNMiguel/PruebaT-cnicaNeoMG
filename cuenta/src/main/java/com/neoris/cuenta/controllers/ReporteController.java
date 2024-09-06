package com.neoris.cuenta.controllers;

import com.neoris.cuenta.model.dto.ReporteResponse;
import com.neoris.cuenta.services.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("api/reportes")
    @ResponseStatus(HttpStatus.OK)
    public List<ReporteResponse> getReportes(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        return reporteService.getReporte(fechaInicio, fechaFin);
    }
}


