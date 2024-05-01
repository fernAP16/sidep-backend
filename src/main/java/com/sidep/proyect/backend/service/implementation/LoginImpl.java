package com.sidep.proyect.backend.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.dto.in.LoginInDto;
import com.sidep.proyect.backend.dto.out.LoginOutDto;
import com.sidep.proyect.backend.model.Conductor;
import com.sidep.proyect.backend.model.Revisor;
import com.sidep.proyect.backend.model.Usuario;
import com.sidep.proyect.backend.repository.ConductorRepository;
import com.sidep.proyect.backend.repository.RevisorRepository;
import com.sidep.proyect.backend.repository.UsuarioRepository;
import com.sidep.proyect.backend.service.LoginService;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public class LoginImpl implements LoginService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private RevisorRepository revisorRepository;

    @Override
    public LoginOutDto loginConductor(LoginInDto loginDTO) {
        Optional<Usuario> usuarioLogin = usuarioRepository.findUserToLogin(loginDTO.getUsername());
        if(usuarioLogin.isPresent()){
            Optional<Conductor> conductorLogin = conductorRepository.findByClaveDigitalAndUsuario_IdUsuario(loginDTO.getPassword(), usuarioLogin.get().getIdUsuario());
            if (conductorLogin.isPresent()) {
                return new LoginOutDto("Login Success", true, conductorLogin.get().getIdConductor());
            } else {
                return new LoginOutDto("Login Failed", false, 0);
            }
        } else {
            return new LoginOutDto("Login Failed", false, 0);
        }
    }

    @Override
    public LoginOutDto loginRevisor(LoginInDto loginDTO) {
        Optional<Usuario> usuarioLogin = usuarioRepository.findUserToLogin(loginDTO.getUsername());
        if(usuarioLogin.isPresent()){
            Optional<Revisor> revisorLogin = revisorRepository.findByContrasenaAndUsuario_IdUsuario(loginDTO.getPassword(), usuarioLogin.get().getIdUsuario());
            if (revisorLogin.isPresent()) {
                return new LoginOutDto("Login Success", true, revisorLogin.get().getIdRevisor());
            } else {
                return new LoginOutDto("Login Failed", false, 0);
            }
        } else {
            return new LoginOutDto("Login Failed", false, 0);
        }
    }
}
