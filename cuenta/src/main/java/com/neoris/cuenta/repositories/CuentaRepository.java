package com.neoris.cuenta.repositories;

import com.neoris.cuenta.entities.Cuenta;
import com.neoris.cuenta.model.dto.ReporteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query(value = "SELECT m.fecha, m.id_movimiento from movimiento m join cuenta c on m.numero_cuenta=c.numero_cuenta", nativeQuery = true)
    public List<ReporteResponse> getReporteInfo();
}
