package com.neoris.cuenta.repositories;

import com.neoris.cuenta.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}
