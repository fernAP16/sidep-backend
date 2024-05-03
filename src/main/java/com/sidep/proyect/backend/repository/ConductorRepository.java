package com.sidep.proyect.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sidep.proyect.backend.model.Conductor;

public interface ConductorRepository extends JpaRepository<Conductor, Long>{
    
    Optional<Conductor> findByClaveDigitalAndUsuario_IdUsuario(String claveDigital, Integer idUsuario);
}
