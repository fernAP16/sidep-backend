package com.sidep.proyect.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sidep.proyect.backend.model.Planta;

public interface PlantaRepository extends JpaRepository<Planta, Long>{
}
