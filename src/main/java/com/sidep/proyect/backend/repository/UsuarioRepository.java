package com.sidep.proyect.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sidep.proyect.backend.model.Usuario;
import com.sidep.proyect.backend.model.ZonaBalanza;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query(value =  "SELECT * FROM sd_usuario " +
                    "WHERE dni = :username", nativeQuery = true)
    Optional<Usuario> findUserToLogin(@Param("username") String username);

    @Query(value =  "SELECT * FROM sd_zona_balanza " +
                    "WHERE codigo = :username AND contrasena = :password ", nativeQuery = true)
    Optional<ZonaBalanza> findBalanzaToLogin(@Param("username") String username, @Param("password") String password);
}
