package com.sidep.proyect.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sidep.proyect.backend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query(value =  "SELECT * FROM sd_usuario " +
                    "WHERE dni = :username", nativeQuery = true)
    Optional<Usuario> findUserToLogin(@Param("username") String username);

}
