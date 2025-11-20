package com.javanauta.usuario.infrastructure.repository;


import com.javanauta.usuario.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
// Usuario é a entity "tabela"
// Long é id
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    // Para evitar o retorno Null e não cair a api (Ex. Usuario não existe)
    Optional<Usuario>  findByEmail (String email);

    @Transactional
    void deleteByEmail(String email);

}

