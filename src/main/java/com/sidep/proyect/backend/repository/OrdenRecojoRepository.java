package com.sidep.proyect.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.model.OrdenRecojo;

@Repository
public interface OrdenRecojoRepository extends JpaRepository<OrdenRecojo, Long> {

    List<OrdenRecojo> findByConductor_IdConductor(Integer idConductor);

    
}
