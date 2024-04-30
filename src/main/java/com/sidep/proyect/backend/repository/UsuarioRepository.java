package com.sidep.proyect.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sidep.proyect.backend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
