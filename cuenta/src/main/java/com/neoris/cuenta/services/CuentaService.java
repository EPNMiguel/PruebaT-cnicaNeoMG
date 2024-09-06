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


    public String addCuenta(CuentaRequest cuentaRequest) {
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
                    URL urlC = new URL("http://localhost:8085/api/cuentas");
                    HttpURLConnection connC = (HttpURLConnection) urlC.openConnection();
                    connC.setRequestMethod("GET");
                    connC.connect();
                    StringBuilder sbC = new StringBuilder();
                    Scanner scannerC = new Scanner(urlC.openStream());
                    while (scannerC.hasNext()) {
                        sbC.append(scannerC.nextLine());
                    }
                    if (sbC.isEmpty() || sbC.toString().contains(cuentaRequest.getNumeroCuenta().toString())) {
                        //ya existe la cuenta
                        log.info("Ya existe la cuenta");
                        return "{\"message\":\"Ya existe la cuenta\",\"code\":1}";
                    } else {
                        var cuenta = Cuenta.builder()
                                .numeroCuenta(cuentaRequest.getNumeroCuenta())
                                .tipoCuenta(cuentaRequest.getTipoCuenta())
                                .saldo(cuentaRequest.getSaldo())
                                .estado(cuentaRequest.getEstado())
                                .identificacion(cuentaRequest.getIdentificacion())
                                .build();

                        cuentaRepository.save(cuenta);
                        log.info("Cuenta Creada {}", cuenta.getNumeroCuenta());
                        return "{\"message\":\"Cuenta " + cuenta.getNumeroCuenta() + " creada\",\"code\":0}";
                    }
                } else {
                    //No hay cliente
                    log.info("Cliente no registrado");
                    return "{\"message\":\"Cliente no registrado\",\"code\":1}";
                }
            } catch (Exception e) {

                log.error("No se puede crear cuenta " + e);
                return "{\"message\":\"No se puede crear cuenta " + e + "\",\"code\":1}";
            }


        } catch (Exception e) {
            log.error("No se pudo crear cuenta :" + e);
            return "{\"message\":\"No se puede crear cuenta " + e + "\",\"code\":1}";
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
                .saldo(cuenta.getSaldo())
                .estado(cuenta.getEstado())
                .identificacion(cuenta.getIdentificacion())
                .build();

    }

    public String updateCuenta(Cuenta cuenta) {
        try {
            Optional<Cuenta> res = cuentaRepository.findById(cuenta.getNumeroCuenta());
            if (!res.isEmpty()) {
                cuenta.setIdentificacion(res.get().getIdentificacion());
                cuentaRepository.save(cuenta);
                return "{\"message\":\"Actualizado con éxito\",\"code\":0}";
            } else {
                log.error("No existe la cuenta a actualizar");
                return "{\"message\":\"No existe la cuenta a actualizar\",\"code\":0}";
            }
        } catch (Exception e) {
            log.error("No se pudo actualizar la cuenta " + e);
            return "{\"message\":\"\"No se pudo actualizar la cuenta " + e + "\",\"code\":0}";
        }
    }
}
