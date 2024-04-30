package com.sidep.proyect.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

import com.sidep.proyect.backend.model.Conductor;


public interface ConductorRepository extends JpaRepository<Conductor, Long>{

    // @Query(value = 
    // "SELECT * FROM products " +
    // "WHERE dni = ?1 " + 
    // "AND contrasena = ?2", nativeQuery = true)
    // Conductor authenticateConductor(String dni, String contrasena);
    
}
