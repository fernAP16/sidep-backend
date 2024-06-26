package com.sidep.proyect.backend.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.dto.in.LoginInDto;
import com.sidep.proyect.backend.dto.out.LoginOutDto;
import com.sidep.proyect.backend.model.Conductor;
import com.sidep.proyect.backend.model.Revisor;
import com.sidep.proyect.backend.model.Usuario;
import com.sidep.proyect.backend.model.ZonaBalanza;
import com.sidep.proyect.backend.repository.ConductorRepository;
import com.sidep.proyect.backend.repository.RevisorRepository;
import com.sidep.proyect.backend.repository.UsuarioRepository;
import com.sidep.proyect.backend.repository.ZonaBalanzaRepository;
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

    @Autowired
    private ZonaBalanzaRepository zonaBalanzaRepository;

    @Override
    public LoginOutDto loginConductor(LoginInDto loginDTO) {
        Optional<Usuario> usuarioLogin = usuarioRepository.findUserToLogin(loginDTO.getUsername());
        if(usuarioLogin.isPresent()){
            Optional<Conductor> conductorLogin = conductorRepository.findByClaveDigitalAndUsuario_IdUsuario(loginDTO.getPassword(), usuarioLogin.get().getIdUsuario());
            if (conductorLogin.isPresent()) {
                String nombres = usuarioLogin.get().getNombres().split(" ")[0] + " " + usuarioLogin.get().getPrimerApellido();
                return new LoginOutDto("Login Success", true, conductorLogin.get().getIdConductor(), nombres);
            } else {
                return new LoginOutDto("Login Failed", false, 0, "");
            }
        } else {
            return new LoginOutDto("Login Failed", false, 0, "");
        }
    }

    @Override
    public LoginOutDto loginRevisor(LoginInDto loginDTO) {
        Optional<Usuario> usuarioLogin = usuarioRepository.findUserToLogin(loginDTO.getUsername());
        if(usuarioLogin.isPresent()){
            Optional<Revisor> revisorLogin = revisorRepository.findByContrasenaAndUsuario_IdUsuario(loginDTO.getPassword(), usuarioLogin.get().getIdUsuario());
            if (revisorLogin.isPresent()) {
                String nombres = usuarioLogin.get().getNombres().split(" ")[0] + " " + usuarioLogin.get().getPrimerApellido();
                return new LoginOutDto("Login Success", true, revisorLogin.get().getIdRevisor(), nombres);
            } else {
                return new LoginOutDto("Login Failed", false, 0, "");
            }
        } else {
            return new LoginOutDto("Login Failed", false, 0, "");
        }
    }

    @Override
    public LoginOutDto loginBalanza(LoginInDto inDto){
        Optional<ZonaBalanza> balanzaLogin = zonaBalanzaRepository.findBalanzaToLogin(inDto.getUsername(), inDto.getPassword());
        if(balanzaLogin.isPresent()){
            String nombres = balanzaLogin.get().getCodigo();
            return new LoginOutDto("Login Success", true, balanzaLogin.get().getIdZonaBalanza(), nombres);
            
        } else {
            return new LoginOutDto("Login Failed", false, 0, "");
        }
    }
}
