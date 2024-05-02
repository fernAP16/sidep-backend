package com.sidep.proyect.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sidep.proyect.backend.model.OrdenRecojo;
import com.sidep.proyect.backend.repository.OrdenRecojoRepository;

@Service
public class OrdenRecojoService {
    private final OrdenRecojoRepository ordenRecojoRepository;

    public OrdenRecojoService(OrdenRecojoRepository ordenRecojoRepository) {
        this.ordenRecojoRepository = ordenRecojoRepository;
    }

    public List<OrdenRecojo> getOrdenesRecojoPorConductor(Integer idConductor) {
        return ordenRecojoRepository.findByConductor_IdConductor(idConductor);
    }

}