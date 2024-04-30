package com.sidep.proyect.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sidep.proyect.backend.model.EstadoOrden;

public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, Long>{
    
}
