package com.sidep.proyect.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.model.Usuario;
import com.sidep.proyect.backend.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/usuario")
public class UsuarioController{
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/listAll")
    public List<Usuario> getAllUsuario() {
        return usuarioRepository.findAll();
    }
    

}