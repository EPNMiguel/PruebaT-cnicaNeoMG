package com.neoris.cuenta.services;

import ch.qos.logback.core.net.server.Client;
import com.neoris.cuenta.entities.Cuenta;
import com.neoris.cuenta.model.dto.CuentaRequest;
import com.neoris.cuenta.model.dto.CuentaResponse;
import com.neoris.cuenta.repositories.CuentaRepository;
import com.neoris.persona.entities.Cliente;
import com.neoris.persona.repositories.ClienteRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaService {

    private final CuentaRepository cuentaRepository;


    public ResponseEntity<Object> addCuenta(CuentaRequest cuentaRequest) {
        try {
            try {
                //traigo cliente con cedula
                URL url = new URL("http://localhost:8084/api/clientes");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                StringBuilder sb = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine());
                }
                if (!sb.isEmpty() && sb.toString().contains(cuentaRequest.getIdentificacion())) {
                    //si hay cliente
                    var cuenta = Cuenta.builder()
                            .numeroCuenta(cuentaRequest.getNumeroCuenta())
                            .tipoCuenta(cuentaRequest.getTipoCuenta())
                            .saldoInicial(cuentaRequest.getSaldoInicial())
                            .estado(cuentaRequest.getEstado())
                            .identificacion(cuentaRequest.getIdentificacion())
                            .build();

                    cuentaRepository.save(cuenta);
                    log.info("Cuenta Creada {}", cuenta);
                    return new ResponseEntity<>(
                            HttpStatus.OK
                    );
                } else {
                    //No hay cliente
                    log.info("Cliente no registrado");
                    return new ResponseEntity<>(
                            HttpStatus.CONFLICT
                    );
                }
            } catch (Exception e) {

                log.error("No se puede crear cuenta " + e);
                return new ResponseEntity<>(
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
            }


        } catch (Exception e) {
            log.error("No se pudo crear cuenta :" + e);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
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
        try {
            Optional<Cuenta> res = cuentaRepository.findById(cuenta.getNumeroCuenta());
            if (!res.isEmpty()) {
                cuenta.setIdentificacion(res.get().getIdentificacion());
                cuentaRepository.save(cuenta);
                return new ResponseEntity<>(
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        HttpStatus.CONFLICT
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
