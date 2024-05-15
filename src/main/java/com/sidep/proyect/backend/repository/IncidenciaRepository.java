package com.sidep.proyect.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sidep.proyect.backend.model.Incidencia;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long>{
    List<Incidencia> findAll();
}
