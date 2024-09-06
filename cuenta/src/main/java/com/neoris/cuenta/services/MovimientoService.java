package com.neoris.cuenta.services;

import com.neoris.cuenta.entities.Cuenta;
import com.neoris.cuenta.entities.Movimiento;
import com.neoris.cuenta.model.dto.MovimientoRequest;
import com.neoris.cuenta.model.dto.MovimientoResponse;
import com.neoris.cuenta.repositories.CuentaRepository;
import com.neoris.cuenta.repositories.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    public String addMovimiento(MovimientoRequest movimientoRequest) {
        try {
            Cuenta cuentaObtenida = cuentaRepository.getReferenceById(movimientoRequest.getNumeroCuenta());

            if (movimientoRequest.getTipoMovimiento().equals("deposito")) {
                //si es deposito siempre guarda
                double saldo = cuentaObtenida.getSaldo();
                Double SaldoTotalNuevo = saldo + Math.abs(movimientoRequest.getValor());
                log.info("SaldoTotalNuevo: " + SaldoTotalNuevo);
                var movimiento = Movimiento.builder()
                        .tipoMovimiento(movimientoRequest.getTipoMovimiento())
                        .numeroCuenta(movimientoRequest.getNumeroCuenta())
                        .Valor(Math.abs(movimientoRequest.getValor()))
                        .Saldo(SaldoTotalNuevo)
                        .build();
                movimientoRepository.save(movimiento);
                cuentaObtenida.setSaldo(SaldoTotalNuevo);
                cuentaRepository.save(cuentaObtenida);
                log.info("Movimiento creado");
                return "{\"message\":\"Movimiento creado\",\"code\":0}";
            } else { //retiro
                //validar que saldo no quede menor a cero
                double tempSaldo = cuentaObtenida.getSaldo() - Math.abs(movimientoRequest.getValor());
                if (tempSaldo < 0) {
                    // no guarda
                    log.info("Saldo Final menor a 0, no se puede guardar");
                    return "{\"message\":\"Saldo Final menor a 0, no se puede guardar\",\"code\":1}";
                } else {
                    // guadar y actualiza el saldo
                    log.info("tempSaldo: " + tempSaldo);
                    var movimiento = Movimiento.builder()
                            .tipoMovimiento(movimientoRequest.getTipoMovimiento())
                            .numeroCuenta(movimientoRequest.getNumeroCuenta())
                            .Valor(-Math.abs(movimientoRequest.getValor()))
                            .Saldo(tempSaldo)
                            .build();
                    movimientoRepository.save(movimiento);
                    cuentaObtenida.setSaldo(tempSaldo);
                    cuentaRepository.save(cuentaObtenida);
                    log.info("Movimiento creado");
                    return "{\"message\":\"Movimiento creado\",\"code\":0}";
                }
            }
        } catch (Exception e) {
            log.error("No se pudo crear movimiento " + e);
            return "{\"message\":\"\"No se pudo crear movimiento " + e.getMessage() + "\",\"code\":0}";
        }
    }

    public List<MovimientoResponse> getAllMovimientos() {
        var movimientos = movimientoRepository.findAll();
        return movimientos.stream().map(this::mapToMovimientosResponse).toList();

    }

    private MovimientoResponse mapToMovimientosResponse(Movimiento movimiento) {
        return MovimientoResponse.builder()
                .idMovimiento(movimiento.getIdMovimiento())
                .numeroCuenta(movimiento.getNumeroCuenta())
                .fecha(movimiento.getFecha())
                .TipoMovimiento(movimiento.getTipoMovimiento())
                .Valor(movimiento.getValor())
                .Saldo(movimiento.getSaldo())
                .build();

    }
}
