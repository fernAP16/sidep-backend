package com.sidep.proyect.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sidep.proyect.backend.model.Revisor;

public interface RevisorRepository extends JpaRepository<Revisor, Long>{
    
    Optional<Revisor> findByContrasenaAndUsuario_IdUsuario(String contra, Integer idUsuario);

}
