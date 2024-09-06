package com.neoris.cuenta.repositories;

import com.neoris.cuenta.entities.Cuenta;
import com.neoris.cuenta.model.dto.ReporteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query(value = "SELECT m.fecha as fechaMovimiento, m.id_movimiento as idMovimiento, l.identificacion as identificacionCliente, l.nombre as nombreCliente, c.numero_cuenta as numeroCuenta , c.tipo_cuenta as tipoCuenta, c.saldo as saldo, l.estado as estado, m.tipo_movimiento as tipoMovimiento, m.valor as valor from movimiento m join cuenta c on m.numero_cuenta=c.numero_cuenta join cliente l on l.identificacion = c.identificacion where m.fecha >= :fechaInicio and m.fecha <= :fechaFin", nativeQuery = true)
    List<Object[]> getReporteInfo(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
}
