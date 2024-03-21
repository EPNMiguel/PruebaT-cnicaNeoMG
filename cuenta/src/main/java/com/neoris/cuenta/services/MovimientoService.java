package com.neoris.cuenta.services;

import com.neoris.cuenta.entities.Cuenta;
import com.neoris.cuenta.entities.Movimiento;
import com.neoris.cuenta.model.dto.CuentaRequest;
import com.neoris.cuenta.model.dto.MovimientoRequest;
import com.neoris.cuenta.repositories.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;

    public void addMovimiento(MovimientoRequest movimientoRequest) {
        Cuenta cuenta = new Cuenta();
         //INICIO trae una cuenta consumiendo el servicio de cuenta
        


        //FIN
        var movimiento = Movimiento.builder()
                .tipoMovimiento(movimientoRequest.getTipoMovimiento())
                .cuenta(cuenta)

                .Valor(movimientoRequest.getValor())
                .Saldo(movimientoRequest.getSaldo())
                .build();

        movimientoRepository.save(movimiento);
        log.info("Movimiento creado");
    }
}
