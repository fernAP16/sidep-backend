package com.sidep.proyect.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sidep.proyect.backend.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

    
}
