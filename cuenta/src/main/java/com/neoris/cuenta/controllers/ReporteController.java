package com.neoris.cuenta.controllers;

import com.neoris.cuenta.model.dto.ReporteResponse;
import com.neoris.cuenta.repositories.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/reporte")
@RequiredArgsConstructor
public class ReporteController {

    private final CuentaRepository cuentaRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReporteResponse> getReporteInfo() {
        try {
            return this.cuentaRepository.getReporteInfo();
        } catch (Exception e) {

            return null;
        }
    }
}
