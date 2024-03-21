package com.neoris.cuenta.services;

import com.neoris.cuenta.entities.Cuenta;
import com.neoris.cuenta.model.dto.CuentaRequest;
import com.neoris.cuenta.model.dto.CuentaResponse;
import com.neoris.cuenta.repositories.CuentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public void addCuenta(CuentaRequest cuentaRequest) {
        var cuenta = Cuenta.builder()
                .numeroCuenta(cuentaRequest.getNumeroCuenta())
                .tipoCuenta(cuentaRequest.getTipoCuenta())
                .saldoInicial(cuentaRequest.getSaldoInicial())
                .estado(cuentaRequest.getEstado())
                .identificacion(cuentaRequest.getIdentificacion())
                .build();

        cuentaRepository.save(cuenta);
        log.info("Cuenta Creada {}", cuenta);

    }

    public List<CuentaResponse> getAllCuentas() {
        var cuetas = cuentaRepository.findAll();
        return cuetas.stream().map(this::mapToCuentaResponse).toList();
    }

    private CuentaResponse mapToCuentaResponse(Cuenta cuenta) {
        return CuentaResponse.builder()
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .estado(cuenta.getEstado())
                .identificacion(cuenta.getIdentificacion())
                .build();

    }

    public ResponseEntity<Object> updateCuenta(Cuenta cuenta) {
        Optional<Cuenta> res = cuentaRepository.findById(cuenta.getNumeroCuenta());
        if (!res.isEmpty()) {
            cuentaRepository.save(cuenta);
            return new ResponseEntity<>(
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    HttpStatus.CONFLICT
            );
        }
    }
}
