package com.neoris.cuenta.services;

import com.neoris.cuenta.entities.Cuenta;
import com.neoris.cuenta.entities.Movimiento;
import com.neoris.cuenta.model.dto.MovimientoRequest;
import com.neoris.cuenta.model.dto.MovimientoResponse;
import com.neoris.cuenta.repositories.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;

    public ResponseEntity<Object> addMovimiento(MovimientoRequest movimientoRequest) {
        Cuenta cuenta = new Cuenta();
        //INICIO trae una cuenta consumiendo el servicio de cuenta

        try {
            URL url = new URL("http://localhost:8085/api/cuentas");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            scanner.close();
            log.info("RESULTADO: " + sb);
            if (!sb.isEmpty() && sb.toString().contains(movimientoRequest.getNumeroCuenta().toString())) {
                //hay la cuenta
                if (movimientoRequest.getTipoMovimiento().toString().equals("deposito")) {
                    double saldo = 0.0;
                    try {
                        saldo = Double.parseDouble(movimientoRequest.getSaldo().toString());

                    } catch (Exception e) {
                        log.info("no hay saldo");
                        saldo = 0.0;
                    }
                    //si es deposito siempre guarda
                    var movimiento = Movimiento.builder()
                            .tipoMovimiento(movimientoRequest.getTipoMovimiento())
                            .numeroCuenta(movimientoRequest.getNumeroCuenta())
                            .Valor(movimientoRequest.getValor())
                            .Saldo(saldo + movimientoRequest.getValor())
                            .build();
                    movimientoRepository.save(movimiento);
                    log.info("Movimiento creado");
                    return new ResponseEntity<>(
                            HttpStatus.OK
                    );
                } else { //retiro
                    //validar que saldo no quede menor a cero
                    double tempSaldo = movimientoRequest.getSaldo() - movimientoRequest.getValor();
                    if (tempSaldo < 0) {
                        // no guarda
                        log.info("Saldo Final menor a 0, no se puede guardar");
                        return new ResponseEntity<>(
                                HttpStatus.CONFLICT
                        );
                    } else {
                        // guadar y actualiza el saldo
                        var movimiento = Movimiento.builder()
                                .tipoMovimiento(movimientoRequest.getTipoMovimiento())
                                .numeroCuenta(movimientoRequest.getNumeroCuenta())
                                .Valor(movimientoRequest.getValor())
                                .Saldo(tempSaldo)
                                .build();
                        movimientoRepository.save(movimiento);
                        log.info("Movimiento creado");
                        return new ResponseEntity<>(
                                HttpStatus.OK
                        );
                    }
                }


            } else {
                //No hay cuenta
                log.info("No hay numero de cuenta " + movimientoRequest.getNumeroCuenta().toString());
                return new ResponseEntity<>(
                        HttpStatus.CONFLICT
                );
            }
        } catch (Exception e) {
            log.error("No se pudo crear movimiento " + e);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
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
