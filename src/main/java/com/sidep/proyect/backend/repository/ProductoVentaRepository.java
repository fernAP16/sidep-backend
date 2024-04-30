package com.sidep.proyect.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sidep.proyect.backend.model.ProductoVenta;

public interface ProductoVentaRepository extends JpaRepository<ProductoVenta, Long>{
    
}
