package com.neoris.persona.repositories;

import com.neoris.persona.entities.Cliente;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.catalina.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

     Cliente findByIdentificacion(String identificacion);

}
