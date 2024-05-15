package com.sidep.proyect.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.sidep.proyect.backend.model.Despacho;

import java.util.Optional;


public interface DespachoRepository extends JpaRepository<Despacho, Long>{

    Optional<Despacho> findByIdDespacho(@Param("idDespacho") Integer idDespacho);
}
