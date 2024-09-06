package com.neoris.cuenta.services;

import com.neoris.cuenta.model.dto.ReporteResponse;
import com.neoris.cuenta.repositories.CuentaRepository;
import com.neoris.cuenta.repositories.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    public List<ReporteResponse> getReporte(LocalDate fechaInicio, LocalDate fechaFin) {
        List<ReporteResponse> reportes = new ArrayList<>();

        for (Object[] resultado : cuentaRepository.getReporteInfo(fechaInicio, fechaFin)) {
            ReporteResponse reporte = new ReporteResponse();
            reporte.setFechaMovimiento(((Timestamp) resultado[0]).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            reporte.setIdMovimiento((Long) resultado[1]);
            reporte.setIdentificacionCliente((String) resultado[2]);
            reporte.setNombreCliente((String) resultado[3]);
            reporte.setNumeroCuenta((Long) resultado[4]);
            reporte.setTipoCuenta((String) resultado[5]);
            reporte.setSaldo((Double) resultado[6]);
            reporte.setEstado((Boolean) resultado[7]);
            reporte.setTipoMovimiento((String) resultado[8]);
            reporte.setValor((Double) resultado[9]);

            reportes.add(reporte);
        }
        return reportes;
    }
}
