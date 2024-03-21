package com.neoris.cuenta.services;

import com.neoris.cuenta.entities.Cuenta;
import com.neoris.cuenta.entities.Movimiento;
import com.neoris.cuenta.model.dto.MovimientoRequest;
import com.neoris.cuenta.model.dto.MovimientoResponse;
import com.neoris.cuenta.repositories.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public void addMovimiento(MovimientoRequest movimientoRequest) {
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
        } catch (Exception e) {
            log.error("No se pudo conectar a api/cuentas");
        }
        //FIN
        var movimiento = Movimiento.builder()
                .tipoMovimiento(movimientoRequest.getTipoMovimiento())
                .numeroCuenta(movimientoRequest.getNumeroCuenta())
                .Valor(movimientoRequest.getValor())
                .Saldo(movimientoRequest.getSaldo())
                .build();

        movimientoRepository.save(movimiento);
        log.info("Movimiento creado");
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
