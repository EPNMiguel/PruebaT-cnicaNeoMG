package com.neoris.cuenta.services;

import com.neoris.cuenta.model.dto.ReporteResponse;
import com.neoris.cuenta.repositories.CuentaRepository;
import com.neoris.cuenta.repositories.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    public List<ReporteResponse> getReporte(LocalDate fechaInicio, LocalDate fechaFin) {
        return cuentaRepository.getReporteInfo(fechaInicio, fechaFin);
    }
}
